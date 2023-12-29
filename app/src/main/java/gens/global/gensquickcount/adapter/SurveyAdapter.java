package gens.global.gensquickcount.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import gens.global.gensquickcount.R;
import gens.global.gensquickcount.model.SurveyModel;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.MyViewHolder> {
    Context context;
    List<SurveyModel> list_today;
    public SurveyAdapter(Context context, List<SurveyModel> listSurat) {
        this.context = context;
        this.list_today = listSurat;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setSurvey(List<SurveyModel> movieList) {
        this.list_today = movieList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_survey,parent,false);
        return new MyViewHolder(itemView);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Jakarta");
            sdf.setTimeZone(timeZone);
            Date d = sdf.parse(list_today.get(position).getCreated_at());
            holder.tanggal.setText(output.format(d));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.wilayah.setText(list_today.get(position).getKecamatan());
        holder.tps.setText("TPS : "+list_today.get(position).getTps());
        holder.count.setText(list_today.get(position).getCount()+" Suara");

        holder.list_report.setOnLongClickListener(v -> {
            Toast.makeText(context, list_today.get(position).getCount(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        if(list_today != null){
            return list_today.size();
        }
        return 0;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView wilayah,count,tanggal,tps;
        LinearLayout list_report;

        public MyViewHolder(View itemView) {
            super(itemView);
            wilayah = itemView.findViewById(R.id.wilayah);
            count = itemView.findViewById(R.id.count);
            tps = itemView.findViewById(R.id.tps);
            tanggal = itemView.findViewById(R.id.tanggal);
            list_report = itemView.findViewById(R.id.list_report);
        }
    }
}
