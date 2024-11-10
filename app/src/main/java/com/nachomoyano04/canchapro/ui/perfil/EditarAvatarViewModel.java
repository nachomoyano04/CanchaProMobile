package com.nachomoyano04.canchapro.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Usuario;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarAvatarViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mAvatar;

    public EditarAvatarViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getMAvatar(){
        if(mAvatar == null){
            mAvatar = new MutableLiveData<>();
        }
        return mAvatar;
    }

    public void setearImagen(String imagen){
        if(imagen != null && !imagen.isEmpty()){
            mAvatar.postValue(ApiCliente.URLIMAGENUSUARIO+imagen);
        }
    }

    public void recibirFoto(ActivityResult o) {
        if(o.getResultCode() == RESULT_OK){
            Uri uri = o.getData().getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            mAvatar.setValue(uri.toString());
        }
    }

    public void guardar(){
        String img = mAvatar.getValue();
        if(img != null && !img.isEmpty()){
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(img));
                String fileName = "userAvatar"+System.currentTimeMillis()+".jpg";
                File file =new File(context.getCacheDir(), fileName);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                inputStream.close();

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part imagen = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
                api.editarAvatar(ApiCliente.getToken(context), imagen).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        }else{
                            if(response.code() != 401){
                                try {
                                    Log.d("errorCambiarAvatar", response.errorBody().string());
                                    Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {

                    }
                });
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(context, "Seleccione una imagen por favor", Toast.LENGTH_SHORT).show();
        }
    }
}