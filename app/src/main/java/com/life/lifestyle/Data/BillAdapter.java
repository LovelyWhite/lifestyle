package com.life.lifestyle.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.life.lifestyle.R;

import java.util.Vector;

public class BillAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Vector<Bill> vector;

    public BillAdapter(Context context, Vector<Bill> vector) {
        layoutInflater = LayoutInflater.from(context);
        this.vector = vector;
    }

    @Override
    public int getCount() {
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
        public TextView bPrice;
        public TextView bTime;
        public TextView bType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.bill, null);
            holder = new ViewHolder();
            holder.bTime = convertView.findViewById(R.id.bTime);
            holder.bPrice = convertView.findViewById( R.id.bPrice);
            holder.bType = convertView.findViewById( R.id.bType);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bPrice.setText("金额：" + ((Bill) vector.get(position)).getPrice());
        holder.bTime.setText("时间:" +((Bill) vector.get(position)).getTime());
        if (((Bill) (vector.get(position))).isBit()) {
            holder.bType.setText("类型:取入");
        }
        else
        {
            holder.bType.setText("类型:取出");
        }
        notifyDataSetChanged();
        return convertView;
    }

}
