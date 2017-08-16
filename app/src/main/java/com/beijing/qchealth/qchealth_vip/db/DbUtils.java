package com.beijing.qchealth.qchealth_vip.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import org.haitao.common.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhy on 2017/7/4.
 */

public class DbUtils {
    
    private DbOpenHelper helper;
    private String TAG = this.getClass().getSimpleName();

    public DbUtils(Context context) {
       super();
        helper=new DbOpenHelper(context);
    }

    /**
     *  删除表格所有数据
     * @param tableName
     */
    public void deleteTableData(String tableName){

        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            db.execSQL("delete from " + tableName);
        }catch (SQLException e) {
            AppLog.i(TAG,"清空表失败");
        }finally {
            db.close();
        }
    }

    /**
     *    查询所有用户
     * @return
     */
    public List<User> queryAllUser(){
        
        List<User>  list=new ArrayList<>();
        SQLiteDatabase db=helper.getWritableDatabase();
        try {
            Cursor cursor=db.query(DbOpenHelper.USER_TABLE,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                User user=new User();
                user.setId(cursor.getString(cursor.getColumnIndex("id")));
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
                user.setRealName(cursor.getString(cursor.getColumnIndex("realName")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
                user.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
                user.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
                user.setNation(cursor.getString(cursor.getColumnIndex("nation")));
                user.setState(cursor.getInt(cursor.getColumnIndex("state")));
                user.setRingId(cursor.getString(cursor.getColumnIndex("ringId")));
                user.setRegistrationId(cursor.getString(cursor.getColumnIndex("registrationId")));
                user.setProvinceCode(cursor.getString(cursor.getColumnIndex("provinceCode")));
                user.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
                user.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
                user.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
                user.setCountyCode(cursor.getString(cursor.getColumnIndex("countyCode")));
                user.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
                user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                user.setAvatar(cursor.getString(cursor.getColumnIndex("avatar")));
                user.setStature(cursor.getString(cursor.getColumnIndex("stature")));
                user.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
                user.setEducation(cursor.getInt(cursor.getColumnIndex("education")));
                user.setVocation(cursor.getInt(cursor.getColumnIndex("vocation")));
                list.add(user);
            }
            cursor.close();
            db.close();
            
        }catch (SQLiteException e){
            e.printStackTrace();
           
        }finally {
            db.close();
        }
        return list;
    }


    /**
     *           更新用户信息
     * @param values
     */
    public void updataUserInfo(ContentValues values){
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            if (values!=null){
                db.update(DbOpenHelper.USER_TABLE,values,"id=?",new String[]{/*MyApplication.getInstance().user.getId()*/});
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }


    public long insertUser(User user){
        long ret=-1;
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues  values=new ContentValues();
        values.put("id",user.getId());
        values.put("name",user.getName());
        values.put("nickName",user.getNickName());
        values.put("realName",user.getMobile());
        values.put("password",user.getAvatar());
        values.put("mobile",user.getMobile());
        values.put("gender",user.getGender());
        values.put("birthday",user.getBirthday());
        values.put("nation",user.getNation());
        values.put("state",user.getState());
        values.put("ringId",user.getRingId());
        values.put("registrationId",user.getRegistrationId());
        values.put("provinceCode",user.getProvinceCode());
        values.put("provinceName",user.getProvinceName());
        values.put("cityCode",user.getCityCode());
        values.put("cityName",user.getCityName());
        values.put("countyCode",user.getCountyCode());
        values.put("countyName",user.getCountyName());
        values.put("address",user.getAddress());
        values.put("avatar",user.getAvatar());
        values.put("stature",user.getStature());
        values.put("weight",user.getWeight());
        values.put("education",user.getEducation());
        values.put("vocation",user.getVocation());
       

        try {
            ret=db.insert(DbOpenHelper.USER_TABLE,null,values);
            AppLog.e("err", "insert user success");
        }catch (Exception e){
            AppLog.e("err", "insert user failed"+e);
            e.printStackTrace();
        }finally {
            db.close();
        }
        return ret;

    }
    
    
    
    
}
