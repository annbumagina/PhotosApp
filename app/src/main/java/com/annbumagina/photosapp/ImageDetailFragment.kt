package com.annbumagina.photosapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso


class ImageDetailFragment : Fragment() {

    private var desc: String? = null
    private var link: String? = null
    private var dbId: Int = 0
    private var title: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            desc = it.getString(EXTRA_DESC)
            link = it.getString(EXTRA_URL)
            dbId = it.getInt(EXTRA_IDS)
            title = it.getString(EXTRA_TITLE)
        }
        return inflater.inflate(R.layout.image_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView2 = view.findViewById<TextView>(R.id.textView2)
        val btn = view.findViewById<Button>(R.id.btn)
        val imageDetailView = view.findViewById<ImageView>(R.id.imageDetailView)


        if (desc != null) {
            textView2.text = desc
            Picasso.get().load(link).into(imageDetailView)
            val dbHelper = (activity?.application as PhotosApp).dbHelper

            if (dbId >= 0) {
                btn.text = "Remove"
            } else {
                btn.text = "Add"
            }
            btn.setOnClickListener {
                if (dbId >= 0) {
                    dbHelper.deleteElement(dbId)
                } else {
                    dbHelper.addElement(title, desc, link)
                }
            }
        }
    }
}