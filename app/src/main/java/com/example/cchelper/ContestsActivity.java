package com.example.cchelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContestsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, NavigationView.OnNavigationItemSelectedListener {

    Button navBtn;
    Button hideBtn;
    NavigationView nav;


    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> listItems;
    private NotificationHelper mNotificationHelper;
    private Button btn;
    private ImageView imgview;
    private ArrayList<ContestsModel> contestlist;
    private void apireq() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://clist.by/api/v2/contest/?username=dhavalmaniar05&api_key=e825bf3e300213ac2ddc47d5ad0f11dd4347ce5a&format=json";

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
                                contestlist.add(mdl);
                            }
                            recyclerView=findViewById(R.id.recview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ContestsActivity.this));
                            listItems= new ArrayList<>();
                            for (int i=0;i<30;i++)
                            {
                                ListItems temp= new ListItems("Platform- "+contestlist.get(i).site+'\n'+
                                        "start time-"+contestlist.get(i).start_time+'\n'+
                                        "end time-"+contestlist.get(i).end_time+'\n'+
                                        "URL- "+contestlist.get(i).url,contestlist.get(i).getName());
                                listItems.add(temp);
                            }
                            adapter= new contestsAdapter(listItems,ContestsActivity.this);
                            recyclerView.setAdapter(adapter);
                            Log.d("mohit", "onResponse: " + contestlist.size());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contests);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Upcoming Contests");
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navigationView);
        nav.setNavigationItemSelectedListener(this);

        contestlist=new ArrayList<>();
        apireq();
        Log.d("shubh", "onCreate: "+ contestlist.size());

        mNotificationHelper= new NotificationHelper(this);

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

    public void sendOnchannel1(String title,String Message)
    {
        NotificationCompat.Builder nb=mNotificationHelper.getCHannelNotification(title,Message);
        mNotificationHelper.getManager().notify(1,nb.build());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.MONTH,1);//month
        calSet.set(Calendar.YEAR,2015);//year
        calSet.set(Calendar.DAY_OF_MONTH,12);//day
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }



        updatetimetext(calSet);
        startalarm(calSet);
    }
    private void updatetimetext(Calendar c)
    {
        String timetext="Alarm set for :";
        timetext+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

    }
    private void startalarm(Calendar c)
    {
        AlarmManager alarmManager=(AlarmManager) getSystemService(this.ALARM_SERVICE);
        Intent intent=new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        if(c.before(Calendar.getInstance()))
            c.add(Calendar.DATE,1);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
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
        else if(id==R.id.menu_contest) {

            Intent lc = new Intent(this, ContestsActivity.class);
            startActivity(lc);
        }
        return true;
    }
}