package com.example.lakshmi.quickread;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<MessageObject> inboxMessages;
    private ArrayList<MessageObject> classifiedMessages;
    public IntentCallback cb = null;

    private String classes[] = {"99Acres", "Airtel", "Amazon", "Apollo", "Axis Bank", "Blue Dart",
            "BookMyShow", "CommonFloor", "FAASOS", "Fast Track", "Flipkart", "FoodPanda", "HDFC Bank",
            "ICICI Bank", "Idea", "Indane", "Indian Bank", "IndianOil", "IRCTC", "IRCTC Insurance",
            "Jio", "KSRTC", "LG Care", "Lenskart", "MagicBricks", "MedPlus", "Metropolis", "MyGovt",
            "NoBroker", "Ola", "PayTM", "State Bank of India", "Sulekha", "Swiggy", "TaxiForSure",
            "Tikona", "Titan watch", "Uber", "Viveks"};

    private ArrayList<String> className;
    private ArrayList<String> contactNumbers;
    private ArrayList<String> contactNames;

    private HashMap<String, Integer> classNumber;
    private boolean isPresent[];

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        listView = (ListView) findViewById(R.id.list);

        className = new ArrayList<>();
        Collections.addAll(className, classes);

        inboxMessages = new ArrayList<>();
        classifiedMessages = new ArrayList<>();

        defineCallback();

        Classifier ob = new Classifier(getApplicationContext());
        contactNumbers = ob.getContactNumbers();
        contactNames = ob.getContactNames();
        className.addAll(contactNames);
        Log.d(TAG, "Classname arraylist size is " + className.size());

        // Get the messages from the inbox and create the arraylist [
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        if(cursor != null)
        {
            Log.d(TAG, "Cursor is not empty");
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++)
            {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String sender = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String dateTime = getDateTime(cursor);

                // Get the respective category of the message [
                String classNamify = ob.getClass(sender, message);
                // ]

                MessageObject singleMessage = new MessageObject(id, sender, message, dateTime, classNamify);
                inboxMessages.add(singleMessage);
                cursor.moveToNext();
            }
        }
        if(cursor != null)
            cursor.close();
        // ]

        // Create separate folders and group similar messages [
        classNumber = new HashMap<>();
        for(int i = 0; i < className.size(); i++)
        {
            classNumber.put(className.get(i), i);
        }

        if(inboxMessages != null)
        {
            isPresent = new boolean[className.size()];
            for(int i = 0; i < className.size(); i++)
                isPresent[i] = false;
            for(int i = 0; i < inboxMessages.size(); i++)
            {
                MessageObject obj = inboxMessages.get(i);
                String classNamify = obj.getClassName();
                if(classNamify.equals("Misc"))
                    classifiedMessages.add(obj);
                else
                {
                    if(classNumber != null)
                    {
                        int index = classNumber.get(classNamify);
                        if(!isPresent[index])
                        {
                            isPresent[index] = true;
                            classifiedMessages.add(obj);
                        }
                    }
                }
            }
        }
        // ]

        MainAdapter adapter = new MainAdapter(getApplicationContext(), classifiedMessages, inboxMessages, cb);
        listView.setAdapter(adapter);
    }
    public interface IntentCallback
    {
        void callSecondActivity(ArrayList<MessageObject> ob);
    }

    private void defineCallback()
    {
        cb = new IntentCallback() {
            @Override
            public void callSecondActivity(ArrayList<MessageObject> individualMessages) {
                Intent intent = new Intent(getApplicationContext(), IndividualItemActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelableArrayList("ARRAYLIST", individualMessages);
                intent.putExtras(extras);
                startActivity(intent);
            }
        };
    }

    private String getDateTime(Cursor cursor)
    {
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

        Long timestamp = Long.parseLong(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        Date datify = calendar.getTime();

        // Message date
        String msgDate = DateFormat.getDateInstance().format(datify);

        // Today's date
        String todaysDate = DateFormat.getDateInstance().format(new Date());

        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        Date datify1 = calendar1.getTime();

        // Yesterday's date
        String yesterdaysDate = DateFormat.getDateInstance().format(datify1);

        if(msgDate.equals(todaysDate))
            date = "Today";
        else if(msgDate.equals(yesterdaysDate))
            date = "Yesterday";
        else
        {
            int smsDate = calendar.get(Calendar.DATE);
            int smsMonth = calendar.get(Calendar.MONTH) + 1;
            int smsYear = calendar.get(Calendar.YEAR);

            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            if(thisYear == smsYear)
                date = smsDate + "-" + smsMonth;
            else
                date = smsDate + "-" + smsMonth + "-" + smsYear;
        }

        int smsHour = calendar.get(Calendar.HOUR_OF_DAY);
        int smsMin = calendar.get(Calendar.MINUTE);

        String hour = "" + smsHour;
        String min = "" + smsMin;

        if(smsHour < 10)
            hour = "0" + hour;
        if(smsMin < 10)
            min = "0" + min;

        String smsTime = hour + ":" + min;

        String dateTime = date + ";" + smsTime;
        return dateTime;
    }
}
