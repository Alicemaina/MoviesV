package com.alice.moviesv.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.alice.moviesv.util.MyDebug;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by alice on 3/29/17.
 */

public class MovieProvider extends ContentProvider {
    private final static String TAG = "MovieProvider";
    private final static boolean DEBUG = true;

    private MovieDatabase mDatabase;
    private static final String FAVORITES_TABLE = MovieDatabase.Tables.FAVORITES;

    private static final UriMatcher sURIMatcher;

    private static final int FAVORITES = 100;

    private Context mContext;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        sURIMatcher.addURI(authority, "favorites", FAVORITES);

    }


    public MovieProvider(MovieDatabase mDatabase) {
        this.mDatabase = mDatabase;
    }

    public MovieProvider(Context context) {
        mContext = context;
        this.mDatabase = MovieDatabase.getInstance(context);
    }

    public MovieProvider() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreate() {
        MyDebug.LOGD(TAG, "onCreate()");
        if (mContext == null) mContext = getContext();
        mDatabase = MovieDatabase.getInstance(mContext);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sURIMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                return MovieContract.Movies.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        if (DEBUG)
            MyDebug.LOGD(TAG, "query(uri: " + uri + ", COLUMNS: " + Arrays.toString(projection) + ")");

        checkColumns(FAVORITES_TABLE, projection);

        final SQLiteDatabase db = mDatabase.getReadableDatabase();
        Cursor cursor = db.query(true, FAVORITES_TABLE, projection, selection, selectionArgs, null, null, sortOrder, null);

        return cursor;
    }

    @Override
    public Uri insert(
            Uri uri,
            ContentValues values) {

        if (DEBUG)
            MyDebug.LOGD(TAG, "insert(" + sURIMatcher.match(uri) + ": uri=" + uri + ", Values=" + values.toString() + ")");

        final SQLiteDatabase db = mDatabase.getWritableDatabase();

        switch (sURIMatcher.match(uri)) {
            case FAVORITES: {
                checkColumns(FAVORITES_TABLE, values.keySet().toArray(new String[values.size()]));
                db.insertOrThrow(MovieDatabase.Tables.FAVORITES, null, values);
                notifyChange(uri);
                return MovieContract.Movies.buildItemUri(values.getAsString(MovieContract.Movies.MOVIE_ID));
            }

            default:
                throw new UnsupportedOperationException("Unknown Insert URI: " + uri);
        }
    }

    public Uri insertOnConflict(Uri uri, ContentValues values, int conflictOption) {
        final SQLiteDatabase db = mDatabase.getWritableDatabase();

        switch (sURIMatcher.match(uri)) {
            case FAVORITES:
                if (DEBUG) MyDebug.LOGD(TAG, "inserting favorite into db...");
                long insertResult = db.insertWithOnConflict(MovieDatabase.Tables.FAVORITES, null, values, conflictOption);
                if (DEBUG) MyDebug.LOGD(TAG,"insert result:" + insertResult);
                notifyChange(uri);
                return MovieContract.Movies.buildItemUri(values.getAsString(MovieContract.Movies.MOVIE_ID));

            default:
                throw new UnsupportedOperationException("Unknown Insert URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        MyDebug.LOGD(TAG, "delete(uri=" + uri.getPath());
        final SQLiteDatabase db = mDatabase.getWritableDatabase();
        int response = db.delete(FAVORITES_TABLE, selection, selectionArgs);
        notifyChange(uri);
        return response;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void notifyChange(Uri uri) {
        mContext.getContentResolver().notifyChange(uri, null);
    }

    private void checkColumns(String pTableName, String[] projection) {

        HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
        HashSet<String> existingColumns = new HashSet<>(Arrays.asList(MovieContract.Movies.PROJECTION_ALL));
        if (!existingColumns.containsAll(requestedColumns)) {
            MyDebug.LOGE(TAG, "Table (" + pTableName + ") does not contain all requested columns!  Looking for missing columns...");
            MyDebug.LOGE(TAG, "Requested Columns:" + requestedColumns.toString());
            MyDebug.LOGE(TAG, "Existing Columns:" + existingColumns.toString());
            for (String requestedColumn : requestedColumns) {
                if (!existingColumns.contains(requestedColumn))
                    MyDebug.LOGE(TAG, "MISSING COLUMN:" + requestedColumn);
            }
            throw new IllegalArgumentException("Table (" + pTableName + ") does not contain all requested columns: " + Arrays.toString(projection));
        }
    }
}
