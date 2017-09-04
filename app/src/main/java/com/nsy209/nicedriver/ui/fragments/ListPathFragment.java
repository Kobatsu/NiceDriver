package com.nsy209.nicedriver.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.objects.Path;
import com.nsy209.nicedriver.tasks.UpdateAdapterWithPathTask;
import com.nsy209.nicedriver.tasks.UpdateDataXeeTask;
import com.nsy209.nicedriver.ui.activities.LoginActivity;
import com.nsy209.nicedriver.ui.activities.MainActivity;
import com.nsy209.nicedriver.ui.adapters.ListPathAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListPathFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPathFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    @BindView(R.id.list_path)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private ListPathAdapter mAdapter;

    public ListPathFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListPathFragment.
     */
    public static ListPathFragment newInstance() {
        ListPathFragment fragment = new ListPathFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_path, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new ListPathAdapter((MainActivity) getActivity(), new ArrayList<Path>()));
        updateList();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataXee();
                updateList();
            }
        });


        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_disconnect:
                        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove(MainActivity.USER_TYPE).commit();
                        getActivity().finish();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

//                //get suggestions based on newQuery
//
//                //pass them on to the search view
//                mSearchView.swapSuggestions(newSuggestions);
            }
        });

        return view;
    }

    private void updateDataXee() {
        new UpdateDataXeeTask(getContext(), mAdapter).execute();
    }

    private void updateList() {
        new UpdateAdapterWithPathTask(getContext(), mAdapter).execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
