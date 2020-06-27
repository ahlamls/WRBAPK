package id.wrbcatering.aplikasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import id.wrbcatering.aplikasi.indihomo.Agus;
import id.wrbcatering.aplikasi.indihomo.Pras;
import id.wrbcatering.aplikasi.prakmen.CartFragment;
import id.wrbcatering.aplikasi.prakmen.HomeFragment;

import static id.wrbcatering.aplikasi.indihomo.Pras.SP_NAME;
import static id.wrbcatering.aplikasi.indihomo.Pras.generateId;

public class MainActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {
    private String identifier;

    String manufacturer = Build.MANUFACTURER;
    String model = Build.MODEL;
    String versi = Build.VERSION.RELEASE;
    String boardname = Build.BOARD;
    String brand = Build.BRAND;
    String productname = Build.PRODUCT;

    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        identifier = sharedpreferences.getString("id","asede");
        if (identifier.equals("asede")) {
           identifier = generateId();
           phoneHome(identifier);
        }

        checkUpdate();


// inisialisasi BottomNavigaionView
         bottomNavigationView= findViewById(R.id.bn_main);
         bottomNavigationView.setVisibility(View.GONE);
// beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

// method untuk load fragment yang sesuai
        private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.fl_container, fragment)
        .commit();
        return true;
        }
        return false;
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                case R.id.home_menu:
                fragment = new HomeFragment();
                break;
                case R.id.cart_menu:
                fragment = new CartFragment();
                break;
                }
                return loadFragment(fragment);
                }

    public void phoneHome(final String id) {
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        final String sayamaleus = "WRBAPK/" + Pras.APP_VERSION + " Android/" + versi + " boardname/" + boardname + " brand/" + brand + " model/" + model + " productname/" + productname ;

        String url = Agus.URL + "setorKue";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("phoneHome Response", response);
                        if (response.equals("sukses")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("id",id);
                            editor.apply();
                        } else {
                            showExitDialog("Kesalahan Server . Pastikan Layanan WRB Tidak diblokir");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("phoneHome Error", error.toString());
                        showExitDialog("Gagal Koneksi Terhadap Server . Error : " +  error.toString());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("kue", Pras.bangsatkau(id));
                params.put("ua", Pras.bangsatkau(sayamaleus));

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void checkUpdate() {
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        String url = Agus.URL + "cekApdet";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("checkUpdate Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");
                            String url = jObj.getString("url");
                            int uptodate = jObj.getInt("uptodate");
                            String message = jObj.getString("message");

                            // Display the first 500 characters of the response string.

                            if (success == 1) {

                                if (uptodate == 1) {
                                    bottomNavigationView.setVisibility(View.VISIBLE);
                                    loadFragment(new HomeFragment());
                                } else {
                                    showUpdateDialog(message,url);
                                }

                            } else {
                                showExitDialog("Gagal Cek Pembaruan. Pastikan Terkoneksi dengan jaringan internet " + message);
                            }


                        } catch (JSONException e) {
                            showExitDialog("Kesalahan Parsing . " + e.toString());
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("checkUpdate Error", error.toString());
                        showExitDialog("Gagal Koneksi Terhadap Server untuk periksa pembaharuan . Error : " +  error.toString());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("v", Pras.bangsatkau(Pras.APP_VERSION));

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void showUpdateDialog(final String message,final String url){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(R.string.app_name);

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(message)
                .setIcon(R.drawable.logo)
                .setCancelable(false)
                .setPositiveButton("Update",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        //intent ke url update
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                }).setNegativeButton("Tutup",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // jika tombol diklik, maka akan menutup activity ini
                System.exit(0);
            }
        });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showExitDialog(final String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(R.string.app_name);

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(message)
                .setIcon(R.drawable.logo)
                .setCancelable(false)
                .setPositiveButton("Tutup Aplikasi",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        System.exit(1);
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }




                }
