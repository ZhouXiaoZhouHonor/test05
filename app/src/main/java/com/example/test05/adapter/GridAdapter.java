package com.example.test05.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test05.MainActivity;
import com.example.test05.R;
import com.example.test05.dao.Temperature;

import java.util.List;
import java.util.zip.Inflater;

public class GridAdapter extends BaseAdapter {

    private List<Temperature> list;
    private List<String> list_name;
    private Context context;
    public GridAdapter(Context context, List<Temperature> list){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {//返回有多少个数据值
        return list.size();//集合中的数据个数
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);//获取每个list集合中的对象
    }

    @Override
    public long getItemId(int position) {
        return position;//获取列表中的位置
    }

    @Override
    //gridview适配器代码
    public View getView(int position, View convertView, ViewGroup parent) {
        Temperature temperature=(Temperature) list.get(position);
        if(convertView==null) {
            convertView = View.inflate(context,R.layout.grid_text, null);
        }
        TextView textView=convertView.findViewById(R.id.grid_city);
        textView.setText(temperature.getCity_name());
        return convertView;
    }
}
