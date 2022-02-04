package com.example.natan.storagehoodieholic.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.natan.storagehoodieholic.model.Barang;
import com.example.natan.storagehoodieholic.model.Transaksi;
import com.example.natan.storagehoodieholic.model.User;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hoodieholic.sql";
    private static final int DATABASE_VERSION = 8;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_BARANG = "CREATE TABLE "+ Barang.Entry.TABLE_NAME+ " ( "+
                Barang.Entry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Barang.Entry.COLUMN_ID+" INTEGER,"+
                Barang.Entry.COLUMN_NAMA_BARANG+" TEXT,"+
                Barang.Entry.COLUMN_IMAGE+" TEXT,"+
                Barang.Entry.COLUMN_STOK+" INTEGER );";
        sqLiteDatabase.execSQL(CREATE_TABLE_BARANG);

        String CREATE_TABLE_TRANSAKSI = "CREATE TABLE "+ Transaksi.Entry.TABLE_NAME+ " ( "+
                Transaksi.Entry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Transaksi.Entry.COLUMN_ID+" INTEGER,"+
                Transaksi.Entry.COLUMN_ID_BARANG+" TEXT,"+
                Transaksi.Entry.COLUMN_JUMLAH+" INTEGER,"+
                Transaksi.Entry.COLUMN_NAMA_BARANG+" TEXT,"+
                Transaksi.Entry.COLUMN_NAMA_USER+" TEXT,"+
                Transaksi.Entry.COLUMN_UPDATED+" TEXT,"+
                Transaksi.Entry.COLUMN_GAMBAR +" TEXT,"+
                Transaksi.Entry.COLUMN_USER_ID+" INTEGER );";
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSAKSI);

        String CREATE_TABLE_USER = "CREATE TABLE "+ User.Entry.TABLE_NAME+ " ( "+
                User.Entry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                User.Entry.COLUMN_ID+" INTEGER,"+
                User.Entry.COLUMN_NAME+" TEXT,"+
                User.Entry.COLUMN_USERNAME+" TEXT );";
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_BARANG = "DROP TABLE "+Barang.Entry.TABLE_NAME+";";
        String DROP_TABLE_TRANSAKSI = "DROP TABLE "+Transaksi.Entry.TABLE_NAME+";";
        String DROP_TABLE_USER = "DROP TABLE "+User.Entry.TABLE_NAME+";";

        sqLiteDatabase.execSQL(DROP_TABLE_BARANG);
        sqLiteDatabase.execSQL(DROP_TABLE_TRANSAKSI);
        sqLiteDatabase.execSQL(DROP_TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public void createBarang(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String CREATE_TABLE_BARANG = "CREATE TABLE "+ Barang.Entry.TABLE_NAME+ " ( "+
                Barang.Entry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Barang.Entry.COLUMN_ID+" INTEGER,"+
                Barang.Entry.COLUMN_NAMA_BARANG+" TEXT,"+
                Barang.Entry.COLUMN_IMAGE+" TEXT,"+
                Barang.Entry.COLUMN_STOK+" INTEGER );";
        sqLiteDatabase.execSQL(CREATE_TABLE_BARANG);
    }

    public void createTransaksi(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String CREATE_TABLE_TRANSAKSI = "CREATE TABLE "+ Transaksi.Entry.TABLE_NAME+ " ( "+
                Transaksi.Entry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Transaksi.Entry.COLUMN_ID+" INTEGER,"+
                Transaksi.Entry.COLUMN_ID_BARANG+" TEXT,"+
                Transaksi.Entry.COLUMN_JUMLAH+" INTEGER,"+
                Transaksi.Entry.COLUMN_NAMA_BARANG+" TEXT,"+
                Transaksi.Entry.COLUMN_NAMA_USER+" TEXT,"+
                Transaksi.Entry.COLUMN_UPDATED+" TEXT,"+
                Transaksi.Entry.COLUMN_GAMBAR +" TEXT,"+
                Transaksi.Entry.COLUMN_USER_ID+" INTEGER );";
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSAKSI);
    }

    public void createUser(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String CREATE_TABLE_USER = "CREATE TABLE "+ User.Entry.TABLE_NAME+ " ( "+
                User.Entry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                User.Entry.COLUMN_ID+" INTEGER,"+
                User.Entry.COLUMN_NAME+" TEXT,"+
                User.Entry.COLUMN_USERNAME+" TEXT );";
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    public void insertBarang(int id, String nama_barang, int stok, String image){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Barang.Entry.COLUMN_ID, id);
        contentValues.put(Barang.Entry.COLUMN_NAMA_BARANG, nama_barang);
        contentValues.put(Barang.Entry.COLUMN_STOK, stok);
        contentValues.put(Barang.Entry.COLUMN_IMAGE, image);

        sqLiteDatabase.insert(Barang.Entry.TABLE_NAME, null, contentValues);
    }

    public void insertTransaksi(int id, int id_barang, int jumlah, int user_id, String updated_at, String nama_user, String nama_barang, String gambar){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Transaksi.Entry.COLUMN_ID, id);
        contentValues.put(Transaksi.Entry.COLUMN_ID_BARANG, id_barang);
        contentValues.put(Transaksi.Entry.COLUMN_JUMLAH, jumlah);
        contentValues.put(Transaksi.Entry.COLUMN_USER_ID, user_id);
        contentValues.put(Transaksi.Entry.COLUMN_NAMA_BARANG, nama_barang);
        contentValues.put(Transaksi.Entry.COLUMN_NAMA_USER, nama_user);
        contentValues.put(Transaksi.Entry.COLUMN_GAMBAR, gambar);
        contentValues.put(Transaksi.Entry.COLUMN_UPDATED, updated_at);
        sqLiteDatabase.insert(Transaksi.Entry.TABLE_NAME, null, contentValues);
    }

    public void insertUser(int id, String name, String username){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.Entry.COLUMN_ID, id);
        contentValues.put(User.Entry.COLUMN_NAME, name);
        contentValues.put(User.Entry.COLUMN_USERNAME, username);

        sqLiteDatabase.insert(User.Entry.TABLE_NAME, null, contentValues);
    }

    public List<Barang> selectBarang(){
        List<Barang> barangs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(Barang.Entry.TABLE_NAME, null, null, null, null,
                null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Barang.Entry.COLUMN_ID));
                String nama = cursor.getString(cursor.getColumnIndex(Barang.Entry.COLUMN_NAMA_BARANG));
                int stok = cursor.getInt(cursor.getColumnIndex(Barang.Entry.COLUMN_STOK));
                String image = cursor.getString(cursor.getColumnIndex(Barang.Entry.COLUMN_IMAGE));

                Barang tmp = new Barang(id, nama, stok, image);
                barangs.add(tmp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return barangs;
    };

    public List<Transaksi> selectTransaksi(){
        List<Transaksi> transaksis = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(Transaksi.Entry.TABLE_NAME, null, null, null, null,
                null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Transaksi.Entry.COLUMN_ID));
                int id_barang = cursor.getInt(cursor.getColumnIndex(Transaksi.Entry.COLUMN_ID_BARANG));
                int id_user = cursor.getInt(cursor.getColumnIndex(Transaksi.Entry.COLUMN_USER_ID));
                String nama_barang = cursor.getString(cursor.getColumnIndex(Transaksi.Entry.COLUMN_NAMA_BARANG));
                String nama_user = cursor.getString(cursor.getColumnIndex(Transaksi.Entry.COLUMN_NAMA_USER));
                String gambar = cursor.getString(cursor.getColumnIndex(Transaksi.Entry.COLUMN_GAMBAR));
                int jumlah = cursor.getInt(cursor.getColumnIndex(Transaksi.Entry.COLUMN_JUMLAH));
                String updated_at = cursor.getString(cursor.getColumnIndex(Transaksi.Entry.COLUMN_UPDATED));

                Transaksi tmp = new Transaksi(jumlah, updated_at, id_barang, id_user, nama_barang, id, nama_user, gambar);
                transaksis.add(tmp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return transaksis;
    };

    public void dropTabel(String tb_name){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String DROP_TABLE = "DROP TABLE "+tb_name+";";
        sqLiteDatabase.execSQL(DROP_TABLE);
    }

    public void upgradeBarang(){
        dropTabel(Barang.Entry.TABLE_NAME);
        createBarang();
    }

    public void upgradeUser(){
        dropTabel(User.Entry.TABLE_NAME);
        createUser();
    }

    public void upgradeTransaksi(){
        dropTabel(Transaksi.Entry.TABLE_NAME);
        createTransaksi();
    }

}
