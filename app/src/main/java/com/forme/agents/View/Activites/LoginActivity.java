package com.forme.agents.View.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.forme.agents.DTO.DeviceUUID;
import com.google.android.material.textfield.TextInputEditText;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    String yourToken=null;


    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void start(Context context, boolean clear) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    PreferenceHelper preferenceHelper;


    public Map<String, String> getHeaders() throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("Authorization", "Bearer "+ yourToken);
        return params;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferenceHelper = new PreferenceHelper(this);
    }

    @BindView(R.id.etUsername)
    TextInputEditText etUsername;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btnLogin)
    Button btnLogin;


    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    @OnClick(R.id.btnLogin)
    public void LoginPressed() {
        if (validInput()) {
            requestLogin(userName, password);
        }
    }

    private void requestLogin(String userName, String password) {


        showProgress();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);

        Call<LoginResponse> loginCall = api.login(userName, password ,DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(this));
        loginCall.enqueue(new Callback<LoginResponse>() {

                              @Override
                              public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                  if (response.isSuccessful()) {
                                      try {
                                          LoginResponse loginResponse = response.body();
                                          preferenceHelper.saveLoginData(loginResponse);
                                          MainActivity.start(LoginActivity.this, true);
                                      } catch (Exception ex) {
                                          Tools.showMessage(LoginActivity.this, "لا يوجد اتصال بالسرفر");
                                      }

                                  } else {
                                      try {
                                          if (null != response.errorBody()) {
                                              String errorResponse = response.errorBody().string();
                                              errorResponse = errorResponse.replace("\"", "");
                                              Tools.showMessage(LoginActivity.this, errorResponse);
                                          }
                                      } catch (Exception e) {
                                          e.printStackTrace();
                                      }
                                  }
                                  hideProgress();
                                  /*
                                  if (response.code() == 401) {

                                     Tools.showMessage(LoginActivity.this, "Invalid Username/Password");
                                  } else if (response.code() == 200) {
                                      try {
                                          LoginResponse loginResponse = response.body();
                                          preferenceHelper.saveLoginData(loginResponse);
                                          MainActivity.start(LoginActivity.this, true);
                                      } catch (Exception ex) {
                                          Tools.showMessage(LoginActivity.this, "Invalid Response from server");
                                      }
                                  }
                                  hideProgress();
                                  */
                                  /*
                                  if (response.isSuccessful()) {
                                      Gson gson = new Gson();
                                      String successResponse = gson.toJson(response.body());
                                      Tools.showMessage(LoginActivity.this, successResponse);

                                  } else {
                                      try {
                                          if (null != response.errorBody()) {
                                              String errorResponse = response.errorBody().string();
                                              errorResponse = errorResponse.replace("\"", "");
                                              Tools.showMessage(LoginActivity.this, errorResponse);
                                          }
                                      } catch (Exception e) {
                                          e.printStackTrace();
                                      }
                                  }
                                  */
                              }

                              @Override
                              public void onFailure(Call<LoginResponse> call, Throwable t) {
                                  Tools.showMessage(LoginActivity.this, t.getMessage());
                                  hideProgress();
                              }
                          }
        );
    }


    String userName = "", password = "";

    private boolean validInput() {
        boolean validInput = true;


        etUsername.setError(null);
        etPassword.setError(null);

        userName = Tools.getString(etUsername);
        password = Tools.getString(etPassword);

        if (userName.equals("")) {
            etUsername.setError("required");
            validInput = false;
        }

        if (password.equals("")) {
            etPassword.setError("required");
            validInput = false;
        }


        return validInput;
    }

    void hideProgress() {
        btnLogin.setEnabled(true);
        progressBar.setVisibility(View.GONE);

    }

    void showProgress() {
        btnLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvRegister)
    public void register()
    {
        RegistrationActivity.start(this);
    }


}
