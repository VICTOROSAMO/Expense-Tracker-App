package com.osamo.expensetrackingapp;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class AnalyticsFragment extends Fragment  {
    private PieChart mChart;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View analytics_view = inflater.inflate(R.layout.fragment_analytics, container, false);

        mChart = analytics_view.findViewById(R.id.pie_chart);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();


        //Retrieve the data

        databaseReference.child("IncomeData").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float totalIncome = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("AnalyticsFragment", "myObject is " + snapshot);

                    float amount = snapshot.child("amount").getValue(Float.class);
                    totalIncome += amount;
                }
                // do something with the total income

                // create pie chart
                PieChart pieChart = analytics_view.findViewById(R.id.pie_chart);
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(totalIncome, "Income"));

                // get total expense
                databaseReference.child("ExpenseData").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        float totalExpense = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            float amount = snapshot.child("amount").getValue(Float.class);
                            totalExpense += amount;
                        }
                        // do something with the total expense

                        // add expense to pie chart
                        entries.add(new PieEntry(totalExpense, "Expense"));

                        // set colors for income and expense categories
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.BLUE); // blue for income
                        colors.add(Color.RED); // red for expense

                        // set pie chart data and colors
                        PieDataSet pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(colors);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });













        /*ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(25f, "Expense"));
        entries.add(new PieEntry(75f, "Income"));

        PieDataSet dataSet = new PieDataSet(entries, "Analytics");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);*/



        return analytics_view;

    }}

