package com.example.lakshmi.quickread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lakshmi Narayanan on 2/26/2017.
 */
public class MainAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList<MessageObject> inboxMessages;
    private ArrayList<MessageObject> classifiedMessages;
    private ArrayList<MessageObject> individualMessages;
    private MainActivity.IntentCallback mCB;
    private String TAG = "MainAdapter";

    public MainAdapter(Context context, ArrayList<MessageObject> classifiedMessages, ArrayList<MessageObject> inboxMessages, MainActivity.IntentCallback cb)
    {
        super(context, R.layout.main_item_view, classifiedMessages);
        this.context = context;
        this.inboxMessages = inboxMessages;
        this.classifiedMessages = classifiedMessages;
        this.mCB = cb;
    }

    private class ViewHolder
    {
        LinearLayout inboxItem;
        ImageView icon;
        TextView sender;
        TextView message;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // pos = position;
        // Log.d(TAG, "Position is " + pos);
        ViewHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.main_item_view, parent, false);

            holder = new ViewHolder();
            holder.inboxItem = (LinearLayout) convertView.findViewById(R.id.inbox_item);
            holder.icon = (ImageView) convertView.findViewById(R.id.sender_icon);
            holder.sender = (TextView) convertView.findViewById(R.id.sender);
            holder.message = (TextView) convertView.findViewById(R.id.message);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.inboxItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageObject obj = classifiedMessages.get(position);
                String className = obj.getClassName();
                individualMessages = new ArrayList<>();
                if (className.equals("Misc"))
                {
                    individualMessages.add(obj);
                }
                else
                {
                    for(int i = 0; i < inboxMessages.size(); i++)
                    {
                        MessageObject ob = inboxMessages.get(i);
                        if(ob.getClassName().equals(className))
                            individualMessages.add(ob);
                    }
                }
                mCB.callSecondActivity(individualMessages);
            }
        });

        if(classifiedMessages != null)
        {
            MessageObject obj = classifiedMessages.get(position);
            String className = obj.getClassName();
            if (className.equals("Misc"))
            {
                holder.sender.setText(obj.getSenderNumber());
                holder.icon.setImageDrawable(context.getDrawable(R.drawable.user_icon));
            }
            else
            {
                holder.sender.setText(className);
                holder.icon.setImageDrawable(context.getDrawable(R.drawable.folder_icon));
            }
            holder.message.setText(obj.getMessage());
        }
        return convertView;
    }
}
