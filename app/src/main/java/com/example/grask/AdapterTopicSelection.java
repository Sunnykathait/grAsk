package com.example.grask;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdapterTopicSelection extends RecyclerView.Adapter<AdapterTopicSelection.ViewHolder> {

    private ArrayList<String> arraylist;
    private Context context;

    public ArrayList<String> currentSelectedtags;

    public  AdapterTopicSelection(ArrayList<String> itemList, Context context) {
        this.arraylist = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.giditemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str = arraylist.get(position);
        holder.textView.setText(str);
        SharedCustomeClass sharedCustomeClass = new SharedCustomeClass(context);
        String userNumber = sharedCustomeClass.getString("userNumber","");
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        int selectedColor = context.getResources().getColor(R.color.cardViewSelectColor);
        int UnselectedColor = context.getResources().getColor(R.color.white);

        currentSelectedtags = new ArrayList<>();

        DocumentReference documentReference = firestore.collection("users").document(userNumber);

        // getting this arraylist to see what are the tags selected by the users and change their color to selected
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if(snapshot.exists()) {
                    currentSelectedtags = (ArrayList<String>) snapshot.get("userTopics");
                    if(currentSelectedtags.contains(holder.textView.getText().toString())){
                        holder.cardView.setCardBackgroundColor(selectedColor);
                    }
                }
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snapshot) {
                        if(snapshot.exists()){
                            ArrayList<String> tagsList = (ArrayList<String>) snapshot.get("userTopics");
                            String forToast;

                            if(!tagsList.isEmpty() && tagsList.contains(holder.textView.getText().toString())){
                                tagsList.remove(holder.textView.getText().toString());
                                forToast = "Removed";
                                holder.cardView.setCardBackgroundColor(UnselectedColor);

                            }else{
                                tagsList.add(holder.textView.getText().toString());
                                forToast = "Added";
                                holder.cardView.setCardBackgroundColor(selectedColor);
                            }


                            documentReference.update("userTopics", tagsList)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, forToast, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_tag);
            cardView = itemView.findViewById(R.id.CV_tags);
        }
    }
}
