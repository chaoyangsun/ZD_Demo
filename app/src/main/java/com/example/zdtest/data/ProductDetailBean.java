package com.example.zdtest.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailBean {

    private String lang;
    private DataBean data;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<Product> products;

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public static class Product {
            @SerializedName("product-name")
            private String productname;
            @SerializedName("product-description")
            private String productdescription;
            @SerializedName("product-url")
            private String producturl;
            @SerializedName("product-image-landscape")
            private String productimagelandscape;
            @SerializedName("cta-label-primary")
            private String ctalabelprimary;
            @SerializedName("cta-url-primary")
            private String ctaurlprimary;
            @SerializedName("product-code")
            private String productcode;
            @SerializedName("category-name")
            private String categoryname;
            @SerializedName("category-code")
            private String categorycode;

            public String getProductname() {
                return productname;
            }

            public void setProductname(String productname) {
                this.productname = productname;
            }

            public String getProductdescription() {
                return productdescription;
            }

            public void setProductdescription(String productdescription) {
                this.productdescription = productdescription;
            }

            public String getProducturl() {
                return producturl;
            }

            public void setProducturl(String producturl) {
                this.producturl = producturl;
            }

            public String getProductimagelandscape() {
                return productimagelandscape;
            }

            public void setProductimagelandscape(String productimagelandscape) {
                this.productimagelandscape = productimagelandscape;
            }

            public String getCtalabelprimary() {
                return ctalabelprimary;
            }

            public void setCtalabelprimary(String ctalabelprimary) {
                this.ctalabelprimary = ctalabelprimary;
            }

            public String getCtaurlprimary() {
                return ctaurlprimary;
            }

            public void setCtaurlprimary(String ctaurlprimary) {
                this.ctaurlprimary = ctaurlprimary;
            }

            public String getProductcode() {
                return productcode;
            }

            public void setProductcode(String productcode) {
                this.productcode = productcode;
            }

            public String getCategoryname() {
                return categoryname;
            }

            public void setCategoryname(String categoryname) {
                this.categoryname = categoryname;
            }

            public String getCategorycode() {
                return categorycode;
            }

            public void setCategorycode(String categorycode) {
                this.categorycode = categorycode;
            }
        }
    }
}
