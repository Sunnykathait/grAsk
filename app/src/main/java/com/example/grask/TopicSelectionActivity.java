package com.example.grask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TopicSelectionActivity extends AppCompatActivity {

    AdapterTopicSelection adapter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);

        ArrayList<String> arrayListTags = new ArrayList<>();

        firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("Tags");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult()){
                        arrayListTags.add(documentSnapshot.getId());
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    adapter = new AdapterTopicSelection(arrayListTags, getApplicationContext()); // Replace with your data source
                    recyclerView.setAdapter(adapter);



                }
            }
        });


    }
}