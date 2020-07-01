package id.wrbcatering.aplikasi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.indihomo.Pras;
import id.wrbcatering.aplikasi.model.MenuModel;

import static id.wrbcatering.aplikasi.indihomo.Pras.SP_NAME;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.DaftarViewHolder> {


    Context mContext;
    private ArrayList<MenuModel> dataList;
String identifier;
SharedPreferences sharedPreferences;
    public MenuAdapter(ArrayList<MenuModel> dataList, Context context) {
        this.dataList = dataList;
        this.mContext= context;
        sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        identifier = sharedPreferences.getString("id","asede");

    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_menu, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarViewHolder holder, final int position) {
        holder.nama_tv.setText(dataList.get(position).getNama());
        holder.harga_tv.setText(dataList.get(position).getHarga());
        Picasso.get().load(Agus.DATA_URL + dataList.get(position).getGambar()).into(holder.gambar_iv);
        final String id = dataList.get(position).getId();

        //tambah handler onclick
        holder.tambah_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(mContext);  // this = context

                String url = Agus.URL + "addToCart";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("phoneHome Response", response);
                                if (response.equals("sukses")) {
                                    showDialog(1, dataList.get(position).getNama() + " Sudah dimasukan ke keranjang");
                                } else {
                                    showDialog(2,"Gagal menambahkan menu ke keranjang");
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("phoneHome Error", error.toString());
                                showDialog(2,"Terjadi Kesalahan saat menambahkan menu ke keranjang");
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("kue", Pras.bangsatkau(identifier));
                        params.put("menu", Pras.bangsatkau(id));

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }

    void showDialog(final int success,final String message) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    mContext);

            // set title dialog
            if (success == 1) {
                alertDialogBuilder.setTitle("Keranjang");
            } else {
                alertDialogBuilder.setTitle("Error");
            }


            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage(message)
                    .setIcon(R.drawable.logo)
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.dismiss();
                        }
                    });

            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();


    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_tv,harga_tv;
        ImageView gambar_iv;
        Button tambah_btn;
        public DaftarViewHolder(View itemView) {
            super(itemView);
            nama_tv = (TextView) itemView.findViewById(R.id.nama);
            harga_tv = (TextView) itemView.findViewById(R.id.harga);
            gambar_iv = (ImageView) itemView.findViewById(R.id.gambar);
            tambah_btn = (Button) itemView.findViewById(R.id.button3);

        }
    }
}
