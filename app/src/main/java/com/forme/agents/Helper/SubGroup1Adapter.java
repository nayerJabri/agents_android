package com.forme.agents.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.forme.agents.DTO.Stock;
import com.forme.agents.DTO.SubView;
import com.forme.agents.R;
import com.forme.agents.View.Activites.SubGroup1Activity;
import com.forme.agents.View.Activites.SubGroup2Activity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubGroup1Adapter extends RecyclerView.Adapter<SubGroup1Adapter.ViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    private ViewHolder holder;
    private RecyclerView recyclerView;
    private ArrayList<HashMap> list ;
    private String nameStock;
    Fragment fragment = null;
    int lastPosition=-1;

    public SubGroup1Adapter(Context context, ArrayList<HashMap> list,String nameStock, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
        this.nameStock = nameStock;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.subgroup_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap subView = list.get(position);
            if(!subView.get("ID").toString().isEmpty()) {
                holder.textLabel.setText(subView.get("Name").toString());
                setAnimation(holder.itemView, position);
                holder.bind(list.get(position),list.get(position).get("Name").toString(),list, listener);
            }
            else if(subView.get("ID").toString().isEmpty()){
                Intent intent = new Intent(context, SubGroup2Activity.class);
                ArrayList<HashMap> mapview = (ArrayList<HashMap>) subView.get("SubGroup2");
                ArrayList<Object> objects = new ArrayList<>();
                for(int i=0 ; i<mapview.size();i++){
                    SubView sb = new SubView();
                    sb.setSubgroup(mapview.get(i).get("item"));
                    sb.setId(mapview.get(i).get("ID").toString());
                    sb.setName(mapview.get(i).get("Name").toString());
                    objects.add(sb);
                }
                intent.putExtra("name", nameStock);
                intent.putExtra("subView", objects);
                intent.putExtra("previous list",list);
                intent.putExtra("previous name",nameStock);
                setAnimation(holder.itemView, position);
                holder.bind(list.get(position),nameStock,list, listener);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);



            //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
            //holder.textCompanyName.setText(String.valueOf(job.getCompany().getName()));
            //holder.imgCompany.setImageResource(String.valueOf(job.getYear()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel, menu, name;

        public ViewHolder(View itemView) {
            super(itemView);
            textLabel = itemView.findViewById(R.id.stock_label);
            name = itemView.findViewById(R.id.namegp);
            menu = itemView.findViewById(R.id.menu);


            //imgCompany = itemView.findViewById(R.id.job_image);
        }

        public void bind(HashMap subView,String name,ArrayList<HashMap>list, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                        Intent intent = new Intent(context, SubGroup2Activity.class);
                        ArrayList<Object> mapview = (ArrayList<Object>) subView.get("SubGroup2");
                        intent.putExtra("subView", mapview);
                        intent.putExtra("previous list",list);
                        intent.putExtra("previous name",nameStock);
                        intent.putExtra("name", name);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

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
