package com.example.zdtest.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductBean {
    private String lang;
    private List<CategoriesBean> categories;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class CategoriesBean {
        @SerializedName("category-name")
        private String categoryName;
        @SerializedName("category-code")
        private String categoryCode;
        private List<ProductsItemBean> products;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        public List<ProductsItemBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsItemBean> products) {
            this.products = products;
        }

        public static class ProductsItemBean {
            @SerializedName("product-name")
            private String productName;
            @SerializedName("product-code")
            private String productCode;
            @SerializedName("product-details")
            private String productDetails;
            @SerializedName("category")
            private String category;

            //custom
            private String categoryName;
            private String categoryCode;
            private int type;//0 item  1 title
            private int total;

            public ProductsItemBean() {
            }

            public ProductsItemBean(String categoryName, String categoryCode, int type) {
                this.categoryName = categoryName;
                this.categoryCode = categoryCode;
                this.type = type;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getProductDetails() {
                return productDetails;
            }

            public void setProductDetails(String productDetails) {
                this.productDetails = productDetails;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            //custom

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getCategoryCode() {
                return categoryCode;
            }

            public void setCategoryCode(String categoryCode) {
                this.categoryCode = categoryCode;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public boolean isTitleItem(){
                return this.type == 1;
            }
        }


    }
}
