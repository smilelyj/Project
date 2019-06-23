package com.yongji.walmartlabs.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetAllProductsResponseModel implements Parcelable
{

    @SerializedName("products")
    @Expose
    private ArrayList<GetAllProductInfo> data = null;

    @SerializedName("totalProducts")
    @Expose
    private Integer totalProducts;

    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;

    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    private final static long serialVersionUID = -2613125685515023398L;

    protected GetAllProductsResponseModel(Parcel in) {
        data = in.createTypedArrayList(GetAllProductInfo.CREATOR);
        if (in.readByte() == 0) {
            totalProducts = null;
        } else {
            totalProducts = in.readInt();
        }
        if (in.readByte() == 0) {
            pageNumber = null;
        } else {
            pageNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            pageSize = null;
        } else {
            pageSize = in.readInt();
        }
        if (in.readByte() == 0) {
            statusCode = null;
        } else {
            statusCode = in.readInt();
        }
    }

    public static final Creator<GetAllProductsResponseModel> CREATOR = new Creator<GetAllProductsResponseModel>() {
        @Override
        public GetAllProductsResponseModel createFromParcel(Parcel in) {
            return new GetAllProductsResponseModel(in);
        }

        @Override
        public GetAllProductsResponseModel[] newArray(int size) {
            return new GetAllProductsResponseModel[size];
        }
    };

    public ArrayList<GetAllProductInfo> getData() {
        return data;
    }

    public void setData(ArrayList<GetAllProductInfo> data) {
        this.data = data;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
        if (totalProducts == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalProducts);
        }
        if (pageNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pageNumber);
        }
        if (pageSize == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pageSize);
        }
        if (statusCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(statusCode);
        }
    }
}