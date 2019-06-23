package com.yongji.walmartlabs.remote.products;

import com.yongji.walmartlabs.models.products.GetAllProductsResponseModel;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProductsFragmentServiceImpl implements ProductsFragmentService {

    @Override
    public void getAllProducts(
            int pageNumber,
            int perPage,
            ProductsFragmentFactory productsFragmentFactory,
            ProductsFragmentGetAllProductsCallback productsFragmentGetAllProductsCallback) {

        productsFragmentFactory.getAllProducts(String.valueOf(pageNumber), String.valueOf(perPage))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<GetAllProductsResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<GetAllProductsResponseModel> getAllProductsResponseModelResponse) {
                        if (getAllProductsResponseModelResponse.raw().networkResponse() != null &&
                                getAllProductsResponseModelResponse.isSuccessful()||
                                getAllProductsResponseModelResponse.code() == HttpsURLConnection.HTTP_OK ||
                                getAllProductsResponseModelResponse.code() == HttpURLConnection.HTTP_NOT_MODIFIED) {
                            productsFragmentGetAllProductsCallback.onResponse(getAllProductsResponseModelResponse.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        productsFragmentGetAllProductsCallback.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
