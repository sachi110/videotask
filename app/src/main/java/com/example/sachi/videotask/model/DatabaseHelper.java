package com.example.sachi.videotask.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sachi.videotask.R;
import com.firebase.ui.auth.data.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public  static String DATABASE_NAME="VideoDB";
    public  static int DATABASE_VERSION=1;

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, VideoData.class);
            TableUtils.createTable(connectionSource, ImageData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            ;
            try {
                TableUtils.dropTable(connectionSource, VideoData.class, true);
                TableUtils.dropTable(connectionSource, ImageData.class, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                R.raw.ormlite_config);
    }
}
