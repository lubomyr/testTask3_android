package com.testtaskapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.testtaskapp.databinding.ItemFeedBinding;
import com.testtaskapp.entities.FeedItem;

import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    private int mResources;
    private List<FeedItem> itemList;
    private FeedsAdapter.OnItemClickListener onItemClickListener;
    private Context mContext;
    private FeedItem selectedItem;

    public FeedsAdapter(Context context, int resources) {
        this.mResources = resources;
        this.mContext = context;
    }


    @Override
    public FeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFeedBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), mResources, parent, false);

        return new FeedsAdapter.ViewHolder(binding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(FeedsAdapter.ViewHolder holder, int position) {
        FeedItem item = itemList.get(position);
        holder.setItem(item);
        holder.itemView.setTag(item);

        Glide.with(mContext).load(item.getArtworkUrl100()).into(holder.binding.image);
        holder.binding.setItem(item);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setData(List<FeedItem> list) {
        this.itemList = list;
    }

    public void setOnItemClickListener(FeedsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(FeedItem item);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemFeedBinding binding;
        private FeedsAdapter.OnItemClickListener onItemClickListener;
        private FeedItem item;

        ViewHolder(ItemFeedBinding binding, FeedsAdapter.OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = onItemClickListener;
            binding.layout.setOnClickListener(FeedsAdapter.ViewHolder.this);
        }

        public void setItem(FeedItem item) {
            this.item = item;
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                selectedItem = item;
                onItemClickListener.onItemClick(item);
                notifyDataSetChanged();
            }
        }
    }
}