package com.antonsusman.middleexr3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Anton on 4/11/2016.
 */
public class Person implements Serializable {


    private String firstName;
    private String PhoneNumber;
    private String Email;
    private String Address;
    private String Website;
    private String Birthdate;
    private String Timetocall;
    transient private Bitmap photo;

    public Person(String firstName, String PhoneNumber, String Email, String Address, String Website, String Birthdate, String Timetocall, Bitmap photo) {

        this.firstName = firstName;
        this.PhoneNumber = PhoneNumber;
        this.Email= Email;
        this.Address= Address;
        this.Website = Website;
        this.Birthdate = Birthdate;
        this.Timetocall = Timetocall;
        this.photo = photo;


    }



    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return Email ;
    }

    public String getAddress() {
        return Address;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public String getTimetocall() {
        return Timetocall;
    }

    public String getWebsite() {

        return Website;
    }


    public Bitmap getPhoto() {
        return photo;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        photo.compress(Bitmap.CompressFormat.JPEG,50,oos);
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException {
        photo = BitmapFactory.decodeStream(ois);
        ois.defaultReadObject();
    }

}