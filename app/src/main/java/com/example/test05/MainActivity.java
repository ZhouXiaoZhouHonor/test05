package com.example.test05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.test05.adapter.GridAdapter;
import com.example.test05.dao.Temperature;
import com.example.test05.service.GetXmlAndParse;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Temperature> list;//城市对象集合
    private List<String> list_name;//城市的名字集合
    private ListView listView;
    private GridView gridView;
    ArrayAdapter<String> arrayAdapter;
    /*class MyHandler extends  Handler{
        public void handleMessage(Message message){
            switch (message.what){
                case GetXmlAndParse.PARSESUCCWSS:
                    list=(List<Temperature>) message.obj;
                    Log.d("tempture",String.valueOf(list.size()));
                    initData();
                    break;
            }
            super.handleMessage(message);
        }
    }*/
    //Handler handler=new MyHandler();
    Handler handler=new Handler(){
        public void handleMessage(Message message){//实现该方法用于接收数据,匿名内部类
            Log.d("handler","刚进入");
            switch (message.what){
                case GetXmlAndParse.PARSESUCCWSS:
                    list=(List<Temperature>) message.obj;
                    Log.d("tempture",String.valueOf(list.size()));
                    initData();
                    break;
            }
            //super.handleMessage(message);//调用父类的方法，该方法为空方法，没什么用处
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listView=findViewById(R.id.listview1);
        gridView=findViewById(R.id.gridview1);//找到gridview
        Log.d("begin","开始");
        GetXmlAndParse getXmlAndParse=new GetXmlAndParse(handler);
        Log.d("获取:","handler");
        getXmlAndParse.getXml();
    }

    protected  void initData(){
        list_name=new ArrayList<>();
        Log.d("initzhouze",String.valueOf(list_name.size()));
        for(Temperature temperature:list){
            list_name.add(temperature.getCity_name());
        }

        Log.d("子那个定义",String.valueOf(list.size()));
        GridAdapter gridAdapter=new GridAdapter(this,list);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("grid点击",String.valueOf(position));
                Temperature temperature;
                temperature=list.get(position);
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                Bundle bundle=new Bundle();
                bundle.putString("city_name",temperature.getCity_name());
                bundle.putInt("tem_state1",temperature.getTem_state1());
                bundle.putInt("tem_state2",temperature.getTem_state2());
                bundle.putInt("tem_high",temperature.getTm1());
                bundle.putInt("tem_low",temperature.getTm2());
                bundle.putString("wind_state",temperature.getWind());
                bundle.putString("tem_detailed",temperature.getState_Detailed());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //SimpleAdapter simpleAdapter=new SimpleAdapter(this,list_name,R.layout.grid_text);
        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,list_name);
        //listView.setAdapter(arrayAdapter);
        //对列表中的元素进行监听,用于跳转界面显示各个城市的详细天气
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("asdf",String.valueOf(list.size()));
                Temperature temperature;
                temperature=list.get(position);
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                Bundle bundle=new Bundle();
                bundle.putString("city_name",temperature.getCity_name());
                bundle.putInt("tem_state1",temperature.getTem_state1());
                bundle.putInt("tem_state2",temperature.getTem_state2());
                bundle.putInt("tem_high",temperature.getTm1());
                bundle.putInt("tem_low",temperature.getTm2());
                bundle.putString("wind_state",temperature.getWind());
                bundle.putString("tem_detailed",temperature.getState_Detailed());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
    }
}
