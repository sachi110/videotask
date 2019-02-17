package com.example.sachi.videotask;

import android.util.Log;

import com.example.sachi.videotask.model.BaseRepository;
import com.example.sachi.videotask.model.VideoData;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

public class VideoRepo extends BaseRepository<VideoData> {
    public static VideoRepo videoRepo;
    private String TAG="REpo";

    public VideoRepo(Class<VideoData> type) {
        super(type);
    }

    VideoRepo() {
        super(VideoData.class);
    }


    public static VideoRepo getInstance() {
        if (videoRepo == null)
            videoRepo = new VideoRepo();

        return  videoRepo;
    }
    public List<VideoData> getAll()
    {
        try {

                QueryBuilder<VideoData, Integer> qb = getDao(type).queryBuilder();
                return qb.query();

        } catch (Exception e) {
            Log.e(TAG, "exception", e);
        }
        return null;
    }
}