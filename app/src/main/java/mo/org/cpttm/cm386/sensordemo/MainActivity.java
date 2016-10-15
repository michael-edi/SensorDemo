package mo.org.cpttm.cm386.sensordemo;
/*
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
*/
import android.hardware.SensorManager;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManagerSimulator sensorManager;
    //SensorManager sensorManager;
    Ball ball;
    FrameLayout mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (FrameLayout) findViewById(R.id.mainView);
        ball = new Ball(this, 50, 50, 50);
        mainView.addView(ball);

        sensorManager = (SensorManagerSimulator) SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        sensorManager.connectSimulator();
        //sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_FASTEST);
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d("Sensor Data", "On Sensor Changed!");

        int sensor = sensorEvent.type;
        //int sensor = sensorEvent.sensor.getType();

        if (sensor == Sensor.TYPE_ACCELEROMETER){
            float[] values = sensorEvent.values;
            float xx = values[0];
            float yy = values[1];
            //Log.d("Sensor Data", "xx:"+xx+" yy:"+yy);

            float newX=mainView.getWidth() / 2;
            float newY=mainView.getHeight() / 2;
            float maxRadius=newX;
            if (newX>newY) maxRadius=newY;

            float newRadius=(maxRadius/20)*(10+yy);
            if (newRadius<50) newRadius=50;
            if (newRadius>maxRadius) newRadius=maxRadius;

            Ball newBall = new Ball(this, newX, newY, (int) newRadius);
            mainView.removeAllViews();
            mainView.addView(newBall);
            ball = newBall;
        } else if (sensor == Sensor.TYPE_TEMPERATURE) {
            float[] values = sensorEvent.values;
            float xx = values[0];
            //Log.d("Sensor Data", "TYPE_TEMPERATURE:"+xx);
            //set simulator.temperature as 17
            if (xx>17) Ball.setColor(1); else Ball.setColor(0);
        }

    }
}
