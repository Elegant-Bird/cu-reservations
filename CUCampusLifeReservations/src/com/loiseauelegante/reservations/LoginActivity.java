package com.loiseauelegante.reservations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class LoginActivity extends Activity {
	String[] login_info_array = new String[]
			{
			"First Name", 
			 "Middle Initial",
			 "Last Name",
			 "Email Address",
			 "Contact Number 1",
			 "Contact Number 2",
			 "Address Line 1",
			 "Address Line 2",
			 "City",
			 "State",
			 "Zip Code"
			};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//prevent the keyboard from auto-opening
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		
		//set the content view to this layout
		setContentView(R.layout.activity_login);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		//store all of the text views on this layout into an array
    	final TextView [] login_text_array = new TextView[11];
    	login_text_array[0] =  (TextView) findViewById(R.id.firstname_field);
    	login_text_array[1] = (TextView) findViewById(R.id.middleinitial_field);
    	login_text_array[2] = (TextView) findViewById(R.id.lastname_field);
    	login_text_array[3] = (TextView) findViewById(R.id.email_field);
    	login_text_array[4] = (TextView) findViewById(R.id.contact1_field);
    	login_text_array[5] = (TextView) findViewById(R.id.contact2_field);
    	login_text_array[6] = (TextView) findViewById(R.id.address1_field);
    	login_text_array[7] = (TextView) findViewById(R.id.address2_field);
    	login_text_array[8] = (TextView) findViewById(R.id.city_field);
    	login_text_array[9] = (TextView) findViewById(R.id.state_field);
    	login_text_array[10] = (TextView) findViewById(R.id.zipcode_field); 	
		
		try {
			RetrieveLoginFile(login_text_array);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		final Button save_login_button = (Button) findViewById(R.id.save_login_button);
		save_login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            	if(CheckValidEntry(login_text_array)) {
	                //store the information entered into the text boxes into a string array
	            	try {
						CreateLoginFile(login_text_array);
					} catch (WriteException e) 	{	e.printStackTrace();	}
	            	catch (IOException e) 		{	e.printStackTrace();	}
	            		            	
	            	finish();
	            }
            	else {
            		//display a toast letting the user know which fields are missing
            		String necessary_fields = "Fields cannot be left blank! \n" + 
            		"First Name, Last Name, Email, Phone Number, \n" +
            		"Address 1, City, State, and Zip";
            		
            		Context context = getApplicationContext();
            		CharSequence text = necessary_fields;
            		int duration = Toast.LENGTH_LONG;

            		Toast toast = Toast.makeText(context, text, duration);
            		toast.show();
            	}
            }
        });
		
		final Button cancel_login_button = (Button) findViewById(R.id.cancel_login_button);
		cancel_login_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private boolean CheckValidEntry(TextView[] login_fields) {
    	//if index 0, 2, 3, 4, 6, 8, 9, 10 are not left blank, save it
    	if(login_fields[0].getText().toString().trim().compareTo("") != 0 && 
			login_fields[2].getText().toString().trim().compareTo("") != 0 && 
			login_fields[3].getText().toString().trim().compareTo("") != 0 && 
			login_fields[4].getText().toString().trim().compareTo("") != 0 && 
			login_fields[6].getText().toString().trim().compareTo("") != 0 && 
			login_fields[8].getText().toString().trim().compareTo("") != 0 && 
			login_fields[9].getText().toString().trim().compareTo("") != 0 && 
			login_fields[10].getText().toString().trim().compareTo("") != 0) {
    		return true;
    	}
    		
		return false;
	}
	
	private void CreateLoginFile(TextView[] login_fields) throws IOException, WriteException {
		//Create the login file
		
		File reservations_directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/");
		if (!reservations_directory.exists()) {
        	reservations_directory.mkdir();
        }
		
		String filename = reservations_directory.getPath() + "/login.xls";
		Log.v("File Path: ", filename);
		File login_file = new File(filename);
		if(!login_file.exists()) {
			login_file.getParentFile().mkdir();
			boolean created = 		login_file.createNewFile();
			Log.v("File Created?", String.valueOf(created));
		}
		
		WorkbookSettings workbook_settings = new WorkbookSettings();
		workbook_settings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook = Workbook.createWorkbook(login_file, workbook_settings);
		
		WritableSheet login_sheet = workbook.createSheet("LoginSheet", 0);
		
		//Format the font
		WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		format.setWrap(true);
		int row = 0;
		int column = 0;
		
		//for each item in login_fields, write a new label and data
		for(int i = 0; i < 11; i++) {
			Label cell_label = new Label(row, 0, login_info_array[i], format);
			login_sheet.addCell(cell_label);
			Label cell_data = new Label(column, 1, login_fields[i].getText().toString(), format);
			login_sheet.addCell(cell_data);
			row++;
			column++;
		}
		
		workbook.write();
		workbook.close();
		
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
		getMenuInflater().inflate(R.menu.login, menu);
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
	
	
	private void RetrieveLoginFile(TextView[] login_text_array) throws IOException {
		File reservations_directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/");
		
		if (!reservations_directory.exists()) {
			Log.v("Directory does not exist", "NONEXISTENT DIRECTORY");
        	return;
        }
		
		String filename = reservations_directory.getPath() + "/login.xls";
    	
    	try {
    		File login_file = new File(filename);
    		Workbook login_workbook = Workbook.getWorkbook(login_file);
    		Sheet login_sheet = login_workbook.getSheet(0);
			int text_counter = 0;

    		for(int col = 0; col < login_sheet.getColumns(); col++){
    			int counter = 0;
    			for(int row = 0; row < login_sheet.getRows(); row++){
    				Cell cell = login_sheet.getCell(col, row);
    				CellType type = cell.getType();
    				if(type == CellType.LABEL && counter == 1){
    					login_text_array[text_counter].setText(cell.getContents());
    					text_counter++;
    				}
    				counter++;
    			}
    		}
    	} catch(BiffException e) {
    		e.printStackTrace();
    	}
	}

}
