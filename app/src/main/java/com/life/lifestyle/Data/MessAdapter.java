package com.life.lifestyle.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.life.lifestyle.R;

import java.util.Vector;

public class MessAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Vector<Message> vector;
    public MessAdapter(Context context , Vector<Message> vector)
    {
        layoutInflater = LayoutInflater.from(context);
        this.vector =vector;
    }
    @Override
    public int getCount() {
        if (vector==null)
        return 0;
        else
            return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    static class ViewHolder {
        public TextView messName;
        public TextView messCont;
        public TextView messTime;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder =null;
        if(convertView==null)
        {
            convertView =  layoutInflater.inflate(R.layout.mess,null);
            holder = new ViewHolder();
            holder.messName = convertView.findViewById(R.id.messName);
            holder.messCont = convertView.findViewById(R.id.messCont);
            holder.messTime = convertView.findViewById(R.id.messTime);
            convertView .setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.messTime.setText("时间："+((Message)vector.get(position)).getSendTime());
        holder.messCont.setText("内容："+((Message)vector.get(position)).getInformation());
        holder.messName.setText("用户ID:"+((Message)vector.get(position)).getUserId());
        notifyDataSetChanged();
        return convertView;
    }

}
