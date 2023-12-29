package gens.global.gensquickcount.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gens.global.gensquickcount.R;
import gens.global.gensquickcount.function.MyMessage;
import gens.global.gensquickcount.function.api.RetrofitClient;
import gens.global.gensquickcount.function.api.UserInterfaces;
import gens.global.gensquickcount.model.UserModel;
import gens.global.gensquickcount.model.WilayahModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    TextView masuk;
    EditText phone,username,password,name,address,tps;
    LoadingButton daftar;
    UserInterfaces userInterfaces;
    Call<UserModel> userModelCall;
    MyMessage myMessage;
    Call<List<WilayahModel>> listCall;
    Spinner provinsi,kabupaten,kecamatan,kelurahan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        myMessage = new MyMessage(getContext());
        userInterfaces = RetrofitClient.getClient().create(UserInterfaces.class);
        masuk = root.findViewById(R.id.masuk);
        phone = root.findViewById(R.id.phone);
        name = root.findViewById(R.id.name);
        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        daftar = root.findViewById(R.id.daftar);
        masuk.setOnClickListener(V->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        daftar.setOnClickListener(V->registration());
        provinsi = root.findViewById(R.id.provinsi);
        address = root.findViewById(R.id.address);
        tps = root.findViewById(R.id.tps);
        kabupaten = root.findViewById(R.id.kabupaten);
        kecamatan = root.findViewById(R.id.kecamatan);
        kelurahan = root.findViewById(R.id.kelurahan);
        showProvinsi();
        return root;
    }

    private void registration(){
        daftar.showLoading();
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("username", username.getText().toString().trim());
            paramObject.put("password", password.getText().toString().trim());
            paramObject.put("phone", phone.getText().toString().trim());
            paramObject.put("name", name.getText().toString().trim());
            paramObject.put("address", address.getText().toString().trim());
            paramObject.put("pov", provinsi.getSelectedItem().toString().trim());
            paramObject.put("kab", kabupaten.getSelectedItem().toString().trim());
            paramObject.put("kec", kecamatan.getSelectedItem().toString().trim());
            paramObject.put("kel", kelurahan.getSelectedItem().toString().trim());
            paramObject.put("tps", tps.getText().toString());
            userModelCall = userInterfaces.register(paramObject.toString());
            userModelCall.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    assert response.body() != null;
                    Log.d("RegistrationSuccess", new Gson().toJson(response.body()));
                    daftar.hideLoading();
                    if (response.body().getCode() == 200){
                        myMessage.Success(response.body().getMessage());
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }else{
                        myMessage.Error(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                    Log.d("RegistrationError", call.toString());

                }
            });
        } catch (JSONException e) {
            Log.d("RegistrationErrorDua", Objects.requireNonNull(e.getMessage()));
            throw new RuntimeException(e);
        }
    }
    void showProvinsi() {
        listCall = userInterfaces.getProvinsi();
        listCall.enqueue(new Callback<List<WilayahModel>>() {
            @Override
            public void onResponse(Call<List<WilayahModel>> call, Response<List<WilayahModel>> response) {
                Log.d("showProvinsi", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    List<WilayahModel> data = response.body();
                    List<String> items = new ArrayList<>();
                    for (WilayahModel item : data) {
                        items.add(item.getId() + " : " + item.getName());
                    }
                    ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, items);
                    adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    provinsi.setAdapter(adapterProvinsi);
                    provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            String selectedItem = items.get(position);
                            String prov[] = selectedItem.split(" : ");
                            JSONObject paramObject = new JSONObject();
                            try {
                                paramObject.put("prov", prov[0]);
                                showKabuoaten(paramObject.toString());
                                kabupaten.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {

                        }
                    });
                } else {
                    Log.d("showProvinsiError", new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<WilayahModel>> call, Throwable t) {
                Log.d("showProvinsiError", t.getMessage());
            }
        });
    }

    void showKabuoaten(String prov) {
        listCall = userInterfaces.getKabupaten(prov);
        listCall.enqueue(new Callback<List<WilayahModel>>() {
            @Override
            public void onResponse(Call<List<WilayahModel>> call, Response<List<WilayahModel>> response) {
                Log.d("showKabupaten", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    List<WilayahModel> data = response.body();
                    List<String> items = new ArrayList<>();
                    for (WilayahModel item : data) {
                        items.add(item.getId() + " : " + item.getName());
                    }
                    ArrayAdapter<String> adapterKabupaten = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, items);
                    adapterKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    kabupaten.setAdapter(adapterKabupaten);
                    kabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            String selectedItem = items.get(position);
                            String kab[] = selectedItem.split(" : ");
                            JSONObject paramObject = new JSONObject();
                            try {
                                paramObject.put("kab", kab[0]);
                                showKecamatan(paramObject.toString());
                                kecamatan.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {

                        }
                    });
                } else {
                    Log.d("showKabupaten", new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<WilayahModel>> call, Throwable t) {
                Log.d("showKabupaten", t.getMessage());
            }
        });
    }

    void showKecamatan(String kab) {
        listCall = userInterfaces.getKecamatan(kab);
        listCall.enqueue(new Callback<List<WilayahModel>>() {
            @Override
            public void onResponse(Call<List<WilayahModel>> call, Response<List<WilayahModel>> response) {
                Log.d("showKabupaten", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    List<WilayahModel> data = response.body();
                    List<String> items = new ArrayList<>();
                    for (WilayahModel item : data) {
                        items.add(item.getId() + " : " + item.getName());
                    }
                    ArrayAdapter<String> adapterKecamatan = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, items);
                    adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    kecamatan.setAdapter(adapterKecamatan);
                    kecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            String selectedItem = items.get(position);
                            Log.d("showKelurahan", selectedItem);
                            String kec[] = selectedItem.split(" : ");
                            JSONObject paramObject = new JSONObject();
                            try {
                                paramObject.put("kec", kec[0]);
                                showKelurahan(paramObject.toString());
                                kelurahan.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {

                        }
                    });
                } else {
                    Log.d("showKabupaten", new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<WilayahModel>> call, Throwable t) {
                Log.d("showKabupaten", t.getMessage());
            }
        });
    }
    void showKelurahan(String kel) {
        listCall = userInterfaces.getKelurahan(kel);
        listCall.enqueue(new Callback<List<WilayahModel>>() {
            @Override
            public void onResponse(Call<List<WilayahModel>> call, Response<List<WilayahModel>> response) {
                Log.d("showKelurahan", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    List<WilayahModel> data = response.body();
                    List<String> items = new ArrayList<>();
                    for (WilayahModel item : data) {
                        items.add(item.getId() + " : " + item.getName());
                    }
                    ArrayAdapter<String> adapterKecamatan = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, items);
                    adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    kelurahan.setAdapter(adapterKecamatan);
                    kelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            String selectedItem = items.get(position);
                            Log.d("showKelurahan", selectedItem);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {

                        }
                    });
                } else {
                    Log.d("showKabupaten", new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<WilayahModel>> call, Throwable t) {
                Log.d("showKabupaten", t.getMessage());
            }
        });
    }
}