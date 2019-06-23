package com.yongji.walmartlabs.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ProductsPagerAdapter extends PagerAdapter {

    private List<View> viewList;

    public ProductsPagerAdapter() {
        this.viewList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = viewList.get(position);
        collection.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setData(@Nullable List<View> list) {
        this.viewList.clear();
        if (list != null && !list.isEmpty()) {
            this.viewList.addAll(list);
        }

        notifyDataSetChanged();
    }

    @NonNull
    public List<View> getData() {
        if (viewList == null) {
            viewList = new ArrayList<>();
        }

        return viewList;
    }
}