package com.example.tablayout;

import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Opcion1 :En este ejercicio se ha visto como crear un tablayout de forma independiente al elemento viewpager
 *      para ellos hay que implementar el listener en tablayout y el listener en viewpager
 *      y vincularlos de forma que se actualice el otro elemento.
 *
 * Opcion2: Vincular el tablayout al viewpager con el metodo stupWithViewPager()
 *      se debe cumplir unicamente el siguiente requisito:
 *          el titulo de las pestañas del tab se iniciliza mediante el elemento getPageTitle()
 *
 *
 *
 * Ayudas --
 * link efectos viewpager:
 *      https://developer.android.com/training/animation/screen-slide.html
 *
 * link Typedarray para iconos en el tab:
 *      https://stackoverflow.com/questions/6945678/storing-r-drawable-ids-in-xml-array
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TypedArray tabIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabIcons = getResources().obtainTypedArray(R.array.tabIcons);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Opcion1 viewPager=findViewById(R.id.viewPager);
        //Opcion1 viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        /**
         * Configurar TabLayout
         * para que un tab se pueda mover sea seleccionade hay que hacer setabmode
         */
        tabLayout = findViewById(R.id.tabLayout);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); // permite movernos por las pestañas del tab

        // Añadir el titulo las tabs mediante un array-string
        for (String title:getResources().getStringArray(R.array.tabs)){
            //tabLayout.addTab(tabLayout.newTab().setText(title));
            tabLayout.addTab(tabLayout.newTab());
        }

        // configurar tablayout
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.tabs)))));
        viewPager.setPageTransformer(true,new ZoomOutSlideTransformer());

        /*Opcion2*/
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons(); // Si se quiere personalizar el titulo de las tabs con imagenes

        /* Opcion1
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position,0F,true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

    }

    private void setupTabIcons(){
        // Si se quiere personalizar el titulo de las tabs con imagenes
        for(int i=0; i<tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setIcon(tabIcons.getResourceId(i,-1));
        }
    }

    @Override
    public void onBackPressed() { // ir hacia atras en el orden de las pestañas
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
