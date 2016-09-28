package com.kingdom.test.clearprocess.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingdom.test.clearprocess.EventBus.FirstEvent;
import com.kingdom.test.clearprocess.R;
import com.kingdom.test.clearprocess.javabean.RubbishInfoBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by admin on 2016/9/23.
 */
public class AppCacheAdapter extends BaseAdapter {
    Context context;
    List<RubbishInfoBean> rubbishInfo;
    public AppCacheAdapter(Context context, List<RubbishInfoBean> rubbishInfo) {
       this.context=context;
        this.rubbishInfo=rubbishInfo;
    }

    @Override
    public int getCount() {
        return rubbishInfo.size();
    }

    @Override
    public RubbishInfoBean getItem(int position) {
        return rubbishInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_exlistview_child,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.icon= (ImageView) convertView.findViewById(R.id.iv_item_elv_child_appicon);
            viewHolder.name= (TextView) convertView.findViewById(R.id.tv_item_elv_child_appname);
            viewHolder.size= (TextView) convertView.findViewById(R.id.tv_item_elv_child_appcachs_size);
            viewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.cb_item_elv_child_checkbox);
            convertView.setTag(viewHolder);
        }
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.icon.setImageDrawable(getItem(position).getIcon());
        viewHolder.name.setText(getItem(position).getName());
        viewHolder.size.setText(Formatter.formatFileSize(context, getItem(position).getSize()));
        viewHolder.checkBox.setChecked(getItem(position).isChecked());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getItem(position).isChecked()){
                    //发送消息
                    EventBus.getDefault().post(new FirstEvent("reduce",getItem(position).getSize()));
                }else {
                    //发送消息
                    EventBus.getDefault().post(new FirstEvent("add",getItem(position).getSize()));
                }
                getItem(position).setTag(getItem(position).isChecked());
            }
        });
        return convertView;
    }

    class ViewHolder{
        ImageView icon;
        TextView name;
        TextView size;
        CheckBox checkBox;

    }
}
