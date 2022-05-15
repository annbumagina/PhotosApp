package com.annbumagina.photosapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils

class ImageDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val bundle = Bundle()
        bundle.putString(EXTRA_URL, intent.getStringExtra(EXTRA_URL))
        bundle.putString(EXTRA_DESC, intent.getStringExtra(EXTRA_DESC))
        bundle.putString(EXTRA_TITLE, intent.getStringExtra(EXTRA_TITLE))
        bundle.putInt(EXTRA_IDS, intent.getIntExtra(EXTRA_IDS, -1))


        val fragobj = ImageDetailFragment()
        fragobj.arguments = bundle
        supportFragmentManager.beginTransaction().let {
            it.replace(R.id.activity_fragment_content, fragobj)
            it.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = NavUtils.getParentActivityIntent(this)
            intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            NavUtils.navigateUpTo(this, intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}