package com.forme.agents.Helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.forme.agents.DTO.Stock;
import com.forme.agents.DTO.SubView;
import com.forme.agents.R;
import com.forme.agents.View.Activites.StockActivity;
import com.forme.agents.View.Activites.SubGroup1Activity;
import com.forme.agents.View.Activites.SubGroup2Activity;

import java.util.ArrayList;
import java.util.HashMap;

public class SubGroup2Adapter extends RecyclerView.Adapter<SubGroup2Adapter.ViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    private ViewHolder holder;
    private RecyclerView recyclerView;
    private ArrayList<HashMap> list ;
    private ArrayList<HashMap> previous ;
    private String previousnamegroup;
    private String nameStock;
    Fragment fragment = null;
    int lastPosition=-1;

    public SubGroup2Adapter(Context context, ArrayList<HashMap> list, String nameStock,String previousnamegroup,ArrayList<HashMap> previous, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
        this.nameStock = nameStock;
        this.previous = previous;
        this.previousnamegroup = previousnamegroup;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.subgroup_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap subView = list.get(position);
            if(!subView.get("ID").toString().isEmpty()){
                holder.textLabel.setText(subView.get("Name").toString());
                holder.bind( subView,list.get(position).get("Name").toString(),list, listener);

            }
            else if (subView.get("ID").toString().isEmpty()) {
                Intent intent = new Intent(context, StockActivity.class);
                ArrayList<HashMap> mapview = (ArrayList<HashMap>) subView.get("item");
                ArrayList<Object> objects = new ArrayList<>();
                for(int i=0 ; i<mapview.size();i++){
                    Stock sb = new Stock();
                    sb.setPrice(mapview.get(i).get("Prices").toString());
                    sb.setId(mapview.get(i).get("Stock_Id").toString());
                    sb.setName(mapview.get(i).get("Stock_Name").toString());
                    sb.setScreenNumber(mapview.get(i).get("ScreenNumber").toString());
                    objects.add(sb);
                }
                intent.putExtra("name", nameStock);
                intent.putExtra("stockView", objects);
                intent.putExtra("backup",previous);
                intent.putExtra("backupname",previousnamegroup);
                intent.putExtra("previous list",list);
                intent.putExtra("previous name",nameStock);
                setAnimation(holder.itemView, position);
                holder.bind(list.get(position),nameStock,list, listener);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                    // System.out.println("itemmm"+subView.getBytes(subView.getSubgroup()));
                        Intent intent = new Intent(context, StockActivity.class);
                        intent.putExtra("name", name);
                        ArrayList<HashMap> mapview = (ArrayList<HashMap>) subView.get("item");
                        ArrayList<Object> objects = new ArrayList<>();
                        for(int i=0 ; i<mapview.size();i++){
                            Stock sb = new Stock();
                            sb.setPrice(mapview.get(i).get("Prices").toString());
                            sb.setId(mapview.get(i).get("Stock_Id").toString());
                            sb.setName(mapview.get(i).get("Stock_Name").toString());
                            sb.setScreenNumber(mapview.get(i).get("ScreenNumber").toString());
                            objects.add(sb);
                        }
                          intent.putExtra("stockView", objects);
                          intent.putExtra("backup",previous);
                          intent.putExtra("backupname",previousnamegroup);
                          intent.putExtra("previous list",list);
                          intent.putExtra("previous name",nameStock);
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
