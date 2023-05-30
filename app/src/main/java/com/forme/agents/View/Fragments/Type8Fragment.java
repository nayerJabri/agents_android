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

import com.forme.agents.DTO.DeviceUUID;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.MySoldeResponse;
import com.forme.agents.DTO.PriceResponse;
import com.forme.agents.DTO.RasidResponse;
import com.forme.agents.DTO.SellProductsResponse;
import com.forme.agents.DTO.Stock;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;
import com.forme.agents.View.Activites.LoginActivity;
import com.forme.agents.View.Activites.MainActivity;
import com.google.gson.Gson;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Type8Fragment extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int RESULT_PICK_CONTACT =1;
    private int STORAGE_PERMISSION_CODE = 1;
    private TextView phone,title;
    private EditText ijamli;
    PreferenceHelper preferenceHelper;
    private Stock stock;
    private static final String TAG = "Error";
    LoginResponse loginResponse;
    String jsonArray = null;
    String jsonArray1 = null;
    String checksd = null;
    String backup,slaf;
    private Button select,fahs,done;
    String[] reponses = { "سداد سلفني ",  "نعم" , "لا "};
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type8, container, false);
        Intent i = getActivity().getIntent();
        ijamli = view.findViewById(R.id.ijamli);
        stock = (Stock) i.getSerializableExtra("stock");
        phone = view.findViewById (R.id.phone);
        done = view.findViewById (R.id.done);
        title = view.findViewById(R.id.title);
        select = view.findViewById (R.id.select);
        fahs = view.findViewById(R.id.fahs);
        title.setText(stock.getName());
        Spinner spin = (Spinner) view.findViewById(R.id.salafni);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, reponses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        preferenceHelper = new PreferenceHelper(getContext());
        loadLoginResponse();
        loadPrice();
        checksolde();
        ijamli.setText(jsonArray);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("نعم")) {
                    if(jsonArray1 == null) {
                        Retrofit restAdapter = new Retrofit.Builder()
                                .baseUrl(Constants.BASE_URL)
                                // .client(okHttpClientBuilder.build())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Api api = restAdapter.create(Api.class);
                        String phon = phone.getText().toString().replace(" ","");

                        Call<String> rasid = api.salaf("Bearer " + loginResponse.token, new RasidResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice, DeviceUUID.devicetype, DeviceUUID.getDeviceId(getContext()), phon,loginResponse.transid));
                        rasid.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() == 200) {

                                    try {
                                        jsonArray1 = response.body();
                                        Double pricee = Double.valueOf(ijamli.getText().toString()) + Double.valueOf(String.valueOf(jsonArray1));
                                        ijamli.setText(pricee.toString());
                                        //  System.out.println("RESPONSE: " + response.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e(TAG, t.getMessage());
                                Tools.showMessage(getContext(), "الرجاء وضع رقم الهاتف  ");
                                spin.setSelection(0);
                            }

                        });

                    }
                    else{
                        Double pricee = Double.valueOf(ijamli.getText().toString()) + Double.valueOf(String.valueOf(jsonArray1));
                        ijamli.setText(pricee.toString());
                    }
                }else{

                    ijamli.setText(jsonArray);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        select.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Intent in = new Intent (Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult (in, RESULT_PICK_CONTACT);
                } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[] {Manifest.permission.READ_CONTACTS}, STORAGE_PERMISSION_CODE);
                }

            }
        });
        fahs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().isEmpty())
                    phone.setError("* required");
                else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("هل تريد المتابعة  : ");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "متابعة  ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (jsonArray1 == null) {
                                        Retrofit restAdapter = new Retrofit.Builder()
                                                .baseUrl(Constants.BASE_URL)
                                                // .client(okHttpClientBuilder.build())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        Api api = restAdapter.create(Api.class);
                                        String phon = phone.getText().toString().replace(" ","");
                                        Call<String> rasid = api.salaf("Bearer " + loginResponse.token, new RasidResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice, DeviceUUID.devicetype, DeviceUUID.getDeviceId(getContext()), phon,loginResponse.transid));
                                        rasid.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if (response.code() == 200) {

                                                    try {
                                                        jsonArray1 = response.body();AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                                        builder1.setMessage("السلفة  : " + jsonArray1);
                                                        builder1.setCancelable(true);

                                                        builder1.setPositiveButton(
                                                                "موافق ",
                                                                new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                        dialog.cancel();
                                                                    }
                                                                });

                                                        AlertDialog alert11 = builder1.create();
                                                        alert11.show();

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                Log.e(TAG, t.getMessage());
                                                Tools.showMessage(getContext(), "لا يوجد إتصال بالسرفر ");
                                            }

                                        });
                                    }

                                }
                            });
                    builder1.setNegativeButton(
                            "لا ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.valueOf(checksd) >= Double.valueOf(ijamli.getText().toString())){
                    Retrofit restAdapter = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            // .client(okHttpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Api api = restAdapter.create(Api.class);
                    Call<String> sell = api.sellProducts("Bearer " + loginResponse.token, new SellProductsResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice, DeviceUUID.devicetype, DeviceUUID.getDeviceId(getContext()), stock.getId(),"0",jsonArray,jsonArray1,"1",phone.getText().toString(),loginResponse.transid));
                    sell.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 200) {
                                String jsonArray = null;
                                try {
                                    jsonArray = response.body();
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                    builder1.setMessage(jsonArray);
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "حسنا  ",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    preferenceHelper.saveLoginData(loginResponse);
                                                    MainActivity.start(getActivity(), true);
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e(TAG, t.getMessage());
                            Tools.showMessage(getContext(), "يوجد اختلاف بالسعر");
                        }

                    });
                }

                else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("رصيدك غير كافي ، الرجاء وضع سعر إجمالي مناسب لرصيدك ");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "حسنا  ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void checksolde(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<String> rasid = api.checksolde("Bearer " + loginResponse.token, new MySoldeResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice, DeviceUUID.devicetype, DeviceUUID.getDeviceId(getContext()),"1",loginResponse.transid));
        rasid.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {
                        checksd = response.body();
                        //  System.out.println("RESPONSE: " + response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Tools.showMessage(getContext(), "لا يوجد إتصال بالسرفر ");
            }

        });
    }
    void salaf(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        String phon = phone.getText().toString().replace(" ","");
        Call<String> rasid = api.salaf("Bearer " + loginResponse.token, new RasidResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice, DeviceUUID.devicetype, DeviceUUID.getDeviceId(getContext()), phon,loginResponse.transid));
        rasid.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {
                        jsonArray1 = response.body();
                        //  System.out.println("RESPONSE: " + response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Tools.showMessage(getContext(), "لا يوجد إتصال بالسرفر ");
            }

        });
    }
    void loadPrice(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<String> rasid = api.price("Bearer " + loginResponse.token, new PriceResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice, DeviceUUID.devicetype, DeviceUUID.getDeviceId(getContext()), stock.getId(),"1",loginResponse.transid));
        rasid.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    try {
                        jsonArray = response.body();
                        ijamli.setText(jsonArray);
                        //  System.out.println("RESPONSE: " + response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Tools.showMessage(getContext(), "لا يوجد إتصال بالسرفر ");
            }

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if(resultCode==getActivity().RESULT_OK)
        {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked (data);
                    break;
            }
        }
        else
        {
            Toast.makeText (getContext(), "Failed To pick contact", Toast.LENGTH_SHORT).show ();
        }
    }
    private void contactPicked(Intent data) {
        Cursor cursor = null;

        try {
            String phoneNo = null;
            Uri uri = data.getData ();
            cursor = getActivity().getContentResolver ().query (uri, null, null,null,null);
            cursor.moveToFirst ();
            int phoneIndex = cursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER);

            phoneNo = cursor.getString (phoneIndex);
            String ph = phoneNo.replace(" ","");
            phone.setText (ph);


        } catch (Exception e) {
            e.printStackTrace ();
        }
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
