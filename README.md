# Custom-TabLayout

### Для чего он?

Данный проект предназначен для демонстриации способа кастомизации TabLayout.
Как например на гифке ниже.

![](https://github.com/mercuriy94/Custom-TabLayout/blob/master/images/sample.gif?raw=true)

### В чем соль?

Не для кого не секрет, что используя TabLayout в связке с ViewPager, то неявно создаются объекты - Tab-ы. 

~~~ java

...
    public static class Tab {
        public static final int INVALID_POSITION = -1;
        private Object tag;
        private Drawable icon;
        private CharSequence text;
        private CharSequence contentDesc;
        private int position = -1;
        private View customView;
        public TabLayout parent;
        public TabLayout.TabView view;
      
      ...
      
           @Nullable
        public View getCustomView() {
            return this.customView;
        }

        @NonNull
        public TabLayout.Tab setCustomView(@Nullable View view) {
            this.customView = view;
            this.updateView();
            return this;
        }
        
      ...
  
    }
...
  
~~~

Выше представлены поля класса Tab. А также разработчики оставили нам два метода `getCustomView` и `setCustomView`. Именно customView позволяет нам без труда подставить свою вьюху для представления таба, чтобы tab layout соответствовал дизайну установленного вашим проектом.

### Окей, а как реализовать?

В данном примере представлена лишь одна из возможных вариантов реализации. 
Главной задачей является управлением представлением таба в зависимости от его состояния. 
Для этого хорошо подходит слушатель - TabLayout.OnTabSelectedListener. 
Остается его реализовать.

~~~ java
...
  
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

~~~

Как видно, кастомизация табов дело весьма простое.
В  данном примере метод `updateTabs` обновляет цветовую схему табов  с учетом их позиций, но вы можете легко реализовать свою логику состояния для отображения.

На этом все! Удачи!
