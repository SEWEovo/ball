package com.example.administrator.ball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by Administrator on 2018/6/7.
 */

public class ShopCarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
//        String[] desc={"1233","2334","21321"};
//        String[] name={"一号","二号","三号"};
//        String[] price={"12.3","13.4","12.5"};
        int[] images={R.drawable.a,R.drawable.b,R.drawable.c};


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);
        TextView total=(TextView)findViewById(R.id.total);
//        Button add =(Button) findViewById(R.id.add);
//        Button buy=(Button) findViewById(R.id.buy);
//
        Intent intent=getIntent();
        final String json=(String )intent.getStringExtra("json");
        ArrayList<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
        try {
            JSONArray jsonArray = null;
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", jsonObject.getString("name"));
                map.put("description", jsonObject.getString("description"));
                map.put("price", jsonObject.getString("price"));
                map.put("sum", jsonObject.getString("sum"));
                map.put("image",images[i]);
                list2.add(map);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list2,R.layout.child,
                new String[]{"name","image","description","price","sum"},
                new int[]{R.id.name,R.id.pic,R.id.desc,R.id.price,R.id.sum});
        ListView list=(ListView)findViewById(R.id.listview);
        list.setAdapter(simpleAdapter);


//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//        buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });



    }

}
