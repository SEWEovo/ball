package com.example.administrator.ball;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //登录用户名输入框
    private EditText et_username;
    //登录密码输入框
    private EditText et_password;
    //登录按钮
    private Button bt_login;
    private Button bt_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        bt_login = (Button)findViewById(R.id.bt_login);
        bt_regist=(Button)findViewById(R.id.bt_regist);
        //获取组件
        //对登录按钮的点击监控
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler myHandler = new Handler(){
                    public void handleMessage(Message msg){
                        String responseResult = (String)msg.obj;
                        //登录成功
                        if(responseResult.equals("123")){
                            String username=et_username.getText().toString();
                            Bundle data=new Bundle();
                            data.putString("username",username);
                            Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,IndexActivity.class);
                            intent.putExtras(data);
                            startActivity(intent);
                        }
//                        //登录失败
                        else{
                            Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GuestToServer guestToServer = new GuestToServer();
                        try {
                            String result = guestToServer.doPost(et_username.getText().toString().trim(), et_password.getText().toString().trim());
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