package com.yongji.walmartlabs.remote.products;

import com.yongji.walmartlabs.config.WalmartLabConstant;
import com.yongji.walmartlabs.models.products.GetAllProductsResponseModel;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductsFragmentFactory {

    @GET(WalmartLabConstant.GET_ALL_PRODUCTS + "{pageNumber}/{pageSize}")
    Observable<Response<GetAllProductsResponseModel>> getAllProducts(@Path(value = "pageNumber") String pageNumber,
                                                                   @Path("pageSize") String pageSize);

}
