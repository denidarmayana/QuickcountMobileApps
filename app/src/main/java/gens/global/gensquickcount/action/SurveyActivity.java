package gens.global.gensquickcount.action;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.droidbond.loadingbutton.LoadingButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class SurveyActivity extends AppCompatActivity {
    EditText count;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView preview;
    MySession mySession;
    Call<UserModel> call;
    UserInterfaces userInterfaces;
    MyMessage myMessage;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        mySession = new MySession(this);
        myMessage = new MyMessage(this);
        userInterfaces = RetrofitClient.getClient().create(UserInterfaces.class);
        preview = findViewById(R.id.preview);
        count = findViewById(R.id.count);
        back = findViewById(R.id.back);
        back.setOnClickListener(V->{
            onBackPressed();
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void ambilGambar(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                preview.setVisibility(View.VISIBLE);
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                preview.setImageBitmap(imageBitmap);
                assert imageBitmap != null;
                sendFileToServer(getFileName(getImageUriFromBitmap(imageBitmap)),encodeFileToBase64(getImageUriFromBitmap(imageBitmap)));
            }
        }
    }
    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Image",
                null
        );
        return Uri.parse(path);
    }
    private String encodeFileToBase64(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            inputStream.close();
            return Base64.encodeToString(fileBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    private String getFileName(Uri fileUri) {
        String fileName = "";
        try (Cursor cursor = getContentResolver().query(fileUri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                fileName = cursor.getString(displayNameIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
    void sendFileToServer(String fileName,String base64Data){
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("images", fileName);
            paramObject.put("file",base64Data);
            paramObject.put("petugas",mySession.getPhone().trim());
            paramObject.put("count",count.getText().toString().trim());
            call = userInterfaces.inserSurvey(paramObject.toString());
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    assert response.body() != null;
                    Log.d("LoginUsersSuccess", new Gson().toJson(response.body()));
                    if (response.body().getCode() == 200){
                        myMessage.Success(response.body().getMessage());
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        myMessage.Error(response.body().getMessage());
                        count.setText("");
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