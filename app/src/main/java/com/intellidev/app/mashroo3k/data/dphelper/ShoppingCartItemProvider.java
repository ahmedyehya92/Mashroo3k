package com.intellidev.app.mashroo3k.data.dphelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Ahmed Yehya on 08/03/2018.
 */

public class ShoppingCartItemProvider extends ContentProvider {

    private static final int ITEMS = 100;
    private static final int ITEM_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    SqliteHandler mDbHandler;
    public static final String LOG_TAG = ShoppingCartItemProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mDbHandler = new SqliteHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHandler.getReadableDatabase();
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case ITEMS :
                cursor = db.query(ShoppingCartItemContract.ItemEntry.TABLE_ITEMS,projection,selection,selectionArgs,null,null,null);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS :
                return  insertUser(uri,contentValues);
            default:
                throw new UnsupportedOperationException("Unable to insert rows into: " + uri);

        }
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHandler.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case ITEMS:
                // Delete all rows that match the selection and selection args
                return db.delete(ShoppingCartItemContract.ItemEntry.TABLE_ITEMS, selection, selectionArgs);
            case ITEM_ID:
                // Delete a single row given by the ID in the URI
                selection = ShoppingCartItemContract.ItemEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return db.delete(ShoppingCartItemContract.ItemEntry.TABLE_ITEMS, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public static UriMatcher buildUriMatcher() {
        String contentAuthority = ShoppingCartItemContract.CONTENT_AUTHORITY;
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(contentAuthority, ShoppingCartItemContract.PATH_ITEMS,ITEMS);
        uriMatcher.addURI(contentAuthority,ShoppingCartItemContract.PATH_ITEMS+"/#",ITEM_ID);
        return uriMatcher;
    }

    private Uri insertUser(Uri uri, ContentValues values) {

        // TODO: Insert a new pet into the pets database table with the given ContentValues


        // Get writeable database
        SQLiteDatabase database = mDbHandler.getWritableDatabase();

        long id = database.insert(ShoppingCartItemContract.ItemEntry.TABLE_ITEMS, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);


        return ContentUris.withAppendedId(uri, id);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it


    }
}
