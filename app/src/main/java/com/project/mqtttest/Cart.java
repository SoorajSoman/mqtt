package com.project.mqtttest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class Cart extends AppCompatActivity {
    private Messenger service = null;
    private final Messenger serviceHandler = new Messenger(new ServiceHandler());
    private IntentFilter intentFilter = null;
    private PushReceiver pushReceiver;

    Adptr adptr;
         FancyButton buy;
    ArrayList<ItemModal> feedItemList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private MyRecyclerAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2, bankbal,cash,dt, amount, bname, bicon, btot, bacc, biamt, bala;
    ArrayList<ItemModal>items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();


            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Cart.this.getResources().getColor(R.color.Primarydark));
        }
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        intentFilter = new IntentFilter();

        intentFilter.addAction("com.example.MQTT.PushReceived");
        pushReceiver = new PushReceiver();
        registerReceiver(pushReceiver, intentFilter, null, null);

        startService(new Intent(this, MQTTservice.class));
        bindService(new Intent(this, MQTTservice.class), serviceConnection, 0);
        adptr=new Adptr(getApplicationContext());
        feedItemList = new ArrayList<ItemModal>();
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        //  mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adptr = new Adptr(getApplicationContext());

        feedItemList.clear();
        adptr.read();
        String bankidval = "",rfid="";
        int icons = 0;
        int indexval = 0;
        Cursor c2 = adptr.getcartitems();
        Log.i("String", "count" + c2.getCount());
        if (c2 != null && c2.getCount() > 0) {
            //if(1==0) {
            while (c2.moveToNext()) {
                ItemModal item = new ItemModal();
                bname = c2.getString(c2.getColumnIndex("item_name"));
                icons= c2.getInt(c2.getColumnIndex("item_image"));
                bala = String.valueOf(c2.getDouble(c2.getColumnIndex("price")));
                bacc = c2.getString(c2.getColumnIndex("quantity"));
                rfid= c2.getString(c2.getColumnIndex("rfid"));
               // indexval = c2.getInt(c2.getColumnIndex("id"));
                btot = "1.00";
                item.setBname(bname);
                item.setThumbnail(icons);
                //item.setIdpos(indexval);
                item.setBaccount(bacc);
                item.setBamount(bala);
                item.setrfid(rfid);
                item.setBtotal(btot);

                feedItemList.add(item);
            }


        }
        adptr.close();
        addPublishButtonListener();
        adapter = new MyRecyclerAdapter(getApplicationContext(), feedItemList);

        mRecyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
       // bindService(new Intent(this, MQTTservice.class), serviceConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(pushReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(pushReceiver);
    }

    public class PushReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent i)
        {
            String topic = i.getStringExtra(MQTTservice.TOPIC);
            String message = i.getStringExtra(MQTTservice.MESSAGE);
            Toast.makeText(context, "Push message received - " + topic + ":" + message, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void addPublishButtonListener()
    {
        buy=(FancyButton)findViewById(R.id.buy);

        buy.setOnClickListener(new OnClickListener() {
            //InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            @Override
            public void onClick(View arg0) {
                // EditText t = (EditText) findViewById(R.id.EditTextTopic);
                // EditText m = (EditText) findViewById(R.id.editTextMessage);
                // TextView result = (TextView) findViewById(R.id.textResultStatus);
                // inputMethodManager.hideSoftInputFromWindow(result.getWindowToken(), 0);

                String topic = "inTopic";
                String message = "a";

                if (topic != null && topic.isEmpty() == false && message != null && message.isEmpty() == false) {
                    //result.setText("");
                    Bundle data = new Bundle();
                    data.putCharSequence(MQTTservice.TOPIC, topic);
                    data.putCharSequence(MQTTservice.MESSAGE, message);
                    Message msg = Message.obtain(null, MQTTservice.PUBLISH);
                    msg.setData(data);
                    msg.replyTo = serviceHandler;
                    try {
                        service.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        //    result.setText("Publish failed with exception:" + e.getMessage());
                    }
                } else {
                    // result.setText("Topic and message required.");
                }
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder)
        {
            service = new Messenger(binder);
            Bundle data = new Bundle();
            //data.putSerializable(MQTTservice.CLASSNAME, MainActivity.class);
            data.putCharSequence(MQTTservice.INTENTNAME, "com.example.MQTT.PushReceived");
            Message msg = Message.obtain(null, MQTTservice.REGISTER);
            msg.setData(data);
            msg.replyTo = serviceHandler;
            try
            {
                service.send(msg);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
        }
    };
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
                //adptr.close();

            }else{
                feedListRowHolder.thumbnail.setVisibility(View.INVISIBLE);
            }

            feedListRowHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    //intent.putExtra("id",feedItem.getid());
                   // Intent in=new Intent(getApplicationContext(),CheeseDetailActivity.class);
                   // startActivity(in);
                }

            });





        }

        @Override
        public int getItemCount() {
            return (null != feedItemList ? feedItemList.size() : 0);
        }
    }

    class ServiceHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MQTTservice.SUBSCRIBE: 	break;
                case MQTTservice.PUBLISH:		break;
                case MQTTservice.REGISTER:		break;
                default:
                    super.handleMessage(msg);
                    return;
            }

            Bundle b = msg.getData();
            if (b != null)
            {
              /*  TextView result = (TextView) findViewById(R.id.textResultStatus);
                Boolean status = b.getBoolean(MQTTservice.STATUS);
                if (status == false)
                {
                    result.setText("Fail");
                }
                else
                {
                    result.setText("Success");
                }*/
            }
        }
    }

}
