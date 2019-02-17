package com.example.sachi.videotask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.sachi.videotask.model.VideoData;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoList extends AppCompatActivity {


    public static VideoRepo videoRepo = VideoRepo.getInstance();
    List<VideoData> videos = new ArrayList<>();
    private CustomAdapter mAdapter;
    private RecyclerTouchListener onTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setTitle("Video List");
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        videos = videoRepo.getAll();
        mAdapter = new CustomAdapter(videos);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING || newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }

        });
        onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Intent video=new Intent(VideoList.this,VideoDetails.class);
                        video.putExtra("url",mAdapter.getItemAtPosition(position).getVideourl());
                        video.putExtra("disc",mAdapter.getItemAtPosition(position).getDescription());
                        startActivity(video);




                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {


                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        videos = videoRepo.getAll();
    }


    public class CustomAdapter extends RecyclerView.Adapter<com.example.sachi.videotask.VideoList.CustomAdapter.ViewHolder> {
        private List<VideoData> values;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtHeader;
            public View layout;
            public ImageView imageView;
            public  TextView desc;
            public VideoView videoView;



            public ViewHolder(View v) {
                super(v);
                layout = v;
                txtHeader = (TextView) v.findViewById(R.id.name);
                desc = (TextView) v.findViewById(R.id.disc);
                imageView = (ImageView) v.findViewById(R.id.image);
                videoView = (VideoView) v.findViewById(R.id.video);


                       }
        }


        public void add(int position, VideoData item) {
            values.add(position, item);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            values.remove(position);
            notifyItemRemoved(position);
        }

        public CustomAdapter(List<VideoData> myDataset) {
            values = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public com.example.sachi.videotask.VideoList.CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                 int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v =
                    inflater.inflate(R.layout.video_list, parent, false);
            // set the view's size, margins, paddings and layout parameters
            com.example.sachi.videotask.VideoList.CustomAdapter.ViewHolder vh = new com.example.sachi.videotask.VideoList.CustomAdapter.ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(com.example.sachi.videotask.VideoList.CustomAdapter.ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final String name = values.get(position).getTitle();
            final String disc = values.get(position).getDescription();;
            holder.txtHeader.setText(name);
            holder.desc.setText(disc);
            new DownloadImageTask(holder.imageView).execute(values.get(position).getTumb());
            Uri uri=Uri.parse(values.get(position).getVideourl());
            holder.videoView.setVideoURI(uri);






        }
        public VideoData getItemAtPosition(int position) {
            return values.get(position);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return values.size();
        }


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
