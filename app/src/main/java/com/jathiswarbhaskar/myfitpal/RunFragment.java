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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static androidx.core.content.ContextCompat.getCodeCacheDir;
import static androidx.core.content.ContextCompat.getSystemService;


public class RunFragment extends Fragment {
    private View runFragmentView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        runFragmentView = inflater.inflate(R.layout.fragment_run, container, false);


        return runFragmentView;


    }


}