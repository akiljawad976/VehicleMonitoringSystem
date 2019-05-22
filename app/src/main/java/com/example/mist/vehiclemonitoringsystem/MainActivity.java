package com.example.mist.vehiclemonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button btn,blogout;
    private Context context;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ListView lv;

    String[]  road_id;
    double[]  road_lattitude;
    double[]  road_longditude;
    double[]  road_maxspeed;
    double[]  road_maxsound;
    double[]  road_speedstatus;
    double[]  road_soundstatus;
   // double a,b;


    public MainActivity() {
    }

    public MainActivity(String[] road_id, double[] road_lattitude, double[] road_longditude, double[] road_maxspeed, double[] road_maxsound, double[] road_speedstatus, double[] road_soundstatus) {
        this.road_id = road_id;
        this.road_lattitude = road_lattitude;
        this.road_longditude = road_longditude;
        this.road_maxspeed = road_maxspeed;
        this.road_maxsound = road_maxsound;
        this.road_speedstatus = road_speedstatus;
        this.road_soundstatus = road_soundstatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.button);
        blogout = (Button)findViewById(R.id.logout);
        lv = (ListView)findViewById(R.id.mylist);
        context = this;

        FetchData();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //callAsynchronousTask();
                Intent in = new Intent(getApplicationContext(),MapsActivity.class);
                in= in.putExtra("Lattitude",road_lattitude[0]);
                in= in.putExtra("Longditude",road_longditude[0]);
                in= in.putExtra("MaxSpeed",road_maxspeed[0]);
                in= in.putExtra("MaxSound",road_maxsound[0]);
                in= in.putExtra("SoundStatus",road_soundstatus[0]);
                in= in.putExtra("SpeedStatus",road_soundstatus[0]);
                startActivity(in);



            }
        });
        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });


    }

//    public void callAsynchronousTask() {
//        final Handler handler = new Handler();
//        Timer timer = new Timer();
//        TimerTask doAsynchronousTask = new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    public void run() {
//                        try {
//                           FetchData();
//                        } catch (Exception e) {
//                            // TODO Auto-generated catch block
//                        }
//                    }
//                });
//            }
//        };
//        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
//    }
    private void FetchData() {
        //String myurl = "http://192.168.43.35/interfacing/roadapi.php";
        //String myurl = "https://api.myjson.com/bins/ydobu";
        String myurl = "http://shaila.stereoserver.com/vehicle/fetch.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // String [] road_id = new String[response.length()];
                road_id = new String[response.length()];
                road_lattitude = new double[response.length()];
                road_longditude = new double[response.length()];
                road_soundstatus = new double[response.length()];
                road_speedstatus = new double[response.length()];
                road_maxspeed = new double[response.length()];
                road_maxsound = new double[response.length()];

                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = (JSONObject)response.get(i);
                        road_id[i] = jsonObject.getString("id");
                        road_lattitude[i] = jsonObject.getDouble("lattitude");
                        road_longditude[i] = jsonObject.getDouble("longditude");
                        road_speedstatus[i] = jsonObject.getDouble("speedstatus");
                        road_soundstatus[i] = jsonObject.getDouble("soundstatus");
                        road_maxspeed[i] = jsonObject.getDouble("maxspeed");
                        road_maxsound[i] = jsonObject.getDouble("sound");
                        Log.d("AD", "Value: " + road_lattitude[i]);
                        Log.d("AD", "Value: " + road_longditude[i]);

                        if(road_speedstatus[i]==0){
//                            Toast.makeText(getApplicationContext(),"Speed is too High.Please reduce the speed.Maximum Speed is "+road_maxspeed,Toast.LENGTH_LONG).show();
                            AppConstant.showAlertMessage(context,"Speed is too High.Please reduce the speed.Maximum Speed is "+road_maxspeed[0]);

                        }

                        if(road_soundstatus[i]==0){
//                            Toast.makeText(getApplicationContext(),"Sound is too High.Please reduce the Sound.Maximum Speed is "+road_maxsound,Toast.LENGTH_LONG).show();
                            AppConstant.showAlertMessage(context,"Sound is too High.Please reduce the speed.Maximum Sound is "+road_maxsound[0]);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



                lv.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.mylistview,R.id.roadid,road_id));

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //callAsynchronousTask();
                        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                        intent.putExtra("RoadId",road_id[0]);
                        intent.putExtra("RoadLattitude",road_lattitude[0]);
                        intent.putExtra("RoadLongditude",road_longditude[0]);
                        intent.putExtra("RoadSpeedStatus",road_speedstatus[0]);
                        intent.putExtra("RoadSoundStatus",road_soundstatus[0]);
                        intent.putExtra("RoadSpeed",road_maxspeed[0]);
                        intent.putExtra("RoadSound",road_maxsound[0]);

                        startActivity(intent);

                    }
                });

            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley error",error); // if server is down or 404 not found



            }
        } );

        com.example.mist.vehiclemonitoringsystem.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        // create kora request ta appcontroller er kache pathaya dilam
        Toast.makeText(getApplicationContext(),"Data loaded successfully",Toast.LENGTH_SHORT).show();




    }
}
