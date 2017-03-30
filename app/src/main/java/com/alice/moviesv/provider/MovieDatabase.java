package com.alice.moviesv.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.alice.moviesv.util.MyDebug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alice on 3/29/17.
 */

public class MovieDatabase extends SQLiteOpenHelper {
    private static final String TAG = "MovieDatabase";
    private static final boolean DEBUG_LOG = false;

    private static final String DATABASE_NAME = "popularmovies.db";

    private static final int DATABASE_VERSION = 1;

    private static MovieDatabase mInstance = null;

    public static MovieDatabase getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new MovieDatabase(context.getApplicationContext());
        }
        return mInstance;
    }

    private MovieDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (DEBUG_LOG) MyDebug.LOGD(TAG, "database initialized with version:" + DATABASE_VERSION + " (" + this.getReadableDatabase().getVersion() + ")");
    }

    interface Tables {
        String FAVORITES = "favorites";


        List<String> TABLE_LIST = new ArrayList<String>(
                Arrays.asList(new String[]{FAVORITES}));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MyDebug.LOGI(TAG, "onCreate() checkpoint");

        db.execSQL("CREATE TABLE " + Tables.FAVORITES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MovieContract.MovieColumns.MOVIE_ID + " TEXT NOT NULL,"
                + MovieContract.MovieColumns.MOVIE_TITLE + " TEXT NOT NULL,"
                + MovieContract.MovieColumns.MOVIE_OVERVIEW + " TEXT,"
                + MovieContract.MovieColumns.MOVIE_POPULARITY + " TEXT,"
                + MovieContract.MovieColumns.MOVIE_RATING + " TEXT,"
                + MovieContract.MovieColumns.MOVIE_POSTER_PATH + " TEXT,"
                + MovieContract.MovieColumns.MOVIE_RELEASE_DATE + " TEXT,"
                + "UNIQUE (" + MovieContract.MovieColumns.MOVIE_ID + ") ON CONFLICT REPLACE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MyDebug.LOGI(TAG,"onUpgrade() from " + oldVersion + " to " + newVersion);

    }


}
