package com.yongji.walmartlabs.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class GetAllProductInfo implements Parcelable
{

    @SerializedName("productId")
    @Expose
    private String productId;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;

    @SerializedName("longDescription")
    @Expose
    private String longDescription;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("productImage")
    @Expose
    private String productImage;

    @SerializedName("reviewRating")
    @Expose
    private Float reviewRating;

    @SerializedName("reviewCount")
    @Expose
    private Integer reviewCount;

    @SerializedName("inStock")
    @Expose
    private Boolean inStock;

    private final static long serialVersionUID = 7436791393877309390L;

    protected GetAllProductInfo(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        shortDescription = in.readString();
        longDescription = in.readString();
        price = in.readString();
        productImage = in.readString();
        if (in.readByte() == 0) {
            reviewRating = null;
        } else {
            reviewRating = in.readFloat();
        }
        if (in.readByte() == 0) {
            reviewCount = null;
        } else {
            reviewCount = in.readInt();
        }
        byte tmpInStock = in.readByte();
        inStock = tmpInStock == 0 ? null : tmpInStock == 1;
    }

    public static final Creator<GetAllProductInfo> CREATOR = new Creator<GetAllProductInfo>() {
        @Override
        public GetAllProductInfo createFromParcel(Parcel in) {
            return new GetAllProductInfo(in);
        }

        @Override
        public GetAllProductInfo[] newArray(int size) {
            return new GetAllProductInfo[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Float getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(Float reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeString(price);
        dest.writeString(productImage);
        if (reviewRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(reviewRating);
        }
        if (reviewCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reviewCount);
        }
        dest.writeByte((byte) (inStock == null ? 0 : inStock ? 1 : 2));
    }
}