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
import com.nsy209.nicedriver.model.AppDatabase;
import com.nsy209.nicedriver.model.objects.Location;
import com.nsy209.nicedriver.model.objects.Signal;
import com.nsy209.nicedriver.ui.activities.MainActivity;
import com.nsy209.nicedriver.utils.JSonLogger;
import com.xee.api.Xee;
import com.xee.api.entity.Car;
import com.xee.auth.ConnectionCallback;
import com.xee.auth.SignInButton;
import com.xee.core.XeeRequest;
import com.xee.core.entity.Error;

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
                        //debug purpose
                        new GetUserTask().execute();
                        ((MainActivity) getActivity()).getXeeApi().getCar("43").enqueue(new XeeRequest.Callback<Car>() {
                            @Override
                            public void onSuccess(Car car) {
                                try {
                                    Log.d("Xee", "Car ok");
                                    AppDatabase.getAppDatabase(getContext()).tripDao().insertAll(com.nsy209.nicedriver.model.objects.Trip.convertFromXee(((MainActivity) getActivity()).getXeeApi().getTrips(car.getId()).execute().item));
                                    AppDatabase.getAppDatabase(getContext()).signalDao().insertAll(Signal.convertFromXee(((MainActivity) getActivity()).getXeeApi().getSignals(car.getId()).execute().item));
                                    AppDatabase.getAppDatabase(getContext()).locationDao().insertAll(Location.convertFromXee(((MainActivity) getActivity()).getXeeApi().getLocations(car.getId()).execute().item));
                                    Log.d("Xee", "All ok");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Error error) {
                                Log.d("Xee", "onError");
                            }
                        });


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // SANDBOX prend pas en compte l'authentification : toutes les urls avec users/me/ sont pas utilisables.
                                // On peut pas récup le car d'un user du coup on le récupere direct via l'id du car (mais du coup on a déjà l'id dont on a besoin, un peu stupide)
//                                ((MainActivity) getActivity()).getXeeApi().getCars();
                                try {
                                    // Cette partie devrait marcher mais ça renvoit toujours un vieu SocketTimeOutException que je ne comprends pas (aucune aide...)
                                    // Taper la même chose avec postman marche (ou alors j'ai fait une boulette?)
                                    Car car = ((MainActivity) getActivity()).getXeeApi().getCar("43").execute().item;
                                    AppDatabase.getAppDatabase(getContext()).tripDao().insertAll(com.nsy209.nicedriver.model.objects.Trip.convertFromXee(((MainActivity) getActivity()).getXeeApi().getTrips(car.getId()).execute().item));
                                    AppDatabase.getAppDatabase(getContext()).signalDao().insertAll(Signal.convertFromXee(((MainActivity) getActivity()).getXeeApi().getSignals(car.getId()).execute().item));
                                    AppDatabase.getAppDatabase(getContext()).locationDao().insertAll(Location.convertFromXee(((MainActivity) getActivity()).getXeeApi().getLocations(car.getId()).execute().item));
                                    // Penser à aller dans les paramètres de l'appli (dans les paramètres android) et ajouter l'aurotisation d'écriture pour pouvoir écrire le fichier
                                    // On peut l'explorer ensuite en le copiant sur un pc avec par exemple SQLite Studio
                                    AppDatabase.exportDatabase(getContext(), "niceDriver.db");
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "Terminé", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
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
//            XeeRequest<User> userXeeRequest = ((MainActivity) getActivity()).getXeeApi().getUser();

            // Les requêtes qui suivent marcheraient si on les appelait
//            ((MainActivity) getActivity()).getXeeApi().getCar("3");
//            ((MainActivity) getActivity()).getXeeApi().getTrips("43");
//            ((MainActivity) getActivity()).getXeeApi().getSignals("43");


//            Car car = ((MainActivity) getActivity()).getXeeApi().getCar("43").execute().item;
//            AppDatabase.getAppDatabase(getContext()).tripDao().insertAll(com.nsy209.nicedriver.model.objects.Trip.convertFromXee(((MainActivity) getActivity()).getXeeApi().getTrips(car.getId()).execute().item));
//            AppDatabase.getAppDatabase(getContext()).signalDao().insertAll(Signal.convertFromXee(((MainActivity) getActivity()).getXeeApi().getSignals(car.getId()).execute().item));
//            AppDatabase.getAppDatabase(getContext()).locationDao().insertAll(Location.convertFromXee(((MainActivity) getActivity()).getXeeApi().getLocations(car.getId()).execute().item));
//            // Penser à aller dans les paramètres de l'appli (dans les paramètres android) et ajouter l'aurotisation d'écriture pour pouvoir écrire le fichier
//            // On peut l'explorer ensuite en le copiant sur un pc avec par exemple SQLite Studio
//            AppDatabase.exportDatabase(getContext(), "niceDriver.db");

            return ((MainActivity) getActivity()).getXeeApi().getCar("43").execute();

        }

        @Override
        protected void onPostExecute(XeeRequest.Response response) {
            if (response.error != null) {
                showResult("getCar", response.error, false);
                return;
            }
            showResult("getCar", response, true);
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
