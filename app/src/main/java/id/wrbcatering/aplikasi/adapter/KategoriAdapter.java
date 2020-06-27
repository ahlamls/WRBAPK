package id.wrbcatering.aplikasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.model.KategoriModel;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.DaftarViewHolder> {


    private ArrayList<KategoriModel> dataList;
    Context mContext;
    public KategoriAdapter(ArrayList<KategoriModel> dataList,Context context) {
        this.dataList = dataList;
        this.mContext= context;
    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_kategori, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {
        holder.nama_tv.setText(dataList.get(position).getNama());
        final String id = dataList.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_tv;

        public DaftarViewHolder(View itemView) {
            super(itemView);
            nama_tv = (TextView) itemView.findViewById(R.id.textView3);

        }
    }
}
