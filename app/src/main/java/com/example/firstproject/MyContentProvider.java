package com.example.firstproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.room.Room;

public class MyContentProvider extends ContentProvider {

    private ProductDAO productDAO;

    private static final int ALL_PRODUCTS = 1;
    private static final int SINGLE_PRODUCT = 2;

    private static final String AUTHORITY = "com.example.myprovider";

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher((UriMatcher.NO_MATCH));
        uriMatcher.addURI(AUTHORITY, "products", ALL_PRODUCTS);
        uriMatcher.addURI(AUTHORITY, "product/#", SINGLE_PRODUCT);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch(uriMatcher.match(uri)){
            case ALL_PRODUCTS:
                throw new IllegalArgumentException("Zly URI: " + uri);
            case SINGLE_PRODUCT:
                AppDatabase.getInstance(getContext()).getProductDAO()
                        .deleteById(selection);
            default:
                throw new IllegalArgumentException("Zly URI: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)){
            case ALL_PRODUCTS:
                return "vnd.android.cursor.dir/vnd.com.example.myprovider.product";
            case SINGLE_PRODUCT:
                return "vnd.android.cursor.item/vnd.com.example.myprovider.product";
            default:
                throw new IllegalArgumentException("Zly URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch(uriMatcher.match(uri)){
            case ALL_PRODUCTS:
                AppDatabase.getInstance(getContext())
                        .getProductDAO()
                        .insertAll(ListItem.fromContentValues(values));
            case SINGLE_PRODUCT:
                AppDatabase.getInstance(getContext())
                        .getProductDAO()
                        .insertAll(ListItem.fromContentValues(values));
            default:
                throw new IllegalArgumentException("Zly URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {

        productDAO = Room.databaseBuilder(getContext(), AppDatabase.class, "product")
                .allowMainThreadQueries()
                .build()
                .getProductDAO();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch(uriMatcher.match(uri)){
            case ALL_PRODUCTS:
                return cursor = productDAO.selectAll();
            case SINGLE_PRODUCT:
                return cursor = AppDatabase.getInstance(getContext())
                        .getProductDAO()
                        .selectById(selection);
            default:
                throw new IllegalArgumentException("Zly URI: " + uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch(uriMatcher.match(uri)){
            case ALL_PRODUCTS:
                AppDatabase.getInstance(getContext())
                        .getProductDAO()
                        .update(ListItem.fromContentValues(values));
            case SINGLE_PRODUCT:
                AppDatabase.getInstance(getContext())
                        .getProductDAO()
                        .update(ListItem.fromContentValues(values));
            default:
                throw new IllegalArgumentException("Zly URI: " + uri);
        }
    }
}
