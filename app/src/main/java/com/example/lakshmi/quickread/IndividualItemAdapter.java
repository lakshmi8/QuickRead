package com.example.lakshmi.quickread;

import android.content.AsyncQueryHandler;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lakshmi Narayanan on 3/3/2017.
 */
public class IndividualItemAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList<MessageObject> individualMessages;
    private boolean isDeletePressed = false;
    private IndividualItemActivity.IndividualActivityCB cb;

    private boolean checkedState[];

    private String TAG = "IndividualItemAdapter";

    public IndividualItemAdapter(Context context, ArrayList<MessageObject> individualMessages, IndividualItemActivity.IndividualActivityCB callback)
    {
        super(context, R.layout.individual_item_view, individualMessages);
        this.context = context;
        this.individualMessages = individualMessages;
        this.cb = callback;

        checkedState = new boolean[this.individualMessages.size()];
        for(int i = 0; i < this.individualMessages.size(); i++)
            checkedState[i] = false;
    }

    private class ViewHolder
    {
        TextView dateTime;
        LinearLayout normalView;
        LinearLayout deleteView;
        TextView message;
        TextView message1;
        CheckBox checkBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.individual_item_view, parent, false);

            holder = new ViewHolder();
            holder.dateTime = (TextView) convertView.findViewById(R.id.date_time);
            holder.normalView = (LinearLayout) convertView.findViewById(R.id.message_normal_view);
            holder.deleteView = (LinearLayout) convertView.findViewById(R.id.message_delete_view);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.message1 = (TextView) convertView.findViewById(R.id.message1);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            final CheckBox checkBoxify = holder.checkBox;

            holder.normalView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    cb.deleteOnClick();
                    Log.d(TAG, "Normal view onLongClickListener");
                    checkBoxify.setChecked(true);
                    return true;
                }
            });

            holder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Delete view onClickListener");
                    int pos = (Integer) view.getTag();
                    checkBoxify.setChecked(!checkedState[pos]);
                }
            });

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int pos = (Integer) compoundButton.getTag();
                    checkedState[pos] = b;
                    int count = checkedCount();
                    cb.updateCount(count);
                }
            });

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.normalView.setTag(position);
        holder.deleteView.setTag(position);
        holder.checkBox.setTag(position);

        if(individualMessages != null && individualMessages.size() > 0)
        {
            Log.d(TAG, "Individual Message is not null " + individualMessages.size());
            MessageObject obj = individualMessages.get(position);
            if(isDeletePressed)
            {
                holder.normalView.setVisibility(View.GONE);
                holder.deleteView.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.deleteView.setVisibility(View.GONE);
                holder.normalView.setVisibility(View.VISIBLE);
            }
            holder.checkBox.setChecked(checkedState[position]);

            holder.message.setText(obj.getMessage());
            holder.message1.setText(obj.getMessage());

            String dateTimify = obj.getDateTime();
            dateTimify = dateTimify.replace(';', ' ');
            holder.dateTime.setText(dateTimify);
        }
        else
        {
            Toast.makeText(context, "No messages", Toast.LENGTH_LONG).show();
            Log.d(TAG, "it is null");
        }
        return convertView;
    }

    public void onDeleteClicked()
    {
        isDeletePressed = true;
        for(int i = 0; i < individualMessages.size(); i++)
            checkedState[i] = false;
        notifyDataSetChanged();
    }

    public int checkedCount()
    {
        int count = 0;
        for(int i = 0; i < individualMessages.size(); i++)
            if(checkedState[i])
                count++;
        return count;
    }

    public int totalCount()
    {
        return individualMessages.size();
    }

    public void showNormalView()
    {
        // Log.d(TAG, "Inside showNormalView");
        isDeletePressed = false;
        for(int i = 0; i < individualMessages.size(); i++)
            checkedState[i] = false;
        notifyDataSetChanged();
    }

    public void toggleCheckBoxes(boolean state)
    {
        if(!state)
        {
            int totalCount = totalCount();
            int checkedCount = checkedCount();
            if(totalCount != checkedCount)
                return;
        }
        for(int i = 0; i < individualMessages.size(); i++)
            checkedState[i] = state;
        notifyDataSetChanged();
    }

    public boolean deleteItems()
    {
        boolean itemsExist = true;
        ArrayList<Integer> checkedItems = new ArrayList<>();
        for(int i = 0; i < individualMessages.size(); i++)
        {
            if(checkedState[i])
                checkedItems.add(individualMessages.get(i).getID());
        }
        for(int i = individualMessages.size() - 1; i >= 0; i--)
        {
            if(checkedState[i])
                individualMessages.remove(i);
        }
        new deleteFromDB().execute(checkedItems);
        notifyDataSetChanged();
        if(individualMessages != null)
        {
            if(individualMessages.size() == 0)
                itemsExist = false;
        }
        return itemsExist;
    }

    private class deleteFromDB extends AsyncTask<ArrayList, Void, Void>
    {
        protected Void doInBackground(ArrayList... params)
        {
            ArrayList<Integer> checkedItems = params[0];
            for(int i = 0; i < checkedItems.size(); i++)
            {
                int id = checkedItems.get(i);
                context.getContentResolver().delete(Uri.parse("content://sms/" + id), null, null);
            }
            return null;
        }

        protected void onPostExecute(Void v)
        {
        }
    }
}
