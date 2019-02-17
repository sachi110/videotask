package com.example.sachi.videotask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoDetails extends AppCompatActivity {
    VideoView videoView;
    MediaController videoMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        Bundle exBundle = getIntent().getExtras();
        setTitle("Video Details");
        String dis=exBundle.getString("disc","");
        String url=exBundle.getString("url","");
        videoView=findViewById(R.id.videodetails);
        videoMediaController = new MediaController(this);
        videoView.setVideoPath(url);
        videoMediaController.setMediaPlayer(videoView);
        videoView.setMediaController(videoMediaController);
        videoView.requestFocus();
        videoView.start();


    }
}
