package com.app.aiicapinvest


import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.app.aiicapinvest.adapters.Slider
import kotlinx.android.synthetic.main.fragment_onboard.*
import java.io.File
import java.io.FileReader


class Onboard : Fragment(){

    lateinit var navController: NavController
    lateinit var sliderAdapter: Slider

    var _currentpage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?{

        val view = inflater.inflate(R.layout.fragment_onboard, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        setupViews()
    }

    private fun setupViews(){
        sliderAdapter = Slider(context!!)

        view_pager.adapter = sliderAdapter
        view_pager.addOnPageChangeListener(listener)

        addDotsIndicator(0)

        skip.setOnClickListener{
            navController.navigate(R.id.action_onboard_to_signUp, null,
                NavOptions.Builder().setPopUpTo(R.id.onboard, true).build())
        }
    }

    fun addDotsIndicator(pos: Int) {
        val dots = arrayOfNulls<TextView>(3)
        dotslayout.removeAllViews()

        for (i in dots.indices){
            dots[i] = TextView(context).apply {
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
