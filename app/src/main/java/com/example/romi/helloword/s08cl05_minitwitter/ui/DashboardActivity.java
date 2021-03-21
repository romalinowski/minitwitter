package com.example.romi.helloword.s08cl05_minitwitter.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.romi.helloword.s08cl05_minitwitter.R;
import com.example.romi.helloword.s08cl05_minitwitter.TweetListFragment;
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

        fab = findViewById(R.id.fab);

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
    }

}
