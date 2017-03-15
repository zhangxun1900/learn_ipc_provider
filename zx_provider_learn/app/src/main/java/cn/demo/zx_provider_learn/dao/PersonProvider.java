package cn.demo.zx_provider_learn.dao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 14:57
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/3/13.
 */

public class PersonProvider extends ContentProvider {
    private static final int PERSON = 1;
    /**
     * UriMatcher是Uri匹配器，如果传递过来的Uri不符合就返回-1
     * 如果传递过来的Uri是content://cn.demo.zx_provider_learn.dao/chaperson，那么返回
     */
    public static UriMatcher matcher = new UriMatcher(-1);

    /**
     * 添加Uri，如果传递的Uri在UriMatcher中存在，那么就说明可以有操作的方法，否则就没有
     * authority: 主机名，与清单文件AndroidManifest.xml中注册的一致
     * path:数据库的路径，可以随便起名，
     * code:如果匹配后返回的结果
     */
    static {
        matcher.addURI("cn.demo.zx_provider_learn.dao","chaperson",PERSON);
    }

    MySQLiteOpenHelper mHelper = null;

    @Override
    public boolean onCreate() {
        mHelper = new MySQLiteOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(matcher.match(uri)==PERSON){
            SQLiteDatabase db = mHelper.getReadableDatabase();
            return db.query("person",projection,selection,selectionArgs,null,null,sortOrder);
        }else{
            throw new IllegalArgumentException("这个Uri不处理。。。。。。。。。。。");
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(matcher.match(uri)==PERSON) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            long insert = db.insert("person", null, values);
            return Uri.parse("content://cn.demo.zx_provider_learn.dao/chaperson/" + insert);
        }else{
            throw new IllegalArgumentException("这个Uri不处理。。。。。。。。。。。");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if(matcher.match(uri)==PERSON) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            int delete = db.delete("person", selection, selectionArgs);
            return delete;
        }else{
            throw new IllegalArgumentException("这个Uri不处理。。。。。。。。。。。");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if(matcher.match(uri)==PERSON) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            int update = db.update("person", values, selection, selectionArgs);
            return update;
        }else{
            throw new IllegalArgumentException("这个Uri不处理。。。。。。。。。。。");
        }
    }
}
