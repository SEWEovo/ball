package com.example.administrator.ball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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
        final String json=(String )intent.getStringExtra("json");
        try{
                JSONObject jsonObject =new JSONObject(json);
                String  id=jsonObject.getString(("id"));
                String name=jsonObject.getString("name");
                String price=jsonObject.getString("price");
                String description=jsonObject.getString("description");
                Log.d("name",name);
                bookname.setText(name);
                bookprice.setText(price);
                bookdesc.setText(description);
                pic.setImageResource(images[Integer.parseInt(id)]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
    }
}
