package com.osamo.expensetrackingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osamo.expensetrackingapp.Model.Data;
import com.osamo.expensetrackingapp.Model.myAdapter;
import com.osamo.expensetrackingapp.Model.myAdapter2;

public class ExpensesFragment extends Fragment {


    private FirebaseAuth mAuth;
    private DatabaseReference mExpenseDatabase;
    private RecyclerView recyclerView;
    myAdapter2 adapter2;
    private TextView expenseTotalSum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_expenses, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();
        expenseTotalSum = myView.findViewById(R.id.expense_result);

        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);
        recyclerView = myView.findViewById(R.id.recylcerview_expense);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mExpenseDatabase, Data.class)
                        .build();

        adapter2 = new myAdapter2(options);
        recyclerView.setAdapter(adapter2);

        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalSum = 0;

                for(DataSnapshot mysnap2: snapshot.getChildren()){
                    Data data = mysnap2.getValue(Data.class);

                    totalSum+= data.getAmount();

                    String st2Result = String.valueOf(totalSum);

                    expenseTotalSum.setText(st2Result);


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
        adapter2.startListening();

}

    @Override
    public void onStop() {
        super.onStop();
        adapter2.stopListening();
    }
}