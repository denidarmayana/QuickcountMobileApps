package gens.global.gensquickcount.action;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import gens.global.gensquickcount.R;

public class QuickActivity extends AppCompatActivity {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        back = findViewById(R.id.back);
        back.setOnClickListener(V->{
            onBackPressed();
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}