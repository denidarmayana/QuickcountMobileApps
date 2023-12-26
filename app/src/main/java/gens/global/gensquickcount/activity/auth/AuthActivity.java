package gens.global.gensquickcount.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import gens.global.gensquickcount.R;
import gens.global.gensquickcount.fragment.AuthFragment;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AuthFragment())
                    .commit();
        }
    }
}