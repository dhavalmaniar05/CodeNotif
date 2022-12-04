package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button navBtn;
    Button hideBtn;
    NavigationView nav;


    private Toolbar toolbar;

    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter2;
    private RecyclerView.Adapter adapter3;
    public static   String username="message";
    private codeChefData CodeChefDataModel;
    private ArrayList<ContestPagesModel>arrayList;
    private ArrayList<codeChefData> arraylist2;
    private ArrayList<ContestsModel> contests;
    private ArrayList<ListItems> listItems;
    private ArrayList<CodeForcesModel> arraylist3;

    private FirebaseAuth mauth;
    private FirebaseUser cuser;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private void apicall1()
    {
        String hard="100";
        String easy="256";
        String medium="432";
        String totalqs= "2493";
        String acceptance="78.5";
        String total_easy="611";
        String total_medium="1329";
        String total_hard="553";
        ContestPagesModel model=new ContestPagesModel(hard,medium,easy,totalqs,acceptance,total_easy,total_medium,total_hard);
        arrayList.add(model);
        RecyclerView recyclerView=findViewById(R.id.homepageleetcoderecview);

        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        adapter=new LeetCodeHomePage(arrayList,HomeActivity.this);
        recyclerView.setAdapter(adapter);

        Log.d("shubh", "onResponse: "+ totalqs);

    }

    private void apicall2()
    {


        CodeChefDataModel = new codeChefData(
                "DhavalManair05",
                1205,
                3,
                1989,
                1536,
                298,
                15
        );
        Log.e("Dhaval",CodeChefDataModel.getUsername());
        RecyclerView recyclerView=findViewById(R.id.codechefrecviewhomepage);
        arraylist2.add(CodeChefDataModel);
        Log.e("Dhaval1",CodeChefDataModel.getUsername());
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        adapter2=new CodeChefHomePage(arraylist2,HomeActivity.this);
        recyclerView.setAdapter(adapter2);
        Log.e("Dhaval2",CodeChefDataModel.getUsername());

    }
    private void apicall3(String username)
    {
        String url="https://codeforces.com/api/user.info?handles="+username;
        RequestQueue queue = Volley.newRequestQueue(this);

        Log.d("shubh", "onResponse: "+ url);
// Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject codeChefObject = new JSONObject(response);
                            JSONArray arr = codeChefObject.getJSONArray("result");
                            Log.d("ishan", "onResponse: "+ arr.length());
                            JSONObject jobj = arr.getJSONObject(0);
                            Log.d("shubh", "onResponse1: "+ jobj.getString("maxRank"));
                            String hard=jobj.getString("firstName");
                            Log.d("shubh", "onResponse2: "+ url);
                            String easy=jobj.getString("lastName");
                            Log.d("shubh", "onResponse3: "+ url);
                            Integer medium=jobj.getInt("rating");
                            Log.d("shubh", "onResponse4: "+ url);
                            Integer totalqs= jobj.getInt("maxRating");
                            Log.d("shubh", "onResponse5: "+ url);
                            String acceptance=jobj.getString("rank");
                            Log.d("shubh", "onResponse6: "+ url);
                            String total_easy=jobj.getString("maxRank");
                            Log.d("shubh", "onResponse7: "+ url);
                            String total_medium=jobj.getString("organization");
                            Log.d("shubh", "onResponse8: "+ url);
                            CodeForcesModel model=new CodeForcesModel(hard,easy,medium.toString(),totalqs.toString(),acceptance,total_easy,total_medium);
                            arraylist3.add(model);
                            RecyclerView recyclerView=findViewById(R.id.codeforceshomepage);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                            adapter3=new CodeForcesHomePage(arraylist3,HomeActivity.this);
                            recyclerView.setAdapter(adapter3);
                            Log.d("shubh", "onResponse: "+ totalqs);
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

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        nav = findViewById(R.id.navigationView);
        navBtn = findViewById(R.id.navBtn);
        hideBtn = (Button) nav.findViewById(R.id.hideNavBtn);

        nav.setNavigationItemSelectedListener(this);

        mauth = FirebaseAuth.getInstance();
        cuser = mauth.getCurrentUser();
        String Uid = cuser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Details..");

        progressDialog.show();
        databaseReference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDetails user = snapshot.getValue(userDetails.class);
                if(user!=null){
                    username = user.getCodeforces();
                    Log.d("Animish",username);
                    apicall3(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("animish",error.toString());
            }
        });

        arrayList=new ArrayList<>();
        contests=new ArrayList<>();
        listItems=new ArrayList<>();
        arraylist2=new ArrayList<>();
        arraylist3=new ArrayList<>();
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
//        MenuItem select = (MenuItem) findViewById(id);
//        select.s

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