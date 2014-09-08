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
import android.util.Log;
import android.database.Cursor;


public class HelloWorldActivity extends ListActivity {
	public TextView	txtTemp;
	int request_Code_Add = 1;
	int request_Code_Edit = 2;
	ArrayList<Task> Tasks = new ArrayList<Task>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_hello_world);
		
		DBAdapter db = new DBAdapter(this);
		db.open();
        Cursor c = db.getAllTasks();
        if (c.moveToFirst())
        {
            do {
                CreateTasks(c);
            } while (c.moveToNext());
        }      
        db.close();
		
		createListView();
	}

	public void CreateTasks(Cursor c)
    {        
        Task item = new Task();
        item.setId(c.getInt(0));
        item.setName(c.getString(1));
        item.setStatus(c.getString(2));
        item.setDonedate(c.getInt(3));
        Tasks.add(item);
    }
	
	public void createListView() {
		
		//System.out.println("Testing createListView");
		//Log.v("Tag1","Testing Log");		
		ArrayList<String> TaskList = new ArrayList<String>();
		for(int i=0; i<Tasks.size(); i++) {
			Task item = Tasks.get(i);
			String name = item.getName();
			String status = item.getStatus();
			if (status.equals("Completed")) {
				name = "[DONE] " + name;
			}
			TaskList.add(name);
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
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Task item = Tasks.get(position);
		String taskName = item.getName();
		String status = item.getStatus();
		int donedate = item.getDonedate();
		editActivityatPosition(position, taskName, status, donedate);
	}

	public void editActivityatPosition(int position, String taskName, String status, int donedate) {

		Bundle extras = new Bundle();
        extras.putString("taskName", taskName);
        extras.putInt("position", position);
        extras.putString("status", status);
        extras.putInt("donedate", donedate);
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
				String name = data.getData().toString();
				String status = "Incomplete";
				int donedate = 0;
				DBAdapter db = new DBAdapter(this);
				db.open();
				int id = (int) db.insertTask(name, status, donedate);
				db.close();
				Task item = new Task();
				item.setId(id);
				item.setName(name);
				item.setStatus(status);
				item.setDonedate(donedate);
				Tasks.add(item);
				createListView();
		}
		}
		
		if(requestCode == request_Code_Edit) {
			if(resultCode == RESULT_OK) {
				Bundle b = data.getExtras();
				int position = b.getInt("position");
				String taskName = b.getString("taskName");
				String status = b.getString("status");
				// Calculate DoneDate in sec from UnixTime 0
				int donedate=0;
				if (status.equals("Completed")) {
					donedate = (int) (System.currentTimeMillis() / 1000L);
				}
				DBAdapter db = new DBAdapter(this);
				db.open();
				db.updateTask(position, taskName, status, donedate);
				db.close();
				
				Task item = new Task();
				item.setId(position);
				item.setName(taskName);
				item.setStatus(status);
				item.setDonedate(donedate);
				Tasks.set(position, item);				
				
				createListView();
			}
		}
			
	}
	
}
