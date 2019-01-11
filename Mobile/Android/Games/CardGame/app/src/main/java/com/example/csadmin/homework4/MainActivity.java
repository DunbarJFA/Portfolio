package com.example.csadmin.homework4;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout navDrawer;
    private ActionBarDrawerToggle toggle;
    private ProfileFragment profile;
    private ShopFragment shop;
    private CollectionFragment collection;
    private LogOutFragment logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navDrawer = findViewById(R.id.Drawer_Layout);

        NavigationView navView = findViewById(R.id.NavView);
        navView.setNavigationItemSelectedListener(this);
        Menu navMenu = navView.getMenu();

        for (int i = 0; i < navMenu.size(); i++){

            toggle = new ActionBarDrawerToggle(this, navDrawer,toolbar,R.string.open_drawer,R.string.close_drawer);
            navDrawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        //initialize fragments
        profile = new ProfileFragment();
        shop = new ShopFragment();
        collection = new CollectionFragment();
        logout = new LogOutFragment();
    }
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem){
            //change activity depending on item selected
            if(menuItem.getItemId() == R.id.menu_Profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profile).commit();
            }else if (menuItem.getItemId() == R.id.menu_Shop){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,shop).commit();
            }else if (menuItem.getItemId() == R.id.menu_Collection){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,collection).commit();
            }else if (menuItem.getItemId() == R.id.menu_LogOut){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,logout).commit();

            }
            //highlight selected item
            menuItem.setChecked(true);
            //close the drawer before navigating
            navDrawer.closeDrawers();
            return true;
        }

    @Override
    public void onBackPressed(){
        DrawerLayout dl = findViewById(R.id.Drawer_Layout);
        if(dl.isDrawerOpen(GravityCompat.START)){
            dl.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
