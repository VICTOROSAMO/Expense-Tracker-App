package com.osamo.expensetrackingapp.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.osamo.expensetrackingapp.R;

public class myAdapter3 extends FirebaseRecyclerAdapter<Goals, myAdapter3.myViewHolder3> {

    public myAdapter3(@NonNull FirebaseRecyclerOptions<Goals> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder3 holder, int position, @NonNull Goals model) {

        holder.date3.setText(model.getDate());
        holder.description3.setText(model.getDescription());

    }

    @NonNull
    @Override
    public myViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.goals_recycler_data, parent, false);
        return new myAdapter3.myViewHolder3(view3);
    }


    public class myViewHolder3 extends RecyclerView.ViewHolder{

        TextView date3,description3;

        public myViewHolder3(@NonNull View itemView) {

            super(itemView);

            date3 = itemView.findViewById(R.id.date_goals);
            description3 = itemView.findViewById(R.id.goals_textfield);
        }
    }

}
