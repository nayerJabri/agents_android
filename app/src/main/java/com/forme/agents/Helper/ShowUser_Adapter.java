package com.forme.agents.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.forme.agents.DTO.CheckNewUser;
import com.forme.agents.R;

import java.util.ArrayList;

public class ShowUser_Adapter extends RecyclerView.Adapter<ShowUser_Adapter.ViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    private ArrayList<CheckNewUser> list ;
    Fragment fragment = null;
    int lastPosition=-1;

    public ShowUser_Adapter(Context context, ArrayList<CheckNewUser> list, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.showuser_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getFullname());

        holder.local.setText(list.get(position).getStorename());
        holder.mobile.setText(list.get(position).getMobile());
       // holder.rasidnow.setText(list.get(position).get+"الرصيد الحالي ");
        holder.bind(list.get(position), listener);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, local,mobile,rasidnow;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            local = itemView.findViewById(R.id.local);
            rasidnow = itemView.findViewById(R.id.rasidnow);
            mobile = itemView.findViewById(R.id.phoneuser);
        }

        public void bind(CheckNewUser cbecknewuser, ShowUser_Adapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            });
        }

    }
    public interface OnItemClickListener {
        void onItemClick(CheckNewUser item);
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
