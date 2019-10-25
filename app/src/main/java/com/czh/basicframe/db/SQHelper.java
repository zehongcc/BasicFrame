package com.czh.basicframe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.czh.basicframe.base.BaseApplication;
import com.czh.basicframe.utils.LogUtils;

import static com.czh.basicframe.utils.Code.DB_VERSION;

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
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.e("SQHelper", ">>>>>> onCreate >>>>>>> ");
        //第一次会调用创建方法，仅此一次
        db.execSQL("Create table " + TABLE_NAME_1 + "(_id Integer primary key autoincrement , name varchar (10) , age Integer(3))");
        db.execSQL("create table " + TABLE_NAME_2 + "(_id Integer primary key autoincrement , name varchar (20) , number Integer(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.e("SQHelper", ">>>>>> !! onUpgrade !! >>>>>>> ");
        //版本号升级的时候会调用此方法
        db.execSQL("alter table " + TABLE_NAME_1 + " add sex boolean");
    }
}
