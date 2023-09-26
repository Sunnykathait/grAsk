package com.example.grask;

import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PostFragment extends Fragment {

    EditText editText;
    Button btn_submit;

    public PostFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        editText = view.findViewById(R.id.EDT_query);
        btn_submit = view.findViewById(R.id.btn_post);

        String todayDate = currentDate();

        SharedCustomeClass sharedCustomeClass = SharedCustomeClass.getInstance(getContext());
        String userName = sharedCustomeClass.getString("userName","looser");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length() == 0){
                    Toast.makeText(getContext(), "put some queries first",Toast.LENGTH_SHORT).show();
                    return;
                }
                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(todayDate);

                PostClass postClass = new PostClass(editText.getText().toString(),userName,"",new ArrayList<>());

                collectionReference.add(postClass)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(getContext(),"Added",Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
                        });
            }
        });


        return view;
    }

    public String currentDate(){
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }

        // Get the current date
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }

        // Format the current date as a string
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDate = currentDate.format(formatter);
        }

        return formattedDate;

    }
}