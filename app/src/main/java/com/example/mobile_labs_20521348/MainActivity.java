package com.example.mobile_labs_20521348;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    EditText edtSalary;
    Button btnSubmit;
    ListView listEmp;

    ArrayList<Employee> arrEmp = new ArrayList<Employee>();
    ArrayAdapter<Employee> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.editName);
        edtSalary = (EditText) findViewById(R.id.editSalary);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        listEmp = (ListView) findViewById(R.id.listEmp);
        adapter = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_1, arrEmp);
        listEmp.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String strSalary = edtSalary.getText().toString();
                double salary = Double.parseDouble(strSalary);
                Employee temp = new Employee(name, salary);
                double net = temp.CalNet();
                temp.setNet(net);
                arrEmp.add(temp);
                adapter.notifyDataSetChanged();
            }
        });
    }
}