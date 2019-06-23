package com.yongji.walmartlabs.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.yongji.walmartlabs.R;
import com.yongji.walmartlabs.config.ApplicationConstant;
import com.yongji.walmartlabs.models.products.GetAllProductInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDetailsFragment extends Fragment {

    private MainActivity mainActivity;
    private ArrayList<GetAllProductInfo> productsInfo;
    private int position;

    @BindView(R.id.viewPager)
    ViewPager pager;

    ProductsPagerAdapter adapter;

    public static ViewDetailsFragment newInstance(ArrayList<GetAllProductInfo> allProductsInfo, int position) {

        ViewDetailsFragment fragment = new ViewDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("productsInfo", allProductsInfo);
        arguments.putInt("position", position);

        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main_view = inflater.inflate(R.layout.fragment_view_detail, container, false);
        ButterKnife.bind(this, main_view);

        return main_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        productsInfo  = getArguments().getParcelableArrayList("productsInfo");
        position = getArguments().getInt("position");

        mainActivity.setBackButton();
        setupviews();
    }

    private void setupviews() {

        adapter = new ProductsPagerAdapter();

        adapter.setData(createPageList());
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);

    }


    @NonNull
    private List<View> createPageList() {

        List<View> pageList = new ArrayList<>();

        for(int i = 0; i <productsInfo.size(); i++) {
            GetAllProductInfo info = productsInfo.get(i);
            if(info != null) {
                pageList.add(createPageView(info, i));
            }
        }

        return pageList;
    }

    @NonNull
    private View createPageView(final GetAllProductInfo productInfo, final int index) {

        ScrollView ll = (ScrollView) View.inflate(this.getContext(),
                R.layout.item_product_swipe_summary, null);
        AQuery aq = new AQuery(ll);

        aq.id(R.id.text_view_in_stock).getTextView().setText(productInfo.getInStock() ? "In stock" : "Out of stock");
        aq.id(R.id.text_view_name).getTextView().setText(productInfo.getProductName());

        ImageView thumbnail = aq.id(R.id.image_view_detail_thumbnail).getImageView();

        if (productInfo.getProductImage()!= null) {
            Glide.with(getActivity())
                    .load(ApplicationConstant.BASE_URL +  productInfo.getProductImage())
                    .thumbnail(1.0f)
                    .into(thumbnail);
        }

        if (productInfo.getLongDescription() != null) {
            aq.id(R.id.text_view_long_description).getTextView().setText(Html.fromHtml(productInfo.getLongDescription()));
        }

        aq.id(R.id.text_view_price).getTextView().setText(productInfo.getPrice());
        aq.id(R.id.rating_bar).getRatingBar().setRating(productInfo.getReviewRating());
        aq.id(R.id.text_view_view_count).getTextView().setText(" based on " + productInfo.getReviewCount() +
                (productInfo.getReviewCount() > 1 ? " users" : " user"));

        return ll;
    }


}