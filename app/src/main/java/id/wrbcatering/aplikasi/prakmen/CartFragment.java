package id.wrbcatering.aplikasi.prakmen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.wrbcatering.aplikasi.R;

public class CartFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

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
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
