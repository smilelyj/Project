package com.yongji.walmartlabs.config;


import android.content.Context;

import com.yongji.walmartlabs.BuildConfig;


public interface ApplicationConstant {

    /*Shared preference*/
    int SHARED_PREFERENCE_MODE = Context.MODE_PRIVATE;

    /*Retrofit*/
    int CONNECT_TIMEOUT = 20;
    int READ_TIMEOUT = 20;
    int WRITE_TIMEOUT = 20;
    String GSON_SET_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    String BASE_URL = "https://mobile-tha-server.firebaseapp.com";

    boolean BUILD_CONFIG = BuildConfig.DEBUG;

}
