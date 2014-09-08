package codepath.demos.helloworlddemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.CheckBox;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EditActivity extends Activity {
	public EditText	txtfldEdit;
	public CheckBox chkCompleted;
	public TextView completedDate;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editactivity);
		txtfldEdit = (EditText) findViewById(R.id.txtfldEdit);
		chkCompleted = (CheckBox) findViewById(R.id.chkCompleted);
		completedDate = (TextView) findViewById(R.id.completeddatetxtView);
		Bundle bundle = getIntent().getExtras();
		position = bundle.getInt("position");
		String taskName = bundle.getString("taskName");
		String status = bundle.getString("status");
		int donedate = bundle.getInt("donedate");
		if (donedate != 0)
		{
		Date date = new Date(donedate*1000L); // *1000 seconds conversion
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // Date Format
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-7"));
		String formattedDate = sdf.format(date);
		completedDate.setText(formattedDate);
		}
		txtfldEdit.setText(taskName);
		if (status.equals("Completed")) {
			chkCompleted.setChecked(true);
		}
			else {
				chkCompleted.setChecked(false);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}
	
	public void editTask(View v) {
		Intent data = new Intent();
		// Get the Task Name
		EditText task_name = (EditText) findViewById(R.id.txtfldEdit);
		String taskName = task_name.getText().toString();
		String status;
		if (chkCompleted.isChecked()) {
			status = "Completed";
		}
		else {
			status = "Incomplete";
		}
		// Set the Data to Pass Back
		Bundle extras = new Bundle();
        extras.putString("taskName", taskName);
        extras.putInt("position", position);
        extras.putString("status", status);
        data.putExtras(extras);
		setResult(RESULT_OK, data);
		//Close the Activity, it's job is done.
		finish();	
		
	}
}
