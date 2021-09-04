package com.jathiswarbhaskar.myfitpal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import static androidx.core.content.ContextCompat.getCodeCacheDir;
import static androidx.core.content.ContextCompat.getSystemService;


public class RunFragment extends Fragment implements SensorEventListener {
    private View runFragmentView;
    private boolean isSensorPresent = false;
    private TextView mytextView;
    private static final String LOG_TAG = "STATS";
    private Sensor stepSensor;
    private Integer stepCount = 0;
    private double MagnitudePrevious = 0;
    private SensorManager sensorManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        runFragmentView = inflater.inflate(R.layout.fragment_run, container, false);
        mytextView = (TextView) runFragmentView.findViewById(R.id.run_steps);

        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return  runFragmentView;


    }

    @Override
    public void onResume() {
        super.onResume();
        if(stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            Toast.makeText(getActivity(), "Sensor not available!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isSensorPresent)
        {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorevent) {
        if (sensorevent != null) {
            float x_acceleration = sensorevent.values[0];
            float y_acceleration = sensorevent.values[1];
            float z_acceleration = sensorevent.values[2];
            double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration
                    + z_acceleration*z_acceleration);
            double MagnitudeDelta = Magnitude - MagnitudePrevious;
            MagnitudePrevious = Magnitude;

            if (sensorevent.values[0] > 6){
                stepCount++;
            }
            mytextView.setText(stepCount.toString());
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}