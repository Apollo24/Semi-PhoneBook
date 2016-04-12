package com.antonsusman.middleexr3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Anton on 4/12/2016.
 */
public class ViewContact extends AppCompatActivity {

    Bitmap bitmap;
    ImageView imageView;

    final int CALL_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact);
        {

           TextView namectn = (TextView)findViewById(R.id.namectn);


           TextView phonectn = (TextView)findViewById(R.id.phonectn);

            phonectn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Build.VERSION.SDK_INT >= 23) {
                        int hasCallPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
                        if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST);
                        } else callPhone();
                    } else callPhone();

                }
            });


            final TextView usermail = (TextView) findViewById(R.id.emailout);
            usermail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{usermail.getText().toString()});

                    intent.setType("text/*");
                    // intent.setPackage("il.co.erankat.advanceimplicitexample");
                    startActivity(Intent.createChooser(intent, "Send e-mail with..."));

                }
            });




            TextView useraddress = (TextView) findViewById(R.id.addressout);
            useraddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView editText = (TextView)findViewById(R.id.siteout);
                    Intent intent  = new Intent(Intent.ACTION_VIEW);
                    String address = editText.getText().toString();
                    intent.setData(Uri.parse("geo:0,0?q="+"(address)"));
                    startActivity(intent);
                }
            });


            TextView usersite = (TextView) findViewById(R.id.siteout);
            usersite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView editText = (TextView)findViewById(R.id.siteout);
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://"+editText.getText().toString()));
                    startActivity(intent);
                }
            });



            TextView userbirth = (TextView) findViewById(R.id.birthout);
            TextView ucalltime = (TextView) findViewById(R.id.calltimeout);
            imageView = (ImageView)findViewById(R.id.image_view);

            Bundle extras =getIntent().getExtras();

            int id =extras.getInt("id");

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

            String nameview = AddContact.people.get(id).getFirstName();
            namectn.setText(nameview);

            String phoneview =AddContact.people.get(id).getPhoneNumber();
            phonectn.setText(phoneview);

            String mailview =AddContact.people.get(id).getEmail();
            usermail.setText(mailview);

            String addressview =AddContact.people.get(id).getAddress();
            useraddress.setText(addressview);

            String siteview =AddContact.people.get(id).getWebsite();
            usersite.setText(siteview);




            String birthview =AddContact.people.get(id).getBirthdate();
            userbirth.setText(birthview);

            String ucalltimeview =AddContact.people.get(id).getTimetocall();
            ucalltime.setText(ucalltimeview);


            imageView.setImageBitmap(AddContact.people.get(id).getPhoto());



            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode==CALL_PERMISSION_REQUEST) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            }
            else {
                Toast.makeText(ViewContact.this, "The app needed CALL_PHONE permission", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName())));
            }
        }
        else super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void callPhone() {
        TextView editText = (TextView) findViewById(R.id.phonectn);
        String number = editText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
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
