package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeetCode_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button navBtn;
    Button hideBtn;
    NavigationView nav;


    private Toolbar toolbar;

    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter2;
    public static   String username="message";

    private ArrayList<ContestPagesModel>arrayList;
    private ArrayList<ContestsModel> contests;
    private ArrayList<ListItems> listItems;
    private ProgressDialog progressDialog;
    private void apicall1()
    {

        String hard="100";
        String easy="190";
        String medium="131";
        String totalqs= "2493";
        String acceptance="78.5";
        String total_easy="611";
        String total_medium="1329";
        String total_hard="553";
        ContestPagesModel model=new ContestPagesModel(hard,medium,easy,totalqs,acceptance,total_easy,total_medium,total_hard);
        arrayList.add(model);
        RecyclerView recyclerView=findViewById(R.id.recviewleetcode);

        recyclerView.setLayoutManager(new LinearLayoutManager(LeetCode_page.this));
        adapter=new LeetCodeAdapter(arrayList,LeetCode_page.this);
        recyclerView.setAdapter(adapter);

        Log.d("shubh", "onResponse: "+ totalqs);

    }

    private void apicall2() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clist.by/api/v2/contest/?username=dhavalmaniar05&api_key=e825bf3e300213ac2ddc47d5ad0f11dd4347ce5a&host=leetcode.com&format=json";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("apo", "onResponse: " + response.toString());
                        try {
                            JSONObject codeChefObject = new JSONObject(response);
                            JSONArray arr = codeChefObject.getJSONArray("objects");
                            Log.d("ishan", "onResponse: "+ arr.length());

                            for (int i = 0; i < arr.length(); i++) {

                                JSONObject jobj = arr.getJSONObject(i);
                                Log.d("ishan", "onResponse: "+ jobj.getString("event"));
                                ContestsModel mdl = new ContestsModel(
                                        jobj.getString("event"),
                                        jobj.getString("start"),
                                        jobj.getString("end"),
                                        jobj.getString("duration"),
                                        jobj.getString("href"),
                                        jobj.getString("resource")
                                );
                                contests.add(mdl);
                            }
                            RecyclerView recyclerView=findViewById(R.id.contestleetcode);
                            recyclerView.setLayoutManager(new LinearLayoutManager(LeetCode_page.this));
                            listItems= new ArrayList<>();
                            for (int i=0;i<5;i++)
                            {
                                ListItems temp= new ListItems(
                                        "start time-"+contests.get(i).start_time+'\n'+
                                                "end time-"+contests.get(i).end_time+'\n'+
                                                "URL- "+contests.get(i).url,contests.get(i).getName());
                                listItems.add(temp);
                            }
                            Log.d("mohitm", "onResponse: "+listItems.size());
                            adapter2= new leetcodecontestadapter(listItems,LeetCode_page.this);
                            recyclerView.setAdapter(adapter2);
                            Log.d("mohit", "onResponse: " + contests.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errr", "onResponse: " + e.getLocalizedMessage());
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                // TODO: Handle error
                Log.d("dhaval", "OnError" + error.getLocalizedMessage());

            }
        }

        );


// Access the RequestQueue through your singleton class.

        queue.add(stringRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leet_code_page);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("LeetCode");
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);
        navBtn = findViewById(R.id.navBtn);
        Intent intent=getIntent();
        username=intent.getStringExtra(username);
        arrayList=new ArrayList<>();
        contests=new ArrayList<>();
        listItems=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Details..");

        progressDialog.show();
        apicall1();
        apicall2();
    }

    public void showNav(View view){

        navBtn = findViewById(R.id.navBtn);
        nav = findViewById(R.id.navigationView);

        if(nav.getVisibility() == View.GONE){

            nav.setVisibility(View.VISIBLE);
        }

    }

    public void hideNav(View view){

        nav = findViewById(R.id.navigationView);
        hideBtn = (Button) nav.findViewById(R.id.hideNavBtn);

        nav.setVisibility(View.GONE);
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