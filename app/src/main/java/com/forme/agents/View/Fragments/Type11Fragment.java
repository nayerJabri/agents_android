package com.forme.agents.View.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forme.agents.DTO.Currencie;
import com.forme.agents.DTO.DeviceUUID;
import com.forme.agents.DTO.Exchange;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.MenuResponse;
import com.forme.agents.DTO.Stock;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.Helper.SimpleListAdapter;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;
import com.forme.agents.View.Activites.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Type11Fragment extends Fragment implements Serializable, DatePickerDialog.OnDateSetListener  {

    private static final int RESULT_PICK_CONTACT =1;
    private int STORAGE_PERMISSION_CODE = 1;
    PreferenceHelper preferenceHelper;
    private Stock stock;
    private static final String TAG = "Error";
    LoginResponse loginResponse;
    String jsonArray = null;
    String jsonArray1 = null;
    String checksd = null;
    String imageName = "";

    List<Currencie> currencies = new ArrayList<>();
    ArrayList<String> reponsesCurr = new ArrayList<>();
    private SearchableSpinner CurrenciesSearchableSpinner;
    private SimpleListAdapter CurrenciesSimpleListAdapter;

    List<Exchange> exchanges = new ArrayList<>();
    ArrayList<String> reponsesExch = new ArrayList<>();
    private SearchableSpinner ExachangesSearchableSpinner;
    private SimpleListAdapter ExachangesSimpleListAdapter;
    private String fileUri ;
    private Button done,camera,gallery,scan,scanCin,callender,callenderRea;
    private TextView title;
    private EditText price,otherNote,fullName,numSim,adresse,lieuNaiss,numCin,dateNaiss,dateRea,imageNameText;

    //camera
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type10, container, false);
        Intent i = getActivity().getIntent();

        stock = (Stock) i.getSerializableExtra("stock");
        title = view.findViewById(R.id.title);
        done = (Button) view.findViewById(R.id.done);
        gallery = (Button) view.findViewById(R.id.gallery);
        scan = (Button) view.findViewById(R.id.scan);
        scanCin = (Button) view.findViewById(R.id.scanCin);
        callender = (Button) view.findViewById(R.id.callender);
        callenderRea = (Button) view.findViewById(R.id.callenderRea);
        camera = (Button) view.findViewById(R.id.camera);
        price  = (EditText) view.findViewById(R.id.price);
        otherNote = (EditText) view.findViewById(R.id.otherNote);
        fullName = (EditText) view.findViewById(R.id.fullName);
        numCin = (EditText) view.findViewById(R.id.numCin);
        numSim = (EditText) view.findViewById(R.id.numSim);
        dateNaiss = (EditText) view.findViewById(R.id.dateNaiss);
        lieuNaiss = (EditText) view.findViewById(R.id.lieuNaiss);
        adresse = (EditText) view.findViewById(R.id.adresse);
        dateRea = (EditText) view.findViewById(R.id.dateRea);

        preferenceHelper = new PreferenceHelper(getContext());
        loadLoginResponse();

        title.setText(stock.getName());

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateNaiss.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
            }
        }, 1920, 1, 1);

        dateNaiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if ( name.getText().toString().split(" ").length < 3){
                    name.setError("يجب أن يتكون من 3 أجزاء");
                }
                else if(numCharge.getText().toString().isEmpty())
                    numCharge.setError("مطلوب *");
                else if(mablagh.getText().toString().isEmpty())
                    mablagh.setError("مطلوب *");
                else if (morsalname.getText().toString().split(" ").length < 3)
                    morsalname.setError("يجب أن يتكون من 3 أجزاء");
                else if ( morsalphone.getText().toString().isEmpty())
                    morsalphone.setError("مطلوب *");
                else if (phone.getText().toString().isEmpty())
                    phone.setError("مطلوب *");
                else if (imageName.isEmpty())
                    camera.setError("مطلوب *");
                else{
                    Tools.showMessage(getContext(), "ok " + name.getText().toString().split(" ").length);
                }*/
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
