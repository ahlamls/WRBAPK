package id.wrbcatering.aplikasi.prakmen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.adapter.CarouselAdapter;
import id.wrbcatering.aplikasi.adapter.CartAdapter;
import id.wrbcatering.aplikasi.adapter.KategoriAdapter;
import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.indihomo.IMethodCaller;
import id.wrbcatering.aplikasi.indihomo.Pras;
import id.wrbcatering.aplikasi.model.CarouselModel;
import id.wrbcatering.aplikasi.model.CartModel;
import id.wrbcatering.aplikasi.model.KategoriModel;
import id.wrbcatering.aplikasi.model.MenuModel;

import static android.view.View.VISIBLE;
import static id.wrbcatering.aplikasi.indihomo.Pras.SP_NAME;

public class CartFragment extends Fragment {
    RequestQueue queue;
    String kue;
    SharedPreferences sharedPreferences;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        queue = Volley.newRequestQueue(getActivity());
        sharedPreferences = getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        kue = sharedPreferences.getString("id","asede");


        Bundle bundle = this.getArguments();

//        if(bundle != null){
//            //Toast.makeText(getActivity(),"Aidi : " + String.valueOf(bundle.getInt("aidi")),Toast.LENGTH_SHORT).show();
//            type = bundle.getString("type");
//
//            if (type == "") {
//                Toast.makeText(getActivity(),"Error . -2.2",Toast.LENGTH_LONG).show();
//
//            }
//        } else {
//            Toast.makeText(getActivity(),"Error . -2",Toast.LENGTH_LONG).show();
//        }
        return view;

    }
ProgressBar progressBar;
    RecyclerView rv3;
    EditText info_et;
    TextView total_tv;
    Button pesan_btn;
    LinearLayout ll;

    private CartAdapter adapter;
    private ArrayList<CartModel> cartArrayList;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressBar = getView().findViewById(R.id.progressBar);
        rv3 = getView().findViewById(R.id.rv3);
        info_et = getView().findViewById(R.id.info_et);
        total_tv = getView().findViewById(R.id.total);
        pesan_btn = getView().findViewById(R.id.button);
        ll = getView().findViewById(R.id.ll);

        loadCart();

        pesan_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                ContactFragment fragment2 = new ContactFragment();
                Bundle bundle = new Bundle();
                bundle.putString("info",info_et.getText().toString());
                fragment2.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"backtocart")
                        .commit();

            }
        });
    }

    void loadCartx() {
        loadCart();
    }

    void loadCart() {
        ll.setVisibility(View.GONE);
        progressBar.setVisibility(VISIBLE);
        rv3.setVisibility(View.GONE);

        getTotal();
        cartArrayList = new ArrayList<>();
        String url = Agus.URL + "cartList" ;

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("NewsFrag", "Login Response: " + response.toString());

                try {
                    JSONObject jObjx = new JSONObject(response);
                    int success = jObjx.getInt("success");
                    String message = jObjx.getString("message");
                    String list = jObjx.getString("list");

                    if (success == 1) {
                        JSONArray jObj = new JSONArray(list);
                        for (int i = 0; i < jObj.length(); i++) {
                            JSONObject row = jObj.getJSONObject(i);
                            String nama = row.getString("name");
                            String id = row.getString("id");
                            String gambar = row.getString("gambar");
                            int jumlah = row.getInt("jumlah");
                            int minorder = row.getInt("minorder");
                            int harga = row.getInt("harga");

                            Log.e("NewsFrag", id + nama + gambar + jumlah + minorder + harga);
                            cartArrayList.add(new CartModel(id,nama,gambar,jumlah,minorder,harga));
                        }
                        IMethodCaller iMethodCaller = new IMethodCaller() {
                            @Override
                            public void getKategori(final String id) {

                            }

                            @Override
                            public void loadCart() {
                                loadCartx();
                            }
                        };

                        adapter = new CartAdapter(cartArrayList, getActivity(),iMethodCaller,queue);


                        progressBar.setVisibility(View.GONE);
                        rv3.setVisibility(VISIBLE);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rv3.setLayoutManager(layoutManager);
                        rv3.setAdapter(adapter);

                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),  message , Toast.LENGTH_LONG).show();
                    }

                } catch(JSONException e){
                    // JSON error
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e("NewsFrag", "Gagal Mendapatkan Daftar Keranjang: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Daftar Keranjang" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("kue", Pras.bangsatkau(kue));

                return params;
            }
        };

        // Adding request to request queue
        queue.add(strReq);
    }

    void getTotal() {
        Boolean sukses = false;
        String url = Agus.URL + "totalCart";
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e("NewsFrag", "Login Response: " + response.toString());

                try {
                    JSONObject jObjx = new JSONObject(response);
                    int success = jObjx.getInt("success");
                    String message = jObjx.getString("message");


                    if (success == 1) {
                      total_tv.setText(jObjx.getString("total"));
                      if (jObjx.getString("total").equals("Rp 0")) {
                          Toast.makeText(getActivity(),  "Keranjang Masih Kosong" , Toast.LENGTH_LONG).show();

                      } else {
                          pesan_btn.setVisibility(VISIBLE);
                          ll.setVisibility(View.VISIBLE);
                      }
                        //share_btn.setVisibility(View.VISIBLE);
                        // Check for error node in json

                        // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                    } else {

                        Toast.makeText(getActivity(),  "Gagal Mendapatkan Total Keranjang" , Toast.LENGTH_LONG).show();
                    }

                } catch(JSONException e){
                    // JSON error
                    Toast.makeText(getActivity(), "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("NewsFrag", "Gagal Mendapatkan Total Keranjang: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Gagal Mendapatkan Total Keranjang" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("kue", Pras.bangsatkau(kue));

                return params;
            }
        };;

        // Adding request to request queue
       queue.add(strReq);
    }
}

