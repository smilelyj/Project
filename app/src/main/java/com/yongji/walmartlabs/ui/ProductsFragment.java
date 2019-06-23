package com.yongji.walmartlabs.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yongji.walmartlabs.R;
import com.yongji.walmartlabs.config.RemoteServiceConfig;
import com.yongji.walmartlabs.config.WalmartLabConstant;
import com.yongji.walmartlabs.databinding.FragmentProductsBinding;
import com.yongji.walmartlabs.listeners.OnLoadMoreListener;
import com.yongji.walmartlabs.models.products.GetAllProductInfo;
import com.yongji.walmartlabs.models.products.GetAllProductsResponseModel;
import com.yongji.walmartlabs.remote.products.ProductsFragmentFactory;
import com.yongji.walmartlabs.remote.products.ProductsFragmentGetAllProductsCallback;
import com.yongji.walmartlabs.remote.products.ProductsFragmentService;
import com.yongji.walmartlabs.remote.products.ProductsFragmentServiceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.OnItemClick;


public class ProductsFragment extends Fragment {

    private ProductsFragmentService productsFragmentService = null;
    private ProductsFragmentFactory productsFragmentFactory = null;

    private ProductsFragmentAdapter productsFragmentAdapter = null;
    private LinearLayoutManager linearLayoutManager = null;

    private FragmentProductsBinding fragmentProductsBinding = null;
    private View view = null;
    private File cacheFile = null;

    private int totalPages=0;
    private int currentPage = 0;
    private int nextPage = 0;

    private int pageNumber = WalmartLabConstant.DEFAULT_PAGE_NUMBER;
    private int itemPerPage = WalmartLabConstant.ITEM_PER_PAGE_LIMIT;

    private ArrayList<GetAllProductInfo> getAllProductInfoList;

    private MainActivity mainActivity;


    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProductsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        view = fragmentProductsBinding.getRoot();

        mainActivity = (MainActivity) getActivity();
        mainActivity.disableHomeAsUp();

        getAllProducts();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        fragmentProductsBinding.productsRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentProductsBinding.productsRecyclerView.setLayoutManager(linearLayoutManager);
        productsFragmentAdapter = new ProductsFragmentAdapter(
                fragmentProductsBinding.productsRecyclerView,
                Objects.requireNonNull(getActivity()));


        productsFragmentAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (getAllProductInfoList != null && getAllProductInfoList.size() > 0) {
                        if (totalPages == currentPage) {
                            Toast.makeText(getActivity(), "No more data..." , Toast.LENGTH_SHORT).show();
                        }
                        else {
                        cacheFile = new File(getActivity().getCacheDir(), "responses");
                        productsFragmentFactory = new RemoteServiceConfig<ProductsFragmentFactory>().createApiService(ProductsFragmentFactory.class, cacheFile);
                        productsFragmentService = new ProductsFragmentServiceImpl();
                        productsFragmentService.getAllProducts(
                                nextPage,
                                itemPerPage,
                                productsFragmentFactory,
                                new ProductsFragmentGetAllProductsCallback() {
                                    @Override
                                    public void onResponse(GetAllProductsResponseModel getAllProductsResponseModel) {
                                        if (getAllProductsResponseModel.getData().size() > 0) {
                                            if(getActivity()!=null) {
                                                totalPages = getAllProductsResponseModel.getTotalProducts() / getAllProductsResponseModel.getPageSize() + 1;
                                                currentPage = getAllProductsResponseModel.getPageNumber();
                                                nextPage = getAllProductsResponseModel.getPageNumber() + 1;
                                                itemPerPage = getAllProductsResponseModel.getPageSize();

                                                getAllProductInfoList.addAll(getAllProductsResponseModel.getData());
                                                productsFragmentAdapter.notifyDataSetChanged();
                                                productsFragmentAdapter.setLoaded();
                                                if(getActivity()!=null)
                                                    Toast.makeText(getActivity(), "More data fetched...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }

                    }

                }
            });




    }
    private void getAllProducts() {
        if(getActivity()!=null)
            Toast.makeText(getActivity(), "Fetching products...", Toast.LENGTH_SHORT).show();
        cacheFile = new File(getActivity().getCacheDir(), "responses");
        productsFragmentFactory = new RemoteServiceConfig<ProductsFragmentFactory>().createApiService(ProductsFragmentFactory.class, cacheFile);
        productsFragmentService = new ProductsFragmentServiceImpl();
        productsFragmentService.getAllProducts(
                pageNumber,
                itemPerPage,
                productsFragmentFactory,
                new ProductsFragmentGetAllProductsCallback() {
                    @Override
                    public void onResponse(GetAllProductsResponseModel getAllProductsResponseModel) {

                        if(getActivity()!=null){

                            pageNumber = getAllProductsResponseModel.getPageNumber();

                            totalPages = getAllProductsResponseModel.getTotalProducts() / getAllProductsResponseModel.getPageSize() + 1;
                            currentPage = getAllProductsResponseModel.getPageNumber();
                            nextPage = getAllProductsResponseModel.getPageNumber() + 1;
                            itemPerPage = getAllProductsResponseModel.getPageSize();

                        if (getAllProductsResponseModel.getData().size() > 0) {

                            getAllProductInfoList = getAllProductsResponseModel.getData();
                            productsFragmentAdapter.loadProductData(getAllProductsResponseModel.getData());
                            productsFragmentAdapter.setLazyLoading(true);
                            fragmentProductsBinding.productsRecyclerView.setAdapter(productsFragmentAdapter);
                        }

                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @OnItemClick(R.id.products_recycler_view)
    public void onCurrentRouteItemClicked(int position) {
        ViewDetailsFragment fragment = ViewDetailsFragment.newInstance(getAllProductInfoList, position);
        mainActivity.addFragmentToStack(fragment);
    }

}
