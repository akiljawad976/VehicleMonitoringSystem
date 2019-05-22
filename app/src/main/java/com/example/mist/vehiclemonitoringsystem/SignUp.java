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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    private EditText email,pass,conpass,phn,id;
    private Button signup;
    private TextView logtv;
    private Context context;
    private ProgressBar progressBar;
    DatabaseReference databaseArtists;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this;
        databaseArtists = FirebaseDatabase.getInstance().getReference("user");

        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.tEmail);
        id = (EditText)findViewById(R.id.deviceID);
        pass = (EditText)findViewById(R.id.etPassword);
        conpass = (EditText)findViewById(R.id.ConfPass);
        phn = (EditText)findViewById(R.id.PhnNumb);
        signup = (Button)findViewById(R.id.bsignup);
        logtv = (TextView)findViewById(R.id.tlogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        logtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkPass1 = pass.getText().toString().trim();
                String checkPass2 = conpass.getText().toString().trim();
                if(checkPass1.contentEquals(checkPass2)){
                    registerUser();
                }else{
                    AppConstant.showAlertMessage(context,"Password Didn't Match");
                }

            }
        });
    }
    private void registerUser(){
        String Email = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        //validation
        if (TextUtils.isEmpty(Email)){
            AppConstant.showAlertMessage(context,"PLease Enter Email");
            return;
        }

        if (TextUtils.isEmpty(password)){
            AppConstant.showAlertMessage(context,"Please Enter Password");
            return;
        }

        if (password.length() <= 6){
            AppConstant.showAlertMessage(context,"Password too Small,Must be more than 6 Characters");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
//        progressDialog.setMessage("Registering, Please Wait...");
//        progressDialog.show();

        //now we can create the user
        firebaseAuth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            //progressDialog.hide();
                            adduser();
                            Intent home = new Intent(SignUp.this,MainActivity.class);
                            startActivity(home);
                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUp.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(SignUp.this, "Something went terrible wrong!", Toast.LENGTH_SHORT).show();
                            // progressDialog.hide();
                        }
                    }
                });
    }
    private void adduser(){
        String email1 = email.getText().toString().trim();
        String password1 = pass.getText().toString().trim();
        String phone = phn.getText().toString().trim();
        String deviceId = id.getText().toString().trim();



        User user = new User(email1,password1,phone,deviceId);

        databaseArtists.child(deviceId).setValue(user);
    }
}
