package com.example.lakshmi.quickread;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshmi Narayanan on 3/2/2017.
 */
public class IndividualItemActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<MessageObject> individualMessages;
    private IndividualItemAdapter adapter;
    private boolean isDeletePressed = false;
    private IndividualActivityCB cb = null;

    private RelativeLayout actionBar1;
    private RelativeLayout actionBar2;
    private TextView countify;
    private TextView delete;
    private TextView cancel;
    private CheckBox selectAll;

    private String TAG = "IndividualItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_item);

        getSupportActionBar().hide();

        LinearLayout backButton = (LinearLayout) findViewById(R.id.back_layout);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            individualMessages = extras.getParcelableArrayList("ARRAYLIST");

        defineIndividualActivityCB();

        String classNamify = "";

        if(individualMessages != null)
        {
            classNamify = individualMessages.get(0).getClassName();
            if(classNamify.equals("Misc"))
            {
                String number = individualMessages.get(0).getSenderNumber();
                classNamify = number;
            }
        }

        TextView className = (TextView) findViewById(R.id.class_name);
        className.setText(classNamify);

        actionBar1 = (RelativeLayout) findViewById(R.id.action_bar1);
        actionBar2 = (RelativeLayout) findViewById(R.id.action_bar2);
        countify = (TextView) findViewById(R.id.count);

        listView = (ListView) findViewById(R.id.individual_list);
        adapter = new IndividualItemAdapter(getApplicationContext(), individualMessages, cb);
        listView.setAdapter(adapter);

        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Delete Dialog

                final Dialog dialog = new Dialog(IndividualItemActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView ok = (TextView) dialog.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean itemsExist = adapter.deleteItems();
                        isDeletePressed = false;
                        actionBar2.setVisibility(View.GONE);
                        actionBar1.setVisibility(View.VISIBLE);
                        adapter.showNormalView();
                        if(!itemsExist)
                            onBackPressed();
                        dialog.dismiss();
                    }
                });
                TextView cancelify = (TextView) dialog.findViewById(R.id.cancelify);
                cancelify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDeletePressed = false;
                actionBar1.setVisibility(View.VISIBLE);
                actionBar2.setVisibility(View.GONE);
                adapter.showNormalView();
            }
        });

        selectAll = (CheckBox) findViewById(R.id.select_all_checkbox);
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.toggleCheckBoxes(b);
            }
        });
    }

    public interface IndividualActivityCB
    {
        void updateCount(int count);
        void deleteOnClick();
    }

    private void defineIndividualActivityCB()
    {
        cb = new IndividualActivityCB() {
            @Override
            public void updateCount(int count) {
                int totalCount = adapter.totalCount();
                if(count > 0)
                {
                    if(count == 1)
                        countify.setText("1 item selected");
                    else
                        countify.setText(count + " items selected");
                }
                else
                    countify.setText("Select items to delete");
                if(totalCount != count)
                {
                    if(selectAll.isChecked())
                        selectAll.setChecked(false);
                }
                else
                {
                    if(!selectAll.isChecked())
                        selectAll.setChecked(true);
                }
            }

            @Override
            public void deleteOnClick()
            {
                isDeletePressed = true;
                actionBar1.setVisibility(View.GONE);
                actionBar2.setVisibility(View.VISIBLE);
                adapter.onDeleteClicked();
            }
        };
    }

    @Override
    public void onBackPressed()
    {
        if(isDeletePressed)
        {
            isDeletePressed = false;
            actionBar1.setVisibility(View.VISIBLE);
            actionBar2.setVisibility(View.GONE);
            adapter.showNormalView();
        }
        else
            super.onBackPressed();
    }
}
