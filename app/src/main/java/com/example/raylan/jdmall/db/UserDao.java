package com.example.raylan.jdmall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.raylan.jdmall.cons.DbConst;

/**
 * Created by Raylan on 2017/7/11.
 */

public class UserDao {

    private DbOpenHelper mDbOpenHelper;

    public UserDao(Context c){
        mDbOpenHelper = new DbOpenHelper(c);
    }

    public boolean saveUser(String name,String pwd){
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(DbConst._NAME,name);
        values.put(DbConst._PWD,pwd);
        long insertId = db.insert(DbConst.USER_TABLE,null,values);
        return insertId!=-1;
    }

    public void clearUsers(){
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.delete(DbConst.USER_TABLE,null,null);
    }

    public UserInfo getLastUser(){
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(DbConst.USER_TABLE,
                new String[]{DbConst._NAME,DbConst._PWD},
                null,null,null,null,null);
        if (cursor.moveToFirst()){
            String name = cursor.getString(0);
            String pwd = cursor.getString(1);
            return new UserInfo(name,pwd);
        }
        return null;
    }

    public class UserInfo{
        public String username;
        public String userpwd;

        public UserInfo(String username, String userpwd) {
            this.username = username;
            this.userpwd = userpwd;
        }

    }
}
