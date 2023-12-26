package gens.global.gensquickcount.function;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import gens.global.gensquickcount.R;

public class MyMessage {
    Context mContext;
    public MyMessage(Context context){
        this.mContext = context;
    }
    public void Success(String message) {
        // Inflate custom layout
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customToastView = inflater.inflate(R.layout.custom_toast, null);
        TextView textViewMessage = customToastView.findViewById(R.id.message);
        textViewMessage.setText(message);
        textViewMessage.setBackground(mContext.getResources().getDrawable(R.drawable.bg_toast_success));
        textViewMessage.setTextColor(mContext.getResources().getColor(R.color.white));

        Toast customToast = new Toast(mContext);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(customToastView);
        customToast.setGravity(Gravity.TOP | Gravity.END, 0, 0);
        customToast.show();
    }
    public void Error(String message) {
        // Inflate custom layout
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customToastView = inflater.inflate(R.layout.custom_toast, null);
        TextView textViewMessage = customToastView.findViewById(R.id.message);
        textViewMessage.setText(message);
        textViewMessage.setBackground(mContext.getResources().getDrawable(R.drawable.bg_toast_error));
        textViewMessage.setTextColor(mContext.getResources().getColor(R.color.white));
        // Create custom Toast
        Toast customToast = new Toast(mContext);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(customToastView);
        customToast.setGravity(Gravity.TOP | Gravity.END, 0, 0);
        customToast.show();
    }
}
