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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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


        FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();

        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtNumber.getText().toString().length() != 10 || edtName.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "krle thoda kaam bhadwe , dono cheeze daal", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference databaseReference = firebaseDatabase.collection("users").document(edtNumber.getText().toString());

                    databaseReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(getApplicationContext(),"This number already exist",Toast.LENGTH_SHORT).show();
                            }else{
                                UserClass userClass = new UserClass(edtNumber.getText().toString(), edtName.getText().toString(), edtPassword.getText().toString(),new ArrayList<String>(),new ArrayList<String>());

                                databaseReference.set(userClass);
                                saveUserInfo();
                                changeActivity();

                            }
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
                    DocumentReference userRef = firebaseDatabase.collection("users").document(edtNumber.getText().toString());

                    userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();
                                String userPassword = documentSnapshot.get("userPassword").toString();
                                if(userPassword.equals(edtPassword.getText().toString())){
                                    Toast.makeText(getApplicationContext(),"Account verified",Toast.LENGTH_SHORT).show();
                                    saveUserInfo();
                                    changeActivity();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Wrong account details",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"number not exist",Toast.LENGTH_SHORT).show();
                            }
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