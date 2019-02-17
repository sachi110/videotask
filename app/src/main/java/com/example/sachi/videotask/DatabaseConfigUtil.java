package com.example.sachi.videotask;

import com.example.sachi.videotask.model.ImageData;
import com.example.sachi.videotask.model.VideoData;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[]{
            VideoData.class,ImageData.class};

    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
