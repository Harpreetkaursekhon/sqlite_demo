package com.app.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText ed1,ed2,ed3,ed4;
SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.ed1);
        ed2=(EditText)findViewById(R.id.ed2);
        ed3=(EditText)findViewById(R.id.ed3);
        ed4=(EditText)findViewById(R.id.ed4);

        sqLiteDatabase=openOrCreateDatabase("empdb", Context.MODE_PRIVATE,null);

        //if not exists added as every time on run oncreate method will be call
        sqLiteDatabase.execSQL("create table if not exists employee(id number, name varchar(100), designation varchar(100), department varchar(100))");
    }



    public void update(View view) {

        ContentValues values=new ContentValues();

//these 3 records want to update
        values.put("name", ed2.getText().toString());
        values.put("designation", ed3.getText().toString());
        values.put("department", ed4.getText().toString());

        //on which basis and where we will update is "where id=?"
        //to check record is updated or not will add cursor
        long count=sqLiteDatabase.update("empoloyee", values, "id=?", new String[]{ed1.getText().toString()});
        if(count>0){
            Toast.makeText(getApplicationContext(), "record updated",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "record not updated",Toast.LENGTH_LONG).show();
        }

    }

    public void insert(View view) {

        //step2 create object for contentvalues
        ContentValues values=new ContentValues();
        //s3 now which data want to show on content
        values.put("id", Integer.parseInt(ed1.getText().toString()));
        values.put("name", ed2.getText().toString());
        values.put("designation", ed3.getText().toString());
        values.put("department", ed4.getText().toString());
            //s4 now to check if any record is inserted in db will add long count in insert method


        //step1 insert method 3params name of db, columnhack null always optional, object of content values so create it above
        long count=sqLiteDatabase.insert("employee", null,values);

        if(count>0){
            Toast.makeText(getApplicationContext(), "record inserted",Toast.LENGTH_LONG).show();

            //after data insertion, dont want to keep it on text boxes so to clear the data
            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
            ed4.setText("");

        } else{
            Toast.makeText(getApplicationContext(), "record is not inserted",Toast.LENGTH_LONG).show();
        }

    }

    public void read(View view) {

       Cursor cursor= sqLiteDatabase.query("employee", null, null, null, null, null, null);

       //now cursor will check from -1 to till the end of record
        //there are several records so put in while loop
        while (cursor.moveToNext()){


                 Toast.makeText(getApplicationContext(),
                         cursor.getInt(0)+ "\n"+cursor.getString(1)+ "\n"+
                                 cursor.getString(2)+ "\n" + cursor.getString(3),
                         Toast.LENGTH_LONG).show();
        }

    }
    public void delete(View view) {

      long count=  sqLiteDatabase.delete("employee", "id=?", new String[]{ed1.getText().toString()});
        if(count>0){
            Toast.makeText(getApplicationContext(), "record deleted",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "record not deleted",Toast.LENGTH_LONG).show();
        }

    }
}
