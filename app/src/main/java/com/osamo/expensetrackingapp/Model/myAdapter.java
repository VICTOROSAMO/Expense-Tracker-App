package com.osamo.expensetrackingapp.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.osamo.expensetrackingapp.IncomeFragment;
import com.osamo.expensetrackingapp.R;

public class myAdapter extends FirebaseRecyclerAdapter<Data,myAdapter.myViewHolder> {

    IncomeFragment incomeFragment;

    public myAdapter(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Data model) {
        holder.setDate(model.getDate());
        holder.setType(model.getType());
        holder.setNote(model.getNote());
       holder.setAmount(model.getAmount());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_data,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView dte,tpe,nte,amnt;
        View mView;
       // CardView myView;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
           mView = itemView;
        }

            //int stAmount = Integer.parseInt(String.valueOf(amnt));

           // dte = itemView.findViewById(R.id.date_income);
            //tpe = itemView.findViewById(R.id.type_txt_income);
            //nte = itemView.findViewById(R.id.note_txt_income);
            //stAmount = itemView.findViewById(R.id.amount_income);
            //myView = itemView.findViewById(R.id.income_cardview);


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
