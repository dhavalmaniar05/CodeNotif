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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CodeChefPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView.Adapter adapter3;
    private RecyclerView.Adapter adapter2;
    public static   String username="message";

    private ArrayList<ContestPagesModel> arrayList;
    private ArrayList<codeChefData> arraylist2;
    private ArrayList<ContestsModel> contests;
    private ArrayList<ListItems> listItems;

    private ProgressDialog progressDialog;

    Button navBtn;
    Button hideBtn;
    NavigationView nav;


    private Toolbar toolbar;
    private void apicall1()
    {


        codeChefData CodeChefDataModel = new codeChefData(
                "dhavalmaniar05",
                1205,
                3,
                1989,
                1536,
                298,
                15
        );
        Log.e("Dhaval",CodeChefDataModel.getUsername());
        RecyclerView recyclerView=findViewById(R.id.recvieCodeChef1);
        arraylist2.add(CodeChefDataModel);
        Log.e("Dhaval1",CodeChefDataModel.getUsername());
        recyclerView.setLayoutManager(new LinearLayoutManager(CodeChefPage .this));
        adapter2=new CodeChefDetails(arraylist2,CodeChefPage.this);
        recyclerView.setAdapter(adapter2);
        Log.e("Dhaval2",CodeChefDataModel.getUsername());

    }
    private void apicall2() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clist.by/api/v2/contest/?username=dhavalmaniar05&api_key=e825bf3e300213ac2ddc47d5ad0f11dd4347ce5a&host=codechef.com&format=json";
        Log.d("apo", "onResponse: abcd"  );
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
                            RecyclerView recyclerView=findViewById(R.id.contestCodeChef);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CodeChefPage.this));
                            listItems= new ArrayList<>();
                            for (int i=0;i<5;i++)
                            {
                                ListItems temp= new ListItems(
                                        " start time-"+contests.get(i).start_time+'\n'+'\n'+
                                                " end time-"+contests.get(i).end_time+'\n'+'\n'+
                                                " URL- "+contests.get(i).url,contests.get(i).getName());
                                listItems.add(temp);
                            }
                            Log.d("mohitm1", "onResponse: "+listItems.size());
                            adapter2= new CodeCHefContestAdapter(listItems,CodeChefPage.this);
                            recyclerView.setAdapter(adapter2);
                            Log.d("mohi1t", "onResponse: " + contests.size());
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
        setContentView(R.layout.activity_code_chef_page);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("CodeChef");
        setSupportActionBar(toolbar);


        nav = findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);

        Intent intent=getIntent();

        arrayList=new ArrayList<>();
        arraylist2=new ArrayList<>();
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