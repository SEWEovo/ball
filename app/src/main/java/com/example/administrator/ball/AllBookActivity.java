package com.example.administrator.ball;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2018/6/6.
 */
public class AllBookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        int[] images={R.drawable.a,R.drawable.b,R.drawable.c};
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_allbook);
        Intent intent=getIntent();
        final String username=intent.getStringExtra("username");
        final String json=(String )intent.getStringExtra("json");
        ArrayList<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
            try {
                JSONArray jsonArray = null;
                jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("id", jsonObject.getString("id"));
                    map.put("name", jsonObject.getString("name"));
                    map.put("description", jsonObject.getString("description"));
                    map.put("price", jsonObject.getString("price"));
                    map.put("image",images[i]);
                    list2.add(map);
                }
            }
        catch (Exception e){
            e.printStackTrace();
        }
        Button btn=(Button)findViewById(R.id.look);
        Button car=(Button)findViewById(R.id.car);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler myHandler = new Handler(){
                    public void handleMessage(Message msg){
                        String responseResult = (String)msg.obj;
                        // 获取请求的json数据，并解析

                        Bundle data=new Bundle();
                        data.putString("json",responseResult);
                        data.putString("username",username);
                        Intent intent=new Intent(AllBookActivity.this,ShopCarActivity.class);
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
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list2,R.layout.booklist,
                new String[]{"name","image","description","price"},
                new int[]{R.id.name,R.id.head,R.id.desc,R.id.price});
        ListView list=(ListView)findViewById(R.id.listView);
        list.setAdapter(simpleAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String id=String.valueOf(i+1);
                final Handler myHandler = new Handler(){
                    public void handleMessage(Message msg){
                        String responseResult = (String)msg.obj;

                        Bundle data=new Bundle();
                        data.putString("json",responseResult);
                        data.putString("username",username);
                        Intent intent=new Intent(AllBookActivity.this,BookActivity.class);
                        intent.putExtras(data);
                        startActivity(intent);
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BooKToServer allBookToServer = new BooKToServer();
                        try {
                            String result = allBookToServer.doPost(id);
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
