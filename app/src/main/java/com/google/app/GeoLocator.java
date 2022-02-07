package com.google.app;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocator {

    public void getLocation(String location, Context context, Handler handler)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();

                Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
                String latitude = null;
                String longitude = null;
                try {
                    List addressList = geocoder.getFromLocationName(location, 1);
                    if(addressList != null && addressList.size() >0)
                    {
                        Address address = (Address) addressList.get(0);
                        StringBuilder stringBuilder_lat = new StringBuilder();
                        stringBuilder_lat.append(address.getLatitude());
                        StringBuilder stringBuilder_lng = new StringBuilder();
                        stringBuilder_lng.append(address.getLongitude());
                        latitude = stringBuilder_lat.toString();
                        longitude = stringBuilder_lng.toString();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if(latitude != null && longitude != null)
                    {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("Latitude", latitude);
                        bundle.putString("Longitude", longitude);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
