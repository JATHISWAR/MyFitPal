package com.jathiswarbhaskar.myfitpal;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public YouTubePlayerView player;
    public TextView title,description;
    public Button watchButton;
    public ItemClickListener listener;

    public VideosViewHolder(@NonNull View itemView) {
        super(itemView);
        player = (YouTubePlayerView) itemView.findViewById(R.id.layout_videoplayer);
        title = (TextView)itemView.findViewById(R.id.layout_video_title);
        description = (TextView)itemView.findViewById(R.id.layout_video_description);
        watchButton = (Button)itemView.findViewById(R.id.layout_watch_btn);

    }
    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}