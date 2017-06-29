package com.androidapps.avinashtadavarthy.spiderappdevtask3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    TextView senval,tempdisp;
    SensorManager sensorManager;
    Sensor sensor;

    static MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senval = (TextView) findViewById(R.id.senval);
        tempdisp = (TextView) findViewById(R.id.tempdisp);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(sound.isPlaying())
            sound.pause();

        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sound = MediaPlayer.create(this, R.raw.alarm);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sound.start();
        sound.setLooping(true);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sound.stop();
        sound.release();
        sensorManager.unregisterListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        sound.stop();
        sound.release();
        sensorManager.unregisterListener(this);
    }

    int i;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            senval.setText("" + event.values[0]);

            if(event.values[0] < 5){
                tempdisp.setText("Lesser than 5 cm");

                Handler handler1 = new Handler();

                handler1.postDelayed(new Runnable() {

                    public void run() {

                        sound.start();
                        sound.setLooping(true);
                    }
                }, 10000);

            }
            else if(event.values[0] == 5) {
                tempdisp.setText("Equal to 5 cm");
                sound.pause();
            }
            else {
                tempdisp.setText("Greater than 5 cm");
                sound.pause();
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
