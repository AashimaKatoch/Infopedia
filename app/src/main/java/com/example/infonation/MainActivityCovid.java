package com.example.infonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityCovid extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    CountryCodePicker countryCodePicker;
    TextView mtodaytotal,mtotal,mactive,mtodayactive,mrecovered,mtodayrecovered,mdeaths,mtodaydeaths;

    String country;
    TextView mfilter;
    Spinner spinner;
    String[] types={"cases","deaths","recovered","active"};
    private List<ModelClassC> modelClassListX;
    private List<ModelClassC> modelClassListY;
    PieChart mpiechart;
    private RecyclerView recyclerView;
    com.example.infonation.AdapterC adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_covid);


        countryCodePicker = findViewById(R.id.ccp);
        mtodayactive = findViewById(R.id.todayactive);
        mactive = findViewById(R.id.activecases);
        mdeaths = findViewById(R.id.totaldeaths);
        mtodaydeaths = findViewById(R.id.todaydeath);
        mrecovered = findViewById(R.id.recoveredcases);
        mtodayrecovered = findViewById(R.id.todayrecovered);
        mtotal = findViewById(R.id.totalcase);
        mtodaytotal = findViewById(R.id.todaytotal);
        mpiechart = findViewById(R.id.piechart);
        spinner = findViewById(R.id.spinner);
        mfilter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recycleview);
        modelClassListX = new ArrayList<>();
        modelClassListY = new ArrayList<>();

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

 ApiUtilitiesC.getAPIInterface().getcountrydata().enqueue(new retrofit2.Callback<List<ModelClassC>>() {
     @Override
     public void onResponse(Call<List<ModelClassC>> call, retrofit2.Response<List<ModelClassC>> response) {
         modelClassListY.addAll(response.body());
     }

     @Override
     public void onFailure(Call<List<ModelClassC>> call, Throwable t) {

     }
 });

        adapter = new AdapterC(getApplicationContext(), modelClassListY);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        countryCodePicker.setAutoDetectedCountry(true);
        country=countryCodePicker.getSelectedCountryCode();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country=countryCodePicker.getSelectedCountryName();
                fetchdata();
            }
        });

        fetchdata();



    }

    private void fetchdata() {

        ApiUtilitiesC.getAPIInterface().getcountrydata().enqueue(new Callback<List<ModelClassC>>() {
            @Override
            public void onResponse(Call<List<ModelClassC>> call, Response<List<ModelClassC>> response) {

                modelClassListX.addAll(response.body());
                for(int i=0;i< modelClassListX.size();i++)
                {
                    if(modelClassListX.get(i).getCountry().equals(country))
                    {
                        mactive.setText((modelClassListX.get(i).getActive()));
                        mtodaydeaths.setText((modelClassListX.get(i).getTodayDeaths()));
                        mtodayrecovered.setText((modelClassListX.get(i).getTodayRecovered()));
                        mtodaytotal.setText((modelClassListX.get(i).getTodayCases()));
                        mtotal.setText((modelClassListX.get(i).getCases()));
                        mdeaths.setText((modelClassListX.get(i).getDeaths()));
                        mrecovered.setText(modelClassListX.get(i).getRecovered());

                        int active,total,recovered,deaths;
                        active=Integer.parseInt(modelClassListX.get(i).getActive());
                        total=Integer.parseInt(modelClassListX.get(i).getCases());
                        recovered=Integer.parseInt(modelClassListX.get(i).getRecovered());
                        deaths=Integer.parseInt(modelClassListX.get(i).getDeaths());

                        updateGraph(active,total,recovered,deaths);



                    }
                }

            }

            @Override
            public void onFailure(Call<List<ModelClassC>> call, Throwable t) {

            }
        });







    }

    private void updateGraph(int active, int total, int recovered, int deaths) {
        mpiechart.clearChart();
        mpiechart.addPieSlice(new PieModel("Confirm",total, Color.parseColor("#FFB701")));
        mpiechart.addPieSlice(new PieModel("Active",active,Color.parseColor("#FF4CAF50")));
        mpiechart.addPieSlice(new PieModel("Recovered",recovered,Color.parseColor("#38ACCD")));
        mpiechart.addPieSlice(new PieModel("Deaths",deaths,Color.parseColor("#F55C47")));
        mpiechart.startAnimation();

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item=types[position];
        mfilter.setText(item);
        adapter.filter(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intx = new Intent(MainActivityCovid.this,dashboard.class);
        startActivity(intx);
        finish();
    }
}
