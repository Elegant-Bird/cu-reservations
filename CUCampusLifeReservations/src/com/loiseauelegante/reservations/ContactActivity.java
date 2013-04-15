package com.loiseauelegante.reservations;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.util.Linkify;

public class ContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//populate the name fields
		PopulateNames();
		//populate the contact fields
		PopulateContact();
		
	}

	public void PopulateNames() {
		TextView mandy_name = (TextView)findViewById(R.id.mandy_name_textview);
		TextView alanna_name = (TextView)findViewById(R.id.alanna_name_textview);
		
		mandy_name.setText("Mandy Hays");
		alanna_name.setText("Alanna Waldrop");
	}
	
	public void PopulateContact(){
		TextView alanna_contact = (TextView)findViewById(R.id.alanna_contact_textview);
		alanna_contact.setText(	"\n203 Hendrix Student Center\n" + 
								"Clemson, SC 29634");
		
		TextView alanna_email = (TextView)findViewById(R.id.alanna_email_textview);
		alanna_email.setText("alanna@clemson.edu");
		alanna_email.setLinksClickable(true);
		Linkify.addLinks(alanna_email, Linkify.ALL);

		TextView alanna_phone = (TextView)findViewById(R.id.alanna_phone_textview);
		alanna_phone.setText("(864) 656-6551");
		alanna_phone.setLinksClickable(true);
		Linkify.addLinks(alanna_phone, Linkify.ALL);

		TextView alanna_fax = (TextView)findViewById(R.id.alanna_fax_number_textview);
		alanna_fax.setText("(864) 656-6119\n\n\n");
		alanna_fax.setLinksClickable(true);
		Linkify.addLinks(alanna_fax, Linkify.ALL);
		
		//Begin Mandy
		TextView mandy_contact = (TextView)findViewById(R.id.mandy_contact_textview);
		TextView mandy_email = (TextView)findViewById(R.id.mandy_email_textview);
		TextView mandy_phone = (TextView)findViewById(R.id.mandy_phone_textview);
		mandy_contact.setText("Director of Campus Life Facilities & Operations\n\n" +
								"310 Hendrix Student Center\n" +
								"Clemson, SC 29634");
		
		mandy_email.setText("hays2@clemson.edu");
		mandy_email.setLinksClickable(true);
		Linkify.addLinks(mandy_email, Linkify.ALL);
		
		mandy_phone.setText("(864) 656-5826");
		mandy_phone.setLinksClickable(true);
		Linkify.addLinks(mandy_phone, Linkify.ALL);
		
		
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
		getMenuInflater().inflate(R.menu.contact, menu);
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
