package gens.global.gensquickcount.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gens.global.gensquickcount.R;
import gens.global.gensquickcount.adapter.SurveyAdapter;
import gens.global.gensquickcount.function.api.RetrofitClient;
import gens.global.gensquickcount.function.api.UserInterfaces;
import gens.global.gensquickcount.model.SurveyModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataSurveyFragment extends Fragment {
    RecyclerView list_survey;
    UserInterfaces userInterfaces;
    List<SurveyModel> surveyModelList;
    Call<List<SurveyModel>> listCall;
    SurveyAdapter surveyAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_survey, container, false);
        userInterfaces = RetrofitClient.getClient().create(UserInterfaces.class);
        list_survey = root.findViewById(R.id.list_survey);
        surveyModelList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list_survey.setLayoutManager(layoutManager);
        surveyAdapter = new SurveyAdapter(getContext(),surveyModelList);
        list_survey.setAdapter(surveyAdapter);
        getSurvey();
        return root;
    }
    void getSurvey(){
        listCall = userInterfaces.getSurvey();
        listCall.enqueue(new Callback<List<SurveyModel>>() {
            @Override
            public void onResponse(Call<List<SurveyModel>> call, Response<List<SurveyModel>> response) {
                if (response.isSuccessful()){
                    surveyModelList = response.body();
                    surveyAdapter.setSurvey(surveyModelList);
                }else{
                    Log.d("errt", ""+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<SurveyModel>> call, Throwable t) {

            }
        });
    }
}