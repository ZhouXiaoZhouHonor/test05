package com.example.test05.service;

import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetGif {
    private String url;//数据存储的网址
    public static final int PARSESUCCWSS=0x2002;
    private Handler handler;
    Image image;
    byte[] byte_image;

    public GetGif(Handler handler){
        this.handler=handler;
    }
    //private Bitmap bitmap;

    public  GetGif(){}//默认构造函数
    public byte[] getImage(final int index, final int location){
        url="http://www.weather.com.cn/m/i/weatherpic/29x20/d"+index+".gif";//拼凑图片的地址
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("image_begin","开始获取照片流");
                    HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(5000);//设置连接超时
                    conn.setRequestMethod("GET");//设置请求方法
                    Log.d("联结","成功");
                    Log.d("res_code",String.valueOf(conn.getResponseCode()));
                    if(conn.getResponseCode()==200){//验证响应码，200即为正常响应
                        Log.d("code响应码","有没有响应");
                        InputStream inputStream=conn.getInputStream();//获取数据流
                        byte_image=pullImage(inputStream);
                        Log.d("照片长度",String.valueOf(byte_image.length));
                        if(byte_image.length>0){
                            Message message=Message.obtain();
                            message.obj=byte_image;
                            message.arg1=location;
                            message.what=GetGif.PARSESUCCWSS;
                            handler.sendMessage(message);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return byte_image;
    }

    /*解析XML文件*/
    protected byte[] pullImage(InputStream inputStream) throws IOException {
        Log.d("pullImage","开始读写数据流");
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        while((len=inputStream.read(buffer))!=-1){
            byteArrayOutputStream.write(buffer,0,len);
        }
        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}
