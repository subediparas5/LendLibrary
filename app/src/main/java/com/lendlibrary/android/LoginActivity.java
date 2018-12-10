package com.lendlibrary.android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton register;
    Button login;
    EditText Username,Password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        register = findViewById(R.id.register_button);
        findViewById(R.id.register_button).setOnClickListener(this);
        login=findViewById(R.id.sign_in_button);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        Username = findViewById(R.id.username_field);
        Password=findViewById(R.id.password_field);
        progressBar=findViewById(R.id.progress_bar);
        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser()!=null){
                    finish();
                    Intent login = new Intent(LoginActivity.this, DashboardActivity.class);
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
            Intent register = new Intent(LoginActivity.this, DashboardActivity.class);
            register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(register);
        }
    }
    private void signIn(){

        final String username = Username.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        if (username.isEmpty()) {
            Username.setError("Username or phone number is required");
            Username.requestFocus();
            return;
        }
        if (!(Patterns.PHONE.matcher(username).matches() ||username.length()>6)) {
            Username.setError("Please enter a valid phone number or username.");
            Username.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required.");
            Password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        if(Patterns.PHONE.matcher(username).matches()) {
            DatabaseReference databaseReference=firebaseDatabase.getReference("PhoneEmailLink").child(username);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    PhoneLink phoneLink=dataSnapshot.getValue(PhoneLink.class);
                    String eemail=Objects.requireNonNull(phoneLink).getEmail();
                    mAuth.signInWithEmailAndPassword(eemail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Logged in", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent login = new Intent(LoginActivity.this, DashboardActivity.class);
                                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(login);
                            } else
                                Username.setError("Phone number did not match with any account"); }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(username.length()>6){
            DatabaseReference databaseReference= firebaseDatabase.getReference("UserNames").child(username);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UsernameLink usernameLink=dataSnapshot.getValue(UsernameLink.class);
                    String emaill = Objects.requireNonNull(usernameLink).getEmail();
                    String password = Password.getText().toString().trim();
                    mAuth.signInWithEmailAndPassword(emaill, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                 @Override
                                                                                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                     progressBar.setVisibility(View.GONE);
                                                                                                     if (task.isSuccessful()) {
                                                                                                         Toast.makeText(getApplicationContext(), "User Logged in", Toast.LENGTH_SHORT).show();
                                                                                                         finish();
                                                                                                         Intent login = new Intent(LoginActivity.this, DashboardActivity.class);
                                                                                                         login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                                         startActivity(login);
                                                                                                     } else
                                                                                                         Username.setError("Username did not match with any account");
                                                                                                 }
                                                                                             }
                    );

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button: {
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            }
            case R.id.sign_in_button:{
                signIn();
                break;
            }
        }
    }
}
