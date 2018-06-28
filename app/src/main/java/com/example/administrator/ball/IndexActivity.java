package com.example.administrator.ball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/6.
 */


public class IndexActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView,listView2,listView3;
        String[] desc={"1233","2334","21321"};
        String[] name={"一号","二号","三号"};
        int[] images={R.drawable.a,R.drawable.b,R.drawable.c};
        Intent intent=getIntent();
        final String username=intent.getStringExtra("username");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
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
                        Intent intent=new Intent(IndexActivity.this,ShopCarActivity.class);
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
        // 初始化布局 View视图
        initViews();

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();
        List<Map<String,Object>> listItems=new ArrayList<Map<String, Object>>();
        for(int i=0;i<name.length;i++){
            Map<String,Object>listItem=new HashMap<String, Object>();
            listItem.put("head",images[i]);
            listItem.put("name",name[i]);
            listItem.put("desc",desc[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,listItems,R.layout.bestlist,
                new String[]{"name","head","desc"},
                new int[]{R.id.name,R.id.head,R.id.desc});
        ListView list=(ListView)findViewById(R.id.listView);
        list.setAdapter(simpleAdapter);

        ListView list2=(ListView)findViewById(R.id.listView2);
        list2.setAdapter(simpleAdapter);

        ListView list3=(ListView)findViewById(R.id.listView3);
        list3.setAdapter(simpleAdapter);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Handler myHandler = new Handler(){
                   public void handleMessage(Message msg){
                       String responseResult = (String)msg.obj;
                       //获取请求的json数据，并解析
                           Bundle data=new Bundle();
                           data.putString("json",responseResult);
                           data.putString("username",username);
                           Intent intent=new Intent(IndexActivity.this,AllBookActivity.class);
                           intent.putExtras(data);
                           startActivity(intent);
                   }
               };

               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       AllBookToServer allBookToServer = new AllBookToServer();
                       try {
                           String result = allBookToServer.doPost();
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
        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }

            ;
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
//      viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);

        tv_desc = (TextView) findViewById(R.id.tv_desc);

    }

    /**
     * 初始化要显示的数据
     */
    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述
        contentDescs = new String[]{
                "流水有痕",
                "爱看书的人，运气不会太差",
                "孔子永远不会过时",
                "体会黑与白之美",
                "在书中寻找美"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.timg);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化: " + position);

//          newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }

}
