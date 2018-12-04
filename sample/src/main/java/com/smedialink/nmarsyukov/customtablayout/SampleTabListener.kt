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

                //Устанавливем камтомную вью, если этого небыло сделанно ранее
                if (tab.customView == null) {


                    val tabView = LayoutInflater.from(tabLayout.context)
                            .inflate(R.layout.custom_tab, tabLayout, false)
                    tab.customView = tabView

                }

                tab.customView?.apply {

                    tvTabTitle.text = tabLayout.context.getString(R.string.page_number, (i + 1))

                    when {
                        //Если позиция таба меньше выбранного
                        i < tabLayout.selectedTabPosition -> {
                            rootTabLayout.setBackgroundColor(ContextCompat.getColor(tabLayout.context,
                                    R.color.colorTabBack))
                        }

                        //Если таб явялется выбранным
                        i == tabLayout.selectedTabPosition -> {
                            rootTabLayout.setBackgroundColor(ContextCompat.getColor(tabLayout.context,
                                    R.color.colorTabSelected))
                        }

                        //Если таб по позиции расположен выше выбранного
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