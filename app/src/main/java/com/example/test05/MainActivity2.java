package com.example.test05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test05.dao.Temperature;
import com.example.test05.service.GetGif;
import com.example.test05.service.GetXmlAndParse;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private TextView textView1,textView2,textView3,textView4;
    private ImageView imageView1,imageView2;
    private byte[] byte_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle=getIntent().getExtras();
        String city_name=bundle.getString("city_name");
        Integer tem_state1=bundle.getInt("tem_state1");//天气变化的状态1
        Integer tem_state2=bundle.getInt("tem_state2");//天气变化的状态2
        Integer tem_high=bundle.getInt("tem_high");
        Integer tem_low=bundle.getInt("tem_low");
        String wind_state=bundle.getString("wind_state");
        String tem_state=bundle.getString("tem_detailed");
        Log.d("获取到的值:",city_name+tem_state1+tem_state2+tem_high+tem_low+wind_state+tem_state);

        textView1=findViewById(R.id.city_name);
        textView2=findViewById(R.id.tem_state);
        textView3=findViewById(R.id.tem);
        textView4=findViewById(R.id.wind_state);
        imageView1=findViewById(R.id.tem_image1);
        imageView2=findViewById(R.id.tem_image2);

        textView1.setText(city_name);
        textView2.setText(tem_state);
        textView3.setText(tem_high+"～"+tem_low+"℃");
        textView4.setText(wind_state);
        //imageView.setImageBitmap();
        /*GetGif getGif=new GetGif();
        byte[] image1=getGif.getImage(tem_state1);
        Bitmap bitmap1= BitmapFactory.decodeByteArray(image1,0,image1.length);
        imageView1.setImageBitmap(bitmap1);

        byte[] image2=getGif.getImage(tem_state2);
        Bitmap bitmap2=BitmapFactory.decodeByteArray(image2,0,image2.length);
        imageView2.setImageBitmap(bitmap2);*/
        Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message message) {
                Log.d("handler","刚进入");
                switch (message.what){
                    case GetGif.PARSESUCCWSS:
                        byte_image=(byte[])message.obj;
                        int index=message.arg1;
                        Log.d("image_location",String.valueOf(index));
                        initImage(index);
                        break;
                }
                super.handleMessage(message);
            }
        };

        GetGif getGif=new GetGif(handler);
        Log.d("begin_image","开始设置图片");
        getGif.getImage(tem_state1,0);
        try {
            Thread.sleep(10);//两次获取图片，由于有线程操作，可能欧是照片资源被覆盖（后一个覆盖前一个），需要使用线程休眠
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getGif.getImage(tem_state2,1);
    }

    public void initImage(int index){
        Bitmap bitmap=BitmapFactory.decodeByteArray(byte_image,0,byte_image.length);
        Log.d("image_index2",String.valueOf(index));
        if(index==0){
            imageView1.setImageBitmap(bitmap);
        }else if(index==1){
            imageView2.setImageBitmap(bitmap);
        }else{//空什么都不做

        }
    }

}
