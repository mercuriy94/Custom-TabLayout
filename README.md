# Custom-TabLayout

### Для чего он?

Данный проект предназначен для демонстрации способа кастомизации TabLayout.
Как например на гифке ниже.

![](https://github.com/mercuriy94/Custom-TabLayout/blob/master/images/sample.gif?raw=true)

### В чем соль?

![](http://pavon.kz/cache/normal/media/img/gallery/source/1757/1397196341_13958330342959.jpeg)

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

Выше представлены поля класса Tab. И я не просто так оставил два методы `getCustomView` и `setCustomView`. Именно customView позволяет нам без труда подставить свою вьюху, чтобы tab layout соответствовал дизайну.

### Окей, а как реализовать?

На самом деле варианты реализации ограничиваются лишь вашим воображением. Но давайте я в кратце расскажу как я это реализовал в своем примере.
 
Первым делом создадим класс CustomTabLayout и и унаследуем его от TabLayout. 
Мы с вами значем, что в  api базовго класса есть  публичный метод addTab, давайте его расширим. 
Получаем примерно такой метод 

~~~ java
...
  
  override fun addTab(tab: Tab, position: Int, setSelected: Boolean) {
        super.addTab(tab, position, setSelected)

        //Передаем кастомную вьюху, которая будет отображаться вместо дефолтной
        tab.customView = CustomTabView(context)
        //принидительно обновляем табы
        updateTabs()

    }

...
~~~

По сути мы просто передали нашу кастомную вьюху (вы можете раздуть вашу любую вьюху например из ресурсов layout).

Окей, а как быть с переключением табов, как понять когда надо перерисовать наши кастомные табы?

Изучив класс TabLayout я обнаружил метод `selectTab` . Он вызывается всегда когда позиция выбранного таба изменилась. Но есть один ньюанс! Он имеет дефолтный модифкатор доступа. Уууупс!

Решение -  переместить наш кастомный таб в пакет в котором и находится базовый TabLayout. 

Тут вы должны задаться вопросом? WTF! Никита, что ты творишь????

![](https://alicegellmdia5003.files.wordpress.com/2015/05/what_meme.jpg)

Но одну секунду. Давайте взглянем и проанализируем  откуда вызывается метод  `selectTab` .  Данный метод вызывается из четырёх мест, начнем по порядку: 

1.  Знакомый слушатель?

~~~ java

  public static class TabLayoutOnPageChangeListener implements OnPageChangeListener {
  ....

        public void onPageSelected(int position) {
            TabLayout tabLayout = (TabLayout)this.tabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position && position < tabLayout.getTabCount()) {
                boolean updateIndicator = this.scrollState == 0 || this.scrollState == 2 && this.previousScrollState == 0;
                tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator);
            }

        }
...
    }

~~~

Когда мы передаем ViewPager в адаптер методом  `setupWithViewPager`, под капотом происходит добавление данного слушателя. При изменении страницы и вызывается наш необходимый метод. Окей, пока все законно, а что с другими вызывами?

2. Когда мы удаляем какой-либо выбранный таб методом `removeTabAt`.  

~~~ java

    public void removeTabAt(int position) {

        ...
          
        if (selectedTabPosition == position) {
            this.selectTab(this.tabs.isEmpty() ? null : (TabLayout.Tab)this.tabs.get(Math.max(0, position - 1)));
        }

    }

~~~

Хорошо, вроде тоже все нормально и под нашим контролем.

3.  Внутри метода `populateFromPagerAdapter`.
Тут  немного цепочка длинее, но тоже вполне логична. Опять же при добавлении ViewPager, TabLayout вешает слушатель  `PagerAdapterObserver`. Тут признаюсь, я не могу с увереностью сказать в каких случаях вызываются методы данного наблюдателя. Но в докуменатции пишут, что  соответствующие методы вызываются когда набор данных был изменен или стал недействительным. На практике я так и не смог спровоцировать вызовы методов этого слушателя. Но они в свою очередь вызывают  `populateFromPagerAdapter`. Если кто-нибудь может что нибудь добавить по этому слушателю, то можете оставить issue.

4. Внутри метода `select` класса Tab. Например когда происходит программное назначение таба текущим выбранным. Все логично!

![](http://memesmix.net/media/created/ktk2te.jpg)

Теперь когда мы разобрались, в каких случаях вызывается метод `selectTab`.  То можно взглянуть на пример его расширения. 

~~~ java

    override fun selectTab(tab: Tab?, updateIndicator: Boolean) {
        super.selectTab(tab, updateIndicator)
        updateTabs()
    }

~~~

В  данном примере метод `updateTabs` обновляет цветовую схему табов  с учетом их позиций, но вы можете легко реализовать свою логику состояния для отображения.

Вот и все! Удачи!

![](http://i.imgur.com/OL1Cf.jpg)
