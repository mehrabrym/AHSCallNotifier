package com.rmehrab.ahscallnotifier;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<PersonItem> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<PersonItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.nameView = convertView.findViewById(R.id.person_name);
            holder.phoneView = convertView.findViewById(R.id.person_phone);
            holder.emailView = convertView.findViewById(R.id.person_email);
            holder.notifyView = convertView.findViewById(R.id.person_notify);
            holder.notifySwitch = convertView.findViewById(R.id.person_switch);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).getPersonName());
        holder.phoneView.setText("Phone: " + listData.get(position).getPersonPhone());
        holder.emailView.setText("Email: " + listData.get(position).getPersonEmail());
        holder.notifyView.setText("Notify: " + listData.get(position).getPersonNotify());
        holder.notifySwitch.setChecked(listData.get(position).getPersonNotify().contains("true"));
        holder.notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listData.get(position).setPersonNotify(isChecked? "true":"false");
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView phoneView;
        TextView emailView;
        TextView notifyView;
        SwitchCompat notifySwitch;
    }

}
