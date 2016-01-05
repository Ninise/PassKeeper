package nkteam.com.passkeeper.passlist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import nkteam.com.passkeeper.R;
import nkteam.com.passkeeper.database.PKDataModel;

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.ViewHolder> {

    List <PKDataModel> mDataSet;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView mUrlTextView;
       CardView cv;

        public ViewHolder(View itemtView) {
            super(itemtView);
            cv = (CardView) itemtView.findViewById(R.id.cv);
            mUrlTextView = (TextView) itemtView.findViewById(R.id.data_url);
        }
    }

    public PassAdapter (List<PKDataModel> dataset, Context context) {
        mDataSet = dataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mUrlTextView.setText(mDataSet.get(position).getUrl());
        animate(holder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void insert(int position, PKDataModel data) {
        mDataSet.add(position, data);
        notifyItemInserted(position);
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context,
                R.anim.anticipate_overshoot_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }


    public void remove(PKDataModel data) {
        int position = mDataSet.indexOf(data);
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }
}