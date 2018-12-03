package com.smedialink.nmarsyukov.customtablayout

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager()
    }

    private fun setupViewPager() {

        customTabLayout.addOnTabSelectedListener(SampleTabListener())

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

                    )

                    customTabLayout.setupWithViewPager(this)
                }

        val startPage = 0

        viewPager.currentItem = startPage
        customTabLayout.getTabAt(startPage)?.select()

    }

}
