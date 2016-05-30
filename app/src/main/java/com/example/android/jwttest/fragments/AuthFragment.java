package com.example.android.jwttest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.jwttest.R;

/**
 * Created by Android on 5/27/2016.
 */
public class AuthFragment extends Fragment {
    TextView result;
    EditText et_userName, et_password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth,container,false);
        et_userName = (EditText)view.findViewById(R.id.editText_user_name);
        et_password = (EditText)view.findViewById(R.id.editText_password);
        result = (TextView)view.findViewById(R.id.text_response);
        view.findViewById(R.id.clear_response).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(R.string.response);
            }
        });
        return view;
    }
}
