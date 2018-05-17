package fr.wcs.hackathon2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AcceuilFragment extends android.support.v4.app.Fragment {

    public static android.support.v4.app.Fragment newInstance() {
        AcceuilFragment fragment = new AcceuilFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_acceuil,container,false);

       return view;
    }
}
