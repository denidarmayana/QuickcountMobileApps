package gens.global.gensquickcount.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensquickcount.R;
import gens.global.gensquickcount.action.ProfileActivity;
import gens.global.gensquickcount.action.QuickActivity;
import gens.global.gensquickcount.action.SurveyActivity;
import gens.global.gensquickcount.adapter.DtaTabAdapter;
import gens.global.gensquickcount.adapter.SliderAdapter;
import gens.global.gensquickcount.adapter.SliderIndicator;
import gens.global.gensquickcount.fragment.FragmentSlider;
import gens.global.gensquickcount.function.BannerSlider;
import gens.global.gensquickcount.function.MyLocation;
import gens.global.gensquickcount.function.MySession;
import gens.global.gensquickcount.function.api.RetrofitClient;
import gens.global.gensquickcount.function.api.UserInterfaces;
import gens.global.gensquickcount.model.SliderModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    MyLocation myLocation;
    MySession mySession;
    TextView name,address;
    BannerSlider bannerSlider;
    List<Fragment> fragments;
    SliderAdapter sliderAdapter;
    SliderIndicator sliderIndicator;
    LinearLayout pagesContainer;
    UserInterfaces userInterfaces;
    TabLayout tabLayout;
    ViewPager viewPager;
    DtaTabAdapter dtaTabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userInterfaces = RetrofitClient.getClient().create(UserInterfaces.class);
        myLocation = new MyLocation(this);
        mySession = new MySession(this);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        name.setText(mySession.getName());
        address.setText(myLocation.getAddressLine(this));
        pagesContainer = findViewById(R.id.pagesContainer);
        bannerSlider = findViewById(R.id.sliderView);
        bannerSlider.setDurationScroll(800);
        fragments = new ArrayList<>();
        Call<List<SliderModel>> callberita = userInterfaces.getSlider();
        callberita.enqueue(new Callback<List<SliderModel>>() {
            @Override
            public void onResponse(Call<List<SliderModel>> call, Response<List<SliderModel>> response) {
                Log.d("berita_ku", new Gson().toJson(response.body()));
                if (response.isSuccessful()){
                    List<SliderModel> beritaList = response.body();
                    for (SliderModel berita : beritaList) {
                        String images = berita.getImages();
                        fragments.add(FragmentSlider.newInstance(images));
                    }
                    sliderAdapter = new SliderAdapter(getSupportFragmentManager(), fragments);
                    bannerSlider.setAdapter(sliderAdapter);
                    sliderIndicator = new SliderIndicator(getApplicationContext(), pagesContainer, bannerSlider, R.drawable.indicator_circle);
                    sliderIndicator.setPageCount(fragments.size());
                    sliderIndicator.show();
                }else{
                    System.out.println("lberita "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<SliderModel>> call, Throwable t) {
                Log.d("DataModel", ""+t.getMessage());
            }

        });
        viewPager = findViewById(R.id.viewPagers_transfer);
        tabLayout = findViewById(R.id.tabs_transfer);
        dtaTabAdapter = new DtaTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(dtaTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void insertSurvey(View view) {
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
    }

    public void insertQuick(View view) {
        Intent intent = new Intent(this, QuickActivity.class);
        startActivity(intent);
    }
}