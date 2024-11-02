package com.nachomoyano04.canchapro.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiCliente {
    public static final String URL_BASE = "http://192.168.1.9:5021/api/";
    public static SharedPreferences sp;

    public static SharedPreferences conectar(Context context){
        if(sp == null){
            sp =context.getSharedPreferences("tokenUsuario", 0);
        }
        return sp;
    }

    public static void guardarToken(Context context, String token){
        SharedPreferences shared = conectar(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("token", "Bearer "+token);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences shared = conectar(context);
        String token = shared.getString("token", "");
        return token;
    }

    public static CanchaProService getApiCanchaPro(Context context){
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizeInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(CanchaProService.class);
    }

    public interface CanchaProService{
        //Login
        @FormUrlEncoded
        @POST("usuario/login")
        Call<String> login(@Field("correo") String correo, @Field("password") String password);

        //Registrar
        @FormUrlEncoded
        @POST("usuario")
        Call<String> registrar(@Field("dni") String dni, @Field("nombre") String nombre, @Field("apellido") String apellido, @Field("correo") String correo, @Field("password") String password);

        //Enviar mail recuperacion password
        @FormUrlEncoded
        @POST("usuario/recuperarpass")
        Call<String> recuperarPassword(@Field("correo") String correo);

        //Nueva password en recuperar password
        @FormUrlEncoded
        @PUT("usuario/nuevapassword")
        Call<String> nuevaPassword(@Header("Authorization") String token, @Field("nuevaPassword") String nuevaPassword);
    }
}