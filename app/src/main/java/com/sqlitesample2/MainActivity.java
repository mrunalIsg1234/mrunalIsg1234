package com.sqlitesample2;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<EmployeeModel> employeeModelList =new ArrayList<>();

    RecyclerView empListRecyclerView;

    EmployeeListAdapter employeeListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empListRecyclerView = (RecyclerView) findViewById(R.id.empListRecyclerView);

        employeeModelList = getAllEmployee();

        if (!employeeModelList.isEmpty()) {
            fillRecyclerView(employeeModelList);

        } else {
            Toast.makeText(MainActivity.this, "Empty list", Toast.LENGTH_LONG).show();

        }
    }

    public List<EmployeeModel> getAllEmployee() {
        List<EmployeeModel> empList = new ArrayList<>();


        try {

            Uri URI = Uri.parse("content://com.sqlitesample1.provider/employee");

            ContentProviderClient myCR = getContentResolver().acquireContentProviderClient(URI);

            Cursor cursor = myCR.query(URI, null, null, null, null);

            if (cursor == null || cursor.isAfterLast()) {
                return empList;
            }

            if (cursor.moveToFirst()) {
                do {

                    empList.add(new EmployeeModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            myCR.release();



        } catch (Exception e) {
e.printStackTrace();
        }

        return empList;

    }

    public void fillRecyclerView(List<EmployeeModel> employeeModelList){

        employeeListAdapter = new EmployeeListAdapter(employeeModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        empListRecyclerView.setLayoutManager(mLayoutManager);
        empListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        empListRecyclerView.setAdapter(employeeListAdapter);
        employeeListAdapter.notifyDataSetChanged();
        empListRecyclerView.setNestedScrollingEnabled(true);
    }

}
