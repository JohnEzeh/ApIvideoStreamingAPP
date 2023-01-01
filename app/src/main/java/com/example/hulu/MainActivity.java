package com.example.hulu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hulu.adapter.VideoAdapter;
import com.example.hulu.pojo_class.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerVideo_list;
    private ProgressBar progressBar;
    VideoAdapter videoAdapter;
    List<Video> allVideos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        allVideos = new ArrayList<>();
        recyclerVideo_list = findViewById(R.id.recycler_video_list);
        recyclerVideo_list.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this,allVideos);
        recyclerVideo_list.setAdapter(videoAdapter);

        getJsonData();

    }

    private void getJsonData() {
        String URL = "https://raw.githubusercontent.com/bikashthapa01/myvideos-android-app/master/data.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               // Log.d(TAG, "onResponse: "+response);
                try {
                    JSONArray categories = response.getJSONArray("categories");
                    JSONObject categoriesData = categories.getJSONObject(0);
                    JSONArray videos = categoriesData.getJSONArray("videos");

                    for (int i = 0; i < videos.length(); i++) {
                        JSONObject video = videos.getJSONObject(i);

                        Video v = new Video();
                        v.setTitle(video.getString("title"));
                        v.setDescription(video.getString("description"));
                        v.setAuthur(video.getString("subtitle"));
                        v.setImageUrl(video.getString("thumb"));

                        JSONArray videoUrl = video.getJSONArray("sources");
                        v.setVideoUrl(videoUrl.getString(0));

                        allVideos.add(v);
                        videoAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d(TAG, "onErrorResponse: "+error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}