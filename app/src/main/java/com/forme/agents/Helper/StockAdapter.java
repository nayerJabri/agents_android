package com.forme.agents.Helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.forme.agents.DTO.Stock;
import com.forme.agents.DTO.SubView;
import com.forme.agents.R;
import com.forme.agents.View.Activites.ScreenNumberActivity;
import com.forme.agents.View.Activites.SubGroup1Activity;
import com.forme.agents.View.Activites.SubGroup2Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    private ViewHolder holder;
    private RecyclerView recyclerView;
    private ArrayList<Stock> list ;
    Fragment fragment = null;
    int lastPosition=-1;

    public StockAdapter(Context context, ArrayList<Stock> list, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.stocklist_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textLabel.setText(list.get(position).getName());

        holder.menu.setText(list.get(position).getPrice());
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
        }

        public void bind(Stock stockview, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(context, ScreenNumberActivity.class);
                     intent.putExtra("stock",stockview);
                     context.startActivity(intent);
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
