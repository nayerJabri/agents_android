package com.forme.agents.View.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.forme.agents.DTO.DeviceUUID;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.Stock;
import com.forme.agents.Helper.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.forme.agents.DTO.RegistrationModel;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    PreferenceHelper preferenceHelper;
    LoginResponse loginResponse;
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    public static void start(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.etUsername)
    TextInputEditText etUsername;

    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    @BindView(R.id.etfullname)
    TextInputEditText etfullname;



    @BindView(R.id.etEmail)
    TextInputEditText etEmail;

    @BindView(R.id.etMobile)
    TextInputEditText etMobile;

    @BindView(R.id.etStoreName)
    TextInputEditText etStoreName;

    @BindView(R.id.btnRegister)
    Button btnRegister;




    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loadLoginResponse();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvLogin})
    void login()
    {
        LoginActivity.start(this,true);
    }

    @OnClick(R.id.btnRegister)
    void btnRegisterClicked()
    {
        if(validInput())
        {
            registerUser();
        }
    }
    RegistrationModel registrationModel;
    private boolean validInput() {
        boolean valid = true;
        registrationModel = new RegistrationModel(Tools.getString(etUsername),Tools.getString(etPassword),Tools.getString(etfullname),Tools.getString(etEmail),Tools.getString(etMobile),Tools.getString(etStoreName), DeviceUUID.UUIDDevice, DeviceUUID.devicetype , DeviceUUID.getDeviceId(this),loginResponse.transid);

        if(registrationModel.username.isEmpty())
        {
            etUsername.setError("* ادخل اسم المستخدم");
            valid = false;
        }

        if(registrationModel.password.isEmpty())
        {
            etPassword.setError("* ادخل كلمة المرور");
            valid = false;
        }

        if(registrationModel.fullname.isEmpty())
        {
            etfullname.setError("* ادخل الاسم الكامل");
            valid = false;
        }

        if(registrationModel.email.isEmpty())
        {
            etEmail.setError("* ادخل الايميل");
            valid = false;
        }
        else
        if(!Tools.isValidEmail(registrationModel.email))
        {
            etEmail.setError("* هذا ليس ايميل");
            valid = false;
        }


        if(registrationModel.mobile.isEmpty())
        {
            etMobile.setError("* ادخل رقم الهاتف");
            valid = false;
        }


        if(registrationModel.storename.isEmpty())
        {
            etStoreName.setError("* ادخل اسم المحل");
            valid = false;
        }

        return valid;
    }
    private void registerUser() {

        showProgress();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<String> registrationCall = api.register(registrationModel);
        registrationCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG,response.toString());

                /*
                if(response.code()==409)
                {
                   Tools.showMessage(RegistrationActivity.this,"Username already registered");
                    //Tools.showMessage(RegistrationActivity.this,response.errorBody().toString());
                }
                if(response.code()==200)
                {
                   // Tools.showMessage(RegistrationActivity.this,response.body());
                   Tools.showMessage(RegistrationActivity.this,"Success");
                    LoginActivity.start(RegistrationActivity.this,true);
                }
                hideProgress();
                */



                if (response.isSuccessful()) {
                    try {
                       // Tools.showMessage(RegistrationActivity.this,"Success");
                        String successResponse = response.body();
                        Tools.showMessage(RegistrationActivity.this, successResponse);


                        LoginActivity.start(RegistrationActivity.this,true);
                    } catch (Exception ex) {
                        Tools.showMessage(RegistrationActivity.this, "لا يوجد اتصال بالسرفر");
                    }

                } else {
                    try {
                        if (null != response.errorBody()) {
                            String errorResponse = response.errorBody().string();
                            errorResponse = errorResponse.replace("\"", "");
                            Tools.showMessage(RegistrationActivity.this, errorResponse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG,t.toString());
                Tools.showMessage(RegistrationActivity.this,t.getMessage());
                hideProgress();
            }
        });
    }

    void loadLoginResponse() {
        String loginData = preferenceHelper.getLoginData();
        try {
            loginResponse = new Gson().fromJson(loginData, LoginResponse.class);

        } catch (Exception ex) {
            LoginActivity.start(getApplicationContext(), true);
        }
    }

    void showProgress() {
    btnRegister.setEnabled(false);
    progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgress() {
        btnRegister.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
