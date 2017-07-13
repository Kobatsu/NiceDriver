package com.nsy209.nicedriver.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.objects.Path;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sébastien on 13/07/2017.
 */

public class ListPathAdapter extends RecyclerView.Adapter<ListPathAdapter.PathHolder> {

    private final ArrayList<Path> mList;

    public ListPathAdapter() {
        mList = new ArrayList<>();
        mList.add(new Path("6 avenue Saint-Granier, Toulouse", "13/07/2017 13:20",
                "5 rue du Pêcheur, Toulouse", "13/07/2017 13h42",
                "12 km", "22 min"));
        mList.add(new Path("7 avenue Saint-Granier, Toulouse", "14/07/2017 15:20",
                "12 rue du Rosier, Toulouse", "13/07/2017 15h40",
                "12 km", "22 min"));
        mList.add(new Path("6 avenue Pompidou, Perigueux", "15/07/2017 08:20",
                "8 avenue Saint-Martin, Toulouse", "13/07/2017 11h35",
                "256 km", "3h15"));
        mList.add(new Path("4 route de Grenade, Blagnac", "13/07/2017 14:20",
                "37 rue de Zarowski, Toulouse", "13/07/2017 15h03",
                "24 km", "43 min"));
        mList.add(new Path("6 avenue Saint-Granier, Toulouse", "13/07/2017 16:20",
                "42 route de Mendes, Toulouse", "13/07/2017 16h47",
                "15 km", "27 min"));

    }

    @Override
    public PathHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_path, parent, false);
        return new PathHolder(v);
    }

    @Override
    public void onBindViewHolder(PathHolder pathHolder, int i) {
        Path path = mList.get(pathHolder.getLayoutPosition());

        pathHolder.startAdress.setText(path.getStartAdress());
        pathHolder.startDate.setText(path.getStartDate());
        pathHolder.endAdress.setText(path.getEndAdress());
        pathHolder.endDate.setText(path.getEndDate());
        pathHolder.distance.setText(path.getDistance());
        pathHolder.time.setText(path.getTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PathHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_path_start_adress)
        TextView startAdress;
        @BindView(R.id.item_path_start_date)
        TextView startDate;
        @BindView(R.id.item_path_end_adress)
        TextView endAdress;
        @BindView(R.id.item_path_end_date)
        TextView endDate;
        @BindView(R.id.item_path_distance)
        TextView distance;
        @BindView(R.id.item_path_time)
        TextView time;

        public PathHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
