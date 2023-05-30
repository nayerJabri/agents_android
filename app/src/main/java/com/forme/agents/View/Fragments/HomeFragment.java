package com.forme.agents.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.forme.agents.DTO.DeviceUUID;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.MenuResponse;
import com.forme.agents.DTO.SubView;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.HomeAdapter;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;
import com.forme.agents.View.Activites.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class HomeFragment extends Fragment implements ScreenShotable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LoginResponse loginResponse;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    PreferenceHelper preferenceHelper;
    private ArrayList<SubView> stocklist;
    SwipeRefreshLayout swiperefresh;
    private View containerView;
    private Bitmap bitmap;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private static final String TAG = "Error";



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String mParam1,String mParam2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mParam1);
        args.putString(ARG_PARAM2, mParam2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                HomeFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        preferenceHelper = new PreferenceHelper(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.stockList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.container);
        swiperefresh.setColorSchemeColors(Color.BLUE);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swiperefresh.setRefreshing(false);
                        loadStock();
                    }
                },6000);
            }
        });
        loadLoginResponse();
        SharedPreferences preferences = getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<SubView>>() {}.getType();
        stocklist = gson.fromJson(json,type);
        if(stocklist != null) {
            adapter = new HomeAdapter(getActivity(), stocklist, null);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else
        loadStock();
        // Inflate the layout for this fragment
        return view;
    }

    private void loadStock() {

        stocklist = new ArrayList<>();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<ArrayList<Object>> StockCall = api.listofstock("Bearer "+loginResponse.token,new MenuResponse(loginResponse.renewaltoken,DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(getContext()),loginResponse.transid) );
        StockCall.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                if(response.code()==200) {
                    ArrayList<Object> jsonArray = null;
                    try {
                        jsonArray = response.body();

                          System.out.println("RESPONSE: " + response.toString());
                        for (int i = 0; i < jsonArray.size(); i++) {
                                SubView stock = new SubView();
                                LinkedTreeMap stock1 =  (LinkedTreeMap) jsonArray.get(i);
                                  stock.setId(stock1.get("ID").toString());
                                  stock.setName(stock1.get("Name").toString());
                                  stock.setSubgroup(stock1.get("SubGroup1"));
                                  stocklist.add(stock);
                            }
                        SharedPreferences preferences = getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(stocklist);
                        editor.putString("task list",json);
                        editor.apply();
                        adapter = new HomeAdapter(getActivity(), stocklist,null);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
                Log.e(TAG , t.getMessage());
                Tools.showMessage(getContext(),"لا يوجد إتصال بالسرفر ");
            }

        });
    }

    void loadLoginResponse() {
        String loginData = preferenceHelper.getLoginData();
        try {
            loginResponse = new Gson().fromJson(loginData, LoginResponse.class);

        } catch (Exception ex) {
            LoginActivity.start(getContext(), true);
        }
    }





}
