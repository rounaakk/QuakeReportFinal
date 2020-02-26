package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public EarthquakeAdapter(Context context, ArrayList<Earthquake> eq) {
        super(context,0, eq);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        View view = convertView;
        int pos=position;
        Earthquake currentEarthquake = (Earthquake) getItem(pos);

        if (view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);

        }


        double mag= currentEarthquake.getmMag();

        DecimalFormat magdec = new DecimalFormat("0.0");
        String magdecStr = magdec.format(mag);


        TextView magObj= (TextView) view.findViewById(R.id.magView);
        magObj.setText(magdecStr);




        String place = currentEarthquake.getmPlace();
        String[] placeSplit =place.split("of");

        TextView placeObj=(TextView) view.findViewById(R.id.placeView);
        placeObj.setText(placeSplit[0]+"of");

        TextView place1Obj=(TextView) view.findViewById(R.id.placeView1);
        place1Obj.setText(placeSplit[1]);





        Date date = new Date(currentEarthquake.getmTime());

        String dateStr= formatDate(date);

        TextView dateObj=(TextView) view.findViewById(R.id.dateView);
        dateObj.setText(dateStr);

        String timeStr= formatTime(date);

        TextView timeObj=(TextView) view.findViewById(R.id.timeView);
        timeObj.setText(timeStr);

        return view;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


}
