package com.example.romi.helloword.s08cl05_minitwitter.ui;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romi.helloword.s08cl05_minitwitter.R;
import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;
import com.example.romi.helloword.s08cl05_minitwitter.common.SharedPreferencesManager;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.MiniTwitterClient;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.MiniTwitterService;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.request.RequestLogin;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.ResponseAuth;

import java.util.concurrent.locks.ReadWriteLock;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    TextView tvSignUp;
    EditText etEmail, etPassword;
    //CL13 1)
    MiniTwitterService miniTwitterService;
    MiniTwitterClient miniTwitterClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        retrofitInit();
        findViews();
        events();

    }

    private void retrofitInit() {
        //CL13 2)
        miniTwitterClient = MiniTwitterClient.getInstance();
        //CL13 3)
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    private void events() {
        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void findViews() {
         etEmail = findViewById(R.id.editTextEmail);
         etPassword = findViewById(R.id.editTextPass);
         btnLogin = findViewById(R.id.buttonLogin);
         tvSignUp = findViewById(R.id.textViewSignUp);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.buttonLogin:
                //CL13 4)
                goToLogin();
                break;
            case R.id.textViewSignUp:
                goToSignUp();
                break;

        }


    }

    //CL13 4)
    private void goToLogin() {
       String email = etEmail.getText().toString();
       String password = etPassword.getText().toString();

       if(email.isEmpty()){
           etEmail.setError("El email es obligatorio.");
       }else if(password.isEmpty()){
           etPassword.setError("La contraseña es obligatoria.");
       }else{
           //CL13-4)a-
           RequestLogin requestLogin = new RequestLogin(email,password);
           //CL13-4)b-
           Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);
           //CL13 4)c-
           call.enqueue(new Callback<ResponseAuth>() {
               @Override
               public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                   //Este y el de abajo se generan automaticamente, aca recibimos la respuesta en caso
                   //de que no haya habido problema en la comunicacion con el servidor()
                   if(response.isSuccessful()){
                     Toast.makeText(MainActivity.this,"Sesión Iniciada Correctamente",Toast.LENGTH_LONG).show();

                     //15-1-c)
                      SharedPreferencesManager
                              .setSomeStringValue(Constantes.PREF_TOKEN ,response.body().getToken());
                       SharedPreferencesManager
                               .setSomeStringValue(Constantes.PREF_CREATED ,response.body().getCreated());
                       SharedPreferencesManager
                               .setSomeStringValue(Constantes.PREF_USERNAME ,response.body().getUsername());
                       SharedPreferencesManager
                               .setSomeStringValue(Constantes.PREF_PHOTO_URL ,response.body().getPhotoUrl());
                       SharedPreferencesManager
                               .setSomeStringValue(Constantes.PREF_EMAIL ,response.body().getEmail());
                       SharedPreferencesManager
                               .setSomeBooleanValue(Constantes.PREF_ACTIVE,response.body().getActive());

                        Intent i = new Intent(MainActivity.this,DashboardActivity.class);
                        startActivity(i);

                        //Destruimos este activity para que no se pueda volver ya que no se puede volver al loggin
                       //(O no se deberia poder)
                       finish();
                   }   else {
                       Toast.makeText(MainActivity.this,"Revise los datos de loggin",Toast.LENGTH_LONG).show();

                   }
                   
               }

               @Override
               public void onFailure(Call<ResponseAuth> call, Throwable t) {
                   //Tambien se genera automaticamente y aca entra si hubo algun problema en la comunicacion/conexion
                   //con el servidor
                   Toast.makeText(MainActivity.this,"Problemas de Conexión.Reintente",Toast.LENGTH_LONG);

               }
           });
       }
    }

    private void goToSignUp() {

        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }
}
