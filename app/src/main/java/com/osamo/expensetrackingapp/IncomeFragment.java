package com.osamo.expensetrackingapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;
import com.osamo.expensetrackingapp.Model.Data;
import com.osamo.expensetrackingapp.Model.myAdapter;

public class IncomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDb;
    private RecyclerView recyclerView;
    myAdapter adapter;
    private EditText editAmmout, editTyype, editNoote;
    private Button btn_update, btn_delete;
    private TextView incomeTotalSum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_income, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            // handle the case where the user is not authenticated
            return myView;
        }
        String uid = mUser.getUid();

        mIncomeDb = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        recyclerView = myView.findViewById(R.id.recylcerview_income);
        incomeTotalSum = myView.findViewById(R.id.income_result);


        mIncomeDb = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        recyclerView = myView.findViewById(R.id.recylcerview_income);
        incomeTotalSum = myView.findViewById(R.id.income_result);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mIncomeDb, Data.class)
                        .build();

        adapter = new myAdapter(options);
        recyclerView.setAdapter(adapter);


        mIncomeDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalSum = 0;

                for(DataSnapshot mysnap1: snapshot.getChildren()){
                    Data data = mysnap1.getValue(Data.class);

                    totalSum+= data.getAmount();

                    String stResult = String.valueOf(totalSum);

                    incomeTotalSum.setText(stResult);


                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();


        mIncomeDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }}

