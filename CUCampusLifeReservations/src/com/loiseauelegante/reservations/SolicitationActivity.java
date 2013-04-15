package com.loiseauelegante.reservations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class SolicitationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitation);

		String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CampusLife_Reservations/solicitation_form.pdf";
		AssetManager assetManager = getAssets();

		try {
			InputStream pdfFileStream = assetManager.open("solicitation_form.pdf");
			CreateFileFromInputStream(pdfFileStream, filename);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		File pdfFile = new File(filename); 

		if(pdfFile.exists()) 
        {
            Uri path = Uri.fromFile(pdfFile); 
            
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(pdfIntent);
                finish();
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(SolicitationActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
                finish();
            }
        }
		
		// Show the Up button in the action bar.
		setupActionBar();
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

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solicitation, menu);
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
