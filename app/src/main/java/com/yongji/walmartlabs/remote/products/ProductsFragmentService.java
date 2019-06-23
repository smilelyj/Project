package com.yongji.walmartlabs.remote.products;

public interface ProductsFragmentService {

    void getAllProducts(
            final int pageNumber,
            final int perPage,
            final ProductsFragmentFactory productsFragmentFactory,
            final ProductsFragmentGetAllProductsCallback productsFragmentGetAllProductsCallback);

}
