package com.nachomoyano04.canchapro.request;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.nachomoyano04.canchapro.ui.login.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizeInterceptor implements Interceptor {

    private Context context;

    public AuthorizeInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if(response.code() == 401){
            Intent i = new Intent(context, LoginActivity.class);
            i.putExtra("mensaje", "Sesión finalizada, debe volver a loguearse.");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
        }
        return response;
    }
}
