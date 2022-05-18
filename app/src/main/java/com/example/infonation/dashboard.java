package com.example.infonation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class dashboard extends AppCompatActivity implements View.OnClickListener {


    private CardView news, covid, weather, aboutus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        news = findViewById(R.id.news);
        news.setOnClickListener(this);

        weather = findViewById(R.id.weather);
        weather.setOnClickListener(this);

        covid= findViewById(R.id.covid);
        covid.setOnClickListener(this);

        aboutus= findViewById(R.id.aboutus);
        aboutus.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.news:
                i = new Intent(this, MainActivityNews.class);
                startActivity(i);
                finish();
                break;

            case R.id.weather:
                i = new Intent(this, MainActivityWeather.class);
                startActivity(i);
                finish();
                break;

            case R.id.covid:
                i = new Intent(this, MainActivityCovid.class);
                startActivity(i);
                finish();
                break;

            case R.id.aboutus:
                i = new Intent(this, AboutUs.class);
                startActivity(i);
                finish();
                break;



        }

    }

}