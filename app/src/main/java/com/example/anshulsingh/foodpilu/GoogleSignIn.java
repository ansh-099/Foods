package com.example.anshulsingh.foodpilu;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class GoogleSignIn extends AppCompatActivity {
    FirebaseUser firebaseUser;
    public static final int RC_SIGN_IN= 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        SignInButton button= findViewById(R.id.google_login_at_signUp);
        button.setOnClickListener(new View.OnClickListener() {
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

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(GoogleSignIn.this,Dashboard.class);
                startActivity(i);

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
