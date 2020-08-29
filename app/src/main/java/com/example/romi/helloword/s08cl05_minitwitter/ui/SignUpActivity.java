package com.example.romi.helloword.s08cl05_minitwitter.ui;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
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
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.request.RequestSignUp;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.ResponseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvLogin;
    Button btnSignUp;
    EditText etUser, etPassword, etMail;
    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();
        retrofitInit();
        findviews();
        events();

    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    private void findviews() {
        btnSignUp   = findViewById(R.id.buttonSignUp);
        tvLogin     = findViewById(R.id.textViewLogin);
        etUser      = findViewById(R.id.editTextUser);
        etPassword  = findViewById(R.id.editTextPass);
        etMail      = findViewById(R.id.editTextEmail);
    }

    private void events() {
        tvLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.textViewLogin:
                goToLogin();
                break;
            case R.id.buttonSignUp:
                goToSignUp();
                break;
        }
    }

    //CL14)
    private void goToSignUp() {
        String username  = etUser.getText().toString();
        String email = etMail.getText().toString();
        String password = etPassword.getText().toString();

        if(username.isEmpty() ) {
            etUser.setError("Debe Ingresar el Nombre de Usuario.");
        }else if(email.isEmpty()){
            etMail.setError("Debe Ingresar el Email.");
        }else if(password.isEmpty() || password.length()< 4){
            etPassword.setError("Debe ingresar una contraseña y debe tener al menos cuatro caracteres.");
        }else{
            String code = "UDEMYANDROID";
            RequestSignUp requestSignup = new RequestSignUp(username,email,password,code);
            Call<ResponseAuth> call = miniTwitterService.doSignUp(requestSignup);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if(response.isSuccessful()){

                        //15-1-e)
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

                        Intent i = new Intent(SignUpActivity.this,DashboardActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText( SignUpActivity.this,"Algo Salio mal, revise los datos",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this,"Hubo un problema en la conexion, intentelo luego.",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void goToLogin() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
