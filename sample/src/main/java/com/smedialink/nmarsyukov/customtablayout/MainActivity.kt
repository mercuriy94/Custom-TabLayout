package com.smedialink.nmarsyukov.customtablayout

import android.database.DataSetObserver
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ViewPager>(R.id.viewPager)
                .apply {
                    adapter = SamplePageAdapter(
                            supportFragmentManager,
                            listOf(
                                    SampleFragmentPage.newInstance(1),
                                    SampleFragmentPage.newInstance(2),
                                    SampleFragmentPage.newInstance(3),
                                    SampleFragmentPage.newInstance(4)
                            )

                    ).apply {

                        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                            override fun onPageScrollStateChanged(p0: Int) {
                                var i = 0;
                            }

                            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                                var i = 0; }

                            override fun onPageSelected(p0: Int) {
                                var i = 0;
                            }

                        })

                    }

                    customTabLayout.setupWithViewPager(this)


                }


    }
}
