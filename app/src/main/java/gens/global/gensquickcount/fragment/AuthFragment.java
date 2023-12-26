package gens.global.gensquickcount.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import gens.global.gensquickcount.R;
import gens.global.gensquickcount.activity.HomeActivity;
import gens.global.gensquickcount.function.MyMessage;
import gens.global.gensquickcount.function.MySession;
import gens.global.gensquickcount.function.api.RetrofitClient;
import gens.global.gensquickcount.function.api.UserInterfaces;
import gens.global.gensquickcount.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthFragment extends Fragment {
    TextView daftar;
    EditText username,password;
    LoadingButton loadingButton;
    UserInterfaces userInterfaces;
    Call<UserModel> userModelCall;
    MyMessage myMessage;
    MySession mySession;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_auth, container, false);
        myMessage = new MyMessage(getContext());
        userInterfaces = RetrofitClient.getClient().create(UserInterfaces.class);
        mySession = new MySession(requireContext());
        daftar = root.findViewById(R.id.daftar);
        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        loadingButton = root.findViewById(R.id.login);
        daftar.setOnClickListener(V-> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RegisterFragment())
                .addToBackStack(null)
                .commit());
        loadingButton.setOnClickListener(V-> login());
        return root;
    }
    private void login(){
        loadingButton.showLoading();
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("username", username.getText().toString());
            paramObject.put("password", password.getText().toString());
            userModelCall = userInterfaces.loginUser(paramObject.toString());
            userModelCall.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    assert response.body() != null;
                    Log.d("LoginUsersSuccess", new Gson().toJson(response.body()));
                    loadingButton.hideLoading();
                    if (response.body().getCode() == 200){
                        myMessage.Success(response.body().getMessage());
                        mySession.createSession(username.getText().toString(),response.body().getData());
                        Intent intent = new Intent(requireContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        myMessage.Error(response.body().getMessage());
                        username.setText("");
                        password.setText("");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call,@NonNull Throwable t) {
                    Log.d("LoginUsersError", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (JSONException e) {
            Log.d("LoginUsersError", Objects.requireNonNull(e.getMessage()));
            throw new RuntimeException(e);
        }
    }
}