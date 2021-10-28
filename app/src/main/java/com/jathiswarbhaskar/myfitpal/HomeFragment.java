package com.jathiswarbhaskar.myfitpal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

public class HomeFragment extends Fragment {
    DatabaseReference VideosRef;
    RecyclerView recyclerView;
    String videoId;
    ProgressDialog loadingBar;
    Button mybutton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, view, false);
        mybutton = (Button) rootView.findViewById(R.id.layout_watch_btn);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FullScreenActivity.class);
                startActivity(intent);
            }
        });


     /*   VideosRef = FirebaseDatabase.getInstance().getReference().child("Videos");
        recyclerView = view.findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        loadingBar = new ProgressDialog(getContext());
        recyclerView.setLayoutManager(layoutManager);


        loadingBar.setTitle("Please Wait");
        loadingBar.setMessage("Your videos are loading...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        onStart();*/

        return rootView;

    }


    public void adapter() {


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
                                videoId = model.getLink();
                                youTubePlayer.cueVideo(videoId, 0);
                            }
                        });

                        holder.watchButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(view.getContext(), FullScreenActivity.class);
                                intent.putExtra("link", model.getLink());
                                startActivity(intent);
                            }
                        });

                        loadingBar.dismiss();


                    }


                    @NonNull
                    @Override
                    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
                        VideosViewHolder holder = new VideosViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
