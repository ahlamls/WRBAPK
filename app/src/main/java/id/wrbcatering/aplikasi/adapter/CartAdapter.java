package id.wrbcatering.aplikasi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.indihomo.IMethodCaller;
import id.wrbcatering.aplikasi.indihomo.Pras;
import id.wrbcatering.aplikasi.model.CartModel;
import id.wrbcatering.aplikasi.model.MenuModel;

import static android.view.View.GONE;
import static id.wrbcatering.aplikasi.indihomo.Pras.SP_NAME;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.DaftarViewHolder> {


    private ArrayList<CartModel> dataList;
    Context mContext;
    RequestQueue kuewe;
    String identifier;
    SharedPreferences sharedPreferences;
    public CartAdapter(ArrayList<CartModel> dataList, Context context, IMethodCaller listener, RequestQueue queue) {
        this.dataList = dataList;
        this.mContext= context;
        this.listener = listener;
        this.kuewe = queue;
        sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        identifier = sharedPreferences.getString("id","asede");
    }

    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_cart, parent, false);
        return new DaftarViewHolder(view);
    }

    private IMethodCaller listener;
    @Override
    public void onBindViewHolder(final DaftarViewHolder holder, int position) {

        final String nama = dataList.get(position).getNama();
        holder.nama_tv.setText(nama);
        Picasso.get().load(Agus.DATA_URL + dataList.get(position).getGambar()).into(holder.gambar_iv);
        final String aidi = dataList.get(position).getId();
        final int harga = dataList.get(position).getHarga();
        final int minorder = dataList.get(position).getMinorder();
        final int amount = dataList.get(position).getJumlah();
        holder.harga_tv.setText(countHarga(harga,amount));
        holder.amount_et.setText(String.valueOf(amount));

        //tambah handler onclick
        holder.inc_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               editCart(aidi,2,amount ,holder,harga);
            }
        });
        holder.dec_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (amount <= minorder) {
                    Toast.makeText(mContext,"Minimal jumlah pembelian barang ini adalah " + String.valueOf(minorder),Toast.LENGTH_SHORT).show();
                } else {
                    editCart(aidi, 1,amount,holder,harga);
                }
            }
        });
        holder.hapus_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);

                // set title dialog

                    alertDialogBuilder.setTitle("Konfirmasi");



                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Apakah kamu yakin ingin menghapus " + nama + " dari keranjang")
                        .setIcon(R.drawable.logo)
                        .setCancelable(true)
                        .setPositiveButton("Hapus",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // jika tombol diklik, maka akan menutup activity ini
                                editCart(aidi,3,amount,holder,harga);
                            }
                        }).setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                       dialog.dismiss();
                    }});

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();

                }
        });

        holder.amount_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                int value = minorder;
                try {
                    if (Integer.valueOf(s.toString()) < minorder) {
                        holder.amount_et.setText(String.valueOf(minorder));
                    } else {
                        value = Integer.valueOf(s.toString());
                    }
                } catch (Exception e) {
                    holder.amount_et.setText(String.valueOf(minorder));
                }
                editCart(aidi,4,value,holder,harga);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
            });


    }


    void editCart(final String id , final int type,final int amount,final DaftarViewHolder holder,final int price) {

        int finalAmount  = amount;
        if (type == 1) {
            finalAmount = finalAmount - 1;
        } else if (type == 2) {
            finalAmount = finalAmount + 1;
        } else if (type == 3){


        } else if (type == 4) {
            finalAmount = finalAmount;
        }



        final int finalizedAmount = finalAmount;
        Boolean sukses = false;
        String url = "";
        if (type != 3) {
            url = Agus.URL + "updateCart/" + id;
        } else {
            url = Agus.URL + "deleteCart/" + id;
        }
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("NewsFrag", "Login Response: " + response.toString());

                if (response.equals("sukses")) {
                    //holder.harga_tv.setText(countHarga(price , finalizedAmount));
                    //holder.amount_et.setText(String.valueOf(finalizedAmount));
                    listener.loadCart();
                } else {
                    Toast.makeText(mContext,
                            "Gagal Mengubah Jumlah Keranjang " , Toast.LENGTH_LONG).show();
                }

//                if (type == 3) {

//                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("NewsFrag", "Gagal Mengubah jumlah" + error.getMessage());
                Toast.makeText(mContext,
                        "Gagal Mengubah Jumlah" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("kue", Pras.bangsatkau(identifier));
                params.put("amount", Pras.bangsatkau(String.valueOf(finalizedAmount)));

                return params;
            }
        };

        // Adding request to request queue
        kuewe.add(strReq);

       // holder.harga_tv.setText(countHarga(harga,amount));

    }
//
//    void coli(final String aidi) {

//    }

    String countHarga(final int price , final int amount) {
        String text = "Rp " + NumberFormat.getNumberInstance(Locale.US).format(price) + " x " + amount + " | Rp " + NumberFormat.getNumberInstance(Locale.US).format(price * amount);
        return text;
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_tv,harga_tv;
        EditText amount_et;
        Button dec_btn,inc_btn,hapus_btn;
        private ImageView gambar_iv;

        public DaftarViewHolder(View itemView) {
            super(itemView);
            nama_tv = (TextView) itemView.findViewById(R.id.nama);
            harga_tv = (TextView) itemView.findViewById(R.id.harga);
            dec_btn = itemView.findViewById(R.id.dec_btn);
            amount_et = itemView.findViewById(R.id.amount_et);
            inc_btn = itemView.findViewById(R.id.inc_btn);
            hapus_btn = itemView.findViewById(R.id.hapus_btn);

            gambar_iv = (ImageView) itemView.findViewById(R.id.gambar);

        }
    }
}
