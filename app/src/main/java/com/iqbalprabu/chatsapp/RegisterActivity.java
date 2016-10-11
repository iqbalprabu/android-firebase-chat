package com.iqbalprabu.chatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtEmail, edtPassword;
    private Button btnRegister;
    private FirebaseAuth mFirebaseAuth;
    private TextView txtLogin;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();

        edtName = (EditText) findViewById(R.id.edt_nama);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Register");
        mProgressDialog.setMessage("Loading....");

        AppPreference mAppPreference = new AppPreference(RegisterActivity.this);
        if(!TextUtils.isEmpty(mAppPreference.getEmail())){

            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_register)
        {
            boolean isEmptyField = false;

            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(name.isEmpty())
            {
                isEmptyField = true;
                edtName.setError("Field Required");
            }else{
                edtName.setError(null);
            }

            if(email.isEmpty())
            {
                isEmptyField = true;
                edtEmail.setError("Field Required");
            }else{
                edtEmail.setError(null);
            }

            if(password.isEmpty())
            {
                isEmptyField = true;
                edtPassword.setError("Field Required");
            }else{
                edtPassword.setError(null);
            }

            if(!isEmptyField)
            {
                mProgressDialog.show();

                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mProgressDialog.cancel();

                        if(task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else if(view.getId() == R.id.txtLogin)
        {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }

    }
}
