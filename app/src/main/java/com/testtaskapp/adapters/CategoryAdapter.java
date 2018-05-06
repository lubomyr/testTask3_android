package com.testtaskapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.testtaskapp.R;
import com.testtaskapp.databinding.ItemCategoryBinding;
import com.testtaskapp.entities.Category;
import com.testtaskapp.entities.FeedItem;
import com.testtaskapp.repository.FeedsRepository;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements FeedsAdapter.OnItemClickListener {
    private int mResources;
    private List<Category> itemList;
    private CategoryAdapter.Listener listener;
    private Context mContext;

    public CategoryAdapter(Context context, int resources) {
        this.mResources = resources;
        this.mContext = context;
    }


    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), mResources, parent, false);

        return new CategoryAdapter.ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category item = itemList.get(position);

        holder.setItem(item);
        holder.itemView.setTag(item);
        
        holder.binding.setItem(item);
        List<FeedItem> favorites = FeedsRepository.getFavoritesByCategory(item.getName());
        bindFeedsAdapter(holder.binding, favorites);
        holder.binding.executePendingBindings();
    }

    private void bindFeedsAdapter(ItemCategoryBinding binding, List<FeedItem> items) {
        binding.recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        FeedsAdapter feedsAdapter = new FeedsAdapter(mContext, R.layout.item_feed);
        feedsAdapter.setOnItemClickListener(this);
        feedsAdapter.setData(items);
        binding.recycler.setAdapter(feedsAdapter);
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

    public void setData(List<Category> list) {
        this.itemList = list;
    }

    @Override
    public void onItemClick(FeedItem item) {
        if (listener != null)
            listener.onItemClick(item);
    }

    public void setListener(CategoryAdapter.Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onItemClick(FeedItem item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;
        private CategoryAdapter.Listener listener;
        private Category item;

        ViewHolder(ItemCategoryBinding binding, CategoryAdapter.Listener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void setItem(Category item) {
            this.item = item;
        }
    }
}