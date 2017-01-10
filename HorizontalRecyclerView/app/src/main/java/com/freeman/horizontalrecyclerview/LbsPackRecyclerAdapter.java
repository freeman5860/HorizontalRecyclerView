package com.freeman.horizontalrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.freeman.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class LbsPackRecyclerAdapter extends RecyclerView.Adapter<LbsPackRecyclerAdapter.ViewHolder>  {

    private int mPagePadding = 15;
    private int mShowLeftCardWidth = 15;

    private List<Integer> mList = new ArrayList<>();

    RecyclerView mRecyclerView;
    RecyclerScaleHelper mRecyclerScaleHelper = null;

    public LbsPackRecyclerAdapter(RecyclerView view, List<Integer> mList) {
        this.mList = mList;

        mRecyclerView = view;
        mRecyclerScaleHelper = new RecyclerScaleHelper();
        mRecyclerScaleHelper.attachToRecyclerView(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        lp.width = parent.getWidth() - dip2px(itemView.getContext(), 2 * (mPagePadding + mShowLeftCardWidth));
        itemView.setLayoutParams(lp);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int padding = dip2px(holder.itemView.getContext(), mPagePadding);
        holder.itemView.setPadding(padding, 0, padding, 0);
        int leftMarin = position == 0 ? padding + dip2px(holder.itemView.getContext(), mShowLeftCardWidth) : 0;
        int rightMarin = position == getItemCount() - 1 ? padding + dip2px(holder.itemView.getContext(), mShowLeftCardWidth) : 0;
        setViewMargin(holder.itemView, leftMarin, 0, rightMarin, 0);

        holder.mImageView.setImageResource(mList.get(position));
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPos = holder.getPosition();
                mList.remove(curPos);
                notifyItemRemoved(curPos);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerScaleHelper.adjustCurrentItemPos();
                        mRecyclerScaleHelper.computeCurrentItemPos();
                        mRecyclerScaleHelper.onScrolledChangedCallback();
                    }
                }, 300);
                Toast.makeText(holder.mImageView.getContext(), "" + curPos, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
