package com.yarolegovich.slidingrootnav.sample;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.sample.fragment.FreeRidesFragment;
import com.yarolegovich.slidingrootnav.sample.fragment.HelpFragment;
import com.yarolegovich.slidingrootnav.sample.fragment.MapViewFragment;
import com.yarolegovich.slidingrootnav.sample.fragment.PaytmFragment;
import com.yarolegovich.slidingrootnav.sample.fragment.RideHistroyFragment;
import com.yarolegovich.slidingrootnav.sample.menu.DrawerAdapter;
import com.yarolegovich.slidingrootnav.sample.menu.DrawerItem;
import com.yarolegovich.slidingrootnav.sample.menu.SimpleItem;

import java.util.Arrays;



public class SampleActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_CART = 3;
    private static final int POS_HELP = 4;
    private static final int POS_SOS = 5;
    private static final int POS_refer = 6;


    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    private android.support.v4.app.Fragment fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_CART),
                createItemFor(POS_HELP),
                createItemFor(POS_SOS),
                createItemFor(POS_refer)
                ));

        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public void onItemSelected(int position) {

        slidingRootNav.closeMenu();
        if(position==0) {
            fragment = new MapViewFragment();
        }else if(position==1){
            fragment = FreeRidesFragment.createFor(screenTitles[1]);
        }else if(position==2) {
            fragment = RideHistroyFragment.createFor(screenTitles[2]);
        }else if(position==3) {
            fragment = PaytmFragment.createFor(screenTitles[3]);
        }else if(position==4) {
            fragment = HelpFragment.createFor(screenTitles[4]);
        }else if(position==5) {
            fragment = HelpFragment.createFor(screenTitles[5]);
        }else if(position==6) {
            fragment = HelpFragment.createFor(screenTitles[6]);
        }
        showFragment(fragment);
    }

    private void showFragment(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}
