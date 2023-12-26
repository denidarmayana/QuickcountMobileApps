package gens.global.gensquickcount.function;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.HashMap;

import gens.global.gensquickcount.activity.HomeActivity;
import gens.global.gensquickcount.activity.auth.AuthActivity;
public class MySession {
    Context mContext;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static final String PREF_NAME = "UserName";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_PHONE = "phone";
    public MySession(Context context){
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    @NonNull
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(PREF_NAME, sharedPreferences.getString(PREF_NAME, null));
        user.put(KEY_PHONE, sharedPreferences.getString(KEY_PHONE, null));
        return user;
    }
    public void createSession(String phone,String name){
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_PHONE, phone);
        editor.putString(PREF_NAME, name);
        editor.commit();
    }
    public  String getPhone(){
        HashMap<String, String> user = getUserDetails();
        return user.get(KEY_PHONE);
    }
    public  String getName(){
        HashMap<String, String> user = getUserDetails();
        return user.get(PREF_NAME);
    }
    public void checkLogin(){

        if (!this.is_login()){
            Intent intent = new Intent(mContext, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }else {
            Intent intent = new Intent(mContext, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
    public boolean is_login() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(mContext, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
