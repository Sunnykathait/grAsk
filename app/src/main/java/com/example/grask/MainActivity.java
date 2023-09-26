package com.example.grask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtNumber;
    EditText edtName;
    EditText edtPassword;
    Button btnSignUP, btnLogIn;

    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedCustomeClass sharedPreferencesManager = SharedCustomeClass.getInstance(getApplicationContext());
        boolean isLoggedIn = sharedPreferencesManager.getBoolean("isLoggedIn",false);

        if(isLoggedIn){
            changeActivity();
        }

        edtName = findViewById(R.id.name);
        edtNumber = findViewById(R.id.phoneNumber);
        edtPassword = findViewById(R.id.password);

        btnSignUP = findViewById(R.id.signUp);
        btnLogIn = findViewById(R.id.logIn);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtNumber.getText().toString().length() != 10 || edtName.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "krle thoda kaam bhadwe , dono cheeze daal", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference databaseReference = firebaseDatabase.getReference("users");

                    DatabaseReference userRef = firebaseDatabase.getReference("users").child(edtNumber.getText().toString());

                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                if(snapshot.hasChild("userPassword")){
                                    Toast.makeText(getApplicationContext(),"This number already exist",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                UserClass userClass = new UserClass(edtNumber.getText().toString(), edtName.getText().toString(), edtPassword.getText().toString(),new ArrayList<String>());

                                databaseReference.child(edtNumber.getText().toString()).setValue(userClass, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if(error == null){
                                            saveUserInfo();
                                            changeActivity();
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().length() != 10 && edtPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Naam daal bhadwe", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference userRef = firebaseDatabase.getReference("users").child(edtNumber.getText().toString());

                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                if(snapshot.hasChild("userPassword")){
                                    String userPassword = snapshot.child("userPassword").getValue().toString();
                                    if(userPassword.equals(edtPassword.getText().toString())){
                                        Toast.makeText(getApplicationContext(),"Account verified",Toast.LENGTH_SHORT).show();
                                        saveUserInfo();
                                        changeActivity();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Wrong account details",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"number not exist",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

    }

    void saveUserInfo(){
        SharedCustomeClass sharedPreferencesManager = SharedCustomeClass.getInstance(getApplicationContext());
        sharedPreferencesManager.setString("userName", edtName.getText().toString());
        sharedPreferencesManager.setString("userPassword", edtPassword.getText().toString());
        sharedPreferencesManager.setString("userNumber", edtNumber.getText().toString());
    }


    void changeActivity(){
        Intent intent = new Intent(getApplicationContext(), HomeApp.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {

    }

}