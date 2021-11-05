package com.jathiswarbhaskar.myfitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

public class VideosActivity extends AppCompatActivity {
    private DatabaseReference VideosRef, SearchRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private EditText searchtext;
    private Button searchbtn;
    private String searchinput;
    private String type = "";
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        VideosRef = FirebaseDatabase.getInstance().getReference().child("Videos");
        searchtext = (EditText) findViewById(R.id.search_product);
        searchbtn = (Button) findViewById(R.id.search_btn);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        loadingBar = new ProgressDialog(this);
        recyclerView.setLayoutManager(layoutManager);


        loadingBar.setTitle("Please Wait");
        loadingBar.setMessage("Your videos are loading...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchinput = searchtext.getText().toString();
                SearchRef = FirebaseDatabase.getInstance().getReference().child("Videos");


                FirebaseRecyclerOptions<VideosModel> options =
                        new FirebaseRecyclerOptions.Builder<VideosModel>()
                                .setQuery(SearchRef.orderByChild("title").startAt(searchinput), VideosModel.class)
                                .build();

                if (searchinput.isEmpty()) {
                    options =
                            new FirebaseRecyclerOptions.Builder<VideosModel>()
                                    .setQuery(SearchRef, VideosModel.class)
                                    .build();
                }


                FirebaseRecyclerAdapter<VideosModel, VideosViewHolder> adapter =
                        new FirebaseRecyclerAdapter<VideosModel, VideosViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull VideosViewHolder holder, int position, @NonNull final VideosModel model) {
                                holder.title.setText(model.getTitle());
                                holder.description.setText(model.getDescription());
                                holder.player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady(YouTubePlayer youTubePlayer) {
                                        String videoId = model.getLink();
                                        youTubePlayer.loadVideo(videoId, 0);

                                    }
                                });


                            }

                            @NonNull
                            @Override
                            public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_layout, parent, false);
                                VideosViewHolder holder = new VideosViewHolder(view);
                                return holder;
                            }
                        };

                recyclerView.setAdapter(adapter);
                adapter.startListening();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<VideosModel> options =
                new FirebaseRecyclerOptions.Builder<VideosModel>()
                        .setQuery(VideosRef, VideosModel.class)
                        .build();






        FirebaseRecyclerAdapter<VideosModel, VideosViewHolder> adapter =
                new FirebaseRecyclerAdapter<VideosModel, VideosViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final VideosViewHolder holder, int position, @NonNull final VideosModel model) {
                        holder.title.setText(model.getTitle());
                        holder.description.setText(model.getDescription());
                        holder.player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(YouTubePlayer youTubePlayer) {
                                String videoId = model.getLink();
                                youTubePlayer.loadVideo(videoId, 0);
                            }
                        });



                        loadingBar.dismiss();



                    }





                    @NonNull
                    @Override
                    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_layout, parent, false);
                        VideosViewHolder holder = new VideosViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}




