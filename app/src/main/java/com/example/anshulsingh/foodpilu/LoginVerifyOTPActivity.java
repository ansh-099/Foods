package com.example.anshulsingh.foodpilu;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class LoginVerifyOTPActivity extends AppCompatActivity {

    private EditText loginVerifyOTP;
    private Button loginVerifyOTPBtn;

    private FirebaseAuth mAuth;
    private FirebaseDatabase Database;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    // OTP Timer
    private CountDownTimer otpTimer = null;
    private TextView timerText;
    private TextView resendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verify_otp);

        loginVerifyOTP = (EditText) findViewById(R.id.login_enter_OTP);
        loginVerifyOTPBtn = (Button) findViewById(R.id.login_verify_Button);
        timerText = (TextView) findViewById(R.id.login_otpTimer);
        resendOTP = (TextView) findViewById(R.id.login_resend_otp);

        loginVerifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String verificationCode = loginVerifyOTP.getText().toString();

                Toast.makeText(LoginVerifyOTPActivity.this, "Login In Progress", Toast.LENGTH_SHORT).show();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                signInWithPhoneAuthCredential(credential);

            }
        });


        mAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance();

//        resendOTP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                attemptLoginOTP();
//            }
//        });

        // LOGIN WITH OTP

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w("foodpilu2", "exception: " + e);

                Toast.makeText(LoginVerifyOTPActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("foodpilu", "onCodeSent:" + verificationId);

                Toast.makeText(LoginVerifyOTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;


            }

        };

    }


    private void attemptLoginOTP(){

        final String number = getIntent().getStringExtra("userNumber");

//        startTimer();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                LoginVerifyOTPActivity.this,
                mCallbacks

        );

    }

    // SIGN IN WITH PHONE

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            // Sign in success, update UI with the signed in users information
                            Log.d("foodpilu", "SignInWithCredential is successful");


                            Toast.makeText(LoginVerifyOTPActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();



                            Intent myIntent = new Intent(LoginVerifyOTPActivity.this, Dashboard.class);

                            finish();
                            startActivity(myIntent);
                        }

                        else {

                            Toast.makeText(LoginVerifyOTPActivity.this, "Error Loggin In", Toast.LENGTH_SHORT).show();

                            Log.w("foodpilu", "SignInWithCredential Failed", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                // Verification code entered was invalid
                                Toast.makeText(LoginVerifyOTPActivity.this, "Invalid verification code", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();

        attemptLoginOTP();
        startTimer();


    }


    //start timer function
    private void startTimer() {
        otpTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {

//                String time = (String) millisUntilFinished/1000;

                timerText.setText(String.valueOf(millisUntilFinished/1000));
            }
            public void onFinish() {

                attemptLoginOTP();
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
