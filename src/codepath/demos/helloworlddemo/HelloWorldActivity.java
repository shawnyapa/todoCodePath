package codepath.demos.helloworlddemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.widget.TextView;
import android.content.Intent;
import java.util.ArrayList;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.MenuItem;
import java.io.*;
import org.apache.commons.io.FileUtils;

public class HelloWorldActivity extends ListActivity {
	public TextView	txtTemp;
	int request_Code_Add = 1;
	int request_Code_Edit = 2;
	ArrayList<String> Tasks = new ArrayList<String>();
	ArrayList<Boolean> Statuses = new ArrayList<Boolean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_hello_world);
		Tasks.add("Task 123");
		Statuses.add(false);
		/*
		Tasks.add("Task 456");
		Statuses.add(true);
		Tasks.add("Task 789");	
		Statuses.add(false);
		*/
		//readTasks();
		createListView();
	}
	
	public void createListView() {
		ArrayList<String> TaskList = new ArrayList<String>();
		for(int i=0; i<Tasks.size(); i++) {
			String task = Tasks.get(i);
			Boolean status = Statuses.get(i);
			if (status) {
				task = "[DONE] " + task;
			}
			TaskList.add(task);
		}
		String[] Taskarray = TaskList.toArray(new String[Tasks.size()]);
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Taskarray));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		//getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		inflater.inflate(R.menu.activity_hello_world, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.addTask:
				addActivityMenu();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
	
	public void onListItemClick(ListView parent, View v, int position, long id)
		    {
				String taskName = Tasks.get(position);
				Boolean status = Statuses.get(position);
				editActivityatPosition(position, taskName, status);
		    }

	public void editActivityatPosition(int position, String taskName, Boolean status) {

		Bundle extras = new Bundle();
        extras.putString("taskName", taskName);
        extras.putInt("position", position);
        extras.putBoolean("status", status);
        //---Create Bundle Object and attach to the Intent object---
		Intent i = new Intent(this, EditActivity.class);
        i.putExtras(extras);
        startActivityForResult(i, request_Code_Edit);	
	}
	
	public void addActivityMenu() {
		startActivityForResult(new Intent(this, AddActivity.class), request_Code_Add);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == request_Code_Add) {
			if(resultCode == RESULT_OK) {
				Tasks.add(data.getData().toString());
				Statuses.add(false);
				createListView();
		}
		}
		
		if(requestCode == request_Code_Edit) {
			if(resultCode == RESULT_OK) {
				Bundle b = data.getExtras();
				int position = b.getInt("position");
				String taskName = b.getString("taskName");
				Boolean status = b.getBoolean("status");
				Tasks.set(position, taskName);
				Statuses.set(position, status);
				createListView();
				//saveTasks();
			}
		}
			
	}
	
	public void readTasks() {
		File filesDir = getFilesDir();
		File taskFile = new File(filesDir, "tasks.txt");
		try {
			Tasks = new ArrayList<String>(FileUtils.readLines(taskFile));
		} catch (IOException e) {
			Tasks = new ArrayList<String>();
			e.printStackTrace();
		}
	}
		
	public void saveTasks() {
		File filesDir = getFilesDir();
		File taskFile = new File(filesDir, "tasks.txt");
		try {
			FileUtils.writeLines(taskFile, Tasks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
