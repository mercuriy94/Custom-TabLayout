package com.smedialink.nmarsyukov.customtablayout

import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.custom_tab.view.*

class SampleTabListener : TabLayout.OnTabSelectedListener {

    private fun updateTabs(tabLayout: TabLayout) {

        // Перебираем все табы, чтобы применить к ним цветовые схемы
        for (i in 0 until tabLayout.tabCount) {

            tabLayout.getTabAt(i)?.let { tab ->

                if (tab.customView == null) {
                    val tabView = LayoutInflater.from(tabLayout.context)
                            .inflate(R.layout.custom_tab, tabLayout, false)
                    tab.customView = tabView
                }

                tab.customView?.apply {

                    tvTabTitle.text = tabLayout.context.getString(R.string.page_number, (i + 1))

                    when {
                        i < tabLayout.selectedTabPosition -> {
                            rootTabLayout.setBackgroundColor(ContextCompat.getColor(tabLayout.context,
                                    R.color.colorTabBack))
                        }
                        i == tabLayout.selectedTabPosition -> {
                            rootTabLayout.setBackgroundColor(ContextCompat.getColor(tabLayout.context,
                                    R.color.colorTabSelected))
                        }
                        else -> {
                            rootTabLayout.setBackgroundColor(ContextCompat.getColor(tabLayout.context,
                                    R.color.colorTabNext))
                        }
                    }
                }
            }
        }

    }

    //region TabLayout.OnTabSelectedListener

    override fun onTabReselected(p0: TabLayout.Tab) = updateTabs(p0.parent)

    override fun onTabUnselected(p0: TabLayout.Tab) {
        //do nothing
    }

    override fun onTabSelected(p0: TabLayout.Tab) = updateTabs(p0.parent)

    //endregion TabLayout.OnTabSelectedListener


}