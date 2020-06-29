package id.wrbcatering.aplikasi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.model.CarouselModel;
import id.wrbcatering.aplikasi.model.KategoriModel;
import id.wrbcatering.aplikasi.model.MenuModel;

import static android.view.View.GONE;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.DaftarViewHolder> {


    private ArrayList<KategoriModel> dataList;
    Context mContext;
    RequestQueue kuewe;



    public KategoriAdapter(ArrayList<KategoriModel> dataList, Context context, RequestQueue kuewe) {
        this.dataList = dataList;
        this.mContext= context;
        this.kuewe = kuewe;

    }


    @Override
    public DaftarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_kategori, parent, false);
        return new DaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DaftarViewHolder holder,final int position) {
        holder.rvx.setVisibility(GONE);
        holder.nama_tv.setText(dataList.get(position).getNama());
        String desc = dataList.get(position).getDesc().replace("<br>","\n");
        if (desc.equals("")) {
            holder.desc_tv.setVisibility(GONE);
        }
        holder.desc_tv.setText(desc);
        final String id = dataList.get(position).getId();
        String url = Agus.URL + "getMenu/" + id;


        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
             MenuAdapter adapterxnxx;
           ArrayList<MenuModel> menuArrayList;

            @Override
            public void onResponse(String response) {
                Log.e("NewsFrag", "Login Response: " + response.toString());
                menuArrayList = new ArrayList<>();
                try {
                    JSONObject jObjx = new JSONObject(response);
                    int success = jObjx.getInt("success");
                    String message = jObjx.getString("message");
                    String list = jObjx.getString("list");

                    if (success == 1) {
                        JSONArray jObj = new JSONArray(list);
                        menuArrayList.removeAll(menuArrayList);
                        for (int i = 0; i < jObj.length(); i++) {
                            JSONObject row = jObj.getJSONObject(i);
                            String nama = row.getString("name");
                            String id = row.getString("id");
                            String desc = row.getString("gambar");
                            String harga = row.getString("harga");
                            Log.e("NewsFrag", id + nama + harga + desc);
                            menuArrayList.add(new MenuModel(id,nama,harga,desc));
                        }
                        adapterxnxx = new MenuAdapter(menuArrayList, mContext);


                        holder.rvx.setAdapter(adapterxnxx);

                        holder.progressBar.setVisibility(GONE);
                        holder.rvx.setVisibility(View.VISIBLE);

                        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                        holder.rvx.setLayoutManager(layoutManager);


                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                    } else {
                        holder.progressBar.setVisibility(GONE);
                        Toast.makeText(mContext,  message , Toast.LENGTH_LONG).show();
                    }

                } catch(JSONException e){
                    // JSON error
                    holder.progressBar.setVisibility(GONE);
                    Toast.makeText(mContext, "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.progressBar.setVisibility(GONE);
                Log.e("NewsFrag", "Gagal Mendapatkan Daftar Menu: " + error.getMessage());
                Toast.makeText(mContext,
                        "Gagal Mendapatkan Daftar Menu" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        });

        // Adding request to request queue
        kuewe.add(strReq);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class DaftarViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_tv,desc_tv;
        RecyclerView rvx;
        ProgressBar progressBar;

        public DaftarViewHolder(View itemView) {
            super(itemView);
            nama_tv = (TextView) itemView.findViewById(R.id.textView3);
            desc_tv = (TextView) itemView.findViewById(R.id.textView7);
            rvx = itemView.findViewById(R.id.rvx);
            progressBar = itemView.findViewById(R.id.progressBar4);

        }
    }

}
