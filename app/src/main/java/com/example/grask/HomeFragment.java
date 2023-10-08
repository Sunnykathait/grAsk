package com.example.grask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<PostClass> postClasses;
    CustomAdpaterPostRV customAdpaterPostRV;

    TextView txt_goTOTopicActivity;

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.RV_postHome);

        txt_goTOTopicActivity = view.findViewById(R.id.txt_selectTopicAcitivity);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        postClasses = new ArrayList<>();

        txt_goTOTopicActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TopicSelectionActivity.class);
                startActivity(intent);
            }
        });


//        FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
//        firebaseDatabase.collection(currentDate())
//                .get()
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()){
//                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                            postClasses.add(new PostClass(documentSnapshot.get("post").toString(),
//                                    documentSnapshot.get("userName").toString(),"",new ArrayList<>(), documentSnapshot.getId().toString()));
//                        }
//                        customAdpaterPostRV = new CustomAdpaterPostRV(postClasses,view.getContext());
//                        recyclerView.setAdapter(customAdpaterPostRV);
//                    }else {
//                        Toast.makeText(getContext(),"Some error occured",Toast.LENGTH_SHORT).show();
//                    }
//                });




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