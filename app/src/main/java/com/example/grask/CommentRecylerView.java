package com.example.grask;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentRecylerView extends RecyclerView.Adapter<CommentRecylerView.ViewHolder> {
    private List<CommentClass> menuItems;

    public CommentRecylerView(List<CommentClass> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentClass menuItem = menuItems.get(position);
        holder._txt_userName.setText(menuItem.getUserName());
        holder._txt_userComment.setText(menuItem.getUserComment());

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_cmtUP, img_cmtDOWN;
        TextView txt_cmtCountUP, txt_cmtCountDown, _txt_userName, _txt_userComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cmtUP = itemView.findViewById(R.id.img_btnUP);
            img_cmtDOWN = itemView.findViewById(R.id.img_btnDown);

            txt_cmtCountUP = itemView.findViewById(R.id.txt_countUP);
            txt_cmtCountDown = itemView.findViewById(R.id.txt_countDown);

            _txt_userName = itemView.findViewById(R.id.txt_commentUserName);
            _txt_userComment = itemView.findViewById(R.id.txt_comment);

        }
    }
}