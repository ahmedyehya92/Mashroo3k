package com.intellidev.app.mashroo3k.data.dphelper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ahmed Yehya on 08/03/2018.
 */

public class ShoppingCartItemContract {
    public static final String CONTENT_AUTHORITY = "com.intellidev.app.mashroo3k";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEMS = "items";

    public static final class ItemEntry implements BaseColumns {
        public final static String TABLE_ITEMS = "items";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ITEM_ID = "item_id";
        public final static String COLUMN_ITEM_IMG_URL = "item_img_url";
        public final static String COLUMN_ITEM_TITLE = "item_title";
        public final static String COLUMN_ITEM_PRICE = "item_price";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ITEMS);
    }
}
