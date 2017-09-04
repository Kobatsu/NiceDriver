package com.nsy209.nicedriver.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.nsy209.nicedriver.model.objects.Path;
import com.nsy209.nicedriver.ui.adapters.ListPathAdapter;

import java.util.List;

/**
 * Created by Sébastien on 31/08/2017.
 */

public class UpdateAdapterWithPathTask extends AsyncTask<Void, Void, List<Path>> {

    private final Context mContext;
    private final ListPathAdapter mAdapter;

    public UpdateAdapterWithPathTask(Context context, ListPathAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected List<Path> doInBackground(Void... voids) {
        return Path.getList(mContext);
    }

    @Override
    protected void onPostExecute(List<Path> paths) {
        super.onPostExecute(paths);
        mAdapter.updateList(paths);
    }
}
