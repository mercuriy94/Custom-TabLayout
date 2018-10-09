package android.support.design.widget

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.smedialink.nmarsyukov.customtablayout.R

sealed class TabState {

    object BackPositionState : TabState() {

        override fun getColors(
                context: Context,
                position: Int,
                selectedPosition: Int,
                tabCount: Int
        ): TabColors =
                TabColors(
                        tabColor = ContextCompat.getColor(context, R.color.colorOrderTabBack),
                        dividerColor = Color.WHITE,
                        textColor = Color.WHITE,
                        endTriangleFillColor = if (selectedPosition == position + 1) {
                            ContextCompat.getColor(context, R.color.colorOrderTabCurrent)
                        } else {
                            ContextCompat.getColor(context, R.color.colorOrderTabBack)
                        }
                )
    }

    object SelectedPositionState : TabState() {

        override fun getColors(
                context: Context,
                position: Int,
                selectedPosition: Int,
                tabCount: Int
        ): TabColors {

            val dividerColor: Int
            val endTriangleFillColor: Int

            if (position == tabCount - 1) {
                dividerColor = ContextCompat.getColor(context, R.color.colorOrderTabCurrent)
                endTriangleFillColor = ContextCompat.getColor(context, R.color.colorOrderTabCurrent)
            } else {
                dividerColor = ContextCompat.getColor(context, R.color.colorOrderTabNext)
                endTriangleFillColor = ContextCompat.getColor(context, R.color.colorOrderTabNext)
            }

            return TabColors(
                    tabColor = ContextCompat.getColor(context, R.color.colorOrderTabCurrent),
                    textColor = Color.WHITE,
                    dividerColor = dividerColor,
                    endTriangleFillColor = endTriangleFillColor

            )

        }

    }


    object FollowingPositionState : TabState() {
        override fun getColors(
                context: Context,
                position: Int,
                selectedPosition: Int,
                tabCount: Int
        ): TabColors =
                TabColors(
                        tabColor = ContextCompat.getColor(context, R.color.colorOrderTabNext),
                        endTriangleFillColor = ContextCompat.getColor(context, R.color.colorOrderTabNext),
                        textColor = ContextCompat.getColor(context, R.color.colorOrderTabTextNext),
                        dividerColor = if (position == tabCount - 1) {
                            ContextCompat.getColor(context, R.color.colorOrderTabNext)
                        } else {
                            ContextCompat.getColor(context, R.color.colorOrderTabNextDivider)
                        }

                )

    }


    /**
     * Метод предназначен, что вернуть цветовую схему для таба,
     * которая соответствует текущей позиции.
     *
     * @param context
     * @param position - текущая позиция таба
     * @param selectedPosition  - текущая выбранная позиция
     * @param tabCount - кол-во табов
     *
     * @return цветоая схему для заполнения
     *
     * */
    abstract fun getColors(
            context: Context,
            position: Int,
            selectedPosition: Int,
            tabCount: Int
    ): TabColors
}


data class TabColors(
        val tabColor: Int,
        val dividerColor: Int,
        val textColor: Int,
        val endTriangleFillColor: Int
)