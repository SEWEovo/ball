package com.example.administrator.ball;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2018/6/6.
 */

public class BookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int[] images={R.drawable.a,R.drawable.b,R.drawable.c};
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_book);
        Button add =(Button) findViewById(R.id.add);
        Button buy=(Button) findViewById(R.id.buy);
        ImageView pic=(ImageView)findViewById(R.id.book);
        TextView bookname=(TextView)findViewById(R.id.name) ;
        TextView bookprice=(TextView)findViewById(R.id.price);
        TextView bookdesc=(TextView)findViewById(R.id.desc);

        Intent intent=getIntent();
        final String json=(String)intent.getStringExtra("json");
        final String username=intent.getStringExtra("username");

        try{
                JSONObject jsonObject =new JSONObject(json);
                String id=jsonObject.getString(("id"));
                String name=jsonObject.getString("name");
                String price=jsonObject.getString("price");
                String description=jsonObject.getString("description");
                bookname.setText(name);
                bookprice.setText("价格："+price);
                bookdesc.setText(description);

                pic.setImageResource(images[Integer.parseInt(id)-1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        final String name1=bookname.getText().toString().trim();
        final String price1=bookprice.getText().toString().trim();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler myHandler = new Handler(){
                    public void handleMessage(Message msg){
                        String responseResult = (String)msg.obj;
                        // 获取请求的json数据，并解析
                        Bundle data=new Bundle();
                        data.putString("json",responseResult);
                        data.putString("username",username);
                        Intent intent=new Intent(BookActivity.this,ShopCarActivity.class);
                        intent.putExtras(data);
                        startActivity(intent);
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ShopCarToServer allBookToServer = new ShopCarToServer();
                        try {
                            String result = allBookToServer.doPost(username);
                            Message msg = new Message();
                            msg.obj = result;
                            myHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler myHandler = new Handler(){
                    public void handleMessage(Message msg){
                        String responseResult = (String)msg.obj;
                            Toast.makeText(BookActivity.this, "购买成功！", Toast.LENGTH_LONG).show();;
                        Toast.makeText(BookActivity.this, name1, Toast.LENGTH_LONG).show();

                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ShopToServer shopToServer=new ShopToServer();
                        try {
                            String result = shopToServer.doPost(username,name1,price1);
                            Message msg = new Message();
                            msg.obj = result;
                            myHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
