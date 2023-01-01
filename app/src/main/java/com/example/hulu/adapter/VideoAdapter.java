package com.example.hulu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hulu.PlayerActivity;
import com.example.hulu.R;
import com.example.hulu.pojo_class.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context context;
    private List<Video> allVideos;

    public VideoAdapter(Context context,List<Video> allVideos) {
        this.context = context;
        this.allVideos = allVideos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Video video = allVideos.get(position);
        //holder.video_title.setText(video.getTitle());
        holder.video_title.setText(allVideos.get(position).getTitle());
        //Glide.with(mcontext).load(post.getPostimage()).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(holder.post_imager);
        Picasso.get().load(allVideos.get(position).getImageUrl()).into(holder.video_thumbnail);

        holder.vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoData", allVideos.get(position));
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView video_thumbnail;
        public TextView video_title;
        
        View vv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            video_thumbnail = itemView.findViewById(R.id.video_thumbnail);
            video_title = itemView.findViewById(R.id.video_title);
            vv = itemView;
        }
    }
}
