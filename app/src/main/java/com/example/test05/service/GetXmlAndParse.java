package com.example.test05.service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import com.example.test05.dao.Temperature;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetXmlAndParse {
    private String url="http://flash.weather.com.cn/wmaps/xml/china.xml";//数据存储的网址
    public static final int PARSESUCCWSS=0x2001;
    private Handler handler;
    public GetXmlAndParse(Handler handler){/*构造函数*/
        this.handler=handler;
    }

    private List<Temperature> list;//存储数据的对象
    /*获取网络的xml*/
    public void getXml(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(5000);//设置连接超时
                    conn.setRequestMethod("GET");//设置请求方法
                    if(conn.getResponseCode()==200){//验证响应码，200即为正常响应
                        InputStream inputStream=conn.getInputStream();//获取数据流
                        list=pullXml(inputStream);
                        Log.d("asd",String.valueOf(list.size()));
                        if(list.size()>0){//解析结果不为空则将解析出的数据发送给UI线程
                            Log.d("asd1",String.valueOf(list.size()));
                            Message message=Message.obtain();
                            message.obj=list;
                            message.what=PARSESUCCWSS;
                            handler.sendMessage(message);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*解析XML文件*/
    protected List<Temperature> pullXml(InputStream inputStream){
        list=new ArrayList<>();
        try{
            XmlPullParser pullParser= Xml.newPullParser();//获取xmlPullParser对象
            Temperature temperature=null;
            pullParser.setInput(inputStream,"utf-8");//数据流的解析编码为utf-8
            int eventCode=pullParser.getEventType();
            while(eventCode!=XmlPullParser.END_DOCUMENT){//文件末尾
                String targetName=pullParser.getName();
                switch (eventCode){
                    case XmlPullParser.START_TAG://标签开始
                        if("city".equals(targetName)){//处理news的开始节点
                            temperature=new Temperature();
                            temperature.setCity_name(pullParser.getAttributeValue(2));//城市名
                            temperature.setTem_state1(Integer.parseInt(pullParser.getAttributeValue(3)));//天气情况1
                            temperature.setTem_state2(Integer.parseInt(pullParser.getAttributeValue(4)));//天气情况2
                            temperature.setState_Detailed(pullParser.getAttributeValue(5));//城市天气情况
                            temperature.setTm1(Integer.parseInt(pullParser.getAttributeValue(6)));//最高温度
                            temperature.setTm2(Integer.parseInt(pullParser.getAttributeValue(7)));//最低温度
                            temperature.setWind(pullParser.getAttributeValue(8));//风的情况
                            Log.d("city:",pullParser.getAttributeValue(2));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("city".equals(targetName)){
                            list.add(temperature);
                        }
                        break;
                }
                eventCode=pullParser.next();//解析下一个节点
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /*public List<Temperature> getList(){
        return list;
    }*/
}
