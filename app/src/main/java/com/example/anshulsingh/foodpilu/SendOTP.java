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

public class SendOTP extends AppCompatActivity {

    // TODO: Declare member variable here:

    private EditText verifyPhone;
    private Button sendOTP;
    String countryCode = "+91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        verifyPhone = findViewById(R.id.phoneverify);
        sendOTP = findViewById(R.id.otpSendBtn);


        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name,email,password,phoneNo,enteredNo;

                enteredNo = verifyPhone.getText().toString();

                name = getIntent().getStringExtra("userName");
                email = getIntent().getStringExtra("email");
                password = getIntent().getStringExtra("password");
                phoneNo = countryCode+enteredNo;

                // Check if user exist


                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(email.replace(".",":")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getChildrenCount() > 0){

                            Toast.makeText(SendOTP.this, "User Already exit", Toast.LENGTH_SHORT).show();
                        }

                        else {

                            Intent SendOTPtoEnterOTP = new Intent(SendOTP.this, EnterOTP.class);

                            SendOTPtoEnterOTP.putExtra("name", name);
                            SendOTPtoEnterOTP.putExtra("email", email);
                            SendOTPtoEnterOTP.putExtra("password", password);
                            SendOTPtoEnterOTP.putExtra("phoneNo", phoneNo);

                            startActivity(SendOTPtoEnterOTP);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
