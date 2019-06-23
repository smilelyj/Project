package com.yongji.walmartlabs.remote.products;

import com.yongji.walmartlabs.models.products.GetAllProductsResponseModel;

public interface ProductsFragmentGetAllProductsCallback {

  void onResponse(GetAllProductsResponseModel getAllProductsResponseModel);
  void onError(String errorMessage);

}
