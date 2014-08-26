package codepath.demos.helloworlddemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.net.Uri;
import android.content.Intent;

public class AddActivity extends Activity {
	public EditText	txtfldAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addactivity);
		txtfldAdd = (EditText) findViewById(R.id.txtfldAdd);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}
	
	public void addTask(View v) {
		Intent data = new Intent();
		// Get the Task Name
		EditText task_name = (EditText) findViewById(R.id.txtfldAdd);
		// Set the Data to Pass Back
		data.setData(Uri.parse(task_name.getText().toString()));
		setResult(RESULT_OK, data);
		//Close the Activity, it's job is done.
		finish();	
		
	}
}
