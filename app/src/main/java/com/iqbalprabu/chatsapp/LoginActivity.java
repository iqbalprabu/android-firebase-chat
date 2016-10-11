package com.iqbalprabu.chatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private FirebaseAuth mFirebaseAuth;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Login");
        mProgressDialog.setMessage("Loading....");

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_login)
        {
            Boolean isEpmtyField = false;

            final String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(email.isEmpty())
            {
                edtEmail.setError("Field Required");
                isEpmtyField = true;
            }else{
                edtEmail.setError(null);
            }

            if(password.isEmpty())
            {
                edtPassword.setError("Field Required");
                isEpmtyField = true;
            }else{
                edtPassword.setError(null);
            }

            if(!isEpmtyField)
            {
                mProgressDialog.show();
                mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mProgressDialog.cancel();

                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                            final Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                            Log.d("LoginActivity", "email: " + email);

                            AppPreference appPreference = new AppPreference(LoginActivity.this);
                            appPreference.setEmail(email.toString());

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            startActivity(intent);
                                            finish();
                                        }
                                    },
                                    1000);
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }

        }


    }
}
