package com.osamo.expensetrackingapp.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.osamo.expensetrackingapp.R;

public class myAdapter2 extends FirebaseRecyclerAdapter<Data,myAdapter2.myViewHolder2> {

    public myAdapter2(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder2 holder, int position, @NonNull Data model) {
        holder.setDate(model.getDate());
        holder.setType(model.getType());
        holder.setNote(model.getNote());
        holder.setAmount(model.getAmount());

    }

    @NonNull
    @Override
    public myViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_data, parent, false);
        return new myViewHolder2(view);
    }

    public class myViewHolder2 extends RecyclerView.ViewHolder{

        TextView date2, type2, note2, amount2;
        View mView2;

        public myViewHolder2(@NonNull View itemView) {
            super(itemView);
            mView2 = itemView;
        }

            //date2 = itemView.findViewById(R.id.date_expense);
          // type2 = itemView.findViewById(R.id.type_txt_expense);
           // note2 = itemView.findViewById(R.id.note_txt_expense);
           // amount2 = itemView.findViewById(R.id.amount_expense);

        private void setType(String type){
            TextView mType = mView2.findViewById(R.id.type_txt_expense);
            mType.setText(type);
        }

        private void setNote(String note){

            TextView mNote = mView2.findViewById(R.id.note_txt_expense);
            mNote.setText(note);
        }
        private  void setDate(String date){
            TextView mDate = mView2.findViewById(R.id.date_expense);
            mDate.setText(date);
        }

        private void setAmount(int amount){

            TextView mAmount = mView2.findViewById(R.id.amount_expense);
            String stAmount = String.valueOf(amount);
            mAmount.setText(stAmount);
        }


        }
    }

