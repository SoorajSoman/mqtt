/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.project.mqtttest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import mehdi.sakout.fancybuttons.FancyButton;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;
import com.mikepenz.actionitembadge.library.utils.UIUtil;

public class ItemDetails extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    FancyButton cart,buy;
    TextView t1,t2;
    EditText ed;
    ImageView im;
    Adptr adptr;
    int c2;
    private static final int SAMPLE2_ID = 34535;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);
        buy=(FancyButton)findViewById(R.id.buy);
        cart=(FancyButton)findViewById(R.id.cart);
        im=(ImageView)findViewById(R.id.item);
        t1=(TextView)findViewById(R.id.itemname);
        t2=(TextView)findViewById(R.id.val);
        ed=(EditText)findViewById(R.id.qty);
        t1.setText("Rs."+Constants.price);
        t2.setText(Constants.amt);
        im.setImageResource(Constants.img);
        adptr=new Adptr(ItemDetails.this);
        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();


            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ItemDetails.this.getResources().getColor(R.color.Primarydark));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     /* adptr.read();
      Cursor  c1=adptr.getallcartitems(Constants.itemid);
        c2=c1.getCount();

      adptr.close();*/


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constants.badgeCount < 1 && c2 < 1) {
                    Constants.badgeCount++;
                    if (ed.getText().toString().trim() != null && Integer.parseInt(ed.getText().toString().trim()) > 0) {
                        adptr.read();
                        adptr.insrt_cart(Constants.itemid, Constants.itemname, Constants.qty, Constants.price, Constants.rfid,Constants.img);
                        // onCreateOptionsMenu(R.menu.sample_actions);
                        adptr.close();
                    } else {


                    }

                    invalidateOptionsMenu();


                }
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample_actions, menu);

        if (Constants.badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), UIUtil.getCompatDrawable(this,R.drawable.trolley_1), ActionItemBadge.BadgeStyles.YELLOW, Constants.badgeCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        }

       // new ActionItemBadgeAdder().act(this).menu(menu).title(R.string.sample_1).itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(ActionItemBadge.BadgeStyles.GREY_LARGE, 1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.item_samplebadge) {
            //Toast.makeText(this, R.string.sample_1, Toast.LENGTH_SHORT).show();
            //badgeCount--;
            invalidateOptionsMenu();
            if(Constants.badgeCount>0) {
                Intent intent = new Intent(ItemDetails.this, Cart.class);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
