package com.example.grask;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdpaterPostRV  extends RecyclerView.Adapter<CustomAdpaterPostRV.ViewHolder>{

    private List<PostClass> posts;

    public CustomAdpaterPostRV(List<PostClass> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postlayouthome, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostClass postClass = posts.get(position);
        holder.txt_userName.setText(postClass.getUserName());
        holder.txt_userPost.setText(postClass.getPost());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_userName, txt_userPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           txt_userName = itemView.findViewById(R.id.txt_homeUsername);
           txt_userPost = itemView.findViewById(R.id.txt_homeUserpost);
        }
    }
}
