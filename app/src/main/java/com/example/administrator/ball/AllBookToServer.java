package com.example.administrator.ball;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/6.
 */

public class AllBookToServer {
    //由于Tomcat服务器部署在本地，url为本地Tomcat服务的url，IP地址为本地主机IP地址
    private String url = "http://192.168.1.103:8080/Test/AllBookServlet";
    //服务器返回的结果
    String result = "";

    /**
     * 使用Post方式向服务器发送请求并返回响应
     * @return
     */
    public String doPost() throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        //如果响应成功
        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            //得到信息体
            HttpEntity entity = httpResponse.getEntity();
            InputStream inputStream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String readLine = null;

            while((readLine = br.readLine()) != null){
                result += readLine;
            }
            inputStream.close();
            return result;
        }
        //响应失败
        else{
            return "Error";
        }
    }

}
