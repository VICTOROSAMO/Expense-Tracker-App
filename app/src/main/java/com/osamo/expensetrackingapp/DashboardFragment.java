package com.osamo.expensetrackingapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osamo.expensetrackingapp.Model.Data;

import java.text.DateFormat;
import java.util.Date;


public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button expbtn;
    private Button incbtn;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;
    private TextView totalIncome;
    private TextView totalExpense;

    public DashboardFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        incbtn = myView.findViewById(R.id.inc_btn);
        expbtn = myView.findViewById(R.id.exp_btn);
        imageView = myView.findViewById(R.id.dashboard_img);

        totalIncome = myView.findViewById(R.id.income_set_result);
        totalExpense = myView.findViewById(R.id.expense_set_result);

        Glide.with(this)
                .load(R.drawable.budget_icon)
                .into(imageView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        incbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeDataInsert();
            }
        });

        expbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expenseDataInsert();
            }

        });


        //Calculate total income

        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalSum = 0;

                for(DataSnapshot mysnap: snapshot.getChildren()){
                Data data = mysnap.getValue(Data.class);

                totalSum+= data.getAmount();

               String stResult = String.valueOf(totalSum);

               totalIncome.setText(stResult);

            }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Calculate total expense

       mExpenseDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               int totalSum = 0;

               for(DataSnapshot mysnap: snapshot.getChildren()){
                   Data data = mysnap.getValue(Data.class);

                   totalSum+= data.getAmount();

                   String strResult = String.valueOf(totalSum);

                   totalExpense.setText(strResult);

           }}

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });



        return myView;
    }

   public void incomeDataInsert(){

       AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
       LayoutInflater inflater = LayoutInflater.from(getActivity());
       View myView1 = inflater.inflate(R.layout.custom_layout_for_incomedata,null);
       mydialog.setView(myView1);
       AlertDialog dialog = mydialog.create();


       EditText edtAmount = myView1.findViewById(R.id.amount);
       EditText edtType = myView1.findViewById(R.id.type);
       EditText edtNote = myView1.findViewById(R.id.note);

       Button btnSave = myView1.findViewById(R.id.btn_Save);
       Button btnCancel = myView1.findViewById(R.id.btn_Cancel);


       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String type = edtType.getText().toString().trim();
               String ammount = edtAmount.getText().toString().trim();
               String note = edtNote.getText().toString().trim();


               if(TextUtils.isEmpty(ammount)){

                   edtAmount.setError("Required Field...");
                   return;
               }

               int ourAmountInt = Integer.parseInt(ammount);

               if(TextUtils.isEmpty(type)){

                   edtType.setError("Required Field...");
                   return;
               }

               if(TextUtils.isEmpty(note)){

                   edtNote.setError("Required Field...");
                   return;
               }

               String id = mIncomeDatabase.push().getKey();
               String mDate = DateFormat.getDateInstance().format(new Date());

               Data data = new Data(ourAmountInt,type,note,id,mDate);

               mIncomeDatabase.child(id).setValue(data);
               Toast.makeText(getActivity(), "Income Data Added", Toast.LENGTH_SHORT).show();
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


   public void expenseDataInsert(){

        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
         View myView = inflater.inflate(R.layout.custom_layout_for_expensedata, null);
         mydialog.setView(myView);

        final AlertDialog dialog = mydialog.create();


         EditText ammount = myView.findViewById(R.id.amount);
         EditText type = myView.findViewById(R.id.type);
         EditText note = myView.findViewById(R.id.note);

         Button btnSave =myView.findViewById(R.id.btn_Save);
         Button btnCancel = myView.findViewById(R.id.btn_Cancel);

         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String tmAmount = ammount.getText().toString().trim();
                 String tmType =  type.getText().toString().trim();
                 String tmNote = note.getText().toString().trim();


                 if(TextUtils.isEmpty(tmAmount)){

                     ammount.setError("Required Field...");
                     return;
                 }
               int inAmount = Integer.parseInt(tmAmount);

                 if(TextUtils.isEmpty(tmType)){

                     type.setError("Required Field...");
                     return;
                 }

                 if(TextUtils.isEmpty(tmNote)){

                     note.setError("Required Field...");
                     return;
                 }

              String id = mExpenseDatabase.push().getKey();
                 String mDate = DateFormat.getDateInstance().format(new Date());

                 Data data = new Data(inAmount,tmType,tmNote,id, mDate);
                 mExpenseDatabase.child(id).setValue(data);

                 Toast.makeText(getActivity(), "Expense Data Added", Toast.LENGTH_SHORT).show();
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