package com.nachomoyano04.canchapro.request;

import android.content.Context;
import android.widget.Toast;

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
            Toast.makeText(context, "Sesión finalizada, debe volver a loguearse.", Toast.LENGTH_SHORT).show();
        }
        return response;
    }
}
