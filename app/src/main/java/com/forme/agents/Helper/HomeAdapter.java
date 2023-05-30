package com.forme.agents.Helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.forme.agents.DTO.SubView;
import com.forme.agents.R;
import com.forme.agents.View.Activites.LoginActivity;
import com.forme.agents.View.Activites.MainActivity;
import com.forme.agents.View.Activites.SubGroup1Activity;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    private ViewHolder holder;
    private RecyclerView recyclerView;
    private List<SubView> list ;
    Fragment fragment = null;
    int lastPosition=-1;

    public HomeAdapter(Context context, ArrayList<SubView> list,OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.subgroup_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubView subview = list.get(position);
        holder.textLabel.setText(subview.getName());
        setAnimation(holder.itemView, position);
        holder.bind(list.get(position), listener);

        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
        //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, menu;
        public ViewHolder(View itemView) {
            super(itemView);
            textLabel = itemView.findViewById(R.id.stock_label);
            menu = itemView.findViewById(R.id.menu);


            //imgCompany = itemView.findViewById(R.id.job_image);
        }

        public void bind(SubView subView, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                   // System.out.println("itemmm"+subView.getBytes(subView.getSubgroup()));
                    try {
                        Intent intent = new Intent(context, SubGroup1Activity.class);
                        ArrayList<Object> mapview = (ArrayList<Object>) subView.getSubgroup();
                        intent.putExtra("subView", mapview);
                        intent.putExtra("name", subView.getName());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                   /* Fragment myFragment = new UpdateactionFragment();
                    myFragment.setArguments(bundle);
                    myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent,myFragment).addToBackStack(null).commit();*/
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(SubView item);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
