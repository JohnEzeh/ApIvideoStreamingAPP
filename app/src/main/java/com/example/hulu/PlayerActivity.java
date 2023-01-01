package com.example.hulu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.hulu.pojo_class.Video;

public class PlayerActivity extends AppCompatActivity {
    private TextView title,description;
    private VideoView videoView;
    private ProgressBar spinner;
    private ImageView fullscreen_option;
    private FrameLayout frameLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
       Toolbar toolbar = findViewById(R.id.toolbag);

       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(view -> {
           finish();
       });

       spinner = findViewById(R.id.spinner);
       fullscreen_option = findViewById(R.id.fullscreen_option);
       frameLayout = findViewById(R.id.frameLayout);

        title = findViewById(R.id.video_title_txt);
        description = findViewById(R.id.video_description);
        videoView = findViewById(R.id.videoView);

        Intent i = getIntent();
        Bundle data = i.getExtras();
        Video video = (Video) data.getSerializable("videoData");

        getSupportActionBar().setTitle(video.getTitle());

        title.setText(video.getTitle());
        description.setText(video.getDescription());

        Uri videoUri = Uri.parse(video.getVideoUrl());
        videoView.setVideoURI(videoUri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
                spinner.setVisibility(View.GONE);
            }
        });

        fullscreen_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullscreen_option.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getSupportActionBar().hide();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                        new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
                videoView.setLayoutParams(new FrameLayout.LayoutParams(
                        new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)));
            }
        });

    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fullscreen_option.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int heightValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220,
                getResources().getDisplayMetrics());

        frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,heightValue)));
        videoView.setLayoutParams(new FrameLayout.LayoutParams(new ViewGroup.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,heightValue)));

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT){
             super.onBackPressed();
        }

    }
}