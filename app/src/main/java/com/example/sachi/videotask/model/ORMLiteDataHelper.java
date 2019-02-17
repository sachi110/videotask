package com.example.sachi.videotask.model;
import android.annotation.SuppressLint;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class ORMLiteDataHelper {
        private static ORMLiteDataHelper instance;
        private DatabaseHelper databaseHelper = null;

        private ORMLiteDataHelper() {
        }

        public static ORMLiteDataHelper getInstance() {
            if (instance == null)
                instance = new ORMLiteDataHelper();
            return instance;
        }

        @SuppressLint("RestrictedApi")
        public DatabaseHelper getDatabaseHelper() {

            if (databaseHelper == null)
                databaseHelper = OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
            return databaseHelper;
        }

        public void release() {
            if (databaseHelper != null) {
                OpenHelperManager.releaseHelper();
                databaseHelper = null;
            }
        }

        public void clearDataBase() {
            if (databaseHelper == null)
                databaseHelper = getDatabaseHelper();

            databaseHelper = getDatabaseHelper();
        }
    }


