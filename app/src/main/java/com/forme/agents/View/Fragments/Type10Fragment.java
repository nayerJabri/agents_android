package com.forme.agents.View.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
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
import com.joanzapata.iconify.IconDrawable;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Type10Fragment extends Fragment implements Serializable {
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
    private Button done,camera;
    private TextView title;
    private EditText numCharge,mablagh,name,phone,morsalname,morsalphone;

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
        camera = (Button) view.findViewById(R.id.camera);
        mablagh  = (EditText) view.findViewById(R.id.mablagh);
        numCharge  = (EditText) view.findViewById(R.id.NumCharge);
        name = (EditText) view.findViewById(R.id.name);
        phone = (EditText) view.findViewById(R.id.phone);
        morsalname = (EditText) view.findViewById(R.id.morsalname);
        morsalphone = (EditText) view.findViewById(R.id.morsalphone);
        imageview = (ImageView) view.findViewById(R.id.imageView);

        preferenceHelper = new PreferenceHelper(getContext());
        loadLoginResponse();
        loadCurrencies();
        loadExchanges();

        title.setText(stock.getName());

        CurrenciesSimpleListAdapter = new SimpleListAdapter(getContext(), reponsesCurr);
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

        ExachangesSimpleListAdapter = new SimpleListAdapter(getContext(), reponsesExch);
        ExachangesSearchableSpinner = (SearchableSpinner) view.findViewById(R.id.Exchanges);
        ExachangesSearchableSpinner.setAdapter(ExachangesSimpleListAdapter);
        ExachangesSearchableSpinner.setOnItemSelectedListener(mOnItemSelectedListenerExchanges);
        ExachangesSearchableSpinner.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {
            }

            @Override
            public void spinnerIsClosing() {

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( name.getText().toString().split(" ").length < 3){
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
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    //camera methodes
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("اختر فعلا");
        String[] pictureDialogItems = {
                "إلغاء",
                "التقاط صورة من الكاميرا" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        try {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageName);
            startActivityForResult(cameraIntent, CAMERA);
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            imageName = encodeImage(thumbnail);
            //saveImage(thumbnail);
            Toast.makeText(getContext(), "تم تحميل الصورة!", Toast.LENGTH_SHORT).show();
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemSelectedListener mOnItemSelectedListenerExchanges = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };


    void loadCurrencies(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<ArrayList<Object>> Currencies = api.Curr("Bearer "+loginResponse.token,new MenuResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(getContext()),loginResponse.transid) );
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

    void loadExchanges(){
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<ArrayList<Object>> Exchanges = api.ExchangeName("Bearer "+loginResponse.token,new MenuResponse(loginResponse.renewaltoken, DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(getContext()),loginResponse.transid) );
        Exchanges.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                if (response.code() == 200) {

                    try {
                        ArrayList<Object> jsonArray = null;
                        jsonArray = response.body();

                        for (int i = 0; i < jsonArray.size(); i++) {
                            Exchange exchange = new Exchange();
                            LinkedTreeMap exchange1 =  (LinkedTreeMap) jsonArray.get(i);
                            exchange.setId(exchange1.get("Id").toString());
                            exchange.setName(exchange1.get("Name").toString());
                            exchanges.add(exchange);
                            reponsesExch.add(exchange.getName());
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
}
