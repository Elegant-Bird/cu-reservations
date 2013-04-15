package com.loiseauelegante.reservations;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity implements OnClickListener{

	public AlertDialog Hours_Dialog() {
		final String building_hours = "Subject To Change \n\n" +
										"Monday — Friday, \n 7 a.m. — 11 p.m. \n\n" +      
										"Saturday — Sunday, \n 11 a.m. — 11 p.m. \n";
		
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Building Hours").setMessage(building_hours).setNeutralButton("Sounds Good", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		AlertDialog dialog = builder.create();
		return dialog;
		
	}
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***		Create a New Reservation			***/
        final Button create_reservation_button = (Button) findViewById(R.id.reservation_button);
        create_reservation_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	boolean login_valid = false;
            	
        		File reservations_directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/");
        		String filename = reservations_directory.getPath() + "/login.xls";
        		File login_file = new File(filename);
        		
        		if (login_file.exists()) {
                	login_valid = true;
                }
        		
        		if(login_valid) {
	                // Open the create reservations page
	            	Intent intent = new Intent(MainActivity.this, CreateActivity.class);
	            	startActivity(intent);
        		}
        		else {
        			//display toast
        			Context context = getApplicationContext();
            		CharSequence text = "Must create login first!!";
            		int duration = Toast.LENGTH_LONG;

            		Toast toast = Toast.makeText(context, text, duration);
            		toast.show();
        		}
            }
        });
        
        final Button create_login_button = (Button) findViewById(R.id.create_login_button);
        create_login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open the create reservations page
            	Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            	startActivity(intent);
            }
        });
        
        final Button view_reservations_button = (Button) findViewById(R.id.view_reservations_button);
        view_reservations_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open the create reservations page
            	Intent intent = new Intent(MainActivity.this, ViewReservationsActivity.class);
            	startActivity(intent);
            }
        });
        
        final Button contact_cu_button = (Button) findViewById(R.id.contact_cu_button);
        contact_cu_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open the create reservations page
            	Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            	startActivity(intent);
            }
        });
            
        final Button view_forms_button = (Button) findViewById(R.id.view_forms_button);
        view_forms_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open the create reservations page
            	Intent intent = new Intent(MainActivity.this, SolicitationActivity.class);
            	startActivity(intent);
            }
        });
        
        final Button get_directions_button = (Button) findViewById(R.id.get_directions_button);
        get_directions_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(MainActivity.this, DirectionsActivity.class);
            	startActivity(intent);
            }
        });
        
        /***		View policies		****/
        final Button view_policy_button = (Button) findViewById(R.id.view_policies_button);
        view_policy_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(MainActivity.this, PoliciesActivity.class);
            	startActivity(intent);
            }
        });
        
        /***		Hendrix Building Hours		****/
        final Button hendrix_hours_button = (Button) findViewById(R.id.hendrix_hours_button);
        hendrix_hours_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AlertDialog mydialog = Hours_Dialog();
            	mydialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		
	}
	

    
}
