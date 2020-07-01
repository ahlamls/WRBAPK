package id.wrbcatering.aplikasi.prakmen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.indihomo.Pras;

import static id.wrbcatering.aplikasi.indihomo.Pras.SP_NAME;

public class ContactFragment extends Fragment {
    String info;
    String kue;
    SharedPreferences sharedPreferences;
    RequestQueue queue;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        sharedPreferences = getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        kue = sharedPreferences.getString("id","asede");
        queue = Volley.newRequestQueue(getContext());

        Bundle bundle = this.getArguments();

        if(bundle != null){
            //Toast.makeText(getActivity(),"Aidi : " + String.valueOf(bundle.getInt("aidi")),Toast.LENGTH_SHORT).show();
            info = bundle.getString("info");
//
//            if (aidi == "") {
//                Toast.makeText(getActivity(),"Error . -2.2",Toast.LENGTH_LONG).show();
//
//            }
            //Toast.makeText(getActivity(),info,Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(),"Error . -2",Toast.LENGTH_LONG).show();
        }
        return view;

    }

    TextView title_tv,cod_text;
    EditText nama_et,nohp_et,alamat_et;
    Switch method_switch;
    Button pesan_btn;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        nama_et = getView().findViewById(R.id.nama);
        nohp_et = getView().findViewById(R.id.nohp);
        alamat_et = getView().findViewById(R.id.alamat);
        method_switch = getView().findViewById(R.id.switch1);
        pesan_btn = getView().findViewById(R.id.button);
        cod_text = getView().findViewById(R.id.textView8);
        cod_text.setVisibility(View.GONE);

        pesan_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String nama = nama_et.getText().toString();
                String nohp = nohp_et.getText().toString();
                String alamat = alamat_et.getText().toString();
                int method = 0;
                if (!method_switch.isChecked()) {
                    method = 1;
                }
                submitOrder(nama,nohp,alamat,method);

            }


        });

        method_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   method_switch.setText("Metode Pembayaran (Transfer)");
                   cod_text.setVisibility(View.GONE);
               } else {
                   cod_text.setVisibility(View.VISIBLE);
                   method_switch.setText("Metode Pembayaran (COD / Cash On Delivery)");
               }
            }
        });


        ImageView backImage = getView().findViewById(R.id.backImage);

        backImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                CartFragment fragment2 = new CartFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment2,"home")
                        .commit();
            }


        });




    }

    void submitOrder(final String nama,final String nohp,final String alamat,final int method) {
        String url = Agus.URL + "submitCart";

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("NewsFrag", "Login Response: " + response.toString());

                if (!response.equals("gagal")) {
                    //holder.harga_tv.setText(countHarga(price , finalizedAmount));
                    //holder.amount_et.setText(String.valueOf(finalizedAmount));
                    TrackFragment fragment2 = new TrackFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("aidi",response);
                    fragment2.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container, fragment2,"backtohome")
                            .commit();
                    Toast.makeText(getContext(),
                            "Sukses Melakukan Pemesanan! . No Order WRB-" + response , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),
                            "Gagal Melakukan Pemesanan" , Toast.LENGTH_LONG).show();
                }

//                if (type == 3) {

//                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("NewsFrag", "Gagal Melakukan pemesanan" + error.getMessage());
                Toast.makeText(getContext(),
                        "Gagal Melakukan Pemesanan" + error.getMessage(), Toast.LENGTH_LONG).show();

                // hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("info", Pras.bangsatkau(info));
                params.put("kue", Pras.bangsatkau(kue));
                params.put("nama", Pras.bangsatkau(nama));
                params.put("nohp", Pras.bangsatkau(nohp));
                params.put("alamat", Pras.bangsatkau(alamat));
                params.put("metode", Pras.bangsatkau(String.valueOf(method)));

                return params;
            }
        };

        // Adding request to request queue
        queue.add(strReq);
    }

}
