package com.forme.agents.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.forme.agents.DTO.CheckNewUser;
import com.forme.agents.DTO.DeviceUUID;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.MenuResponse;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.CheckNewUser_Adapter;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.Helper.ShowUser_Adapter;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;
import com.forme.agents.View.Activites.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class ShowusersFragment extends Fragment implements ScreenShotable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LoginResponse loginResponse;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    PreferenceHelper preferenceHelper;
    private ArrayList<CheckNewUser> userlist;
    SwipeRefreshLayout swiperefresh;
    private View containerView;
    private Bitmap bitmap;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private static final String TAG = "Error";



    public ShowusersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    // TODO: Rename and change types and number of parameters
    public static ShowusersFragment newInstance(String mParam1, String mParam2) {
        ShowusersFragment fragment = new ShowusersFragment();
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
                ShowusersFragment.this.bitmap = bitmap;
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
        View view = inflater.inflate(R.layout.fragment_showusers, container, false);
        preferenceHelper = new PreferenceHelper(getContext());
        System.out.println("dddddd");
        recyclerView = (RecyclerView) view.findViewById(R.id.userslist);
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
        loadStock();
        // Inflate the layout for this fragment
        return view;
    }

    private void loadStock() {

        userlist = new ArrayList<>();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<ArrayList<Object>> StockCall = api.ShowUsers("Bearer "+loginResponse.token,new MenuResponse(loginResponse.renewaltoken,DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(getContext()),loginResponse.transid) );
        StockCall.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                if(response.code()==200) {
                    ArrayList<Object> jsonArray = null;
                    try {
                        jsonArray = response.body();
                        System.out.println("RESPONSE: " + response.body().toString());
                        for (int i = 0; i < jsonArray.size(); i++) {
                            CheckNewUser user = new CheckNewUser();
                            LinkedTreeMap user1 =  (LinkedTreeMap) jsonArray.get(i);
                            user.setFullname(user1.get("fullname").toString());
                            user.setStorename(user1.get("StoreName").toString());
                            user.setMobile(user1.get("Mobile").toString());
                            user.setDeviceIdUser(null);
                            user.setUUIDUser(user1.get("UUIDUser").toString());
                            userlist.add(user);
                        }
                        adapter = new ShowUser_Adapter(getActivity().getApplicationContext(), userlist,null);
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
