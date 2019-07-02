package com.sag.tessera.tessera;

import android.provider.BaseColumns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    //User Private Members
    private String userEmail;
    private String userPassword;
    private String userGmaingName;

    public User(){

    }

    public User(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    //Regex Validations
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    //Validations
    public boolean validRegisterInputs() {
        Matcher match = VALID_EMAIL_ADDRESS_REGEX.matcher(this.userEmail);
        boolean validEmail = match.find();

        if(validEmail == false || !userEmail.matches("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")){
            return false;
        }else
            return true;

    }
}
