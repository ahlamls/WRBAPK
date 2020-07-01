package id.wrbcatering.aplikasi.prakmen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.adapter.CarouselAdapter;
import id.wrbcatering.aplikasi.adapter.KategoriAdapter;
import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.indihomo.IMethodCaller;
import id.wrbcatering.aplikasi.model.CarouselModel;
import id.wrbcatering.aplikasi.model.KategoriModel;

public class HomeFragment extends Fragment implements IMethodCaller {
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        ProgressBar progressBar2,progressBar3;
        Button lacak_btn;
        TextView asede;
        RecyclerView rv1,rv2;
    RequestQueue queue;
    private CarouselAdapter adapter;
    private ArrayList<CarouselModel> carouselArrayList;
    private KategoriAdapter adapterx;
    private ArrayList<KategoriModel> kategoriArrayList;

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            progressBar2 = getView().findViewById(R.id.progressBar2);
            progressBar3 = getView().findViewById(R.id.progressBar3);
            lacak_btn = getView().findViewById(R.id.button2);
            rv1 = getView().findViewById(R.id.rv1);
            rv2 = getView().findViewById(R.id.rv2);
            asede = getView().findViewById(R.id.asede);
            queue = Volley.newRequestQueue(getView().getContext());

            lacak_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showLacakDialog();
                   }
            });
            getCarousel();


        }

        void getCarousel() {

            carouselArrayList = new ArrayList<>();
            String url = Agus.URL + "getCarousel" ;

            StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

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
                                Log.e("NewsFrag", id + nama + gambar);
                                carouselArrayList.add(new CarouselModel(id,nama,gambar));
                            }
                            IMethodCaller iMethodCaller = new IMethodCaller() {
                                @Override
                                public void getKategori(final String id) {
                                    getKategorix(id);
                                }

                                @Override
                                public void loadCart() {

                                }
                            };

                            adapter = new CarouselAdapter(carouselArrayList, getActivity(),iMethodCaller);


                            progressBar2.setVisibility(View.GONE);
                            lacak_btn.setVisibility(View.VISIBLE);
                            asede.setVisibility(View.VISIBLE);
                            rv1.setVisibility(View.VISIBLE);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            rv1.setLayoutManager(layoutManager);
                            rv1.setAdapter(adapter);
                            getKategori("");
                            //share_btn.setVisibility(View.VISIBLE);
                            // Check for error node in json

                            // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                        } else {
                            progressBar2.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),  message , Toast.LENGTH_LONG).show();
                        }

                    } catch(JSONException e){
                        // JSON error
                        progressBar2.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar2.setVisibility(View.GONE);
                    Log.e("NewsFrag", "Gagal Mendapatkan Daftar Kategori: " + error.getMessage());
                    Toast.makeText(getActivity(),
                            "Gagal Mendapatkan Daftar Kategori" + error.getMessage(), Toast.LENGTH_LONG).show();

                    // hideDialog();

                }
            });

            // Adding request to request queue
            queue.add(strReq);



        }

        void getKategorix(final String id) {
            getKategori(id);
        }

        public void getKategori(final String id) {
            rv2.setVisibility(View.GONE);
            progressBar3.setVisibility(View.VISIBLE);

            kategoriArrayList = new ArrayList<>();
            String url = Agus.URL + "getKategori/" + id;
            if (id.equals("")) {
                 url = Agus.URL + "getKategori";
            }
            StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

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
                                String desc = row.getString("desc");
                                Log.e("GetKategori", id + nama + desc);
                                kategoriArrayList.add(new KategoriModel(id,nama,desc));
                            }
                            adapterx = new KategoriAdapter(kategoriArrayList, getActivity(),queue);


                            progressBar3.setVisibility(View.GONE);
                            rv2.setVisibility(View.VISIBLE);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rv2.setLayoutManager(layoutManager);
                            rv2.setAdapter(adapterx);
                            //share_btn.setVisibility(View.VISIBLE);
                            // Check for error node in json

                            // Log.e("Successfully Login!", jObj.toString());



                        /*Toast.makeText(getActivity(),
                                jObj.getString("ReffFrag"), Toast.LENGTH_LONG).show();*/
                        } else {
                            progressBar2.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),  message , Toast.LENGTH_LONG).show();
                        }

                    } catch(JSONException e){
                        // JSON error
                        progressBar2.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Gagal Parsing " + e.toString(), Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar2.setVisibility(View.GONE);
                    Log.e("NewsFrag", "Gagal Mendapatkan Daftar Kategori: " + error.getMessage());
                    Toast.makeText(getActivity(),
                            "Gagal Mendapatkan Daftar Kategori " + error.getMessage(), Toast.LENGTH_LONG).show();

                    // hideDialog();

                }
            });

            // Adding request to request queue
            queue.add(strReq);
            //progressBar3.setVisibility(View.GONE);
        }

    @Override
    public void loadCart() {

    }

    void showLacakDialog() {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            final EditText edittext = new EditText(getActivity());
            edittext.setBackgroundResource(R.drawable.tvbg);

            edittext.setHint("WRB-xxxx");
            edittext.setAllCaps(true);
            alert.setMessage("Masukan Kode Order");
            alert.setTitle("Lacak Pesanan");

            alert.setView(edittext);


            alert.setPositiveButton("Lacak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //What ever you want to do with the value
                    String YouEditTextValue = edittext.getText().toString();
                    if (YouEditTextValue.startsWith("WRB-")) {
                       String aidi = YouEditTextValue.replace("WRB-" , "");
                        boolean numeric = true;
                       try {
                            int num = Integer.valueOf(aidi);
                        } catch (NumberFormatException e) {
                            numeric = false;
                        }

                       if (numeric) {
                           track(aidi);
                       } else {
                           Toast.makeText(getContext(),"Kode Order Tidak Valid",Toast.LENGTH_LONG).show();
                       }

                    } else {
                        Toast.makeText(getContext(),"Kode Order Tidak Valid",Toast.LENGTH_LONG).show();
                    }
                }
            });

            alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                   dialog.dismiss();
                }
            });

            alert.show();
        }

        void track(final String id) {
            TrackFragment fragment2 = new TrackFragment();
            Bundle bundle = new Bundle();
            bundle.putString("aidi",id);
            fragment2.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container, fragment2,"backtohome")
                    .commit();
        }
}
