package com.example.anshulsingh.foodpilu;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button resetPasswordBtn;
    private EditText resetEmailField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPasswordBtn = (Button) findViewById(R.id.reset_password_btn);
        resetEmailField = (EditText) findViewById(R.id.reset_email_field);

        mAuth = FirebaseAuth.getInstance();

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = resetEmailField.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    resetEmailField.setError("This field can't be empty");
                }

                else if (!email.contains("@")) {

                    Toast.makeText(ResetPasswordActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }

                else {

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Check email to reset password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
                            }

                            else {
                                Toast.makeText(ResetPasswordActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}
