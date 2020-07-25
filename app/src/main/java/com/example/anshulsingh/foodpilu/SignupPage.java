package com.example.anshulsingh.foodpilu;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPage extends AppCompatActivity {

    EditText RegUserName,RegEmail,RegPasword,RegRePassword;
    Button RegSignUp;
    TextView RegContactUs;
    String name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        RegUserName = findViewById(R.id.RegUserName);
        RegEmail = findViewById(R.id.RegEmail);
        RegPasword = findViewById(R.id.RegPassword);
        RegRePassword = findViewById(R.id.RegRePassword);
//        RegPhoneNo = findViewById(R.id.RegPhoneNo);
        RegSignUp = findViewById(R.id.RegSignUp);
        RegContactUs = findViewById(R.id.RegContactUs);


       if(FirebaseAuth.getInstance().getCurrentUser() != null){
           RegUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
           RegEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
       }

        RegSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name,email,password,confirmPassword,phoneNo,EnteredNo;

                name= RegUserName.getText().toString();
                email= RegEmail.getText().toString();
                password = RegPasword.getText().toString();
                confirmPassword = RegRePassword.getText().toString();

//                EnteredNo = RegPhoneNo.getText().toString();
//                phoneNo = countryCode+EnteredNo;
//                Log.d("foodpilu", "number "+ phoneNo);

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(name)){
                    RegUserName.setError(getString(R.string.error_field_required));
                    focusView = RegUserName;
                    cancel = true;
                }

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                    RegPasword.setError(getString(R.string.error_invalid_password));
                    focusView = RegPasword;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(email)) {
                    RegEmail.setError(getString(R.string.error_field_required));
                    focusView = RegEmail;
                    cancel = true;
                } else if (!isEmailValid(email)) {
                    RegEmail.setError(getString(R.string.error_invalid_email));
                    focusView = RegEmail;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    // TODO: Call create FirebaseUser() here

                    Intent signUpToSendOTP = new Intent(SignupPage.this, SendOTP.class);
                    signUpToSendOTP.putExtra("userName", name);
                    signUpToSendOTP.putExtra("email", email);
                    signUpToSendOTP.putExtra("password", password);
//                    signUpToSendOTP.putExtra("phoneNo", phoneNo);

                    startActivity(signUpToSendOTP);
                }


            }
        });

    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)

        String confirmPassword = RegRePassword.getText().toString();


        return confirmPassword.equals(password) && password.length() > 5;
    }

}
