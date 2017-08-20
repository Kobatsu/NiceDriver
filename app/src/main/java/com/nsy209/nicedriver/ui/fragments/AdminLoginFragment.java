package com.nsy209.nicedriver.ui.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.ui.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminLoginFragment extends Fragment {


    private Unbinder unbinder;

    @BindView(R.id.go_button)
    Button goButton;
    @BindView(R.id.admin_login)
    EditText login;

    @BindView(R.id.admin_password)
    EditText password;


    public AdminLoginFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        AdminLoginFragment fragment = new AdminLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_login, container, false);
        unbinder = ButterKnife.bind(this, v);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().toLowerCase().equals("admin")
                        && password.getText().toString().toLowerCase().equals("ipstipst")) {
                    Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(MainActivity.USER_TYPE, MainActivity.TYPE_ADMIN);
                    editor.commit();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Login error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
