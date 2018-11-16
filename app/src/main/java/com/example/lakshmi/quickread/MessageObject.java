package com.example.lakshmi.quickread;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lakshmi Narayanan on 2/26/2017.
 */
public class MessageObject implements Parcelable
{
    private int id;
    private String sender;
    private String message;
    private String dateTime;
    private String className;

    public MessageObject(int _id, String number, String message_content, String msg_date_time, String class_name)
    {
        id = _id;
        sender = number;
        message = message_content;
        dateTime = msg_date_time;
        className = class_name;
    }

    protected MessageObject(Parcel in)
    {
        id = in.readInt();
        sender = in.readString();
        message = in.readString();
        dateTime = in.readString();
        className = in.readString();
    }

    public static final Creator<MessageObject> CREATOR = new Creator<MessageObject>()
    {
        @Override
        public MessageObject createFromParcel(Parcel in) {
            return new MessageObject(in);
        }

        @Override
        public MessageObject[] newArray(int size) {
            return new MessageObject[size];
        }
    };

    public int getID() { return id; }
    public String getSenderNumber() { return sender; }
    public String getMessage() { return message; }
    public String getDateTime() { return dateTime; }
    public String getClassName() { return className; }

    public void setID(int _id) { this.id = _id; }
    public void setSenderNumber(String number) { this.sender = number; }
    public void setMessage(String message_content) { this.message = message_content; }
    public void setClassName(String class_name) { this.className = class_name; }
    public void setDate(String msg_date_time) { this.dateTime = msg_date_time; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(id);
        parcel.writeString(sender);
        parcel.writeString(message);
        parcel.writeString(dateTime);
        parcel.writeString(className);
    }
}
