package com.forme.agents.View.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.forme.agents.DTO.Currencie;
import com.forme.agents.DTO.DeviceUUID;
import com.forme.agents.DTO.Governorate;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.MenuResponse;
import com.forme.agents.DTO.MySoldeResponse;
import com.forme.agents.DTO.PriceResponse;
import com.forme.agents.DTO.RasidResponse;
import com.forme.agents.DTO.SellProductsResponse;
import com.forme.agents.DTO.Stock;
import com.forme.agents.DTO.SubView;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.CommissionResponse;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.Helper.SimpleListAdapter;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;
import com.forme.agents.View.Activites.LoginActivity;
import com.forme.agents.View.Activites.MainActivity;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class Type9Fragment extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int RESULT_PICK_CONTACT =1;
    private int STORAGE_PERMISSION_CODE = 1;
    private TextView title;
    private EditText place,mablagh,name,phone,morsalname,morsalphone;
    private TextView currencyPrice,totalCurrency,total ;
    PreferenceHelper preferenceHelper;
    private Stock stock;
    private static final String TAG = "Error";
    LoginResponse loginResponse;
    String jsonArray = null;
    String jsonArray1 = null;
    String checksd = null;
    String backup,slaf;
    private Button select,fahs,done;

    List<Currencie> currencies = new ArrayList<>();
    List<Governorate> governorates = new ArrayList<>();

    ArrayList<String> reponsesGover = new ArrayList<>();
    ArrayList<String> reponsesCurr = new ArrayList<>();

    private Double commissionP = 0.0 ;
    private Double priceP = 0.0 ;
    Currencie curr = null;

    private boolean currencyId = true ;
    private boolean mablaghTest = true ;

    private SearchableSpinner CurrenciesSearchableSpinner;
    private SearchableSpinner GovernoratesSearchableSpinner;
    private SimpleListAdapter CurrenciesSimpleListAdapter;
    private SimpleListAdapter GovernoratesSimpleListAdapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type9, container, false);
        Intent i = getActivity().getIntent();
        place  = (EditText) view.findViewById(R.id.place);
        currencyPrice = (TextView) view.findViewById(R.id.currencyPrice);
        totalCurrency = (TextView) view.findViewById(R.id.totalCurrency);
        total = (TextView) view.findViewById(R.id.total);
        mablagh  = (EditText) view.findViewById(R.id.mablagh);
        name = (EditText) view.findViewById(R.id.name);
        title = view.findViewById(R.id.title);
        stock = (Stock) i.getSerializableExtra("stock");
        phone = (EditText) view.findViewById(R.id.phone);
        morsalname = (EditText) view.findViewById(R.id.morsalname);
        morsalphone = (EditText) view.findViewById(R.id.morsalphone);
        done = (Button) view.findViewById(R.id.done);
        preferenceHelper = new PreferenceHelper(getContext());
        loadLoginResponse();
        loadCurrencies();
        loadGovernorates();

        CurrenciesSimpleListAdapter = new SimpleListAdapter(getContext(), reponsesCurr);
        GovernoratesSimpleListAdapter = new SimpleListAdapter(getContext(), reponsesGover);

        CurrenciesSearchableSpinner = (SearchableSpinner) view.findViewById(R.id.Currencies);
        CurrenciesSearchableSpinner.setAdapter(CurrenciesSimpleListAdapter);
        CurrenciesSearchableSpinner.setOnItemSelectedListener(mOnItemSelectedListener);
        CurrenciesSearchableSpinner.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {
            }

            @Override
            public void spinnerIsClosing() {

            }
        });

        GovernoratesSearchableSpinner = (SearchableSpinner) view.findViewById(R.id.Governorates);
        GovernoratesSearchableSpinner.setAdapter(GovernoratesSimpleListAdapter);
        GovernoratesSearchableSpinner.setOnItemSelectedListener(mOnItemSelectedListener1);
        GovernoratesSearchableSpinner.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {
            }

            @Override
            public void spinnerIsClosing() {

            }
        });

        title.setText(stock.getName());

        mablagh.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    mablaghTest = false ;
                    mablagh.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    priceP = Double.parseDouble(mablagh.getText().toString());}
                else{
                    mablaghTest = true ;
                    mablagh.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    priceP = 0.0 ;
                }
                if(curr != null )
                    loadCommission();
                if(!currencyId) {

                    totalCurrency.setText("الإجمالي " + (commissionP.intValue() + priceP.intValue()) + ".00");
                    total.setText(totalCurrency.getText().toString());

                }
                else{
                    totalCurrency.setText("الإجمالي " + (commissionP.intValue()) + ".00");
                    total.setText("الإجمالي " + priceP.intValue() + ".00");
                }

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( name.getText().toString().split(" ").length < 4){
                    name.setError("يجب أن يتكون من 4 أجزاء");
                }
                else if(place.getText().toString().isEmpty())
                    place.setError("مطلوب *");
                else if(currencyPrice.getText().toString().isEmpty())
                    place.setError("مطلوب *");
                else if (morsalname.getText().toString().split(" ").length < 3)
                    morsalname.setError("يجب أن يتكون من 3 أجزاء");
                else if ( morsalphone.getText().toString().isEmpty())
                    morsalphone.setError("مطلوب *");
                else if (phone.getText().toString().isEmpty())
                    phone.setError("مطلوب *");
                else{
                    Tools.showMessage(getContext(), "ok " + name.getText().toString().split(" ").length);

                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private OnItemSelectedListener mOnItemSelectedListener1 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };


    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            Double idCurrD = Double.parseDouble(currencies.get(position - 1).getCurrency_Id()) ;
            curr = new Currencie(String.valueOf(idCurrD.intValue()),currencies.get(position - 1).getCurrency_Name()) ;

            if(!mablaghTest)
                loadCommission();
        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    void loadLoginResponse() {
        String loginData = preferenceHelper.getLoginData();
        try {
            loginResponse = new Gson().fromJson(loginData, LoginResponse.class);

        } catch (Exception ex) {
            LoginActivity.start(getContext(), true);
        }
    }

    void loadCommission(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<ArrayList<Object>> Commissions = api.Comm("Bearer "+loginResponse.token,new CommissionResponse(loginResponse.renewaltoken,DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(getContext()),loginResponse.transid,stock.getId(),String.valueOf(priceP.intValue()),curr.getCurrency_Id())) ;
        Commissions.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                if (response.code() == 200) {

                    try {
                        currencyId = true ;
                        ArrayList<Object> jsonArray = null;
                        jsonArray = response.body();
                        LinkedTreeMap commission =  (LinkedTreeMap) jsonArray.get(0);
                        Double idCurr1 = Double.parseDouble(commission.get("Currency_Id").toString()) ;
                        if(Integer.parseInt(curr.getCurrency_Id()) == idCurr1.intValue() )
                            currencyId = false ;
                        commissionP = Double.parseDouble(commission.get("commission").toString());
                        currencyPrice.setText(" العمولة " + commission.get("commission").toString() + "0 " +commission.get("Currency_Name")  );

                        totalCurrency.setText(" الإجمالي " + commissionP.intValue()  + ".00 " + commission.get("Currency_Name"));
                        total.setText( " الإجمالي " + (priceP.intValue()) + ".00 " + curr.getCurrency_Name());
                        if(!currencyId) {
                            totalCurrency.setText("الإجمالي " + (commissionP.intValue() + priceP.intValue()) + ".00 " + commission.get("Currency_Name"));

                            total.getLayoutParams().height = 0;
                            total.setVisibility(View.INVISIBLE);

                        }
                        else {
                            total.setVisibility(View.VISIBLE);
                            total.getLayoutParams().height = 70;
                        }
                        total.requestLayout();



                        //  System.out.println("RESPONSE: " + response.toString());
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage() , Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Tools.showMessage(getContext(), "لا يوجد إتصال بالسرفر ");
            }

        });
    }

    void loadCurrencies(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<ArrayList<Object>> Currencies = api.Curr("Bearer "+loginResponse.token,new MenuResponse(loginResponse.renewaltoken,DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(getContext()),loginResponse.transid) );
        Currencies.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                if (response.code() == 200) {

                    try {
                        ArrayList<Object> jsonArray = null;
                        jsonArray = response.body();

                        for (int i = 0; i < jsonArray.size(); i++) {
                            Currencie currencie = new Currencie();
                            LinkedTreeMap currencie1 =  (LinkedTreeMap) jsonArray.get(i);
                            currencie.setCurrency_Id(currencie1.get("Currency_Id").toString());
                            currencie.setCurrency_Name(currencie1.get("Currency_Name").toString());
                            currencies.add(currencie);
                            reponsesCurr.add(currencie.getCurrency_Name());
                        }

                        //  System.out.println("RESPONSE: " + response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Tools.showMessage(getContext(), "لا يوجد إتصال بالسرفر ");
            }

        });
    }

    void loadGovernorates(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<ArrayList<Object>> Governorates = api.Gover("Bearer "+loginResponse.token,new MenuResponse(loginResponse.renewaltoken,DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(getContext()),loginResponse.transid) );
        Governorates.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                if (response.code() == 200) {

                    try {
                        ArrayList<Object> jsonArray = null;
                        jsonArray = response.body();

                        for (int i = 0; i < jsonArray.size(); i++) {
                            Governorate governorate= new Governorate();
                            LinkedTreeMap governorate1 =  (LinkedTreeMap) jsonArray.get(i);
                            governorate.setId(governorate1.get("Id").toString());
                            governorate.setName(governorate1.get("Name").toString());
                            governorates.add(governorate);
                            reponsesGover.add(governorate.getName());
                        }

                        //  System.out.println("RESPONSE: " + response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Tools.showMessage(getContext(), "لا يوجد إتصال بالسرفر ");
            }

        });
    }

}