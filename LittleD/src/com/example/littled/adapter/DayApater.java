package com.example.littled.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.littled.R;
import com.example.littled.R.id;
import com.example.littled.R.layout;
import com.example.littled.domain.Data;
import com.example.littled.domain.Day;
import com.example.littled.domain.Point;
import com.example.littled.utils.VarAll;


public class DayApater extends BaseAdapter {
    //itemA类的type标志
    private static final int TYPE_A = 0;
    //itemB类的type标志
    private static final int TYPE_B = 1;

    private Context context;

    private List<Data> data = new ArrayList<Data>();


    public DayApater(Context context, ArrayList<Data> data2) {
        this.context = context;

        this.data = data2;
    }
    
    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (data.get(position) instanceof Day) {
            result = TYPE_A;
        } else if (data.get(position) instanceof Point) {
            result = TYPE_B;
        }
        return result;
    }

    
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //创建两种不同种类的viewHolder变量
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        if (convertView == null) {
            //实例化
            holder1 = new ViewHolder1(); 
            holder2 = new ViewHolder2();
            //根据不同的type 来inflate不同的item layout
            //然后设置不同的tag
            //这里的tag设置是用的资源ID作为Key
            switch (type) {
                case TYPE_A:
                    convertView = View.inflate(context, R.layout.day_item1, null);
                    holder1.week = (TextView) convertView.findViewById(R.id.leftup);
                    holder1.day = (TextView) convertView.findViewById(R.id.leftbottom);
                    holder1.detail = (TextView) convertView.findViewById(R.id.right);
                    convertView.setTag(R.id.tag_first, holder1);
                    break;
                case TYPE_B:
                    convertView = View.inflate(context, R.layout.point_item, null);
                   
                    holder2.img = (ImageView) convertView.findViewById(R.id.image_point);
                    convertView.setTag(R.id.tag_second, holder2);
                    break;
            }

        } else {
            //根据不同的type来获得tag
            switch (type) {
                case TYPE_A:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.tag_first);
                    break;
                case TYPE_B:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.tag_second);
                    break;
            }
        }

        Data o = data.get(position);
        //根据不同的type设置数据
        switch (type) {
            case TYPE_A:
                Day a = (Day) o;
                holder1.detail.setText(a.getDetail());
                holder1.week.setText(a.getWeek());
                String year = "" + VarAll.nowyear;
                String month = VarAll.monthstring[VarAll.nowmonth-1];
                int ag = Integer.parseInt(a.getDay());
                String sag = "" + ag;
                String san = "" + VarAll.nowday;
                if(a.getWeek().equals("SUN"))
                {
                	holder1.day.setTextColor(Color.rgb(255, 0, 0));
                	holder1.day.setText(a.getDay());
                }
                else if(VarAll.yearmenu.equals(year) && VarAll.monthmenu.equals(month)){
                	if(sag.trim().equals(san)){
                		holder1.day.setTextColor(Color.rgb(249, 204, 226));
                		holder1.day.setText(a.getDay());
                	}else{
                		 holder1.day.setText(a.getDay());
                	}
                }
                else
                {
                	 holder1.day.setText(a.getDay());
                }
                break;

            case TYPE_B:
                Point b = (Point) o;
                holder2.img.setImageResource(b.getImageId());
                break;
        }
        return convertView;
    }


    /**
     * item A 的Viewholder
     */
    private static class ViewHolder1 {
        TextView week;
        TextView day;
        TextView detail;

    }

    /**
     * item B 的Viewholder
     */
    private static class ViewHolder2 {
        ImageView img;
    }

}