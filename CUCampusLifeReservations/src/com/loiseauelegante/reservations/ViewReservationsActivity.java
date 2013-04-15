package com.loiseauelegante.reservations;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ViewReservationsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reservations);
		// Show the Up button in the action bar.
		Log.v("Activity", "Started View");
		
		//get the textview to store the data in
		TextView summary = (TextView) findViewById(R.id.reservation_summary_review_textview);
		try {
			RetrieveReservationsFile(summary);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setupActionBar();
	}
	
	private void RetrieveReservationsFile(TextView summary) throws IOException {
		File reservations_directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/");
		
		if (!reservations_directory.exists()) {
			Log.v("Directory does not exist", "NONEXISTENT DIRECTORY");
        	return;
        }
		
		String filename = reservations_directory.getPath() + "/ExistingReservations.xls";
		StringBuilder reservation_builder = new StringBuilder();
		int loop = 0;
		String [] Titles = new String [13];
    	
    	try {
    		File login_file = new File(filename);
    		Workbook login_workbook = Workbook.getWorkbook(login_file);
    		Sheet login_sheet = login_workbook.getSheet(0);
    		
			for(int row = 0; row < login_sheet.getRows(); row++){
				loop = row;
	    		for(int col = 0; col < login_sheet.getColumns(); col++){
    				Cell cell = login_sheet.getCell(col, row);
    				CellType type = cell.getType();
    				
    				//store all of the labels for this
    				if(type == CellType.LABEL && loop ==0) {
    					Titles[col] = cell.getContents();
    				}
    				else {
    					reservation_builder.append(Titles[col] + "\n");
    					String cell_contents = cell.getContents();
    					if(cell_contents.trim().compareTo("") == 0)
    						reservation_builder.append("None" + "\n\n");
    					else
    						reservation_builder.append(cell_contents + "\n\n");
    				}
    			}
				reservation_builder.append("\n\n ----- Reservation ----- \n\n");

    		}
    	} catch(BiffException e) {
    		e.printStackTrace();
    	}
    	Log.v("Contents", reservation_builder.toString());
    	summary.setText(reservation_builder.toString());
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
		getMenuInflater().inflate(R.menu.view_reservations, menu);
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
