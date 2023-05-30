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
import com.forme.agents.Helper.SubGroup2Adapter;
import com.forme.agents.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class SubGroup2Activity extends AppCompatActivity {

    @BindView(R.id.namegp)
    TextView name;
    @BindView(R.id.toolbar1)
    Toolbar toolbar;
    private String namegroup;
    private String previousnamegroup;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    ArrayList<HashMap> subviewPrevious;
    ArrayList<HashMap> stockgroup1;
    ArrayList<Stock> stockview1;
    private SwipeRefreshLayout swiperefresh;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.Adapter adapter;
    public static void start(Context context) {
        context.startActivity(new Intent(context, SubGroup2Activity.class));
    }

    public static void start(Context context, boolean clear) {
        Intent intent = new Intent(context, SubGroup2Activity.class);
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
                MainActivity.start(SubGroup2Activity.this, true);
            }
        });
        namegroup = (String) getIntent().getSerializableExtra("name");
        name.setText(namegroup);
        stockgroup1 = (ArrayList<HashMap>) getIntent().getSerializableExtra("subView");
        subviewPrevious = (ArrayList<HashMap>) getIntent().getSerializableExtra("previous list");
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
            MainActivity.start(SubGroup2Activity.this,true);

         }
        public void loadData()
        {

                adapter = new SubGroup2Adapter(SubGroup2Activity.this, stockgroup1, namegroup,previousnamegroup,subviewPrevious, null);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                recyclerView1.setAdapter(null);
                adapter.notifyDataSetChanged();

        }
    }
        //




