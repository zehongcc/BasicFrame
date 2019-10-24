package com.czh.basicframe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.czh.basicframe.base.BaseApplication;

/**
 * author  : czh
 * create Date : 2019/10/24  13:54
 * 详情 :
 */
public class SQHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BF_DB";//数据库的名字

    public static final String TABLE_NAME_1 = "test_user_1";//表名
    public static final String TABLE_NAME_2 = "test_book_2";//表名


    public SQHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_NAME_1 + "(_id Integer primary key autoincrement , name varchar (10) , age Integer(3))" );
        db.execSQL("create table " + TABLE_NAME_2 + "(_id Integer primary key autoincrement , name varchar (20) , number Integer(666))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
