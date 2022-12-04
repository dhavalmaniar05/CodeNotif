package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class DsaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button navBtn;
    Button hideBtn;
    NavigationView nav;


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsa);


        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("DSA Cheatsheet");
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);
    }



    public void showNav(View view){

        navBtn = findViewById(R.id.navBtn);
        nav = findViewById(R.id.navigationView);

        nav.setVisibility(View.VISIBLE);
    }


    public void hideNav(View view){

        nav = findViewById(R.id.navigationView);
        hideBtn = (Button) nav.findViewById(R.id.hideNavBtn);

        nav.setVisibility(View.GONE);
    }


//    For the contents of DSA page-







    public void hide5(View view) {

//        TextView t3 = (TextView) findViewById(R.id.str);
//        view.
        if(view.getId() == findViewById(R.id.TreeBtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.tree);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }


        else if(view.getId() == findViewById(R.id.DPbtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.DP);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }


        else if(view.getId() == findViewById(R.id.strBtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.str);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }

        else if(view.getId() == findViewById(R.id.DPbtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.DP);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }


        else if(view.getId() == findViewById(R.id.arrayBtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.array);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }


        else if(view.getId() == findViewById(R.id.graphBtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.graph);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }

        else if(view.getId() == findViewById(R.id.llBtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.ll);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }

        else if(view.getId() == findViewById(R.id.bsbtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.bs);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }

        else if(view.getId() == findViewById(R.id.dacbtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.dac);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }

        else if(view.getId() == findViewById(R.id.grebtn).getId()){

            TextView t3 = (TextView) findViewById(R.id.gre);
            if(t3.getVisibility() == View.GONE){

                t3.setVisibility(View.VISIBLE);
            }
            else{
                t3.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_dsa) {

            Intent dsa = new Intent(this, DsaActivity.class);
            startActivity(dsa);
        }
        else if(id==R.id.menu_home){

            Intent home = new Intent(this, HomeActivity.class);
            startActivity(home);
        }
        else if(id==R.id.menu_profile){
            Intent home = new Intent(this, UserProfileShow.class);
            startActivity(home);
        }else if(id==R.id.menu_codechef){

            Intent lc = new Intent(this, CodeChefPage.class);
            startActivity(lc);

        }else if(id==R.id.menu_codeforces){

            Intent lc = new Intent(this, CodeforcesPage.class);
            startActivity(lc);

        }
        else if(id==R.id.menu_leetcode){

            Intent lc = new Intent(this, LeetCode_page.class);
            startActivity(lc);

        }
        else if(id==R.id.menu_contest){

            Intent lc = new Intent(this, ContestsActivity.class);
            startActivity(lc);
        }
        return true;
    }
}