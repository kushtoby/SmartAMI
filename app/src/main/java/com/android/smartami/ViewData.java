package com.android.smartami;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ViewData extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference ref;
    private Model meterValues;
    private RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference reference = ref.child("Energy_Data1").child("User_1");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String energyAvailable = dataSnapshot.child("energyAvailable").getValue().toString();
                String energyConsumed = dataSnapshot.child("energyConsumed").getValue().toString();
                String power = dataSnapshot.child("power").getValue().toString();
                String current = dataSnapshot.child("current").getValue().toString();
                String voltageInput = dataSnapshot.child("voltageInput").getValue().toString();

                new Model(voltageInput, current, power, energyConsumed, energyAvailable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

              if (meterValues != null) {
                  RecyclerAdapter adapter = new RecyclerAdapter(meterValues);
                  recyclerView.setAdapter(adapter);
              }
    }
}