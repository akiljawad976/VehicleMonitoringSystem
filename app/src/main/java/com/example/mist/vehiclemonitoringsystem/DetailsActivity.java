package com.example.mist.vehiclemonitoringsystem;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class DetailsActivity extends AppCompatActivity {

    TextView road_id, road_lattitude,road_longditude,road_maxspeed,road_maxsound;
    private Context context;
    double defaultspeed = 30;
    double defaultsound = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this;

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            road_id = (TextView)findViewById(R.id.road_id);
                            road_lattitude = (TextView)findViewById(R.id.lattitude);
                            road_longditude = (TextView)findViewById(R.id.longditude);
                            road_maxspeed = (TextView)findViewById(R.id.speed);
                            road_maxsound = (TextView)findViewById(R.id.sound);

                            String _id = getIntent().getStringExtra("RoadId");
                            double _lattitude = getIntent().getDoubleExtra("RoadLattitude",0.00);
                            double _longditude = getIntent().getDoubleExtra("RoadLongditude",0.00);
                            double _speed = getIntent().getDoubleExtra("RoadSpeed",0.00);
                            double _sound = getIntent().getDoubleExtra("RoadSound",0.00);
                            double soundstatus = getIntent().getDoubleExtra("RoadSoundStatus",0.00);
                            double speedstatus = getIntent().getDoubleExtra("RoadSpeedStatus",0.00);


                            road_id.setText("Id of the Road: " + _id+"\n");
                            road_lattitude.setText("Lattitude: " + _lattitude+"\n");
                            road_longditude.setText("Longditude: " + _longditude+"\n");
                            road_maxspeed.setText("Speed Limit: " + _speed+"\n");
                            road_maxsound.setText("Sound Limit: " + _sound+"\n");
                            if(soundstatus==0){
//                                Toast.makeText(getApplicationContext(),"Speed is too High.Please reduce the speed."+_speed+" is greater than Default Speed.",Toast.LENGTH_LONG).show();
                                AppConstant.showAlertMessage(context,"Sound is too High.Please reduce the speed.Maximum Sound is "+_sound);

                            }

                            if(speedstatus==0){
                                Toast.makeText(getApplicationContext(),"Sound is too High.Please reduce the Sound."+_sound+" is greater than Default Sound Limit.",Toast.LENGTH_LONG).show();
                                AppConstant.showAlertMessage(context,"Speed is too High.Please reduce the speed.Maximum Speed is "+_speed);

                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms

    }

}
