package com.loiseauelegante.reservations;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class DirectionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directions);

		// Show the Up button in the action bar.
		setupActionBar();
		
        final Button google_maps_button = (Button) findViewById(R.id.google_maps_button);
        google_maps_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open the create reservations page
            	Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            		    Uri.parse("google.navigation:q=Clemson University, Clemson, SC 29632"));
            	startActivity(intent);
            }
        });
		
		AddDirections();
	}
	
	private void AddDirections() {
		String asheville = 
				"80 miles \n\n" +
				"1. Take US-74 W to US-23 S " +
				"2. Take I-240 S to I-40 E toward Winston-Salem, NC \n "+
				"3. Exit I-26 E toward Charleston \n " +
				"4. Exit US-25 S toward Travelers Rest \n " + 
				"5. Exit US-123 W toward Easley, SC \n " +
				"6. Go through Easley on 123 to Clemson \n " +
				"7. Turn right on to Perimeter Road (first light on US-76 E) \n " +
				"8. Turn right at first stop light \n " +
				"9. Go through 4-way stop \n " +
				"10. Hendrix Center will be on left at next stop light \n\n";
		String atlanta = 
				"130 miles \n\n" +
				"1. Take I-85 toward Charlotte, NC \n" +
				"2. Take exit 19B to US-76 W to Clemson \n" +
				"3. Turn left on to Perimeter Road (first left after passing National Guard) \n" +
				"4. Turn right at first stop light \n" +
				"5. Go through 4-way stop \n" +
				"6. Hendrix Center will be on left at next stop light \n\n";
		
		String charlotte = 
				"130 miles \n\n" +
				"1. Take I-85 S toward Atlanta, GA \n" +
				"2. Take exit 19B to US-76 W to Clemson \n" +
				"3. Turn left on to Perimeter Road (first left after passing National Guard) \n" +
				"4. Turn right at first stop light \n" +
				"5. Go through four way stop \n" +
				"6. Hendrix Center will be on left at next stop light. \n\n";
		String columbia = 
				"125 miles \n\n" +
				"1. Take I-26 toward Spartanburg, SC \n" +
				"2. Take exit I-385 toward Greenville, SC \n" +
				"3. Exit I-85 S toward Atlanta, GA \n" +
				"4. Take exit 19B to US-76 W to Clemson \n" +
				"5. Turn left on Perimeter Road (first left after passing National Guard) \n" +
				"6. Take right at first stop light \n" +
				"7. Go through 4-way stop \n" +
				"8. Hendrix Center will be on left at next stop light \n\n";
	
				
		TextView asheville_textview = (TextView) findViewById(R.id.ashville_directions_textview);
		TextView atlanta_textview = (TextView) findViewById(R.id.atlanta_directions_textview);
		TextView charlotte_textview = (TextView) findViewById(R.id.charlotte_directions_textview);
		TextView columbia_textview = (TextView) findViewById(R.id.columbia_directions_textview);
	
		//set the texts of those fields now
		
		asheville_textview.setText(asheville);
		atlanta_textview.setText(atlanta);
		charlotte_textview.setText(charlotte);
		columbia_textview.setText(columbia);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.directions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
