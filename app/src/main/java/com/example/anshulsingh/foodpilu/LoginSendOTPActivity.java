package com.example.anshulsingh.foodpilu;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginSendOTPActivity extends AppCompatActivity {

    private EditText loginSendOTP;
    private Button loginSendOTPBtn;
    private String countryCode = "+91";

    private FirebaseAuth mAuth;
    private FirebaseDatabase Database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_send_otp);

        loginSendOTP = (EditText) findViewById(R.id.login_phone_verify);
        loginSendOTPBtn = (Button) findViewById(R.id.login_otp_SendBtn);

        loginSendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredNo = loginSendOTP.getText().toString();

                final String userNumber = countryCode+enteredNo;

                // Check if user exist

                Query userExist = FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .orderByChild("userNumber")
                        .equalTo(userNumber);

                userExist.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getChildrenCount() > 0){

                            Intent myIntent = new Intent(LoginSendOTPActivity.this, LoginVerifyOTPActivity.class);
                            myIntent.putExtra("userNumber", userNumber);
                            startActivity(myIntent);

                        }

                        else {

                            Toast.makeText(LoginSendOTPActivity.this, "User Does not exit", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });


        mAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance();



    }
}
