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

public class CustomRVAdapter extends RecyclerView.Adapter<CustomRVAdapter.ViewHolder> {
    private List<SettingItemClass> menuItems;

    public CustomRVAdapter(List<SettingItemClass> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SettingItemClass menuItem = menuItems.get(position);
        holder.textViewMenuItem.setText(menuItem.getText());
        holder.imageViewMenuItem.setImageResource(menuItem.getImageResource());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.textViewMenuItem.getText().toString().equals("Log-out")){
                    SharedCustomeClass sharedCustomeClass = SharedCustomeClass.getInstance(view.getContext());
                    sharedCustomeClass.setBoolean("isLoggedIn",false);
                    Intent intent = new Intent(view.getContext(),MainActivity.class);
                    view.getContext().startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMenuItem;
        TextView textViewMenuItem;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMenuItem = itemView.findViewById(R.id.img_settingIcon);
            textViewMenuItem = itemView.findViewById(R.id.txt_setting_Name);
            cardView = itemView.findViewById(R.id.cv_setting);
        }
    }
}