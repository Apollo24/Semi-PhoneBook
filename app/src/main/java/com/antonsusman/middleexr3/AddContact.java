package com.antonsusman.middleexr3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Anton on 4/11/2016.
 */
public class AddContact extends AppCompatActivity {


    Bitmap bitmap;
    ImageView imageView;



    final int CAMERA_REQUEST = 1;


    static ArrayList<Person> people = new ArrayList<Person>();


    final String FILE_NAME = "persons.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        {

           final EditText name = (EditText)findViewById(R.id.name);
           final EditText phone = (EditText)findViewById(R.id.phone);

            final EditText email = (EditText) findViewById(R.id.email);
            String useremail = email.getText().toString();

            final EditText address = (EditText) findViewById(R.id.address);
            String useraddress = address.getText().toString();

            final EditText website = (EditText) findViewById(R.id.website);
            String usersite = website.getText().toString();


            final TextView birthdate = (TextView) findViewById(R.id.date_chosen);
            final String[] userbirth = {birthdate.getText().toString()};

            final TextView calltime = (TextView) findViewById(R.id.time_fromchosen);
            final String[] ucalltime = {calltime.getText().toString()};



            Button cameraBtn = (Button)findViewById(R.id.picture_btn);
            cameraBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            });




            Button dateBtn = (Button) findViewById(R.id.date_btn);
            dateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar systemCalendar = Calendar.getInstance();
                    int year = systemCalendar.get(Calendar.YEAR);
                    int month = systemCalendar.get(Calendar.MONTH);
                    int day = systemCalendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dpd = new DatePickerDialog(AddContact.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            TextView textView = (TextView) findViewById(R.id.date_chosen);
                            textView.setText(i2 + "/" + (i1 + 1) + "/" + i);


                            userbirth[0] =textView.getText().toString();


                        }
                    }, year, month, day);
                    dpd.show();
                }



            });

            Button timefrom = (Button) findViewById(R.id.timefrom);
            timefrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar systemCalender = Calendar.getInstance();
                    int hour = systemCalender.get(Calendar.HOUR_OF_DAY);
                    int minute = systemCalender.get(Calendar.MINUTE);
                    TimePickerDialog tpd1 = new TimePickerDialog(AddContact.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {

                            TextView textView1 = (TextView) findViewById(R.id.time_fromchosen);
                            textView1.setText(i + " : " + i1);

                            ucalltime[0] = textView1.getText().toString();
                        }
                    }, hour, minute, true);
                    tpd1.show();
                }
            });








            Button save_btn = (Button)findViewById(R.id.save_btn);
            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    try {
                        FileOutputStream fos = openFileOutput(FILE_NAME,MODE_PRIVATE);

                        people.add(new Person(name.getText().toString(),phone.getText().toString(),email.getText().toString(),address.getText().toString(),website.getText().toString(), userbirth[0], ucalltime[0], bitmap));


                        int size=people.size();

                        Toast.makeText(AddContact.this, "added"+size, Toast.LENGTH_SHORT).show();


                       Intent back = new Intent();


                        setResult(RESULT_OK, back);
                        finish();

                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(people);
                        oos.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }

                }

            });


        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.picture_taken);
             bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();

        if(id == R.id.action_back) {
            finish();
            return true;
        }

        if(id ==R.id.about)
        {
            Toast.makeText(getApplicationContext(), "Anton Susman. Android Course Tau 2016", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }



}
