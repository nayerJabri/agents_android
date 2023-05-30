package com.forme.agents.View.Activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.forme.agents.DTO.Stock;
import com.forme.agents.Helper.StockAdapter;
import com.forme.agents.Helper.SubGroup1Adapter;
import com.forme.agents.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class StockActivity extends AppCompatActivity {

    @BindView(R.id.namegp)
    TextView name;
    @BindView(R.id.toolbar1)
    Toolbar toolbar;
    private String namegroup;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    ArrayList<HashMap> subviewPrevious;
    ArrayList<HashMap> backup;
    ArrayList<HashMap> stockgroup1;
    String backupname;
    String previousnamegroup;
    ArrayList<Stock> stockview1;
    private SwipeRefreshLayout swiperefresh;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.Adapter adapter;
    public static void start(Context context) {
        context.startActivity(new Intent(context, StockActivity.class));
    }

    public static void start(Context context, boolean clear) {
        Intent intent = new Intent(context, StockActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgroup1);
        name = findViewById(R.id.namegp);
        recyclerView = findViewById(R.id.subgroup2);
        recyclerView.setHasFixedSize(true);
        stockgroup1 = new ArrayList<>();
        subviewPrevious = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1 = findViewById(R.id.stockgroup);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(layoutManager1);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(StockActivity.this, true);
            }
        });
        namegroup = (String) getIntent().getSerializableExtra("name");
        name.setText(namegroup);
        subviewPrevious = (ArrayList<HashMap>) getIntent().getSerializableExtra("previous list");
        backup = (ArrayList<HashMap>) getIntent().getSerializableExtra("backup");
        backupname = (String) getIntent().getSerializableExtra("backupname");
        previousnamegroup = (String) getIntent().getSerializableExtra("previous name");
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.container1);
        swiperefresh.setColorSchemeColors(Color.BLUE);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swiperefresh.setRefreshing(false);
                        loadData();
                    }
                },4000);
            }
        });

            loadData();
        }
        @Override
         public void onBackPressed(){
            if( !subviewPrevious.get(0).get("ID").toString().equals("")) {
                Intent intent = new Intent(StockActivity.this, SubGroup2Activity.class);
                intent.putExtra("name", previousnamegroup);
                intent.putExtra("subView", subviewPrevious);
                intent.putExtra("previous list", backup);
                intent.putExtra("previous name", backupname);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(StockActivity.this, SubGroup1Activity.class);
                intent.putExtra("subView", backup);
                intent.putExtra("name", backupname);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

    }
        public void loadData()
        {
            stockview1 = (ArrayList<Stock>) getIntent().getSerializableExtra("stockView");
            ArrayList <Stock> sb = new ArrayList<>();
                for(int i = 0; i <stockview1.size() ; i++){
                    Stock stock = new Stock();
                    stock.setId(stockview1.get(i).getId());
                    stock.setName(stockview1.get(i).getName());
                    stock.setPrice(stockview1.get(i).getPrice());
                    stock.setScreenNumber(stockview1.get(i).getScreenNumber());
                    sb.add(stock);
                }
                adapter = new StockAdapter(StockActivity.this, sb, null);
                adapter.notifyDataSetChanged();
                recyclerView1.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }

        //




