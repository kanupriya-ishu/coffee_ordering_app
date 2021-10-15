package com.example.coffeeordering;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Model> modelList;
    Context context;

    public OrderAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
