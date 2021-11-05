package com.jathiswarbhaskar.myfitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {
    private DatabaseReference videosRef;
    private EditText mtitle,mdescription,mlink;
    private String title,description,link,Id;
    private ProgressDialog loadingBar;
    private Button addButton;
    private VideosModel newVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mtitle = (EditText) findViewById(R.id.video_title);
        mdescription = (EditText) findViewById(R.id.video_description);
        mlink = (EditText) findViewById(R.id.video_link);
        videosRef = FirebaseDatabase.getInstance().getReference().child("Videos");
        addButton = (Button) findViewById(R.id.add_new_video_btn);

        newVideo = new VideosModel();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateVideoData();
            }
        });




    }


    protected void ValidateVideoData(){

        title = mtitle.getText().toString().trim();
        description = mdescription.getText().toString().trim();
        link = mlink.getText().toString().trim();

        if (TextUtils.isEmpty(title))
        {
            Toast.makeText(this, "Please write video title...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "Please write video description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(link))
        {
            Toast.makeText(this, "Please write video link...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SaveInfoToDatabase();
Log.d("path",videosRef.toString());
        }



    }


    protected void SaveInfoToDatabase(){
        newVideo.setTitle(title);
        newVideo.setDescription(description);
        newVideo.setLink(link);
        String key = videosRef.push().getKey();
        Log.d("key",key);
        newVideo.setId(key);
        videosRef.child(key).setValue(newVideo);
        Toast.makeText(AdminActivity.this,"Video added successfully",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AdminActivity.this,VideosActivity.class);
        startActivity(intent);
    }






}