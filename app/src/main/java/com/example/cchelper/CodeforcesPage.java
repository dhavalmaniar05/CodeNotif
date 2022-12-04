package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
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

public class CodeforcesPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView.Adapter adapter3;
    private RecyclerView.Adapter adapter2;

    private ArrayList<ContestPagesModel> arrayList;
    private ArrayList<CodeForcesModel> arraylist3;
    private ArrayList<ContestsModel> contests;
    private ArrayList<ListItems> listItems;

    private FirebaseAuth mauth;
    private FirebaseUser cuser;
    private DatabaseReference databaseReference;

     private  String username;
    private String url1;

    Button navBtn;
    Button hideBtn;
    NavigationView nav;


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeforces_page);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("CodeForces");
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);

        mauth = FirebaseAuth.getInstance();
        cuser = mauth.getCurrentUser();
        String Uid = cuser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        databaseReference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDetails user = snapshot.getValue(userDetails.class);
                if(user!=null){
                    username = user.getCodeforces();
                    Log.d("Animish",username);
                    apicall1(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("animish",error.toString());
            }
        });

        Intent intent=getIntent();
        username=intent.getStringExtra(username);
        arrayList=new ArrayList<>();
        contests=new ArrayList<>();
        listItems=new ArrayList<>();
        arraylist3=new ArrayList<>();
        apicall2();
    }

    private void apicall1(String username1)
    {

        String url="https://codeforces.com/api/user.info?handles="+username1;
        RequestQueue queue = Volley.newRequestQueue(this);

        Log.d("ayush", "onResponse: "+ url);
// Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject codeChefObject = new JSONObject(response);
                            // Log.d("shreyan", "onResponse: "+codeChefObject.getString("status"));
                            String s=codeChefObject.getString("status");
                            Log.d("shreyan", "onResponse: "+s);
                            if(s!="FAILED") {
                                JSONArray arr = codeChefObject.getJSONArray("result");

                                Log.d("ishan", "onResponse: " + arr.length());
                                JSONObject jobj = arr.getJSONObject(0);
                                Log.d("shubh", "onResponse1: " + jobj.getString("maxRank"));
                                String hard = jobj.getString("firstName");
                                Log.d("shubh", "onResponse2: " + url);
                                String easy = jobj.getString("lastName");
                                Log.d("shubh", "onResponse3: " + url);
                                Integer medium = jobj.getInt("rating");
                                Log.d("shubh", "onResponse4: " + url);
                                Integer totalqs = jobj.getInt("maxRating");
                                Log.d("shubh", "onResponse5: " + url);
                                String acceptance = jobj.getString("rank");
                                Log.d("shubh", "onResponse6: " + url);
                                String total_easy = jobj.getString("maxRank");
                                Log.d("shubh", "onResponse7: " + url);
                                String total_medium = jobj.getString("organization");
                                Log.d("shubh", "onResponse8: " + url);
                                CodeForcesModel model = new CodeForcesModel(hard, easy, medium.toString(), totalqs.toString(), acceptance, total_easy, total_medium);
                                arraylist3.add(model);
                                RecyclerView recyclerView = findViewById(R.id.recviecodeforces1);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CodeforcesPage.this));
                                adapter3 = new CodeForcesDetails(arraylist3, CodeforcesPage.this);
                                recyclerView.setAdapter(adapter3);
                                Log.d("shubh", "onResponse: " + totalqs);
                            }
                            else
                            {
                                Toast.makeText(CodeforcesPage.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("errr", "onResponse: " + e.getLocalizedMessage());
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("dhaval", "OnError" + error.getLocalizedMessage());
                Toast.makeText(CodeforcesPage.this, "Incorrect username", Toast.LENGTH_SHORT).show();

            }
        }

        );


// Access the RequestQueue through your singleton class.

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void apicall2() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clist.by/api/v2/contest/?username=dhavalmaniar05&api_key=e825bf3e300213ac2ddc47d5ad0f11dd4347ce5a&host=codeforces.com&format=json";
        Log.d("apo", "onResponse: abcd"  );
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
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
                            RecyclerView recyclerView=findViewById(R.id.contestcodefroces);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CodeforcesPage.this));
                            listItems= new ArrayList<>();
                            for (int i=0;i<5;i++)
                            {
                                ListItems temp= new ListItems(
                                        "   START TIME-"+contests.get(i).start_time+'\n'+'\n'+
                                                "   END TIME-"+contests.get(i).end_time+'\n'+'\n'+
                                                "   URL- "+contests.get(i).url,contests.get(i).getName());
                                listItems.add(temp);
                            }
                            Log.d("mohitm1", "onResponse: "+listItems.size());
                            adapter2= new CodeForcesContests(listItems,CodeforcesPage.this);
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
                // TODO: Handle error
                Log.d("dhaval", "OnError" + error.getLocalizedMessage());

            }
        }

        );


// Access the RequestQueue through your singleton class.

        queue.add(stringRequest);
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