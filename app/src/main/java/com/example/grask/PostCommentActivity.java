package com.example.grask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostCommentActivity extends AppCompatActivity {

    EditText editText_cmt;
    TextView txt_post, txt_postBTN;
    RecyclerView recyclerView_cmmt;
    CommentRecylerView commentRecylerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        editText_cmt = findViewById(R.id.EDT_comment);
        txt_post = findViewById(R.id.txt_postTxt);
        txt_postBTN = findViewById(R.id.txt_postBtn);
        recyclerView_cmmt = findViewById(R.id.RV_CommentShower);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_cmmt.setLayoutManager(layoutManager);

        String post_txt = getIntent().getStringExtra("post_txt");
        String post_ID = getIntent().getStringExtra("post_ID");

        txt_post.setText(post_txt);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentRef = db.collection(currentDate()).document(post_ID);

        documentRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                Map<String, Object> firestoreData = documentSnapshot.getData();
                if (firestoreData != null && firestoreData.containsKey("arrayList")) {
                    List<Map<String, Object>> customClassDataList = (List<Map<String, Object>>) firestoreData.get("arrayList");

                    ArrayList<CommentClass> commentClasses = new ArrayList<>();

                    for (Map<String, Object> map : customClassDataList){
                        String userName = (String) map.get("userName");
                        String userComment = (String) map.get("userComment");
//                        int liked =  Integer.parseInt((String) map.get("liked")) ;
//                        String disliked = (String) map.get("disliked");
                        commentClasses.add(new CommentClass(userName,userComment,0, 0));

                    }
                    commentRecylerView = new CommentRecylerView(commentClasses);
                    recyclerView_cmmt.setAdapter(commentRecylerView);

                }

            }
        });

        txt_postBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText_cmt.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(),"Cannot post empty comment...",Toast.LENGTH_SHORT).show();
                    return;
                }
                documentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            SharedCustomeClass sharedCustomeClass = SharedCustomeClass.getInstance(getApplicationContext());
                            String userName = sharedCustomeClass.getString("userName","no_NAME");
                            ArrayList<CommentClass> currentArrayList = (ArrayList<CommentClass>) documentSnapshot.get("arrayList");
                            currentArrayList.add(new CommentClass(userName,editText_cmt.getText().toString(),0,0));
                            documentRef.update("arrayList",currentArrayList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Some error occured plz try again later , after sometime",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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

    public CommentClass deserializeCustomClass(Map<String, Object> data) {
        String _userName, _userComment;
        int _like, _disliked;

        _userName = (String) data.get("userName");
        _userComment = (String) data.get("userComment");
        _like = (int) data.get("liked");
        _disliked = (int) data.get("disliked");

        return  new CommentClass(_userName,_userComment,_like,_disliked);
    }
}