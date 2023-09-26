package com.example.grask;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {

    RecyclerView recyclerView;
    CustomRVAdapter customRVAdapter;

    TextView textView;

    public SettingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        textView = view.findViewById(R.id.txt_setting_userName);

        setUserInfo();

        recyclerView = view.findViewById(R.id.RV_setting);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<SettingItemClass> settingItems = new ArrayList<>();
        settingItems.add(new SettingItemClass("Log-out", R.drawable.baseline_logout_24));

        customRVAdapter = new CustomRVAdapter(settingItems);
        recyclerView.setAdapter(customRVAdapter);

        return view;

    }

    public void setUserInfo(){
        SharedCustomeClass sharedCustomeClass = SharedCustomeClass.getInstance(getActivity());
        String userName = sharedCustomeClass.getString("userName","no_NAME");

        if(userName.equals("no_NAME") || userName.equals("")){

            String userNumber = sharedCustomeClass.getString("userNumber","-1");

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference documentReference = databaseReference.child(userNumber); // Replace with the path to your document

            documentReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("userName").getValue(String.class);
                        textView.setText(userName);
                        sharedCustomeClass.setString("userName",userName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors that may occur during the read operation
                }
            });
        }else{
            textView.setText(userName);
        }



    }

    public void showToast(String strMsg){
        Toast.makeText(getActivity(),strMsg,Toast.LENGTH_SHORT).show();
    }

}