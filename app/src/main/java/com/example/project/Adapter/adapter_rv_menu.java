package com.example.project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Model_rv_menu;
import com.example.project.R;

import java.util.List;

public class adapter_rv_menu extends RecyclerView.Adapter<adapter_rv_menu.ViewHolder> {
    List<Model_rv_menu> model_rv_menuList;

    public adapter_rv_menu(List<Model_rv_menu> model_rv_menuList) {
        this.model_rv_menuList = model_rv_menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = model_rv_menuList.get(position).getImageResource();
        String title = model_rv_menuList.get(position).getName();
        holder.setData(resource,title);

    }

    @Override
    public int getItemCount() {
        return model_rv_menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView menuImageView;
        TextView titleView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuImageView = itemView.findViewById(R.id.dish_menu_imageView);
            titleView = itemView.findViewById(R.id.titleTextId);

        }
        private void setData(int resource,String titleText){
            menuImageView.setImageResource(resource);
            titleView.setText(titleText);
        }
    }

}
