package com.example.yjkim.connectdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imView;
    TextView txtView;
    //TextView txtView2;
    Bitmap bmImg;
    phpDown task;
    Member m = new Member();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = new phpDown();
        txtView = (TextView) findViewById(R.id.txtView);
        imView = (ImageView) findViewById(R.id.imageView1);

        task.execute("http://192.168.0.6:8888/client/bowling2.php");

    }

    private class back extends AsyncTask<String, Integer, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try {
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                //String json = DownloadHtml("http://서버주소/appdata.php");
                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img) {
            imView.setImageBitmap(bmImg);
        }

    }

    private class phpDown extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... urls) {
            int count = 0;
            int counta = 0;
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {  // 1번쨰 for문 : 인원 체크
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                            count++;
                        }

                        System.out.println("count:" + count);
                        br.close();

                        //다시열어서 for문
                        /*
                        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {  // 1번쨰 for문 : 인원 체크
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br2.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                            counta++;
                        }
                        System.out.println("counta:" + counta);
                        br2.close();
                        */
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();

        } //doInBackground


        protected void onPostExecute(String str) {
            txtView.setText(str);

        }//onPostExecute
            /*
            String text = str;
            while(true){
                if(text){

                }


            }

            int i = str.indexOf(":"); // str문자열에서 첫번째 : 인덱스 갖고오기
             name = str.substring(i+1,i+4); //이름 세글자 가져오기 end는 i+4 직전

            System.out.println("이름 :"+ name); //콘솔창 출력
            txtView2.setText(name);
            */


    }//phpDown



}//Main

