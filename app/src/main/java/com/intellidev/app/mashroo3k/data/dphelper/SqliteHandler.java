package com.intellidev.app.mashroo3k.data.dphelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.data.models.CartListModel;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 08/03/2018.
 */

public class SqliteHandler extends SQLiteOpenHelper {

    Context context;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "list_of_shopping_cart";

    public SqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE "+ ShoppingCartItemContract.ItemEntry.TABLE_ITEMS + "(" +
                ShoppingCartItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_ID + " TEXT NOT NULL, "+
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_TITLE + " TEXT, "+
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_PRICE + " TEXT, "+
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_IMG_URL + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ShoppingCartItemContract.ItemEntry.TABLE_ITEMS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addItemsToCart (String itemId, String title, String price, String imgUrl)
    {
        ContentValues values = new ContentValues();
        values.put(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_ID,itemId);
        values.put(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_TITLE,title);
        values.put(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_PRICE,price);
        values.put(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_IMG_URL,imgUrl);

        Uri newUri;
        newUri = context.getContentResolver().insert(ShoppingCartItemContract.ItemEntry.CONTENT_URI,values);

        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
             Toast.makeText(context, "failed to insert",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(context,"insert is done",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<CartListModel> getCartList ()
    {
        ArrayList<CartListModel> cartList = new ArrayList<>();
        String[] projection = {
                ShoppingCartItemContract.ItemEntry._ID,
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_ID,
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_TITLE,
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_PRICE,
                ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_IMG_URL,
        };

        Cursor cursor = context.getContentResolver().query(ShoppingCartItemContract.ItemEntry.CONTENT_URI, projection,null,null,null);

        if (cursor.moveToFirst()){
            do{
                String dbId = cursor.getString(cursor.getColumnIndex(ShoppingCartItemContract.ItemEntry._ID));
                String itemId = cursor.getString(cursor.getColumnIndex(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_ID));
                String title = cursor.getString(cursor.getColumnIndex(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_TITLE));
                String price = cursor .getString(cursor.getColumnIndex(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_PRICE));
                String imgUrl = cursor.getString(cursor.getColumnIndex(ShoppingCartItemContract.ItemEntry.COLUMN_ITEM_IMG_URL));
                cartList.add(new CartListModel(dbId,itemId,title,price,imgUrl));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();
        return cartList;
    }
    public void deleteItemsfromCart(Uri uri)
    {
        int checkEffect = context.getContentResolver().delete(uri,null,null);
        if (checkEffect > 0) {
            // If the new content URI is null, then there was an error with insertion.

            /* Toast.makeText(context, "delete is done",
                    Toast.LENGTH_SHORT).show(); */
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
         /*   Toast.makeText(context,"delete is failed",
                    Toast.LENGTH_SHORT).show(); */
        }
    }
}
