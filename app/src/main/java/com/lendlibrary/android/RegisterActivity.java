package com.lendlibrary.android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public FirebaseAuth mAuth;
    EditText FirstName,LastName, Email, Password, Username,ConfirmPassword,phone;
    Button back,Register;
    ProgressBar progressBar;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        phone=findViewById(R.id.register_phone_field);
        Register=findViewById(R.id.register_submit_button);
        back= findViewById(R.id.sign_in_button);
        Email= findViewById(R.id.register_email);
        Password= findViewById(R.id.register_password_field);
        FirstName= findViewById(R.id.register_fname_field);
        LastName= findViewById(R.id.register_lname_field);
        Username= findViewById(R.id.register_username_field);
        ConfirmPassword= findViewById(R.id.confirm_password_field);
        findViewById(R.id.register_submit_button).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        progressBar= findViewById(R.id.progress_bar);
        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser()!=null){
                    finish();
                    Intent login = new Intent(RegisterActivity.this, DashboardActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);}
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        if(mAuth.getCurrentUser()!=null){
            finish();
            Intent register = new Intent(RegisterActivity.this, DashboardActivity.class);
            register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(register);
        }
    }
    private void registerUser() {
        final String email = Email.getText().toString().trim();
        final String Phone= phone.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        String confirmPassword=ConfirmPassword.getText().toString().trim();
        final String username=Username.getText().toString().trim();
        final String firstName =FirstName.getText().toString().trim();
        final String lastName =LastName.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if(firstName.isEmpty()){
            FirstName.setError("Please enter your first name");
            FirstName.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(lastName.isEmpty()){
            LastName.setError("Please enter your first name");
            LastName.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter a valid email address.");
            Email.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(username.isEmpty()){
            Username.setError("Enter your phone number");
            Username.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (Phone.isEmpty()){
            phone.setError("Enter your phone number");
            phone.requestFocus();
            progressBar.setVisibility(View.GONE);
        }
        if(!Patterns.PHONE.matcher(Phone).matches()){
            phone.setError("Enter a valid phone number");
            phone.requestFocus();
            progressBar.setVisibility(View.GONE);
        }
        if(username.length()<6){
            Username.setError("Minimum length of username is 6.");
            Username.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (password.isEmpty()) {
            Password.setError("Password is required.");
            Password.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (password.length()<6){
            Password.setError("Minimum length of password is 6 digits.");
            Password.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(!password.equals(confirmPassword)){
            ConfirmPassword.setError("Password and Confirm Password didn't match.");
            ConfirmPassword.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        Query emailQuery= FirebaseDatabase.getInstance().getReference().child("UserNames").orderByChild("email").equalTo(email);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>0){
                    Email.setError("User with same email address is already registered.");
                    Email.requestFocus();
                }
                else{
                    Query usernameQuery= FirebaseDatabase.getInstance().getReference().child("UserNames").orderByChild("username").equalTo(username);
                    usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                Username.setError("Username already taken. Enter another username.");
                                Username.requestFocus();
                            }
                            else {
                                Query phoneQuery = FirebaseDatabase.getInstance().getReference().child("UserNames").orderByChild("phone").equalTo(Phone);
                                phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getChildrenCount() > 0) {
                                            phone.setError("User with same phone number is already registered.");
                                            phone.requestFocus();
                                        } else {
                                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        String UID=mAuth.getUid();
                                                        User user = new User(firstName, lastName, email, username, Phone);
                                                        final UsernameLink usernameLink = new UsernameLink(email, username, Phone,UID);
                                                        final PhoneLink phoneLink= new PhoneLink(email);
                                                        FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    FirebaseDatabase.getInstance().getReference("UserNames").child(Objects.requireNonNull(username)).setValue(usernameLink);
                                                                    FirebaseDatabase.getInstance().getReference("PhoneEmailLink").child(Objects.requireNonNull(Phone)).setValue(phoneLink);
                                                                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                    finish();
                                                                    Intent register = new Intent(RegisterActivity.this, DashboardActivity.class);
                                                                    register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    startActivity(register);
                                                                    progressBar.setVisibility(View.GONE);
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                            progressBar.setVisibility(View.GONE);
                                                            Toast.makeText(getApplicationContext(), "User with same email address is already registered.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


            @Override
            public void onClick(View v) {
            switch (v.getId()){
            case R.id.sign_in_button:{
                startActivity(new Intent(this,LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;}
            case R.id.register_submit_button:
                registerUser();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
