package com.choicespropertysolutions.desta;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicespropertysolutions.desta.Adapter.DrawerAdapter;
import com.choicespropertysolutions.desta.SessionManager.SessionManager;
import com.choicespropertysolutions.desta.model.DrawerItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    SessionManager sessionManager;
    TextView lblName;
    TextView lblPhone;
    RecyclerView listItems;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawer;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    DrawerAdapter drawerAdapter;

    public ArrayList<DrawerItems> itemArrayList;
    public ArrayList<DrawerItems> itemSelectedArrayList;

    @Override
    public void setContentView(int layoutResID) {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer, null);
        frameLayout = (FrameLayout) drawer.findViewById(R.id.contentFrame);
        linearLayout = (LinearLayout) drawer.findViewById(R.id.drawerlinearlayout);
        listItems = (RecyclerView) drawer.findViewById(R.id.drawerListItem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItems.setLayoutManager(linearLayoutManager);

        final String[] tittle = new String[]{"Home", "Edit Profile", "My Images", "Result", "Rules & Prizes", "About Us", "Logout"};

        final int[] icons = new int[] {R.drawable.home, R.drawable.profile, R.drawable.mylisting, 0, 0, 0, 0};
        itemArrayList = new ArrayList<DrawerItems>();
        for (int i = 0; i < tittle.length; i++) {
            DrawerItems drawerItems = new DrawerItems();
            drawerItems.setTittle(tittle[i]);
            drawerItems.setIcons(icons[i]);
            itemArrayList.add(drawerItems);
        }

        final int[] selectedicons = new int[] {R.drawable.home_red, R.drawable.profile_red, R.drawable.mylisting_red, 0, 0, 0, 0};
        itemSelectedArrayList = new ArrayList<DrawerItems>();
        for (int i = 0; i < tittle.length; i++) {
            DrawerItems drawerItems = new DrawerItems();
            drawerItems.setTittle(tittle[i]);
            drawerItems.setIcons(selectedicons[i]);
            itemSelectedArrayList.add(drawerItems);
        }
//
        drawerAdapter = new DrawerAdapter(itemArrayList, itemSelectedArrayList, drawer);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        getLayoutInflater().inflate(layoutResID, linearLayout, true);
        drawer.setClickable(true);
        drawerAdapter.notifyDataSetChanged();
        listItems.setAdapter(drawerAdapter);

        toolbar = (Toolbar) drawer.findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        super.setContentView(drawer);
        sessionManager = new SessionManager(getApplicationContext());
    }
}
