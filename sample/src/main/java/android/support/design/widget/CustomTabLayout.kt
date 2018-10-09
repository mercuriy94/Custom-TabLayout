package android.support.design.widget

import android.content.Context
import android.util.AttributeSet
import com.smedialink.nmarsyukov.customtablayout.customview.CustomTabView

class CustomTabLayout : TabLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun addTab(tab: Tab, position: Int, setSelected: Boolean) {
        super.addTab(tab, position, setSelected)

        //Передаем кастомную вьюху, которая будет отображаться вместо дефолтной
        tab.customView = CustomTabView(context)
        //принидительно обновляем табы
        updateTabs()

    }

    override fun selectTab(tab: Tab?, updateIndicator: Boolean) {
        super.selectTab(tab, updateIndicator)
        updateTabs()
    }

    private fun updateTabs() {

        // Перебираем все табы, чтобы применить к ним цветовые схемы
        for (i in 0 until tabCount) {

            getTabAt(i)?.let { tab ->

                tab.customView?.apply {

                    if (this is CustomTabView) {

                        text = viewPager?.adapter?.getPageTitle(i).toString()

                        // Определяем цветовую схему которая соответствует позиции таба
                        val colors = when {
                            i < selectedTabPosition -> TabState.BackPositionState
                            i == selectedTabPosition -> TabState.SelectedPositionState
                            else -> TabState.FollowingPositionState
                        }.getColors(context, i, selectedTabPosition, tabCount)

                        tabColor = colors.tabColor
                        dividerColor = colors.dividerColor
                        endTriangleFillColor = colors.endTriangleFillColor
                        textColor = colors.textColor

                    }
                }
            }
        }

    }

}