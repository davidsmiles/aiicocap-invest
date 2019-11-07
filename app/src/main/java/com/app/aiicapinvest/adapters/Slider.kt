package com.app.aiicapinvest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.app.aiicapinvest.R


class Slider(internal var context: Context): PagerAdapter(){

    lateinit var inflater: LayoutInflater

    var slide_images = intArrayOf(R.drawable.onboard5, R.drawable.onboard3_vint, R.drawable.onboard4)

    var slide_descs = arrayOf(
        "No fees to set up your investments or maintain them.",
        "Risk-free investments guaranteed.",
        "Request Withdrawal anytime."
    )

    override fun getCount(): Int {
        return slide_images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.slide_layout, container, false)

        view.background = ContextCompat.getDrawable(context, slide_images[position])
        val desc = view.findViewById(R.id.desc) as TextView

        desc.text = slide_descs[position]

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}