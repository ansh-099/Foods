package com.example.anshulsingh.foodpilu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EnterOTP extends AppCompatActivity {

    private EditText enterOTP;
    private Button verifyButton;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    // Firebase instance variable
    private FirebaseAuth mAuth;
    private FirebaseDatabase Database;

    // OTP Countdown
    private CountDownTimer otpTimer = null;
    private TextView timerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        enterOTP = findViewById(R.id.enterOTP);
        verifyButton = findViewById(R.id.verifyButton);
        timerText = findViewById(R.id.register_otpTimer);


        mAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String verificationCode = enterOTP.getText().toString();

                Toast.makeText(EnterOTP.this, "Verification In Progress", Toast.LENGTH_SHORT).show();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                signInWithPhoneAuthCredential(credential);

            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w("foodpilu2", "exception: " + e);

                Toast.makeText(EnterOTP.this, "Verification Failed", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("foodpilu", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;



            }


        };


    }

    private void attemptlogin(String number) {

        Toast.makeText(EnterOTP.this, "Registration In Progress", Toast.LENGTH_SHORT).show();

        Log.d("foodpilu", "Creating User");

//        startTimer();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                EnterOTP.this,
                mCallbacks

        );



    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed in users information
                            Log.d("foodpilu", "SignInWithCredential is successful");

//                            linkAccount();

                            User addinguser = new User();
                            addinguser.phno = getIntent().getStringExtra("phoneNo");
                            addinguser.name = getIntent().getStringExtra("name");
                            addinguser.email = getIntent().getStringExtra("email");
                            addinguser.password = getIntent().getStringExtra("password");

                            FirebaseDatabase.getInstance().getReference().child("Users").child(addinguser.email.replace(".",":")).setValue(addinguser);

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("food", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("email",addinguser.email);
                            editor.apply();

                            Intent myIntent = new Intent(EnterOTP.this, Dashboard.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(myIntent);
                        }

                        else {

                            Toast.makeText(EnterOTP.this, "Error Loggin In", Toast.LENGTH_SHORT).show();

                            Log.w("foodpilu2", "SignInWithCredential Failed", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            // Verification code entered was invalid
                                Toast.makeText(EnterOTP.this, "Invalid verification code", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        String userNumber = getIntent().getStringExtra("phoneNo");

        attemptlogin(userNumber);
        startTimer();
    }


    // Link accounts
    private void linkAccount() {

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            saveDisplayName();

                            Log.d("foodpilu", "linkWithCredential:success");

                            Toast.makeText(EnterOTP.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            //Toast.makeText(EnterOTP.this, "Account Link Successful", Toast.LENGTH_SHORT).show();

                            User addinguser = new User();
                            addinguser.phno = getIntent().getStringExtra("phoneNo");
                            addinguser.name = getIntent().getStringExtra("name");
                            addinguser.email = getIntent().getStringExtra("email");
                            addinguser.password = getIntent().getStringExtra("password");

                            FirebaseDatabase.getInstance().getReference().child("Users").child(addinguser.phno).setValue(addinguser);

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("food", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("email",addinguser.email);
                            editor.putString("time","1");
                            editor.apply();

                            Intent myIntent = new Intent(EnterOTP.this, Dashboard.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(myIntent);

                        } else {
                            Log.w("foodpilu", "linkWithCredential:failure", task.getException());
                            Toast.makeText(EnterOTP.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    private void saveDisplayName() {

        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = getIntent().getStringExtra("name");

        if (user !=null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("FlashChat", "User name updated.");
                            }
                        }
                    });

        }

    }

    // OTP Timer

    //start timer function
    private void startTimer() {
        otpTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {

//                String time = (String) millisUntilFinished/1000;

                timerText.setText(String.valueOf(millisUntilFinished/1000));
            }
            public void onFinish() {

                String userNumber = getIntent().getStringExtra("phoneNo");
                attemptlogin(userNumber);
            }
        };
        otpTimer.start();
    }


    //cancel timer
    private void cancelTimer() {
        if(otpTimer!=null)
            otpTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelTimer();
    }

}
