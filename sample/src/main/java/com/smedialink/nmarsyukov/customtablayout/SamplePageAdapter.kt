package com.smedialink.nmarsyukov.customtablayout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SamplePageAdapter(
        fragmentManager: FragmentManager,
        private val list: List<Fragment>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment = list[p0]

    override fun getPageTitle(position: Int): CharSequence = (position + 1).toString()

    override fun getCount(): Int = list.count()

}