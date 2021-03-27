package com.example.romi.helloword.s08cl05_minitwitter.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.romi.helloword.s08cl05_minitwitter.R;
import com.example.romi.helloword.s08cl05_minitwitter.TweetListFragment;
import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;
import com.example.romi.helloword.s08cl05_minitwitter.common.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    FloatingActionButton fab;
    ImageView ivAvatar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_tweets_like:
                    return true;
                case R.id.navigation_profile:
                    return true;
            }
            return false;
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fab         =  findViewById(R.id.fab);
        ivAvatar    = findViewById(R.id.imageViewToolboardPhoto);

        getSupportActionBar().hide();

     //  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_home);
    
     //   navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new TweetListFragment())
                .commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevoTweetDialogFragment dialog = new NuevoTweetDialogFragment();
                dialog.show(getSupportFragmentManager(),"NuevoTweetDialogFragment");
            }
        });
        //08_15-1-g)
      //  String token = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN);
      //  Toast.makeText(this,"Token:"+ token, Toast.LENGTH_LONG).show();
        //COMENTE ESE CODIGO YA QUE EN LA CLASE 16 LO PASE AL AUTHINTERCEPTOR

        //Seteamos la imagen de usuario de perfil
        String photoUrl = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_PHOTO_URL);

        //Me tengo que acordar que el signo de admiracion adelante es NOT / NEGACION (En este caso si no es vacia la URL)
        if(!photoUrl.isEmpty()){
            Glide.with(this)
                    .load(Constantes.API_MINITWITTER_FILES_URL + photoUrl)
                    .into(ivAvatar);
        }
    }

}
