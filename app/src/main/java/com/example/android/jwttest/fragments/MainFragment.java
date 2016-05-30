package com.example.android.jwttest.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.jwttest.R;

/**
 * Created by Android on 5/27/2016.
 */
public class MainFragment extends Fragment {
    Fragment fragment;
    FragmentManager fragmentManager;
    Button button_jwt_fragment, button_auth_fragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        button_jwt_fragment = (Button)view.findViewById(R.id.button_jwt_fragment);
        button_auth_fragment = (Button)view.findViewById(R.id.button_auth_fragment);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button_jwt_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentManager==null){
                    fragmentManager = getFragmentManager();
                }

                fragment = fragmentManager.findFragmentByTag("jwt");
                if(fragment==null){
                    fragment = new JwtFragment();
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, fragment, "jwt");
                fragmentTransaction.addToBackStack("jwt");
                fragmentTransaction.commit();
            }
        });



        button_auth_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager == null) {
                    fragmentManager = getFragmentManager();
                }
                fragment = fragmentManager.findFragmentByTag("auth");
                if (fragment == null) {
                    fragment = new AuthFragment();
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, fragment, "auth");
                fragmentTransaction.addToBackStack("auth");
                fragmentTransaction.commit();
            }
        });
    }
}
