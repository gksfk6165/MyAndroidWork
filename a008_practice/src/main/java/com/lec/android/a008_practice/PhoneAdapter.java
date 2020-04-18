package com.lec.android.a008_practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder>{

    List<Phone> items = new ArrayList<Phone>();

    static PhoneAdapter adapter;
    public PhoneAdapter(){this.adapter = this;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View itemView = inf.inflate(R.layout.item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Phone item = items.get(position);
                holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    ;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvResult1,tvResult2,tvResult3;
        ImageButton btnDelItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvResult1 = itemView.findViewById(R.id.tvResult1);
            tvResult2 = itemView.findViewById(R.id.tvResult2);
            tvResult3 = itemView.findViewById(R.id.tvResult3);

            btnDelItem=itemView.findViewById(R.id.imgbtn1);

            btnDelItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.removeItem(getAdapterPosition());
                    adapter.notifyDataSetChanged();
                }
            });
        }

        public void setItem(Phone item) {
            tvResult1.setText("이름 : " + item.getName());
            tvResult2.setText("나이 : " + item.getAge());
            tvResult3.setText("주소 : " + item.getJuso());
        }
    }

    public void addItem(Phone item) {  items.add(item); }
    public void removeItem(int position){ items.remove(position); }

}
