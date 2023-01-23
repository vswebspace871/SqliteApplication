package com.example.sqliteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name, number;
    Button add, view;
    TextView colid, colname, colnumber;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Model> listContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etName);
        number = findViewById(R.id.etNumber);
        add = findViewById(R.id.buttonAdd);
        view = findViewById(R.id.buttonView);
        recyclerView = findViewById(R.id.recyclerView);

        colid = findViewById(R.id.colid);
        colname = findViewById(R.id.colname);
        colnumber = findViewById(R.id.colnumber);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        MyDBHelper myDBHelper = new MyDBHelper(this);

        add.setOnClickListener(view -> {
            //adding contact detail in database on button click

            String pname = name.getText().toString();
            String phno = number.getText().toString();
            colid.setVisibility(View.VISIBLE);
            colname.setVisibility(View.VISIBLE);
            colnumber.setVisibility(View.VISIBLE);
            myDBHelper.insertContact(pname, phno);
            //empty the editBoxes
            name.setText("");
            number.setText("");
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            listContacts = myDBHelper.viewContact();
            if (listContacts != null && listContacts.size() > 0) {
                adapter = new Adapter(listContacts, MainActivity.this);
                recyclerView.setAdapter(adapter);
                //adapter.setList(listContacts);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(listContacts.size() - 1);
            }

        });


        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listContacts = myDBHelper.viewContact();
                if (listContacts != null && listContacts.size() > 0) {
                    adapter = new Adapter(listContacts, MainActivity.this);
                    recyclerView.smoothScrollToPosition(listContacts.size() - 1);
                    recyclerView.setAdapter(adapter);
                }


            }
        });*/

        // UPDATE WORKING FINE
        /*Model model = new Model();
        model.id = 10;
        model.name = "Ajay";
        model.phone_number = "111";
        myDBHelper.updateContact(model);*/


        //myDBHelper.deleteContact(15);

    }
}