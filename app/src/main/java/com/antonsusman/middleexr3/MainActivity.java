package com.antonsusman.middleexr3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int INPUT_REQUEST = 1;

    private ListView contactlist;

    private ArrayList<String> names = new ArrayList<>();



    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        contactlist = (ListView) findViewById(R.id.contact_list);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        contactlist.setAdapter(adapter);


        Button add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intent, INPUT_REQUEST);


            }

        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FileInputStream fis = null;
        try {
            fis = openFileInput("persons.dat");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddContact.people = null;
        try {
            AddContact.people = (ArrayList<Person>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        int id=AddContact.people.size()-1;


        String nameview = AddContact.people.get(id).getFirstName();

        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        names.add(nameview);
        adapter.notifyDataSetChanged();


        contactlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                   Intent intent = new Intent(MainActivity.this, ViewContact.class);

                                                       intent.putExtra("id", i);

                                                       startActivity(intent);



            }
        });





    }
}







