package com.project.mqtttest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class ItemListRowHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnail;
    protected TextView bname,bamount,btot,bacc;

    public ItemListRowHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.bicon);
        this.bname = (TextView) view.findViewById(R.id.bname);
        this.bamount = (TextView) view.findViewById(R.id.bankbal);
        this.bacc = (TextView) view.findViewById(R.id.acc);
        this.btot = (TextView) view.findViewById(R.id.banktot);
    }

}