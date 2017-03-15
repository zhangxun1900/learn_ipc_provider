package cn.demo.zx_provider_learn;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.demo.zx_provider_learn.dao.PersonDao;
import cn.demo.zx_provider_learn.domain.ContactInfo;
import cn.demo.zx_provider_learn.domain.Person;
import cn.demo.zx_provider_learn.domain.SmsBean;

public class MainActivity extends AppCompatActivity {

    public PersonDao mPersonDao;

    public Uri uri_raw_contact = null;
    public Uri uri_data = null;

    public EditText et_name,et_phone, et_email,et_address,et_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPersonDao = new PersonDao(this);

        uri_raw_contact = Uri.parse("content://com.android.contacts/raw_contacts");
        uri_data = Uri.parse("content://com.android.contacts/data");

        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        et_address = (EditText) findViewById(R.id.et_address);
        et_company = (EditText) findViewById(R.id.et_company);
    }

    /**
     * 增
     * @param view
     */
    public void add(View view){
        mPersonDao.addPerson(new Person(1,"张",12));
    }

    /**
     * 删
     * @param view
     */
    public void delete(View view){
        mPersonDao.deletePerson(1);
    }

    /**
     * update
     * @param view
     */
    public void update(View view){
        mPersonDao.updatePerson(2);
    }

    /**
     * 查
     * @param view
     */
    public void find(View view){
        List<Person> list = mPersonDao.findAll();
        for(int i=0;i<list.size();i++){
            Person person = list.get(i);
            Toast.makeText(this, person.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短信备份
     * @param view
     */
    public void backupSms(View view){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<SmsBean>list = new ArrayList<>();
                Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), new String[]{"address", "date", "read", "body", "type"}, null, null, null);
                while (cursor.moveToNext()){
                    SmsBean smsBean = new SmsBean();
                    smsBean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                    smsBean.setDate(cursor.getString(cursor.getColumnIndex("date")));
                    smsBean.setRead(cursor.getString(cursor.getColumnIndex("read")));
                    smsBean.setBody(cursor.getString(cursor.getColumnIndex("body")));
                    smsBean.setType(cursor.getString(cursor.getColumnIndex("type")));
//                    Toast.makeText(this, smsBean.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("短信：："+smsBean.toString());
                    list.add(smsBean);
                }
            }
        }.start();
    }


    /**
     * 查询联系人
     * @param view
     */
    public void findContacts(View view){
        //获取联系人个数
        Cursor cursor = getContentResolver().query(uri_raw_contact, null, null, null, null);
        while (cursor.moveToNext()){
            String contact_id = cursor.getString(cursor.getColumnIndex("contact_id"));
            Cursor c = getContentResolver().query(uri_data, null, "raw_contact_id=?", new String[]{contact_id}, null);
            ContactInfo info = new ContactInfo();
            while (c.moveToNext()){
                String mimetypes = c.getString(c.getColumnIndex("mimetype"));
                if("vnd.android.cursor.item/name".equals(mimetypes)){
                    info.setName(c.getString(c.getColumnIndex("data1")));
                }else if("vnd.android.cursor.item/phone_v2".equals(mimetypes)){
                    info.setPhone(c.getString(c.getColumnIndex("data1")));
                }else if("vnd.android.cursor.item/email_v2".equals(mimetypes)){
                    info.setEmail(c.getString(c.getColumnIndex("data1")));
                }else if("vnd.android.cursor.item/organization".equals(mimetypes)){
                    info.setCompany(c.getString(c.getColumnIndex("data1")));
                }else if("vnd.android.cursor.item/postal-address_v2".equals(mimetypes)){
                    info.setAddress(c.getString(c.getColumnIndex("data1")));
                }
            }
            Toast.makeText(this, info.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 添加联系人
     * @param view
     */
    public void addContacts(View view){
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String email = et_email.getText().toString();
        String address = et_address.getText().toString();
        String company = et_company.getText().toString();


        //向raw_contact表中添加联系人个数
        ContentValues values = new ContentValues();
        Uri uri = getContentResolver().insert(uri_raw_contact, values);
        long id = ContentUris.parseId(uri);
        Cursor cursor = getContentResolver().query(uri_raw_contact, new String[]{"contact_id"}, "_id=?", new String[]{id + ""}, null);
        String contact_id = null;
        while(cursor.moveToNext()){
            contact_id = cursor.getString(cursor.getColumnIndex("contact_id"));
        }

        //添加联系人名称
        values.clear();
        values.put("mimetype","vnd.android.cursor.item/name");
        values.put("raw_contact_id",contact_id);
        values.put("data1",name);
        getContentResolver().insert(uri_data,values);

        //添加联系人手机号
        values.clear();
        values.put("mimetype","vnd.android.cursor.item/phone_v2");
        values.put("raw_contact_id",contact_id);
        values.put("data1",phone);
        getContentResolver().insert(uri_data,values);

        //添加联系人邮箱
        values.clear();
        values.put("mimetype","vnd.android.cursor.item/email_v2");
        values.put("raw_contact_id",contact_id);
        values.put("data1",email);
        getContentResolver().insert(uri_data,values);

        //添加联系人地址
        values.clear();
        values.put("mimetype","vnd.android.cursor.item/postal-address_v2");
        values.put("raw_contact_id",contact_id);
        values.put("data1",address);
        getContentResolver().insert(uri_data,values);

        //添加联系人公司
        values.clear();
        values.put("mimetype","vnd.android.cursor.item/organization");
        values.put("raw_contact_id",contact_id);
        values.put("data1",company);

        getContentResolver().insert(uri_data,values);
    }

}
