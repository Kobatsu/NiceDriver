package com.nsy209.nicedriver.ui.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.services.ApiSingleton;
import com.nsy209.nicedriver.ui.activities.MainActivity;
import com.xee.auth.ConnectionCallback;
import com.xee.auth.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserLoginFragment extends Fragment {


    private Unbinder unbinder;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.go_button)
    Button goButton;

    public UserLoginFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        UserLoginFragment fragment = new UserLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_login, container, false);
        unbinder = ButterKnife.bind(this, v);
        goButton.setClickable(false);
        signInButton.setClickable(true);

        signInButton.setOnSignInClickResult(ApiSingleton.getXeeApi(), new ConnectionCallback() {
            @Override
            public void onError(@NonNull Throwable throwable) {
                Toast.makeText(getContext(), "Error signin", Toast.LENGTH_SHORT).show();
                goButton.setEnabled(false);
                signInButton.setEnabled(true);
            }

            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "Success signin", Toast.LENGTH_SHORT).show();
                goButton.setEnabled(true);
                signInButton.setEnabled(false);
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MainActivity.USER_TYPE, MainActivity.TYPE_DRIVER);
                editor.commit();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
