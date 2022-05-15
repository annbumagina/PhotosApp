package com.annbumagina.photosapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


const val EXTRA_URL = "com.annbumagina.photosapp.extra.URL"
const val EXTRA_DESC = "com.annbumagina.photosapp.extra.DESC"
const val EXTRA_IDS = "com.annbumagina.photosapp.extra.IDS"
const val EXTRA_TITLE = "com.annbumagina.photosapp.extra.TITLE"


class ImageList : Fragment() {
    private var title: ArrayList<String> = ArrayList()
    private var links: ArrayList<String> = ArrayList()
    private var ids: ArrayList<Int> = ArrayList()
    private var have = false
    private var description: ArrayList<String> = ArrayList()
    private lateinit var moshi: Moshi
    private val handler = Handler(Looper.getMainLooper())
    lateinit var api: PhotosApi
    private lateinit var myPhotos: TextView
    private lateinit var search: EditText
    private lateinit var recyclerView: RecyclerView

    private val onClick = { id: Int ->
        if (activity?.findViewById<View>(R.id.fragment_content) != null) {
            val bundle = Bundle()
            bundle.putString(EXTRA_URL, links[id])
            bundle.putString(EXTRA_DESC, description[id])
            bundle.putString(EXTRA_TITLE, title[id])
            bundle.putInt(EXTRA_IDS, ids[id])
            val fragobj = ImageDetailFragment()
            fragobj.arguments = bundle

            activity?.supportFragmentManager?.beginTransaction()?.let {
                it.replace(R.id.fragment_content, fragobj)
                it.commit()
            }
        } else {
            val intent = Intent(context, ImageDetailActivity::class.java)
            intent.putExtra(EXTRA_URL, links[id])
            intent.putExtra(EXTRA_DESC, description[id])
            intent.putExtra(EXTRA_TITLE, title[id])
            intent.putExtra(EXTRA_IDS, ids[id])
            startActivity(intent)
        }
    }
    var adapter: ListAdapter = ListAdapter(onClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.image_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search = view.findViewById(R.id.search)
        myPhotos = view.findViewById(R.id.myPhotos)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        moshi = Moshi.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        api = retrofit.create(PhotosApi::class.java)

        search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val expr = search.text.toString()
                    val photos = api.getPhotos(expr)
                    photos.enqueue(object : Callback<Photos> {
                        override fun onResponse(call: Call<Photos>, response: Response<Photos>) {
                            if (response.isSuccessful && response.body() != null) {
                                handler.post {
                                    setJSON(response.body()!!)
                                }
                            }
                        }

                        override fun onFailure(call: Call<Photos>, t: Throwable) {
                        }
                    })
                    return true
                }
                return false
            }
        })

        val dbHelper = (activity?.application as PhotosApp).dbHelper
        myPhotos.setOnClickListener {
            dbHelper.restoreData(title, links, description, ids)
            adapter.clear()
            for (i in title.indices) {
                adapter.setElement(i, title[i])
            }
        }

        if (savedInstanceState?.getStringArrayList(EXTRA_TITLE) != null) {
            title = savedInstanceState.getStringArrayList(EXTRA_TITLE) ?: title
            links = savedInstanceState.getStringArrayList(EXTRA_URL) ?: links
            description = savedInstanceState.getStringArrayList(EXTRA_DESC) ?: description
            ids = savedInstanceState.getIntegerArrayList(EXTRA_IDS) ?: ids
            adapter.clear()
            for (i in title.indices) {
                adapter.setElement(i, title[i])
            }
            have = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (have) {
            outState.putStringArrayList(EXTRA_TITLE, title)
            outState.putStringArrayList(EXTRA_URL, links)
            outState.putStringArrayList(EXTRA_DESC, description)
            outState.putIntegerArrayList(EXTRA_IDS, ids)
        }
        super.onSaveInstanceState(outState)
    }

    fun setJSON(result: Photos) {
        val items = result.hits
        title.clear()
        links.clear()
        description.clear()
        ids.clear()
        adapter.clear()
        for (i in items.indices) {
            links.add(items[i].webformatURL)
            description.add(items[i].tags)
            title.add(items[i].tags)
            adapter.setElement(i, title[i])
            ids.add(-1)
        }
        have = true
    }
}