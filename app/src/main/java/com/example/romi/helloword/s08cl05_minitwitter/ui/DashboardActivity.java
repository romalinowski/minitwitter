package com.example.romi.helloword.s08cl05_minitwitter.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.example.romi.helloword.s08cl05_minitwitter.R;
import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;
import com.example.romi.helloword.s08cl05_minitwitter.common.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_home);

     //   navigation.setOnNavigationItemSelectedListener();
        getSupportActionBar().hide();




        //08_15-1-g)
      //  String token = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN);
      //  Toast.makeText(this,"Token:"+ token, Toast.LENGTH_LONG).show();
        //COMENTE ESE CODIGO YA QUE EN LA CLASE 16 LO PASE AL AUTHINTERCEPTOR
    }

}
