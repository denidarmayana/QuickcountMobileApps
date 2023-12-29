package gens.global.gensquickcount.action;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gens.global.gensquickcount.R;
import gens.global.gensquickcount.function.Constant;
import gens.global.gensquickcount.function.api.RetrofitClient;
import gens.global.gensquickcount.function.api.UserInterfaces;
import gens.global.gensquickcount.model.CalonModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView back,images;
    TextView name,biografi;
    Call<CalonModel> calonModelCall;
    UserInterfaces userInterfaces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userInterfaces = RetrofitClient.getClient().create(UserInterfaces.class);
        back = findViewById(R.id.back);
        back.setOnClickListener(V->{
            onBackPressed();
        });
        images = findViewById(R.id.images);
        name = findViewById(R.id.name);
        biografi = findViewById(R.id.biografi);
        calonModelCall = userInterfaces.getCalon();
        calonModelCall.enqueue(new Callback<CalonModel>() {
            @Override
            public void onResponse(Call<CalonModel> call, Response<CalonModel> response) {
                name.setText(response.body().getName());
                biografi.setText(Html.fromHtml(response.body().getBiografi()));
                biografi.setMovementMethod(new ScrollingMovementMethod());
                Glide.with(ProfileActivity.this).load(Constant.UrlCalon+""+response.body().getImages()).into(images);
            }

            @Override
            public void onFailure(Call<CalonModel> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}