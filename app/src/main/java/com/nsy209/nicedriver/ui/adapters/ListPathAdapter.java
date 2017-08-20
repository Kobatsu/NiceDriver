package com.nsy209.nicedriver.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nsy209.nicedriver.R;
import com.nsy209.nicedriver.model.objects.Path;
import com.nsy209.nicedriver.ui.activities.MainActivity;
import com.nsy209.nicedriver.utils.DateUtils;
import com.nsy209.nicedriver.utils.GeoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sébastien on 13/07/2017.
 */

public class ListPathAdapter extends RecyclerView.Adapter<ListPathAdapter.PathHolder> {

    private final List<Path> mList;
    private final MainActivity mMainActivity;

    public ListPathAdapter(final MainActivity mainActivity, List<Path> list) {
        mMainActivity = mainActivity;
        mList = list;
    }

    @Override
    public PathHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_path, parent, false);
        return new PathHolder(v);
    }

    @Override
    public void onBindViewHolder(PathHolder pathHolder, int i) {
        final Path path = mList.get(pathHolder.getLayoutPosition());

        pathHolder.startAdress.setText(GeoUtils.printAddress(path.getStartAddress()));
        pathHolder.startDate.setText(DateUtils.formatDate(path.getStartDate()));
        pathHolder.endAdress.setText(GeoUtils.printAddress(path.getEndAddress()));
        pathHolder.endDate.setText(DateUtils.formatDate(path.getEndDate()));
        pathHolder.distance.setText(GeoUtils.printDistance(GeoUtils.distanceInMeters(new LatLng(path.getStartAddress().getLatitude(),
                path.getStartAddress().getLongitude()), path.getStartAltitude(), new LatLng(path.getEndAddress().getLatitude(),
                path.getEndAddress().getLongitude()),path.getEndAltitude())));
        pathHolder.time.setText(DateUtils.formatDifference(path.getTime()));
        pathHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.displayTrip(path.getTrip());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(List<Path> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
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
