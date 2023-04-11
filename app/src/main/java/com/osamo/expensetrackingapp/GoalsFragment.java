package com.osamo.expensetrackingapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osamo.expensetrackingapp.Model.Data;
import com.osamo.expensetrackingapp.Model.Goals;
import com.osamo.expensetrackingapp.Model.myAdapter2;
import com.osamo.expensetrackingapp.Model.myAdapter3;

import java.text.DateFormat;
import java.util.Date;

public class GoalsFragment extends Fragment {
    Button goal_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference mGoaldatabase;
    private RecyclerView recyclerView2;
    com.osamo.expensetrackingapp.Model.myAdapter3 myAdapter3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View myView2 = inflater.inflate(R.layout.fragment_goals, container, false);

       goal_btn = myView2.findViewById(R.id.add_goal);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser2 = mAuth.getCurrentUser();
        String uid2 = muser2.getUid();

       mGoaldatabase = FirebaseDatabase.getInstance().getReference().child("GoalsData").child(uid2);

       goal_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               goalsDataInsert();
           }
       });


       //Retrieve Goals data from Firebase Realtime Database

        recyclerView2 = myView2.findViewById(R.id.recylcler_goals);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Goals> options =
                new FirebaseRecyclerOptions.Builder<Goals>()
                        .setQuery(mGoaldatabase, Goals.class)
                        .build();

        myAdapter3 = new myAdapter3(options);
        recyclerView2.setAdapter(myAdapter3);

        return myView2;
    }

    @Override
    public void onStart() {
        super.onStart();
        myAdapter3.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        myAdapter3.stopListening();
    }




    public void goalsDataInsert(){

        AlertDialog.Builder mydialog2 = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView2 = inflater.inflate(R.layout.custom_layout_for_goalsdata,null);
        mydialog2.setView(myView2);
        AlertDialog dialog = mydialog2.create();


        TextInputEditText textDescription = myView2.findViewById(R.id.goals_textfield);

        Button btnSave = myView2.findViewById(R.id.btn_Save2);
        Button btnCancel = myView2.findViewById(R.id.btn_Cancel2);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String descr = textDescription.getText().toString().trim();


                if(TextUtils.isEmpty(descr)){

                    textDescription.setError("Required Field...");
                    return;
                }

                String id2 = mGoaldatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Goals goals = new Goals(descr, id2, mDate);

                mGoaldatabase.child(id2).setValue(goals);
                Toast.makeText(getActivity(), "Goal Data Added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }



    }
