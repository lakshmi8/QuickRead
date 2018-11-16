package com.example.lakshmi.quickread;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Lakshmi Narayanan on 2/28/2017.
 */

public class Classifier {

    private Context context;

    private String[] acres = {"NNacre"};
    private String[] airtel = {"Airtel", "AT-", "AIRMTA", "AIROAM"};
    private String[] amazon = {"Amazon"};
    private String[] apollo = {"Apollo"};
    private String[] axis = {"Axis", "AxisBk"};
    private String[] bluedart = {"BluDrt", "BlueDart"};
    private String[] bookmyshow = {"BMSHOW", "BookMyShow"};
    private String[] commonfloor = {"CFLOOR", "CommonFloor"};
    // private String[] ekart = {};
    private String[] faasos = {"FAASOS", "FAASO's"};
    private String[] fasttrack = {"FSTRCK", "FTTAXI", "Fast Track"};
    private String[] flipkart = {"Flipkart"};
    private String[] foodpanda = {"FPANDA", "FoodPanda"};
    private String[] hdfc = {"HDFC"};
    private String[] icici = {"ICICI"};
    private String[] idea = {"IT-612345", "Idea"};
    private String[] indane = {"INDANE"};
    private String[] indianbank = {"Indian bank", "IndBnk", "INDBNK"};
    private String[] indianoil = {"IOCLMD", "IndianOil"};
    private String[] irctc = {"IRCTC"};
    private String[] jio = {"Jio"};
    private String[] ksrtc = {"KSRTC"};
    private String[] lenskart = {"LNSKART", "Lenskart"};
    private String[] lgcare = {"LGCARE"};
    private String[] magicbricks = {"MGCBRK", "MagicBricks"};
    private String[] medplus = {"MEDPLS", "MedPlus"};
    private String[] metropolis = {"MTRPLS", "Metropolis"};
    private String[] mygovt = {"MyGovt"};
    private String[] nobroker = {"NOBRKR", "NoBroker"};
    private String[] ola = {"OLA", "OLACAB", "OLACBS", "OLAMNY"};
    private String[] paytm = {"PayTM"};
    private String[] sbi = {"SBI", "State Bank", "ATMSBI", "SBIINB"};
    private String[] sulekha = {"SULEK", "Sulekha"};
    private String[] swiggy = {"SWIGGY"};
    // private String[] ticketnew = {""};
    private String[] taxiforsure = {"TAXIFS", "TaxiForSure"};
    private String[] tikona = {"TIKONA"};
    private String[] titan = {"TITAN"};
    private String[] uber = {"UBER"};
    private String[] viveks = {"Viveks"};

    private ArrayList<String> contactNumbers;
    private ArrayList<String> contactNames;

    public String getClass(String sender, String message)
    {
        for(int i = 0; i < acres.length; i++)
        {
            if(isSubstring(sender, acres[i])) return "99Acres";
        }

        for(int i = 0; i < airtel.length; i++)
        {
            if(isSubstring(sender, airtel[i])) return "Airtel";
            else if(isSubstring(message, airtel[i])) return "Airtel";
            else if(sender.equals("121")) return "Airtel";
        }

        for(int i = 0; i < amazon.length; i++)
        {
            if(isSubstring(sender, amazon[i])) return "Amazon";
        }

        for(int i = 0; i < apollo.length; i++)
        {
            if(isSubstring(sender, apollo[i])) return "Apollo";
        }

        for(int i = 0; i < axis.length; i++)
        {
            if(isSubstring(sender, axis[i])) return "Axis Bank";
        }

        for(int i = 0; i < bluedart.length; i++)
        {
            if(isSubstring(sender, bluedart[i])) return "Blue Dart";
        }

        for(int i = 0; i < bookmyshow.length; i++)
        {
            if(isSubstring(sender, bookmyshow[i])) return "BookMyShow";
        }

        for(int i = 0; i < commonfloor.length; i++)
        {
            if(isSubstring(sender, commonfloor[i])) return "CommonFloor";
            else if(isSubstring(message, commonfloor[i])) return "CommonFloor";
        }

        for(int i = 0; i < faasos.length; i++)
        {
            if(isSubstring(sender, faasos[i])) return "FAASOS";
            else if(isSubstring(message, commonfloor[i])) return "CommonFloor";
        }

        for(int i = 0; i < fasttrack.length; i++)
        {
            if(isSubstring(sender, fasttrack[i])) return "Fast Track";
        }

        for(int i = 0; i < flipkart.length; i++)
        {
            if(isSubstring(sender, flipkart[i])) return "Flipkart";
        }

        for(int i = 0; i < foodpanda.length; i++)
        {
            if(isSubstring(sender, foodpanda[i])) return "FoodPanda";
        }

        for(int i = 0; i < hdfc.length; i++)
        {
            if(isSubstring(sender, hdfc[i])) return "HDFC Bank";
        }

        for(int i = 0; i < icici.length; i++)
        {
            if(isSubstring(sender, icici[i])) return "ICICI Bank";
        }

        for(int i = 0; i < idea.length; i++)
        {
            if(isSubstring(sender, idea[i])) return "Idea";
        }

        for(int i = 0; i < indane.length; i++)
        {
            if(isSubstring(sender, indane[i])) return "Indane";
        }

        for(int i = 0; i < indianbank.length; i++)
        {
            if(isSubstring(sender, indianbank[i])) return "Indian Bank";
        }

        for(int i = 0; i < indianoil.length; i++)
        {
            if(isSubstring(sender, indianoil[i])) return "IndianOil";
            else if(isSubstring(message, indianoil[i])) return "IndianOil";
        }

        for(int i = 0; i < irctc.length; i++)
        {
            if(isSubstring(sender, irctc[i])) return "IRCTC";
        }

        if(isSubstring(message, "PNR") && isSubstring(message, "insurance")) return "IRCTC Insurance";

        for(int i = 0; i < jio.length; i++)
        {
            if(isSubstring(sender, jio[i])) return "Jio";
        }

        for(int i = 0; i < ksrtc.length; i++)
        {
            if(isSubstring(sender, ksrtc[i])) return "KSRTC";
        }

        for(int i = 0; i < lenskart.length; i++)
        {
            if(isSubstring(sender, lenskart[i])) return "Lenskart";
            else if(isSubstring(message, lenskart[i])) return "Lenskart";
        }

        for(int i = 0; i < lgcare.length; i++)
        {
            if(isSubstring(sender, lgcare[i])) return "LG Care";
        }

        for(int i = 0; i < magicbricks.length; i++)
        {
            if(isSubstring(sender, magicbricks[i])) return "MagicBricks";
        }

        for(int i = 0; i < medplus.length; i++)
        {
            if(isSubstring(sender, medplus[i])) return "MedPlus";
        }

        for(int i = 0; i < metropolis.length; i++)
        {
            if(isSubstring(sender, metropolis[i])) return "Metropolis";
            else if(isSubstring(message, metropolis[i])) return "Metropolis";
        }

        for(int i = 0; i < mygovt.length; i++)
        {
            if(isSubstring(sender, mygovt[i])) return "MyGovt";
        }

        for(int i = 0; i < nobroker.length; i++)
        {
            if(isSubstring(sender, nobroker[i])) return "NoBroker";
        }

        for(int i = 0; i < ola.length; i++)
        {
            if(isSubstring(sender, ola[i])) return "Ola";
        }

        for(int i = 0; i < paytm.length; i++)
        {
            if(isSubstring(sender, paytm[i])) return "PayTM";
        }

        for(int i = 0; i < sbi.length; i++)
        {
            if(isSubstring(sender, sbi[i])) return "State Bank of India";
        }

        for(int i = 0; i < sulekha.length; i++)
        {
            if(isSubstring(sender, sulekha[i])) return "Sulekha";
        }

        for(int i = 0; i < swiggy.length; i++)
        {
            if(isSubstring(sender, swiggy[i])) return "Swiggy";
            else if(isSubstring(message, swiggy[i])) return "Swiggy";
        }

        for(int i = 0; i < taxiforsure.length; i++)
        {
            if(isSubstring(sender, taxiforsure[i])) return "TaxiForSure";
        }

        for(int i = 0; i < tikona.length; i++)
        {
            if(isSubstring(sender, tikona[i])) return "Tikona";
        }

        for(int i = 0; i < titan.length; i++)
        {
            if(isSubstring(message, titan[i])) return "Titan watch";
        }

        for(int i = 0; i < uber.length; i++)
        {
            if(isSubstring(sender, uber[i])) return "Uber";
        }

        for(int i = 0; i < viveks.length; i++)
        {
            if(isSubstring(sender, viveks[i])) return "Viveks";
        }

        for(int i = 0; i < contactNumbers.size(); i++)
        {
            String number = contactNumbers.get(i);
            if(isSubstring(sender, number)) return contactNames.get(i);
        }

        return "Misc";
    }

    private boolean isSubstring(String str, String substr)
    {
        return str.toLowerCase().contains(substr.toLowerCase());
    }

    private void getAllContacts()
    {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext())
        {
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            if(phoneNumber != null)
                contactNumbers.add(phoneNumber);
            else
                contactNumbers.add("");

            if(name != null)
                contactNames.add(name);
            else
                contactNames.add("");
        }
        cursor.close();
    }

    public ArrayList<String> getContactNumbers()
    {
        return contactNumbers;
    }

    public ArrayList<String> getContactNames()
    {
        return contactNames;
    }

    public Classifier(Context mContext)
    {
        context = mContext;
        contactNumbers = new ArrayList<>();
        contactNames = new ArrayList<>();
        getAllContacts();
    }
}
