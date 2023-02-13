package com.osamo.expensetrackingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import Model.Data;

public class IncomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDb;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_income, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncomeDb = FirebaseDatabase.getInstance().getReference().child("transaction 1").child(uid);
        recyclerView = myView.findViewById(R.id.recylcerview_income);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        private void setType(String type){
            TextView mType = mView.findViewById(R.id.type_txt_income);
            mType.setText(type);
        }

        private void setNote(String note){

            TextView mNote = mView.findViewById(R.id.note_txt_income);
            mNote.setText(note);
        }
        private  void setDate(String date){
            TextView mDate = mView.findViewById(R.id.date_income);
            mDate.setText(date);
        }

        private void setAmount(int amount){

            TextView mAmount = mView.findViewById(R.id.amount_income);
            String stAmount = String.valueOf(amount);
            mAmount.setText(stAmount);
        }
    }
}