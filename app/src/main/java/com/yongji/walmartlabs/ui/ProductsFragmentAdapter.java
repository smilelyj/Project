package com.yongji.walmartlabs.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yongji.walmartlabs.R;
import com.yongji.walmartlabs.config.ApplicationConstant;
import com.yongji.walmartlabs.databinding.AdapterProductsFragmentBinding;
import com.yongji.walmartlabs.listeners.OnLoadMoreListener;
import com.yongji.walmartlabs.models.products.GetAllProductInfo;

import java.util.ArrayList;

public class ProductsFragmentAdapter extends RecyclerView.Adapter<ProductsFragmentAdapter.ViewHolder> {

    private Activity activity;
    private MainActivity mainActivity;
    private ArrayList<GetAllProductInfo> getAllProductInfoList;

    private AdapterProductsFragmentBinding adapterProductsFragmentBinding;
    private LayoutInflater layoutInflater;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading = false;
    private boolean scrollSearch = true;

    private OnLoadMoreListener onLoadMoreListener;


    public ProductsFragmentAdapter(@NonNull RecyclerView recyclerView, @NonNull Activity activity) {
        this.activity = activity;
        this.mainActivity = (MainActivity) activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (scrollSearch) {
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }

            }
        });
    }


    public void loadProductData(@NonNull ArrayList<GetAllProductInfo> getAllProductInfo) {
        this.getAllProductInfoList = getAllProductInfo;
    }

    public void setLazyLoading(boolean scrollSearch) {
        this.scrollSearch = scrollSearch;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(activity);
        adapterProductsFragmentBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.adapter_products_fragment, viewGroup, false);

        return new ProductsFragmentAdapter.ViewHolder(adapterProductsFragmentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(ApplicationConstant.BASE_URL + getAllProductInfoList.get(position).getProductImage())
                .thumbnail(0.25f)
                .into(holder.adapterProductsFragmentBinding.productAdapterImageView);

        holder.adapterProductsFragmentBinding.productAdapterInStock.setText(getAllProductInfoList.get(position).getInStock()? "In Stock" : "Out of Stock");
        holder.adapterProductsFragmentBinding.productAdapterProductName.setText(getAllProductInfoList.get(position).getProductName());
        holder.adapterProductsFragmentBinding.productAdapterPrice.setText( getAllProductInfoList.get(position).getPrice());
        holder.adapterProductsFragmentBinding.productAdapterRating.setText("Rating:" +  getAllProductInfoList.get(position).getReviewRating() + "/5.0");
        holder.adapterProductsFragmentBinding.productAdapterReviewCount.setText(" based on " + getAllProductInfoList.get(position).getReviewCount() +
                (getAllProductInfoList.get(position).getReviewCount() > 1 ? " users" : " user"));

        holder.adapterProductsFragmentBinding.productAdapterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDetailsFragment fragment = ViewDetailsFragment.newInstance(getAllProductInfoList, position);
                mainActivity.addFragmentToStack(fragment);
            }
        });
    }


    @Override
    public int getItemCount() {
        return getAllProductInfoList == null ? 0 : getAllProductInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterProductsFragmentBinding adapterProductsFragmentBinding;

        public ViewHolder(@NonNull AdapterProductsFragmentBinding adapterProductsFragmentBinding) {
            super(adapterProductsFragmentBinding.getRoot());
            this.adapterProductsFragmentBinding = adapterProductsFragmentBinding;
        }
    }
}
