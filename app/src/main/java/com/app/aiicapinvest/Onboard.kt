package com.app.aiicapinvest


import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.app.aiicapinvest.adapters.Slider
import kotlinx.android.synthetic.main.fragment_onboard.*


class Onboard : AppCompatActivity(){

    lateinit var sliderAdapter: Slider

    var _currentpage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_onboard)
        setupViews()
    }

    private fun setupViews(){
        sliderAdapter = Slider(this)

        view_pager.adapter = sliderAdapter
        view_pager.addOnPageChangeListener(listener)

        addDotsIndicator(0)

        skip.setOnClickListener{
            Intent(this@Onboard, SignUp::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    fun addDotsIndicator(pos: Int) {
        val dots = arrayOfNulls<TextView>(3)
        dotslayout.removeAllViews()

        for (i in dots.indices){
            dots[i] = TextView(this).apply {
                text = Html.fromHtml("&#8226")
                textSize = 35f
                setTextColor(resources.getColor(R.color.transparentwhite))
            }

            dotslayout.addView(dots[i])
        }

        if(dots.isNotEmpty()){
            dots[pos]?.setTextColor(resources.getColor(R.color.white))
        }
    }

    private val listener = object: ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ){}

        override fun onPageSelected(position: Int) {
            addDotsIndicator(position)

            _currentpage = position

            if(_currentpage == 2)
                skip.text = getString(R.string.get_started)
            else
                skip.text = getString(R.string.skip)
        }

    }

}
