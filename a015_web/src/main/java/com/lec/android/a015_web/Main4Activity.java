package com.lec.android.a015_web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/* XML, JSON 파싱 연습
 *
 * ■서울시 지하철 역사 정보
http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-12753&srvType=A&serviceKind=1&currentPageNo=1
샘플url
XML 버젼
http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/xml/stationInfo/1/5/서울
JSON 버젼
http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/json/stationInfo/1/5/서울
 */

// 32강 com.lec.java.crawl11 참조
public class Main4Activity extends AppCompatActivity {

    EditText editText;
    Button btnXML,btnJSON,btnParse;
    TextView tvResult;
    String url_address;
    String hangle="";
    String reqtype = "";
    String sttinfo ="stationInfo";
    int startIndex = 1;
    int endIndex =5;
    Handler handler = new Handler(); // 화면에 그려주기 위한 객체
    HttpURLConnection conn;
    StringBuffer sb;

    private String apikey= "4d46796d7366726f3833774a774955";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        editText =findViewById(R.id.editText);
        btnXML = findViewById(R.id.btnXML);
        btnJSON = findViewById(R.id.btnJSON);
        btnParse = findViewById(R.id.btnParse);
        tvResult =findViewById(R.id.tvResult);


        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqtype = "xml";
                String name = editText.getText().toString();
                try {
                    hangle=URLEncoder.encode(name, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                url_address ="http://swopenAPI.seoul.go.kr/api/subway/"+
                        apikey +"/"+ reqtype+"/"+sttinfo+"/"+startIndex+"/"+endIndex+"/"
                        +hangle;
                sendRequest();



            }
        });

    }

    void sendRequest() { // 웹에서 html 읽어오기
        tvResult.setText("");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sb = new StringBuffer();

                try {
                    URL url = new URL(url_address);

                    conn = (HttpURLConnection)url.openConnection();// 접속
                    if (conn != null) {
                        conn.setConnectTimeout(2000);
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                        conn.setUseCaches(false);
                        conn.connect();

                        if (conn.getResponseCode()
                                == HttpURLConnection.HTTP_OK){  // 200
                            // 데이터 읽기
                            BufferedReader br
                                    = new BufferedReader(new InputStreamReader
                                    (conn.getInputStream(),"utf-8"));//"utf-8"
                            while(true) {
                                String line = br.readLine();
                                if (line == null) break;
                                sb.append(line+"\n");
                            }
                            br.close(); // 스트림 해제
                        }else{
                            Log.d("text", "getResponseCode(): " + conn.getResponseCode());
                        }
                        conn.disconnect(); // 연결 끊기
                    }else{
                        Log.d("test", "conn NULL!");
                    }

                    // 값을 출력하기
                    Log.d("test", sb.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvResult.setText(sb.toString());
                        } // end run()
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start(); // 쓰레드 시작
    }
}
