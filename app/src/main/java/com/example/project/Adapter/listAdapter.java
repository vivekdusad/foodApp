package com.example.project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.model_rv_list;
import com.example.project.R;

import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {
    List<model_rv_list> model_rv_lists;

    public listAdapter(List<model_rv_list> model_rv_lists) {
        this.model_rv_lists = model_rv_lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = model_rv_lists.get(position).getTitle();
        String body = model_rv_lists.get(position).getBody();
        int resource = model_rv_lists.get(position).getResource();
        holder.setData(title,body,resource);

    }

    @Override
    public int getItemCount() {
        return model_rv_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shopImageView;
        TextView titleView;
        TextView bodyView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopImageView = itemView.findViewById(R.id.shopImageId);
            titleView = itemView.findViewById(R.id.titleId);
            bodyView = itemView.findViewById(R.id.bodyId);
        }
        public void setData(String title,String body,int resource){
            shopImageView.setImageResource(resource);

            titleView.setText(title);
            bodyView.setText(body);


        }
    }
}
