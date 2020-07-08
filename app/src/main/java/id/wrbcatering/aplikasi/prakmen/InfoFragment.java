package id.wrbcatering.aplikasi.prakmen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import id.wrbcatering.aplikasi.R;
import id.wrbcatering.aplikasi.adapter.CarouselAdapter;
import id.wrbcatering.aplikasi.adapter.KategoriAdapter;
import id.wrbcatering.aplikasi.indihomo.IMethodCaller;
import id.wrbcatering.aplikasi.model.CarouselModel;
import id.wrbcatering.aplikasi.model.KategoriModel;

public class InfoFragment extends Fragment {
Button button4,button5,button6;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);


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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        button4 = (Button) getView().findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = "https://instagram.com/wrb_catering";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        button5= (Button) getView().findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = "https://wa.me/6281398741770?text=Assalamualaikum";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        button6 = (Button) getView().findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = "https://online.wrbcatering.id/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }
}