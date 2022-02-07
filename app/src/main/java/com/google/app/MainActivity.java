package com.google.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.app.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private final static String TAG = MainActivity.class.getSimpleName();

    private String LOCATE_BASE = "geo:0,0?q=";
    private String NAVIGATE_BASE = "google.navigation:q=";
    private String LOCATE_URI_BASE = "https://www.google.com/maps/dir/?api=1";
    private String LOCATE_URI_ORIGIN = "&origin=";
    private String LOCATE_URI_DESTINATION = "&destination=";
    private String LOCATE_URI_WAYPOINTS = "&waypoints=";
    private String LOCATE_URI_TRAVELLINGMODE = "&travelmode=";
    private final static  String LOCATE_URI_TAIL = "&avoid=tolls&dir_action=navigate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.locateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LOCATE_BASE = LOCATE_BASE + binding.destination.getText().toString().trim();
                Uri gmmIntentUri = Uri.parse(LOCATE_BASE);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);



            }
        });



        binding.navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(binding.driving.isChecked())
                {
                    LOCATE_URI_TRAVELLINGMODE = LOCATE_URI_TRAVELLINGMODE + "driving";
                }
                else if(binding.bike.isChecked())
                {
                    LOCATE_URI_TRAVELLINGMODE = LOCATE_URI_TRAVELLINGMODE + "two-wheeler";
                }
                else
                {
                    LOCATE_URI_TRAVELLINGMODE = LOCATE_URI_TRAVELLINGMODE + "walking";
                }


                LOCATE_URI_ORIGIN=
                        LOCATE_URI_ORIGIN + binding.originLocation.getText().toString().trim().toLowerCase(Locale.ROOT);

                LOCATE_URI_DESTINATION=
                        LOCATE_URI_DESTINATION + binding.destination.getText().toString().trim().toLowerCase(Locale.ROOT);




                if(binding.via.getText().toString() != null)
                {
                    LOCATE_URI_WAYPOINTS =
                            LOCATE_URI_WAYPOINTS + binding.via.getText().toString().toLowerCase(Locale.ROOT).trim();
                    LOCATE_URI_BASE =
                            LOCATE_URI_BASE + LOCATE_URI_ORIGIN + LOCATE_URI_DESTINATION + LOCATE_URI_WAYPOINTS + LOCATE_URI_TRAVELLINGMODE + LOCATE_URI_TAIL;
                    Log.d(TAG,""+LOCATE_URI_BASE);
                }

                else
                {
                    LOCATE_URI_BASE =
                            LOCATE_URI_BASE + LOCATE_URI_ORIGIN + LOCATE_URI_DESTINATION +LOCATE_URI_TRAVELLINGMODE + LOCATE_URI_TAIL;
                    Log.d(TAG, ""+LOCATE_URI_BASE);
                }

                Uri gmmIntentUri = Uri.parse(LOCATE_URI_BASE);
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        Toast.makeText(MainActivity.this, "Please install a maps application",
                                Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LOCATE_BASE = "geo:0,0?q=";
        LOCATE_URI_BASE = "https://www.google.com/maps/dir/?api=1";
        LOCATE_URI_ORIGIN = "&origin=";
        LOCATE_URI_DESTINATION = "&destination=";
        LOCATE_URI_WAYPOINTS = "&waypoints=";
        LOCATE_URI_TRAVELLINGMODE = "&travelmode=";
    }

}