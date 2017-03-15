package cn.demo.zx_provider_learn.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.demo.zx_provider_learn.domain.Person;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 14:36
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/3/13.
 */

public class PersonDao {
    public Context mContext;

    public MySQLiteOpenHelper mHelper = null;

    public PersonDao(Context context) {
        mContext = context;
        mHelper = new MySQLiteOpenHelper(context);
    }

    /**
     * 添加人的方法
     * @param p
     */
    public void addPerson(Person p){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",p.getName());
        values.put("age",p.getAge());
        db.insert("person",null,values);
        db.close();
    }

    /**
     * 删除人的方法
     */
    public void deletePerson(int id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete("person","id=?",new String[]{id+""});
        db.close();
    }

    /**
     * 修改
     * @param id
     */
    public void updatePerson(int id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name","王八蛋");
        values.put("age",1000);
        db.update("person",values,"id=?",new String[]{id+""});
        db.close();
    }

    /**
     * 查询
     */
    public List<Person> findAll(){
        List<Person>list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("person", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            Person p = new Person();
            p.setId(cursor.getInt(cursor.getColumnIndex("id")));
            p.setName(cursor.getString(cursor.getColumnIndex("name")));
            p.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            list.add(p);
        }
        return list;
    }
}
