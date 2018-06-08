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
/**
 * Created by Administrator on 2018/6/6.
 */

public class RegistActivity extends AppCompatActivity {
    //登录用户名输入框
    private EditText et_username;
    //登录密码输入框
    private EditText et_password;
    private EditText et_repassword;
    //登录按钮
    private Button bt_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        et_repassword=(EditText)findViewById(R.id.et_repassword);

        bt_regist= (Button)findViewById(R.id.bt_regist);
        //获取组件
        //对登录按钮的点击监控
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=et_password.getText().toString();
                String repassword=et_repassword.getText().toString();
                if(password.equals(repassword)){
                    final Handler myHandler = new Handler(){
                        public void handleMessage(Message msg){
                            String responseResult = (String)msg.obj;

                            if(responseResult.equals("123")){
                                Toast.makeText(RegistActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(RegistActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                            else{
                                Toast.makeText(RegistActivity.this, "用户名已存在！", Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RegistToServer guestToServer = new RegistToServer();
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
                else {
                    Toast.makeText(RegistActivity.this, "两次输入密码不一致！", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
