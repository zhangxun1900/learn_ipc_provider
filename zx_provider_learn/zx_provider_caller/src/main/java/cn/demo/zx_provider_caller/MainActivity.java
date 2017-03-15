package cn.demo.zx_provider_caller;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.demo.zx_provider_caller.domain.Person;

public class MainActivity extends AppCompatActivity {

    public Uri mUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUri = Uri.parse("content://cn.demo.zx_provider_learn.dao/chaperson");
    }

    /**
     * 增加
     * @param view
     */
    public void add(View view){
        ContentValues values = new ContentValues();
        values.put("name","这是调用者");
        values.put("age",28);
        getContentResolver().insert(mUri,values);

    }

    /**
     * 删除
     * @param view
     */
    public void delete(View view){
        getContentResolver().delete(mUri,"id=?",new String[]{1+""});
    }

    /**
     * 修改
     * @param view
     */
    public void update(View view){
        ContentValues values = new ContentValues();
        values.put("name","小王八蛋");
        values.put("age",500);
        getContentResolver().update(mUri,values,"name=?",new String[]{"王八蛋"});
    }

    /**
     * 查
     * @param view
     */
    public void find(View view){
        Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
        while (cursor.moveToNext()){
            Person p = new Person();
            p.setId(cursor.getInt(cursor.getColumnIndex("id")));
            p.setName(cursor.getString(cursor.getColumnIndex("name")));
            p.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            Toast.makeText(this, p.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
