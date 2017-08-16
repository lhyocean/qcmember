package com.beijing.qchealth.qchealth_vip.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lhy on 2017/7/4.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    
    private Context mContext;
    public static final String DB_NAME="QcHealthMember";
    public static final int DB_VERSION=1;
    public static final String USER_TABLE="user";
    

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        try {
            String userSql="create table "+USER_TABLE+"(id varchar PRIMARY KEY NOT NULL ,name varchar,nickName varchar," +
                    "realName varchar,password varchar,mobile varchar,gender integer,birthday varchar,nation varchar," +
                    "state integer,ringId varchar,registrationId varchar,provinceCode varchar,provinceName varchar," +
                    "cityCode varchar,cityName varchar,countyCode varchar,countyName varchar,address varchar,avatar varchar," +
                    "stature varchar,weight varchar,education integer,vocation integer,createDate varchar" +
                    ")";
            db.execSQL(userSql);
            
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);

    }
}
