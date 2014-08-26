package codepath.demos.helloworlddemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.CheckBox;

public class EditActivity extends Activity {
	public EditText	txtfldEdit;
	public CheckBox chkCompleted;
	int position;
	Boolean status = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editactivity);
		txtfldEdit = (EditText) findViewById(R.id.txtfldEdit);
		chkCompleted = (CheckBox) findViewById(R.id.chkCompleted);
		Bundle bundle = getIntent().getExtras();
		position = bundle.getInt("position");
		String taskName = bundle.getString("taskName");
		status = bundle.getBoolean("status");
		txtfldEdit.setText(taskName);
		if (status) {
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
		if (chkCompleted.isChecked()) {
			status = true;
		}
		else {
			status = false;
		}
		// Set the Data to Pass Back
		Bundle extras = new Bundle();
        extras.putString("taskName", taskName);
        extras.putInt("position", position);
        extras.putBoolean("status", status);
        data.putExtras(extras);
		//data.setData(Uri.parse(task_name.getText().toString()));
		setResult(RESULT_OK, data);
		//Close the Activity, it's job is done.
		finish();	
		
	}
}
