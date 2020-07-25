package com.example.anshulsingh.foodpilu;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // TODO: Declare member variables here

    private TextView signUpText;
    private EditText emailView;
    private EditText passwordView;
    private Button singInButton;

    private FirebaseAuth mAuth;
    private FirebaseDatabase Database;
    FirebaseUser firebaseUser;
    public static final int RC_SIGN_IN= 101;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    SignInButton google_login_btn;
    // Forget password
    private TextView forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signUpText = (TextView) findViewById(R.id.signUpText);
        emailView = (EditText) findViewById(R.id.loginPhoneNumber);
        passwordView = (EditText) findViewById(R.id.loginPassword);
        singInButton = (Button) findViewById(R.id.signInButton);
        //googleLoginButton = findViewById(R.id.google_login_btn);
        forgetPassword = (TextView) findViewById(R.id.forgot_password_text);
        google_login_btn = findViewById(R.id.google_login_btn);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resetIntent = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(resetIntent);
            }
        });



        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();

            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainToSignUp = new Intent(MainActivity.this, SignupPage.class);
                startActivity(mainToSignUp);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance();


        google_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                                .build(),
                        RC_SIGN_IN);
            }
        });



//         LOGIN WITH OTP
//
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//
//                Log.w("foodpilu2", "exception: " + e);
//
//                Toast.makeText(MainActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//            @Override
//            public void onCodeSent(String verificationId,
//                                   PhoneAuthProvider.ForceResendingToken token) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//                Log.d("foodpilu", "onCodeSent:" + verificationId);
//
//                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;
//
//
//            }
//
//        };

    }

//    private void attemptLoginOTP(){
//
//
//        String countryCode = "+91";
//        String enteredNumber = phoneNumberView.getText().toString();
//        final String number = countryCode+enteredNumber;
//
//        // Check if user exist
//
//        Query userExist = FirebaseDatabase.getInstance().getReference()
//                .child("users")
//                .orderByChild("userNumber")
//                .equalTo(number);
//
//        userExist.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.getChildrenCount() > 0){
//
//                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                            number,
//                            60,
//                            TimeUnit.SECONDS,
//                            MainActivity.this,
//                            mCallbacks
//
//                    );
//
//                }
//
//                else {
//
//                    Toast.makeText(MainActivity.this, "User Does not exit", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }

    // SIGN IN WITH PHONE

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if (task.isSuccessful()) {
//
//
//                            // Sign in success, update UI with the signed in users information
//                            Log.d("foodpilu", "SignInWithCredential is successful");
//
//
//                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//
//
//
//                            Intent myIntent = new Intent(MainActivity.this, Dashboard.class);
//                            startActivity(myIntent);
//                        }
//
//                        else {
//
//                            Toast.makeText(MainActivity.this, "Error Loggin In", Toast.LENGTH_SHORT).show();
//
//                            Log.w("foodpilu", "SignInWithCredential Failed", task.getException());
//
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//
//                                // Verification code entered was invalid
//                                Toast.makeText(MainActivity.this, "Invalid verification code", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//
//                    }
//                });
//
//    }


    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        final String emailOrPhone = emailView.getText().toString();
       final String password = passwordView.getText().toString();

        if (emailOrPhone.length() > 0  && password.length() > 0){

            Toast.makeText(this, "Login in progress", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Please Enter email and password", Toast.LENGTH_SHORT).show();

            return;

        }

        // TODO: Use FirebaseAuth to sign in with email & password
        FirebaseDatabase.getInstance().getReference().child("Users").child(emailOrPhone
                .replace('.',':')).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currUser = dataSnapshot.getValue(User.class);
                if(currUser.email.equals(emailOrPhone)){
                    if(currUser.password.equals(password)){

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("food", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("email",emailOrPhone);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        mAuth.signInWithEmailAndPassword(emailOrPhone, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                Log.d("foodpilu2", "onComplete: Sign in with email " + task.isSuccessful());
//
//                if (!task.isSuccessful()){
//                    Log.d("foodpilu2", "onComplete: problem signing in" + task.getException());
//
//                    showErrorDialog("There was a problem signing in. Incorrect email or password");
//                } else {
//
//                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
//                    finish();
//                    startActivity(intent);
//                }
//
//
//            }
//        });

    }





    @Override
    protected void onStart() {
        super.onStart();


        SharedPreferences pref = getApplicationContext().getSharedPreferences("food", MODE_PRIVATE);
        String user=pref.getString("email", null);

        if (user != null) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, Dashboard.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        } else {
            // User is signed out
            Log.d("foodpilu", "onAuthStateChanged:signed_out");
        }
    }


    // TODO: Show error on screen with an alert dialog

    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)

                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                FirebaseDatabase.getInstance().getReference().child("Users").child(email.replace(".",":")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("food", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("email",email);
                            editor.apply();

                            Intent myIntent = new Intent(MainActivity.this, Dashboard.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(myIntent);

                        }else{

                            Intent i = new Intent(MainActivity.this,SignupPage.class);

                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } else {
                finish();
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    finish();


                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {

                    return;
                }

            }
        }
    }


}
