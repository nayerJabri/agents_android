package com.forme.agents.View.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.forme.agents.DTO.DeviceUUID;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.forme.agents.DTO.ChangePasswordModel;
import com.forme.agents.DTO.ChangePasswordResponse;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.Helper.Api;
import com.forme.agents.Helper.Constants;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.Helper.Tools;
import com.forme.agents.R;
import com.forme.agents.View.Activites.LoginActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class ChangePasswordFragment extends Fragment implements ScreenShotable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View containerView;
    private Bitmap bitmap;
    private static final String TAG = ChangePasswordFragment.class.getSimpleName();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @BindView(R.id.etOldPass)
    TextInputEditText etOldPass;

    @BindView(R.id.etNewPass)
    TextInputEditText etNewPass;

    @BindView(R.id.etComfirm)
    TextInputEditText etComfirm;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    @OnClick(R.id.btnUpdate)
    void OnUpdateButton() {
        if (validInput())
            changePassword();

    }

    private void changePassword() {

        showProgress();
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
//        okHttpClientBuilder
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Request.Builder newRequest = request.newBuilder().header("Authorization", loginResponse.token);
//                        return chain.proceed(newRequest.build());
//                    }
//                });
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
               // .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = restAdapter.create(Api.class);
        Call<String> loginCall = api.changePassword("Bearer "+loginResponse.token, changePasswordModel);
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, response.toString());
                if(response.code()==200)
                {
                    Tools.showMessage(context,"Password Updated");
                    resetForm();
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Tools.showMessage(context,"Unable to Change Password");
                hideProgress();
            }
        });
    }

    private void resetForm() {
        etOldPass.setText("");
        etNewPass.setText("");
        etComfirm.setText("");
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
                ChangePasswordFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }


    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    ChangePasswordModel changePasswordModel;

    private boolean validInput() {
        boolean valid = true;
        changePasswordModel = new ChangePasswordModel(Tools.getString(etOldPass), Tools.getString(etNewPass), Tools.getString(etComfirm),loginResponse.renewaltoken , DeviceUUID.UUIDDevice,DeviceUUID.devicetype,DeviceUUID.getDeviceId(context),loginResponse.transid);

        if (changePasswordModel.oldpassword.isEmpty()) {
            etOldPass.setError("* required");
            valid = false;
        }

        if (changePasswordModel.newpassword.isEmpty()) {
            etNewPass.setError("* required");
            valid = false;
        }

        if (changePasswordModel.confirmpassword.isEmpty()) {
            etComfirm.setError("* required");
            valid = false;
        } else if (!changePasswordModel.newpassword.equals(changePasswordModel.confirmpassword)) {
            etComfirm.setError("* must match with new password");
            valid = false;
        }

        return valid;
    }

    PreferenceHelper preferenceHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(container, view);
        preferenceHelper = new PreferenceHelper(context);
        loadLoginResponse();
        return view;
    }


    LoginResponse loginResponse;

    void loadLoginResponse() {
        String loginData = preferenceHelper.getLoginData();
        try {
            loginResponse = new Gson().fromJson(loginData, LoginResponse.class);

        } catch (Exception ex) {
            LoginActivity.start(context, true);
        }
    }

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }


    void hideProgress() {
        btnUpdate.setEnabled(true);
        progressBar.setVisibility(View.GONE);

    }

    void showProgress() {
        btnUpdate.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }


}
