package com.loiseauelegante.reservations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import android.app.AlertDialog;
import android.app.TimePickerDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;

import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import javax.activation.CommandMap; 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.activation.MailcapCommandMap; 
import javax.mail.BodyPart; 
import javax.mail.Multipart; 
import javax.mail.PasswordAuthentication; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 

public class CreateActivity extends FragmentActivity {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//prevent the keyboard from popping up automatically
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 

		//set the content view to this pager
		setContentView(R.layout.activity_create);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			if(position == 0) {
				Fragment fragment = new OrganizationsSectionFragment();
				Bundle args = new Bundle();
				args.putInt(OrganizationsSectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);				
				
				return fragment;
			}				
			else if(position == 1) {
				Fragment fragment = new EventInformationFragment();
				Bundle args = new Bundle();
				args.putInt(EventInformationFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				
				return fragment;
			} else if(position == 3) {
				Fragment fragment = new FurnitureFragment();
				Bundle args = new Bundle();
				args.putInt(FurnitureFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);				
				
				return fragment;
			}	
			else if(position == 2) {
				Fragment fragment = new EquipmentFragment();
				Bundle args = new Bundle();
				args.putInt(EquipmentFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				
				return fragment;
			}	
			else if(position == 4) {
				Fragment fragment = new CUPDFragment();
				Bundle args = new Bundle();
				args.putInt(CUPDFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);		
				
				return fragment;
			}
			else {
				Fragment fragment = new ConfirmationFragment();
				Bundle args = new Bundle();
				args.putInt(ConfirmationFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);				
				
				return fragment;
			}
		}

		@Override
		public int getCount() {
			// Show 6 total pages.
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_organization).toUpperCase(l);
			case 1:
				return getString(R.string.title_event).toUpperCase(l);
			case 2:
				return getString(R.string.title_equipment).toUpperCase(l);
			case 3:
				return getString(R.string.title_furniture).toUpperCase(l);
			case 4:
				return getString(R.string.title_cupd).toUpperCase(l);
			case 5:
				return getString(R.string.title_confirmation).toUpperCase(l);
			}
			return null;
		}
	}

	
	/************************************************************
	 * Event Information Fragment
	 * @author natasha.loiseau
	 *
	 */
	public static class EventInformationFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static EditText event_name;
		public static TextView location_name;
		public static TimePicker start_time;
		public static TimePicker end_time;
		public static DatePicker date;

		public EventInformationFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.event_information,
					container, false);

			final Button Locations_Button = (Button) rootView
					.findViewById(R.id.location_button);
			event_name = (EditText) rootView.findViewById(R.id.event_name_textview);
			location_name = (TextView) rootView.findViewById(R.id.location_label_item);
			start_time = (TimePicker) rootView.findViewById(R.id.start_time_picker);
			end_time = (TimePicker) rootView.findViewById(R.id.end_time_picker);
			date = (DatePicker) rootView.findViewById(R.id.event_date_picker);
			date.setCalendarViewShown(false);
			
			try {
				java.lang.reflect.Field[] f = date.getClass().getDeclaredFields();
				for (java.lang.reflect.Field field : f) {
				    if (field.getName().equals("mYearPicker")) {
				        field.setAccessible(true);
				        Object yearPicker = new Object();
				        yearPicker = field.get(date);
				        ((View) yearPicker).setVisibility(View.VISIBLE);
				    }
				}
				        } catch (SecurityException e) {
				Log.d("ERROR", e.getMessage());
				} 
				catch (IllegalArgumentException e) {
				Log.d("ERROR", e.getMessage());
				} catch (IllegalAccessException e) {
				Log.d("ERROR", e.getMessage());
				}

			Locations_Button.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	AlertDialog mydialog = Locations_Dialog(location_name);
			    	mydialog.show();
			    }
			});

			return rootView;
		}

		public AlertDialog Locations_Dialog(final TextView selected_item) {
			final String[] location_string = getResources().getStringArray(R.array.Locations_List);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Venues").setItems(R.array.Locations_List, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					selected_item.setText(location_string[which]);
			       }
				});
			
			AlertDialog dialog = builder.create();
			return dialog;
		}
	}

	/*************************************************
	 * Furniture Fragment
	 * @author natasha.loiseau
	 *
	 */
	public static class FurnitureFragment extends Fragment implements View.OnClickListener{
		/*****************************************
		 * The fragment argument representing the section number for this
		 * fragment.
		 * The checkbox array is accessible from other fragments
		 */
		public static final String ARG_SECTION_NUMBER = "section_1";
		public static CheckBox [] furniture_boxes = new CheckBox[18];
		boolean preloaded = false;

		public FurnitureFragment() {
		
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.furniture,
					container, false);

			furniture_boxes[0] = (CheckBox)rootView.findViewById(R.id.chair_checkbox);
			furniture_boxes[1] = (CheckBox)rootView.findViewById(R.id.table_8_checkbox);
			furniture_boxes[2] = (CheckBox)rootView.findViewById(R.id.table_6_checkbox);
			furniture_boxes[3] = (CheckBox)rootView.findViewById(R.id.round_table_checkbox);
			furniture_boxes[4] = (CheckBox)rootView.findViewById(R.id.cocktail_checkbox);
			furniture_boxes[5] = (CheckBox)rootView.findViewById(R.id.conference_checkbox);
			furniture_boxes[6] = (CheckBox)rootView.findViewById(R.id.floor_podium_checkbox);
			furniture_boxes[7] = (CheckBox)rootView.findViewById(R.id.table_podium_checkbox);
			furniture_boxes[8] = (CheckBox)rootView.findViewById(R.id.dance_floor_checkbox);
			furniture_boxes[9] = (CheckBox)rootView.findViewById(R.id.dance_floor2_checkbox);
			furniture_boxes[10] = (CheckBox)rootView.findViewById(R.id.dry_erase_checkbox);
			furniture_boxes[11] = (CheckBox)rootView.findViewById(R.id.portable_board_checkbox);
			furniture_boxes[12] = (CheckBox)rootView.findViewById(R.id.flip_chart_checkbox);
			furniture_boxes[13] = (CheckBox)rootView.findViewById(R.id.easel_checkbox);
			furniture_boxes[14] = (CheckBox)rootView.findViewById(R.id.stage_checkbox);
			furniture_boxes[15] = (CheckBox)rootView.findViewById(R.id.pipe_drape_checkbox);
			furniture_boxes[16] = (CheckBox)rootView.findViewById(R.id.plants_checkbox);
			furniture_boxes[17] = (CheckBox)rootView.findViewById(R.id.linen_checkbox);
			
			//Set the onclick listener for each piece of furniture
			for(int i = 0; i < 18; i++) {
				furniture_boxes[i].setOnClickListener(this);
			}
			
			return rootView;
		}
		
		//create an alert dialog with a number picker to save the number of chairs, etc. entered
		public AlertDialog Furniture_Number(final CheckBox furniture_item) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    LayoutInflater inflater = getActivity().getLayoutInflater();
		    
		    final View builder_view = inflater.inflate(R.layout.number_dialog, null);
		    builder.setView(builder_view);
		  //prevent the keyboard from auto-opening
			
			builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {  
		    	@Override
	               public void onClick(DialogInterface dialog, int id) {
	                   String original_text = (String) furniture_item.getText();
	                   TextView number_text = (TextView)builder_view.findViewById(R.id.number_textview);
	                   furniture_item.setText("(" + number_text.getText() + ") -" + original_text);
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                  //if they cancel, un-check the box
	            	   furniture_item.setChecked(false);
	               }
	           });    
		    
		    return builder.create();
		}

		@Override
		public void onClick(View v) {
			CheckBox thisbutton = (CheckBox)(v);
			String mystring = (String)thisbutton.getText();
				
		   if(thisbutton.isChecked()) {
			   	AlertDialog mydialog = Furniture_Number(thisbutton);
			   	mydialog.show();
		   }
		   else {
			   //set the label back to zero
			   mystring = mystring.substring(mystring.indexOf('-') + 1, mystring.length());
			   thisbutton.setText(mystring);
		   }	
		}
	}
	
	public static class EquipmentFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static CheckBox [] equipment_checkbox = new CheckBox[8];
		
		public EquipmentFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.equipment,
					container, false);
			
			//set all of the checkbox values
			equipment_checkbox[0] = (CheckBox) rootView.findViewById(R.id.conference_projector_checkbox);
			equipment_checkbox[1] = (CheckBox) rootView.findViewById(R.id.house_sound_checkbox);
			equipment_checkbox[2] = (CheckBox) rootView.findViewById(R.id.projector_checkbox);
			equipment_checkbox[3] = (CheckBox) rootView.findViewById(R.id.concert_lights_checkbox);
			equipment_checkbox[4] = (CheckBox) rootView.findViewById(R.id.tv_checkbox);
			equipment_checkbox[5] = (CheckBox) rootView.findViewById(R.id.screen_checkbox);
			equipment_checkbox[6] = (CheckBox) rootView.findViewById(R.id.pa_checkbox);
			equipment_checkbox[7] = (CheckBox) rootView.findViewById(R.id.house_inputs_checkbox);

			return rootView;
		}
	}
	
	/*************************************************
	 * CUPD Fragment is Sales and Solicitation
	 * @author natasha.loiseau
	 *
	 */
	public static class CUPDFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static CheckBox [] solicitation_items = new CheckBox[12];

		public CUPDFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.sales_and_solicitation,
					container, false);

			//set the checkbox items
			solicitation_items[0] = (CheckBox) rootView.findViewById(R.id.guests_checkbox);
			solicitation_items[1] = (CheckBox) rootView.findViewById(R.id.attendance_checkbox);
			solicitation_items[2] = (CheckBox) rootView.findViewById(R.id.alcohol_checkbox);
			solicitation_items[3] = (CheckBox) rootView.findViewById(R.id.money_checkbox);
			solicitation_items[4] = (CheckBox) rootView.findViewById(R.id.catering_checkbox);
			solicitation_items[5] = (CheckBox) rootView.findViewById(R.id.food_checkbox);
			solicitation_items[6] = (CheckBox) rootView.findViewById(R.id.outdoor_checkbox);
			solicitation_items[7] = (CheckBox) rootView.findViewById(R.id.planner_checkbox);
			solicitation_items[8] = (CheckBox) rootView.findViewById(R.id.after_hours_checkbox);
			solicitation_items[9] = (CheckBox) rootView.findViewById(R.id.paw_checkbox);
			solicitation_items[10] = (CheckBox) rootView.findViewById(R.id.tshirt_checkbox);
			solicitation_items[11] = (CheckBox) rootView.findViewById(R.id.fire_checkbox);

			return rootView;
		}
	}
	
	public static class ConfirmationFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public ConfirmationFragment() {
			//debug -- show all new added emails

		}
		
		//get the login first and last name
		private String[] RetrieveLoginFile() throws IOException {
			File reservations_directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/");
			
			if (!reservations_directory.exists()) {
				Log.v("Directory does not exist", "NONEXISTENT DIRECTORY");
	        	return null;
	        }
			
			String filename = reservations_directory.getPath() + "/login.xls";
	    	String [] names = new String[2];
	    	String last_label = "";
			
	    	try {
	    		File login_file = new File(filename);
	    		Workbook login_workbook = Workbook.getWorkbook(login_file);
	    		Sheet login_sheet = login_workbook.getSheet(0);

	    		for(int col = 0; col < login_sheet.getColumns(); col++){
	    			int counter = 0;
	    			for(int row = 0; row < login_sheet.getRows(); row++){
	    				Cell cell = login_sheet.getCell(col, row);
	    				CellType type = cell.getType();
	    				
	    				if(type == CellType.LABEL && counter == 1){
	    					if(last_label.compareTo("First Name") == 0)
	    						names[0] = cell.getContents();
	    					else if(last_label.compareTo("Last Name") == 0)
	    						names[1] = cell.getContents();
	    				}
	    				last_label = cell.getContents();
	    				counter++;
	    			}
	    		}
	    	} catch(BiffException e) {
	    		e.printStackTrace();
	    	}
	    	return names;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.confirmation,
					container, false);
        	final Context context = getActivity().getApplicationContext();

			//get all of the fragment values
			final CheckBox [] solicitation_items = CUPDFragment.solicitation_items;
			final CheckBox [] furniture_items = FurnitureFragment.furniture_boxes;
			final CheckBox [] equipment_items = EquipmentFragment.equipment_checkbox;
			final EditText event_name = EventInformationFragment.event_name;
			final TextView location = EventInformationFragment.location_name;
			final TimePicker start_time = EventInformationFragment.start_time;
			final TimePicker end_time = EventInformationFragment.end_time;
			final DatePicker date = EventInformationFragment.date;
			final EditText organization_name = OrganizationsSectionFragment.OrganizationName;
			final EditText responsible_party = OrganizationsSectionFragment.ResponsibleParty;
			final EditText responsible_email = OrganizationsSectionFragment.ResponsibleEmail;
			
			//add all of these items to the confirmation view
        	StringBuilder confirmation_string = new StringBuilder();
        	StringBuilder furniture_string = new StringBuilder();
        	StringBuilder solicitation_string = new StringBuilder();
        	StringBuilder equipment_string = new StringBuilder();
        	StringBuilder event_info_string = new StringBuilder();
        	StringBuilder organization_string = new StringBuilder();
        	String AdditionalSteps = "";
        	
        	
        	//get the full name to store that and initials for the confirmation page
        	String full_name = "";
        	String initials = "";
        	
        	try {
				String [] names = RetrieveLoginFile();
				if(names != null) {
					full_name = names[0] + " " + names[1];
					initials = names[0].substring(0, 1) + names[1].substring(0, 1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        	/* Add Organization Page Information */
        	String organization_name_string = "Name";
        	String responsible_party_string = "Name at Email";
        	String email_addresses = "";
        	
        	//add extra emails to list
			int email_count = OrganizationsSectionFragment.EmailLayout.getChildCount();// and ViewGroup.getChildAt(int)
			for(int i = 1; i < email_count; i++) {
				EditText email_address = (EditText)OrganizationsSectionFragment.EmailLayout.getChildAt(i);
				Log.v("Email", email_address.getText().toString());
				email_addresses+= email_address.getText().toString() + "; ";
				
			}
        	organization_name_string = organization_name.getText().toString();
        	responsible_party_string = responsible_party.getText().toString() + " - " + responsible_email.getText().toString();
			
        	confirmation_string.append("\n *****Organization Information***** \n");
        	confirmation_string.append("Organization Name: " + organization_name_string + "\n");
			confirmation_string.append("Responsible Party: " + responsible_party_string + "\n");
			confirmation_string.append("Additional People to Email: " + email_addresses + "\n\n");
			
			
        	//set the name and initials text
        	EditText initials_edittext = (EditText) rootView.findViewById(R.id.initials_text);
        	EditText name_edittext = (EditText) rootView.findViewById(R.id.name_text);
        	initials_edittext.setText(initials);
        	name_edittext.setText(full_name);
        	
        	/* Add all of the event information to the confirmation string and event string */
        	confirmation_string.append("\n *****Event Information***** \n");
        	confirmation_string.append("Event Name: " + event_name.getText() + "\n");
        	confirmation_string.append("Location: " + location.getText() + "\n");
        	confirmation_string.append("Start Time: "+ start_time.getCurrentHour() + ":" +
        			start_time.getCurrentMinute().doubleValue() + start_time.getTag(Calendar.AM_PM) + "\n");
        	confirmation_string.append("End Time: " + end_time.getCurrentHour() + ":" +
        			end_time.getCurrentMinute().doubleValue() + end_time.getTag(Calendar.AM_PM) + "\n");
        	confirmation_string.append("Date: " + date.getMonth() + "-" + date.getDayOfMonth() + "-" + date.getYear() + "\n");
        	
        	event_info_string.append(event_name.getText() + "\n");
        	event_info_string.append(location.getText() + "\n");
        	event_info_string.append(start_time.getCurrentHour() + ":" + 
        	start_time.getCurrentMinute().doubleValue() + start_time.getTag(Calendar.AM_PM) + "\n");
        	event_info_string.append(end_time.getCurrentHour() + ":" + 
        			end_time.getCurrentMinute().doubleValue() +  end_time.getTag(Calendar.AM_PM) +  "\n");
        	event_info_string.append(date.getMonth() + "-" + date.getDayOfMonth() + "-" + date.getYear() + "\n");
        	
        	
        	/* Add the Sales and solicitation items to both string builders */
        	boolean special_cases = false;
			confirmation_string.append("\n\n *****Sales and Solicitation Items***** \n");
        	for(int i = 0; i < solicitation_items.length; i++) {
        		if(solicitation_items[i].isChecked()) {
        			confirmation_string.append(solicitation_items[i].getText() + "\n");
        			solicitation_string.append(solicitation_items[i].getText() + "\n");
        			special_cases = true;
        		}
        	}
        	if(special_cases)
        		AdditionalSteps += "Get Sales and Solicitation Form Signature\n";
        	
        	/* Add the furniture items to the string builders */
        	boolean added_furniture = false;
        	confirmation_string.append("\n\n *****Furniture Items***** \n\n");
        	for(int i = 0; i < furniture_items.length; i++) {
        		if(furniture_items[i].isChecked()) {
        			confirmation_string.append(furniture_items[i].getText() + "\n");
        			furniture_string.append(furniture_items[i].getText() + "\n");
        			added_furniture = true;
        		}
        	}
        	if(added_furniture)
        		AdditionalSteps+= "Complete a diagram for furniture items\n";
        	
        	/* Add the audio/visual equipment to the string builders */
        	confirmation_string.append("\n *****Audio/Video Equipment***** \n\n");
        	for(int i = 0; i < equipment_items.length; i++) {
        		if(equipment_items[i].isChecked()) {
        			confirmation_string.append(equipment_items[i].getText() + "\n");
        			equipment_string.append(equipment_items[i].getText() + "\n");
        		}
        	}
        	
        	confirmation_string.append("\n ******Additional Steps Required****** \n");
        	confirmation_string.append(AdditionalSteps + "\n" + "Contact Reservations for Deposit Information \n");

        	TextView confirmation_view = (TextView)rootView.findViewById(R.id.reservation_summary_textview_item);
        	confirmation_view.setText(confirmation_string);
        	
        	final String email_string = confirmation_string.toString();
        	final String furniture_tostring = furniture_string.toString();
        	final String solicitation_tostring = solicitation_string.toString();
        	final String equipment_tostring = equipment_string.toString();
        	final String emails = email_addresses;
        	final String financial_party = responsible_party_string;
        	final String organization = organization_name_string;
        	final String additional_steps = AdditionalSteps;
        	final String event_name_string = event_name.getText().toString();
						
	        Button confirm_button = (Button) rootView.findViewById(R.id.confirm_button);
	        confirm_button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
            		int duration = Toast.LENGTH_LONG;
            		
	            	//store everything to its respective field in the spreadsheet
	            	String event = location.getText().toString();

	            	CheckBox accept_box = (CheckBox)rootView.findViewById(R.id.accept_conditions_checkbox);
	            	if(event.trim().compareTo("Selected Venue Will Appear Here") == 0) {
	            		Toast toast = Toast.makeText(context, "Must choose a venue!", duration);
	            		toast.show();
	            	} else if(organization.trim().compareTo("") == 0) { //else if the organization text equals...
	            		Toast toast = Toast.makeText(context, "Must choose an organization!", duration);
	            		toast.show();
	            	}
	            	else if(!accept_box.isChecked()) {
	            		Toast toast = Toast.makeText(context, "Must accept conditions!!!", duration);
	            		toast.show();
	            	} //make sure financial party is supplied
	            	else if(financial_party.trim().compareTo("") == 0) {
	            		Toast toast = Toast.makeText(context, "Must provide financially responsible party!", duration);
	            		toast.show();
	            	}
	            	//make sure event name is supplied 
	            	else if(event_name_string.compareTo("") == 0) {
	            		Toast toast = Toast.makeText(context, "Must provide an event name!", duration);
	            		toast.show();
	            	}
	            	else {
	            		try {
							CreateReservationsFile(furniture_tostring, solicitation_tostring, 
									equipment_tostring, event_name, location,  start_time,
									 end_time,  date, emails, financial_party, organization, additional_steps);
						} catch (WriteException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
	            		//if it does, add a new line to existing file
	            		catch (BiffException e) {
							e.printStackTrace();
						}
	            		
	            		SendReservationEmail(email_string, emails);
	            		
	            		//Return back to the homescreen
	            		getActivity().finish();
	            	}
	            	
	            }
	        });
			return rootView;
		}
		
		private void SendReservationEmail(String email_string, String emails) {
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"reserve@clemson.edu", emails});
			i.putExtra(Intent.EXTRA_CC, emails);
			i.putExtra(Intent.EXTRA_SUBJECT, "New Reservation from CU Reservations Android Application");
			i.putExtra(Intent.EXTRA_TEXT   , email_string);
			try {
			    startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
			}
		}
		
		private void CreateReservationsFile(String furniture_string, String solicitation_string, 
			String equipment_string, EditText event_name, TextView location, TimePicker start_time,
			TimePicker end_time, DatePicker date, String emails, String financial_party, 
			String organization, String additional_steps) throws IOException, WriteException, BiffException {
			
			String [] Titles_Array = {
					"Organization",	"Responsible Party",	"Additional Emails", 
					"Event Name", "Event Location",	"Start Time",
					"End Time", "Date", "Sales and Solicitation", 
					"AV Equipment", "Furniture", 
					"Conditions Accepted?", "Additional Steps Required"
			};
			
			//get the times and dates into a string
			String start_time_string = start_time.getCurrentHour() + ":" + start_time.getCurrentMinute();
			String end_time_string = end_time.getCurrentHour() + ":" + end_time.getCurrentMinute();
			String date_string = date.getMonth() + " " + date.getDayOfMonth() + ", " + date.getYear();
			
			//store all of the string builders into an array in order by title
			String [] Reservation_Array = {
					organization, financial_party, emails, 
					event_name.getText().toString(), location.getText().toString(), start_time_string, 
					end_time_string, date_string, solicitation_string,
					equipment_string, furniture_string, 
					"Yes", "Submit Deposit to Guest Services Desk \n" + additional_steps
			};
			
			File reservations_directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/");
			if (!reservations_directory.exists()) {
	        	reservations_directory.mkdir();
	        }
			
			String filename = reservations_directory.getPath() + "/ExistingReservations.xls";
			Log.v("Organization File Path: ", filename);
			File reservations_file = new File(filename);
			boolean created = false;
			  
			WritableWorkbook modified_workbook = null;
			Workbook existing_workbook = null;
			WritableSheet reservations_sheet = null;
			int row = 0;
			int column = 0;
			int placement_index = 1;
			
			if(created) {	//if the workbook did not already exist
				WorkbookSettings workbook_settings = new WorkbookSettings();
				workbook_settings.setLocale(new Locale("en", "EN"));
				modified_workbook = Workbook.createWorkbook(reservations_file, workbook_settings);
				reservations_sheet = modified_workbook.createSheet("Reservations_Sheet", 0);
				Log.v("Organizations New File", "Created");
			} else {
				existing_workbook = Workbook.getWorkbook(reservations_file);
				modified_workbook = Workbook.createWorkbook(reservations_file, existing_workbook);
				reservations_sheet = modified_workbook.getSheet(0);
				row = reservations_sheet.getRows();		//get the n
				placement_index = row;
				Log.v("Existing File", "Opened");
			}
			
			//Format the font
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setWrap(true);

			//for each item in login_fields, write a new label and data
			for(int i = 0; i < 13; i++) {
				if(created) {
					Log.v("New File", "Added Label");
					Label cell_label = new Label(row, 0, Titles_Array[i], format);
					reservations_sheet.addCell(cell_label);
				}
				
				Label cell_data = new Label(column, placement_index, Reservation_Array[i], format);
				reservations_sheet.addCell(cell_data);
				Log.v("Adding to File", "Added Data");

				row++;
				column++;
			}
			modified_workbook.write();
			modified_workbook.close();
		}
	}
			
	public static class OrganizationsSectionFragment extends Fragment {		
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static LinearLayout EmailLayout;
		public static EditText OrganizationName;
		public static EditText ResponsibleParty;
		public static EditText ResponsibleEmail;
		
		public OrganizationsSectionFragment() {
		}
		
		private TextView createNewTextView() {
		    final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    Context context = this.getActivity().getApplicationContext();
		    final EditText textView = new EditText(context);
		    
		    textView.setLayoutParams(lparams);
		    textView.setText("@clemson.edu");
		    return textView;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_organizations,
					container, false);
			
			final Button Undergraduate_Button = (Button) rootView
					.findViewById(R.id.undergraduate_button);
			final Button Graduate_Button = (Button) rootView
					.findViewById(R.id.graduate_button);
			final Button Auxilary_Button = (Button) rootView
					.findViewById(R.id.auxilary_button);
			final Button NonAuxilary_Button = (Button) rootView
					.findViewById(R.id.non_auxilary_button);
			final Button AddEmail_Button = (Button) rootView
					.findViewById(R.id.add_email_button);

			EmailLayout = (LinearLayout) rootView.findViewById(R.id.EmailLayout);
			
			final EditText myorg = (EditText) rootView.findViewById(R.id.organization_label_item);
			OrganizationName = myorg;
			ResponsibleParty = (EditText) rootView.findViewById(R.id.financial_name_edittext);
			ResponsibleEmail = (EditText) rootView.findViewById(R.id.financial_email_edittext);
			
			AddEmail_Button.setOnClickListener(new View.OnClickListener() {
				
	            public void onClick(View v) {
	            	//AddEmail_TextView
	            	EmailLayout.addView(createNewTextView());
	            }
	        });
			

			
			Undergraduate_Button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	    			String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/undergrad_customers.xls";
	    			AssetManager assetManager = getActivity().getAssets();
	    			ArrayList<String> organization_list = new ArrayList<String>();
	    		
	    			try {
	    				Log.v("Attempt Org File", "Creating and retrieving");
	    				InputStream OrganizationStream = assetManager.open("Undergrad Customers.xls");
	    				CreateFileFromInputStream(OrganizationStream, filename);
	    				organization_list = RetrieveOrganizationsFile(3);
	    				
	    			} catch (IOException e1) {
	    				Log.v("Failure", "Failed to open undergrad file");
	    				e1.printStackTrace();
	    			}
	    			
	            	
	            	AlertDialog mydialog = Oraganizations_Dialog(myorg, organization_list);
	            	mydialog.show();
	            }
	        });
			
			Graduate_Button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	    			String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/graduate_customers.xls";
	    			AssetManager assetManager = getActivity().getAssets();
	    			ArrayList<String> organization_list = new ArrayList<String>();
	    		
	    			try {
	    				Log.v("Attempt Org File", "Creating and retrieving");
	    				InputStream OrganizationStream = assetManager.open("Grad Customers.xls");
	    				CreateFileFromInputStream(OrganizationStream, filename);
	    				organization_list = RetrieveOrganizationsFile(1);
	    				
	    			} catch (IOException e1) {
	    				Log.v("Failure", "Failed to open grad file");
	    				e1.printStackTrace();
	    			}
	            	
	            	AlertDialog mydialog = Oraganizations_Dialog(myorg, organization_list);
	            	mydialog.show();
	            }
	        });
			
			Auxilary_Button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	    			String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/auxilary_customers.xls";
	    			AssetManager assetManager = getActivity().getAssets();
	    			ArrayList<String> organization_list = new ArrayList<String>();
	    		
	    			try {
	    				Log.v("Attempt Non-Aux File", "Creating and retrieving");
	    				InputStream OrganizationStream = assetManager.open("Aux Customers.xls");
	    				CreateFileFromInputStream(OrganizationStream, filename);
	    				organization_list = RetrieveOrganizationsFile(0);
	    				
	    			} catch (IOException e1) {
	    				Log.v("Failure", "Failed to open aux file");
	    				e1.printStackTrace();
	    			}
	            	
	            	AlertDialog mydialog = Oraganizations_Dialog(myorg, organization_list);
	            	mydialog.show();
	            }
	        });
			
			NonAuxilary_Button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	    			String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/nonauxilary_customers.xls";
	    			AssetManager assetManager = getActivity().getAssets();
	    			ArrayList<String> organization_list = new ArrayList<String>();
	    		
		    			try {
		    				Log.v("Attempt Org File", "Creating and retrieving");
		    				InputStream OrganizationStream = assetManager.open("Non-Aux Customers.xls");
		    				CreateFileFromInputStream(OrganizationStream, filename);
		    				organization_list = RetrieveOrganizationsFile(2);
		    				
		    			} catch (IOException e1) {
		    				Log.v("Failure", "Failed to open non aux file");
		    				e1.printStackTrace();
		    			}
	            	
	            	AlertDialog mydialog = Oraganizations_Dialog(myorg, organization_list);
	            	mydialog.show();
	            }
	        });
			
			return rootView;
		}
		
		public AlertDialog Oraganizations_Dialog(final TextView selected_item, ArrayList<String> organization_list) {
			final String[] location_string = new String[organization_list.size()];
			for(int i = 0; i < organization_list.size(); i++) {
				location_string[i] = organization_list.get(i);
			}
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Organizations").setItems(location_string, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					selected_item.setText(location_string[which]);
		           }
		    	});
			
			AlertDialog dialog = builder.create();
			return dialog;
		}
		
		private ArrayList<String> RetrieveOrganizationsFile(int organization_type) throws IOException {
			File reservations_directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/");
			
			if (!reservations_directory.exists()) {
				Log.v("Directory does not exist", "NONEXISTENT DIRECTORY");
	        	return null;
	        }
			
			String filename = "";
			if(organization_type == 0)			//we have an auxilary button
				filename = reservations_directory.getPath() + "/auxilary_customers.xls";
			else if(organization_type == 1)		//we have the graduate students button
				filename = reservations_directory.getPath() + "/graduate_customers.xls";
			else if(organization_type == 2)		//we have non auxilary button
				filename = reservations_directory.getPath() + "/nonauxilary_customers.xls";
			else								//we have undergrad button
				filename = reservations_directory.getPath() + "/undergrad_customers.xls";
			
			ArrayList<String> organization_array = new ArrayList<String>();

	    	
	    	try {
	    		File organization_file = new File(filename);

	    		Workbook login_workbook = Workbook.getWorkbook(organization_file);
	    		Sheet login_sheet = login_workbook.getSheet(0);

	    		for(int col = 0; col < login_sheet.getColumns(); col++){
	    			int counter = 0;
	    			for(int row = 0; row < login_sheet.getRows(); row++){
	    				Cell cell = login_sheet.getCell(col, row);
	    				CellType type = cell.getType();
	    				if(type == CellType.LABEL && counter > 0 && col == 1){
	    					organization_array.add(cell.getContents());
	    				}
	    				counter++;
	    			}
	    		}
	    	} catch(BiffException e) {
	    		e.printStackTrace();
	    	}
	    	
	    	Log.v("Organization Array", organization_array.toString());
	    	return organization_array;
		}
		
		public void CreateFileFromInputStream(InputStream inStream, String path) throws IOException {
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(path));
		 
			int read = 0;
			byte[] bytes = new byte[1024];
		 
			while ((read = inStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		 
			inStream.close();
			out.flush();
			out.close();
						
		}
		
	}
	


}


