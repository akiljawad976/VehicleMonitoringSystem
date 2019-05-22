package com.example.mist.vehiclemonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText logEmail,logpass;
    private Button login;
    private TextView signtv;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){
            finish();
            Toast.makeText(getApplicationContext(),"User Already Logged In",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        logEmail = (EditText)findViewById(R.id.logdevID);
        logpass = (EditText)findViewById(R.id.logpass);
        login = (Button) findViewById(R.id.bLogin);
        signtv = (TextView) findViewById(R.id.tRegister);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        signtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin(){
        String email = logEmail.getText().toString().trim();
        String passw = logpass.getText().toString().trim();

        if (!AppConstant.isValidEmail(email)){
            AppConstant.showAlertMessage(context,getString(R.string.alertemail));
            return;

        }

        if (TextUtils.isEmpty(passw)){
            AppConstant.showAlertMessage(context,getString(R.string.alertpass));
            return;
        }


        progressBar.setVisibility(View.VISIBLE);


//        progressDialog.setMessage("Signing in,Please Wait...");
//        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    //progressDialog.hide();
                    Intent in = new Intent(Login.this,MainActivity.class);
                    startActivity(in);
                }else {
                    //Toast.makeText(getApplicationContext(), "Please enter Valid Email!", Toast.LENGTH_SHORT).show();
                    AppConstant.showAlertMessage(context,"Please enter Valid Email!");
                    //progressDialog.hide();
                }
            }
        });
    }
}
