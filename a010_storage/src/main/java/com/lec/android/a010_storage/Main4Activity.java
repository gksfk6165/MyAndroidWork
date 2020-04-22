package com.lec.android.a010_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// SQLite3 : 안드로이드에 기본탑재된 모바일 용으로 제작된 경량화 DB
//          C언어로 엔진이 제작되어 가볍다
//          안드로이드에서 sqLite3 를 쉽게 사용할 수 있도록 SQLiteOpenHelper클래스제공

// SQLite 를 사용한 데이터 저장
//   1. SQLiteOpenHelper 를 상속받은 클래스 정의
//      onCreate(), onUpgrade() 작성
//   2. 위 Helper 로부터 SQLiteDatabase  DB 객체 추출
//   3. DML 명령은 : execSQL()
//      SELECT 명령은 : rawQuery() --> 결과는 Cursor 객체 사용하여 다룸

// DML 이나 일반적인 SQL 문은 execSQL() 사용,
// 그러나 affected row 개수등 int 값을 리턴하지는 않음!
// int 값 받고 싶으면 SQLiteDatabase 의 insert(), update(), delete() 사용해야 한다

// ↑ 기존의 JDBC 프로그래밍과는 다른 독특한 메소드를 제공


public class Main4Activity extends AppCompatActivity {

    String dbName = "st_file2.db";
    int dbVersion = 1;
    MySQLiteOpenHelper4 helper;
    SQLiteDatabase db;
    String tableName = "student";

    EditText etName, etAge, etAddress;
    Button btnInsert, btnUpdate, btnDelete, btnSelect;
    TextView tvResult;   // 결과창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etAddress = findViewById(R.id.etAddress);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnSelect = findViewById(R.id.btnSelect);
        tvResult = findViewById(R.id.tvResult);

        helper = new MySQLiteOpenHelper4(
                this,
                dbName,
                null,
                dbVersion
        );
        try{
            db = helper.getWritableDatabase();
        }catch(SQLException e){
            e.printStackTrace();
            Log.e("myapp","데이터 베이스를 열수 없음");  //Logcat 의 error 에 표시됨
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String address = etAddress.getText().toString();

                if("".equals(name)){
                    tvResult.setText("INSERT 실패: 필수 항목 입력하세요");
                    return;
                }

                int a= 0 ;
                try{
                    a=Integer.parseInt(age);

                }catch (NumberFormatException e){
                    tvResult.setText("INSERT 실패 : age 는 숫자로 입력하세요");
                }

                insert(name,a,address);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String address = etAddress.getText().toString();

                if("".equals(name)){
                    tvResult.setText("UPDATE 실패: 필수 항목 입력하세요");
                    return;
                }

                int a= 0 ;
                try{
                    a=Integer.parseInt(age);

                }catch (NumberFormatException e){
                    tvResult.setText("UPDATE 실패 : age 는 숫자로 입력하세요");
                }
                update(name,a,address);
            }

        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();

                if("".equals(name)){
                    tvResult.setText("DELETE 실패 - 삭제할 이름을 입력하세요");
                    return;
                }
                delete(name);
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("");
                select();
            }
        });

    }//end onCreate()

    void insert(String name, int age ,String address){

        ContentValues values = new ContentValues();  //name- value 쌍으로 저장하는 객체

        //키, 값의 쌍으로 데이터 입력
        values.put("name",name);
        values.put("age",age);
        values.put("address",address);

        //INSERT INTO tableName(name,age,address) values(? ,? ,?)
        // 리턴값은 auto-generated key 값
        long result =  db.insert(tableName,null,values);

        Log.d("myapp",result+"개 row Insert 성공");
        tvResult.setText(result+"번째 row Insert 성공");
        select();
    }

    void select(){
        //SELECT 문을 위한 query() 메소스
        Cursor c =         db.query(tableName,null,null,null,null,null,null);

        // db.query("sku_table", columns, "owner=?", new String[] { "Mike" }, null, null, null);
        //                           --> WHERE owner='Mike'
        //  db.query("sku_table", columns, "owner=? AND price=?", new String[] { "Mike", "100" }, null, null, null);
            //                          --> WHERE owner='Mike' AND price=100

        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int age = c.getInt(2);
            String address = c.getString(3);
            String msg = String.format("id:%d, name:%s, age:%d, address: %s",id,name,age,address);

            Log.d("myapp", msg);
            tvResult.append("\n" + msg);

        }//end while

        //키보드 내리기
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"입력해주세요",Toast.LENGTH_LONG).show();
        }


    }//select()

    void update(String name,int age, String address){
        ContentValues values = new ContentValues();

        values.put("age",age);          //변경할 값
        values.put("address",address);  //변경할 값

        //UPDATE student SET age = ?, address = ? WHERE name = ?
        //리턴값은 affected rows
       int cnt= db.update(tableName, //테이블명
                values,    //변경할 값들
                "name=?",  // WHERE 조건절
                new String[]{name}    //조건절의 ?값들.
    );

        String msg = cnt+"개의 row update 성공";
        tvResult.setText(msg);
        select();
    }

    //특정 name 값을 가진 레코드(들) 삭제
    void delete(String name){
        //리턴값은 affected rows
        int cnt = db.delete(tableName,   //테이블명
                "name=?",  //where 조건절
                new String[]{name}   //위 조건절의 ? 값들
                );
        String msg = cnt+"개의 row delete 성공";
        tvResult.setText(msg);
        Log.d("myapp",msg);
        select();
    }



}//Activity
/*
   insert() 의 두번째 매개변수 nullColumnHack 의 의미
       어떤 테이블의 모든 컬럼이 NULL 입력이 가능하다고 하자.
       이 경우 INSERT INTO suchTable; 과 같이 SQL 구문을 작성할 수 있지만 SQLite3 에서는 유효한 구문이 아니다.
       이 대신 INSERT INTO suchTable(column) VALUES(NULL); 처럼 하나라도 값을 채워주어야 SQL 문이 정상적으로 수행한다.
       이처럼 입력하는 레코드의 데이터가 빈 경우, 즉 ContentValues 객체에 put() 메서드로 입력된 값이 없는 경우에는 두 번째 인자인 nullColumnHack 에 문자열로 테이블의 한 컬럼을 지정해줘야 에러 없이 정상적으로 SQL 문이 수행된다.
*/


