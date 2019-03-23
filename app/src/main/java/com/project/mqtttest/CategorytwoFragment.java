package com.project.mqtttest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CategorytwoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "param2";
    Adptr adptr;
    TextView dbt,crdt;
    int color;
    Double cashd;
    ArrayList<ItemModal> feedItemList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private MyRecyclerAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2, bankbal,cash,dt, amount, bname, bicon, btot, bacc, biamt, bala;
   ArrayList<ItemModal>items;
  public CategorytwoFragment(){

  };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cheese_list, container, false);

               adptr=new Adptr(getActivity().getApplicationContext());
        adptr.read();
        Calendar calendar = Calendar.getInstance();
        int cal=calendar.get(Calendar.MONTH);
        int yt=calendar.get(Calendar.YEAR);
        cal=cal+1;


        // bal.setText("\u20B9170000");
        feedItemList = new ArrayList<ItemModal>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
       //  mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        adptr = new Adptr(getActivity().getApplicationContext());

        feedItemList.clear();
        adptr.read();
        String bankidval = "";
        int icons = 0;
        int indexval = 0;
        Cursor c2 = adptr.getallitems("Cat2");
        if (c2 != null && c2.getCount() > 0) {
            //if(1==0) {
            while (c2.moveToNext()) {
                ItemModal item = new ItemModal();
                bname = c2.getString(c2.getColumnIndex("item_name"));
                icons= c2.getInt(c2.getColumnIndex("item_image"));
                bala = String.valueOf(c2.getDouble(c2.getColumnIndex("item_price")));
                bacc = c2.getString(c2.getColumnIndex("item_description"));
                bankidval = c2.getString(c2.getColumnIndex("item_id"));
                indexval = c2.getInt(c2.getColumnIndex("id"));
                btot = "1.00";
                item.setBname(bname);
                item.setThumbnail(icons);
                //item.setIdpos(indexval);
                item.setBaccount(bacc);
                item.setBamount(bala);
                item.setBtotal(btot);

                feedItemList.add(item);
            }


        } else {


        }

        adptr.close();

        adapter = new MyRecyclerAdapter(getActivity().getApplicationContext(), feedItemList);
        mRecyclerView.setAdapter(adapter);

        return view;
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter<ItemListRowHolder> {

        int iconid;
        private List<ItemModal> feedItemList;

        private Context mContext;

        public MyRecyclerAdapter(Context context, List<ItemModal> feedItemList) {
            this.feedItemList = feedItemList;
            this.mContext = context;
        }

        @Override
        public ItemListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_listitem, null);
           // FontsOverride.applyFont(getActivity().getApplicationContext(), v.findViewById(R.id.card_view1), "fonts/proximanova_regular.ttf");
            ItemListRowHolder mh = new ItemListRowHolder(v);

            return mh;
        }

        @Override
        public void onBindViewHolder(final ItemListRowHolder feedListRowHolder, final int i) {
            ItemModal feedItem = feedItemList.get(i);

            if (feedItemList.size() > 0) {
                if (feedItem.getBamount() != null&&!feedItem.getBamount().equalsIgnoreCase("0.00")){
                    feedListRowHolder.bamount.setText("\u20B9" + feedItem.getBamount());
            } else {
                feedListRowHolder.bamount.setVisibility(View.INVISIBLE);
            }
            if (feedItem.getBaccount() != null) {
                feedListRowHolder.bacc.setText(feedItem.getBaccount());

            } else {
                feedListRowHolder.bacc.setVisibility(View.INVISIBLE);
                //feedListRowHolder.linearLayout.setVisibility(View.INVISIBLE);
            }
            if (feedItem.getBname()!= null) {
                feedListRowHolder.bname.setText(feedItem.getBname());
            } else {
                feedListRowHolder.bname.setText("No Bank Data Found");
            }
                int icons=feedItemList.get(i).getThumbnail();
                feedListRowHolder.thumbnail.setImageResource(icons);
                adptr.close();

        }else{
                feedListRowHolder.thumbnail.setVisibility(View.INVISIBLE);
        }

                feedListRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Context context = v.getContext();
                       //intent.putExtra("id",feedItem.getid());
                            //Intent in=new Intent(getActivity().getApplicationContext(),CheeseDetailActivity.class);
                            //startActivity(in);
                        }

                });





        }

        @Override
        public int getItemCount() {
            return (null != feedItemList ? feedItemList.size() : 0);
        }
    }

}

