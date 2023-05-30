package com.forme.agents.Helper;

import com.forme.agents.DTO.ChangePasswordModel;
import com.forme.agents.DTO.CheckNewUser;
import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.MenuResponse;
import com.forme.agents.DTO.MySoldeResponse;
import com.forme.agents.DTO.PriceResponse;
import com.forme.agents.DTO.RasidResponse;
import com.forme.agents.DTO.RegistrationModel;
import com.forme.agents.DTO.SellProductsResponse;
import com.forme.agents.DTO.TransfersResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface Api {

    @FormUrlEncoded
    @POST("/api/Auth/login") //your login function in your api

    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password ,@Field("UUIDDevice")String deviceUUID ,@Field("devicetype")String type ,@Field("DeviceId")String DeviceId ); //this is for your login, and you can used String as response or you can use a POJO, retrofit is very rubust to convert JSON to POJO



    @POST("/api/JwtAuth/register") //your registration function in your api

    Call<String> register(@Body RegistrationModel registrationModel); //this is for your registration, and you can used String as response or you can use a POJO, retrofit is very rubust to convert JSON to POJO


    @POST("/api/profile/resetpassword") //your reset function in your api

    Call<String> changePassword(@Header("Authorization") String auth, @Body ChangePasswordModel changePasswordModel); //this is for your reset, and you can used String as response or you can use a POJO, retrofit is very rubust to convert JSON to POJO

    @GET("/api/Agent/CheckNewUser") //your reset function in your api
    Call<ArrayList<CheckNewUser>> check_New_User(@Header("Authorization") String auth, @Body LoginResponse loginResponse);

    @POST("/api/Collective/MyMenu")
    Call<ArrayList<Object>> listofstock(@Header("Authorization") String auth, @Body MenuResponse menuResponse );

    @POST("/api/Agent/paymentUser") //your reset function in your api
    Call<String> paymentUuser(@Header("Authorization") String auth, @Body MenuResponse menuResponse);

    @POST("/api/Order/rasidy")
    Call<String> radisy(@Header("Authorization") String auth,@Body RasidResponse rasidResponse);

    @POST("/api/Order/salaf")
    Call<String> salaf(@Header("Authorization") String auth,@Body RasidResponse rasidResponse);

    @POST("/api/Order/StockPrice")
    Call<String> price(@Header("Authorization")  String auth,@Body PriceResponse priceResponse);

    @POST("/api/Order/Balance")
    Call<String> checksolde(@Header("Authorization") String auth, @Body MySoldeResponse mySoldeResponse);

    @POST("/api/Order/SellProducts")
    Call<String> sellProducts(@Header("Authorization") String auth, @Body SellProductsResponse sellProductsResponse);

    @POST("/api/Order/Governorates")
    Call<ArrayList<Object>> Gover(@Header("Authorization") String auth, @Body MenuResponse menuResponse);

    @POST("/api/Order/Currencies")
    Call<ArrayList<Object>> Curr(@Header("Authorization") String auth, @Body MenuResponse menuResponse);

    @POST("/api/Order/Commission")
    Call<ArrayList<Object>> Comm(@Header("Authorization") String auth, @Body CommissionResponse commissionResponse);

    @POST("/api/Order/ExchangeName")
    Call<ArrayList<Object>> ExchangeName(@Header("Authorization") String auth, @Body MenuResponse menuResponse);

    @POST("/api/Order/Transfers")
    Call<String> Transfers(@Header("Authorization") String auth, @Body TransfersResponse transfersResponse);

    @POST("/api/Agent/CheckNewUser")
    Call<ArrayList<Object>> CheckNewUsers(@Header("Authorization") String auth, @Body MenuResponse menuResponse);

    @POST("/api/Agent/GetCustomers")
    Call<ArrayList<Object>> ShowUsers(@Header("Authorization") String auth, @Body MenuResponse menuResponse);
}
