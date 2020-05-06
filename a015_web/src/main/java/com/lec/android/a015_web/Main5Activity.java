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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
public class Main5Activity extends AppCompatActivity {

    private TextView tvResult;
    private EditText et;
    private Button btnXML;
    private Button btnJSON;
    private Button btnParse;

    //나의 지하철 api 키
    String api_key = "4d46796d7366726f3833774a774955";
    //무슨 타입인지
    String reqType;
    //인코더 한 역 이름
    String reqSearchStr;
    // 주소
    String urlAddress;

    //
    String reqService = "stationInfo";
    int reqStartIndex = 1;
    int reqEndIndex = 5;

    StringBuffer sb;
    HttpURLConnection conn = null;
    BufferedReader br;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        tvResult = findViewById(R.id.tvResult);
        btnXML = findViewById(R.id.btnXML);
        btnJSON = findViewById(R.id.btnJSON);
        btnParse = findViewById(R.id.btnParse);
        et = findViewById(R.id.editText);


        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqType = "xml";
                // EditText 에 입력된 역 이름의 공백제거.
                reqSearchStr = et.getText().toString().trim().replaceAll(" ", "");

                // url 에 한글이 들어가는 경우 URLEncode 를 해야 한다.
                try {
                    reqSearchStr = URLEncoder.encode(reqSearchStr, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if( reqSearchStr.length() > 0) {
                    urlAddress = "http://swopenAPI.seoul.go.kr/api/subway/" +
                            api_key + "/" + reqType + "/" + reqService + "/" +
                            reqStartIndex + "/" + reqEndIndex + "/" + reqSearchStr;

                    sendRequest(); // 웹에서 html 읽어오기
                } // end if
            } // end onClick()
        });


    }//end onCreate()

    void sendRequest() { // 웹에서 html 읽어오기
        tvResult.setText("");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sb = new StringBuffer();

                try {
                    URL url = new URL(urlAddress);

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
    } // end sendRequest()



}//end activity
