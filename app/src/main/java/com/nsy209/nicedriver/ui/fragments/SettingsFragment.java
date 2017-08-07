package com.nsy209.nicedriver.ui.fragments;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.ui.activities.MainActivity;
import com.nsy209.nicedriver.utils.JSonLogger;
import com.xee.api.Xee;
import com.xee.api.entity.User;
import com.xee.auth.ConnectionCallback;
import com.xee.auth.SignInButton;
import com.xee.core.XeeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {


    private Unbinder unbinder;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, v);
        signInButton.setOnSignInClickResult(((MainActivity) getActivity()).getXeeApi(), new ConnectionCallback() {
            @Override
            public void onError(@NonNull Throwable error) {
                // sign in failed
                error.printStackTrace();
            }

            @Override
            public void onSuccess() {
                // sign in success
                Toast.makeText(getContext(), "You are connected, bravo !", Toast.LENGTH_SHORT).show();
                final Xee xee = ((MainActivity) getActivity()).getXeeApi();
                xee.connect(new ConnectionCallback() {

                    public void onSuccess() {
                        // once here, the SDK is initialized and ready to be used
                        Log.d("Xee", "Connection OK");
                        new GetUserTask().execute();
                    }

                    public void onError(Throwable error) {
                        // something went wrong during the authentication.
                        Log.d("Xee", "Connection ERROR");
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    /**
     * Simple AsyncTask to get current user with a synchronous request
     */
    private class GetUserTask extends AsyncTask<Void, Void, XeeRequest.Response> {
        @Override
        protected XeeRequest.Response doInBackground(Void... voids) {
            // Requête qui suit donnera de la merde : "Can't access to this endpoint without auth".
            // Même pas du JSON, impossible de voir ce que c'est, grosse galère.. J'ai du aller en
            // debug, chercher userXeeRequest.delegate.serviceMethod.relativeUrl pour comprendre
            // quelle url ça tapait, et tester dans postman...
            XeeRequest<User> userXeeRequest = ((MainActivity) getActivity()).getXeeApi().getUser();

            // Les requêtes qui suivent marcheraient si on les appelait
            ((MainActivity) getActivity()).getXeeApi().getCar("3");
            ((MainActivity) getActivity()).getXeeApi().getTrips("43");

            return userXeeRequest.execute();
        }

        @Override
        protected void onPostExecute(XeeRequest.Response response) {
            if (response.error != null) {
                showResult("getUser", response.error, false);
                return;
            }
            showResult("getUser", response, true);
        }
    }

    /**
     * Show the json response (one the right part)
     *
     * @param caller    the caller (web service name)
     * @param data      the data response
     * @param isSuccess true if response is successful, otherwise false
     */
    private void showResult(String caller, Object data, boolean isSuccess) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        final String prettyResult = JSonLogger.getPrettyLog(json);
        new AlertDialog.Builder(getContext()).setTitle(caller).setMessage(prettyResult).show();
    }
}
