package com.jathiswarbhaskar.myfitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
private Button signupbtn;
private EditText memail,mpassword,mrecheck;
private ProgressDialog loadingBar;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signupbtn = (Button) findViewById(R.id.signup_button);
        memail = (EditText) findViewById(R.id.register_email_input);
        mpassword=(EditText) findViewById(R.id.register_password_input);
        mrecheck = (EditText) findViewById(R.id.register_password_input_1);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        Register();



    }


    public void Register(){

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String pwd = mpassword.getText().toString().trim();
                String recheck = mrecheck.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email is required");
                    return;
                }
               else if (TextUtils.isEmpty(pwd)) {
                    mpassword.setError("Password is required");
                    return;
                }

               else if (TextUtils.isEmpty(recheck)){
                   mrecheck.setError("Retype Password is missing");
                }

               else{

                   if(!pwd.equals(recheck)){
                       AlertDialog.Builder recheckbuilder = new AlertDialog.Builder(SignUpActivity.this);
                       recheckbuilder.setMessage("Password and Retype Password not same! Please try again!");
                       recheckbuilder.setCancelable(true);

                       recheckbuilder.setPositiveButton(
                               "Ok",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       dialog.cancel();
                                   }
                               });
                       AlertDialog alert11 = recheckbuilder.create();
                       alert11.show();
                   }

                   else{
                       loadingBar.setTitle("Creating Account");
                       loadingBar.setMessage("Please Wait while we are checking the credentials...");
                       loadingBar.setCanceledOnTouchOutside(false);
                       loadingBar.show();

                       mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()) {
                                   loadingBar.dismiss();
                                   AlertDialog.Builder registration = new AlertDialog.Builder(SignUpActivity.this);
                                   registration.setMessage("You have been successfully registered congrats!!");
                                   registration.setCancelable(true);

                                   registration.setPositiveButton(
                                           "Ok",
                                           new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog, int id) {
                                                   dialog.cancel();
                                               }
                                           });
                                   AlertDialog alert11 = registration.create();
                                   alert11.show();


                                   Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                   startActivity(intent);
                               }
                               else{
                                   Toast.makeText(SignUpActivity.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                               }
                           }
                       });

                   }


                }








            }
        });

    }











}