package id.wrbcatering.aplikasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.model.CarouselModel;


public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.DaftarViewHolder> {


    private ArrayList<CarouselModel> dataList;
    Context mContext;
    public CarouselAdapter(ArrayList<CarouselModel> dataList,Context context) {
        this.dataList = dataList;
        this.mContext= context;
    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_carousel, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, int position) {
        holder.nama_tv.setText(dataList.get(position).getNama());
        Picasso.get().load(Agus.DATA_KAT_URL + dataList.get(position).getGambar()).into(holder.gambar_iv);
        final String id = dataList.get(position).getId();

        //tambah handler onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext,  "ID yang dipencet " + id , Toast.LENGTH_LONG).show();
            }
        });
   }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_tv;
        private ImageView gambar_iv;

        public DaftarViewHolder(View itemView) {
            super(itemView);
            nama_tv = (TextView) itemView.findViewById(R.id.nama);
            gambar_iv = (ImageView) itemView.findViewById(R.id.gambar);

        }
    }
}
