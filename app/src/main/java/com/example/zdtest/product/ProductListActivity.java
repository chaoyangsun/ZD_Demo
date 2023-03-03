package com.example.zdtest.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zdtest.R;
import com.example.zdtest.data.ProductBean;
import com.example.zdtest.databinding.ActivityProductListBinding;
import com.example.zdtest.product.productdetail.ProductDetailActivity;
import com.example.zdtest.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * The welcome page should render a table using the below data in
 * the mentioned url https://av.sc.com/sg/rtob/categories.json
 */
public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.OnItemClickListener {

    private ActivityProductListBinding binding;
    private GridLayoutManager gridLayoutManager;
    private ProductListAdapter mAdapter;
    private List<ProductBean.CategoriesBean.ProductsItemBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final RecyclerView rvListView = binding.rvProductList;
        final LinearLayout clickRetry = binding.hintLayout;
        gridLayoutManager = new GridLayoutManager(ProductListActivity.this, 3, GridLayoutManager.VERTICAL, false);
        rvListView.setLayoutManager(gridLayoutManager);
        mAdapter = new ProductListAdapter(ProductListActivity.this);
        rvListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        requestData();

        clickRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestData();
            }
        });
    }

    public void requestData() {
        binding.hintLayout.setVisibility(View.GONE);
        binding.loading.setVisibility(View.VISIBLE);

        String productUrl = "https://av.sc.com/sg/rtob/categories.json";
        HttpUtil.sendOkHttpRequest(productUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("requestdata", e.getMessage());
                runOnUiThread(() -> {
                    binding.loading.setVisibility(View.GONE);
                    binding.hintLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(ProductListActivity.this, R.string.loaddata_failed, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                ProductBean productBean = new Gson().fromJson(responseText, ProductBean.class);
                if (productBean != null) {
                    setData(productBean);
                }
                runOnUiThread(() -> {
                    binding.loading.setVisibility(View.GONE);
                    mAdapter.addAll(dataList);
                });
            }
        });
    }

    private void setData(ProductBean productBean) {
        List<ProductBean.CategoriesBean> categoriesBeanList = productBean.getCategories();
        dataList = processData(categoriesBeanList);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (dataList.get(position).isTitleItem()) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
    }

    private List<ProductBean.CategoriesBean.ProductsItemBean> processData(List<ProductBean.CategoriesBean> categoriesBeanList) {
        List<ProductBean.CategoriesBean.ProductsItemBean> productsItemBeanList = new ArrayList<>();
        for (int i = 0; i < categoriesBeanList.size(); i++) {
            ProductBean.CategoriesBean categoriesBean = categoriesBeanList.get(i);
            ProductBean.CategoriesBean.ProductsItemBean productsItemBean = new ProductBean.CategoriesBean.ProductsItemBean(categoriesBean.getCategoryName(), categoriesBean.getCategoryCode(), 1);
            productsItemBean.setTotal(categoriesBean.getProducts().size());
            productsItemBeanList.add(productsItemBean);
            productsItemBeanList.addAll(categoriesBean.getProducts());
        }
        return productsItemBeanList;
    }

    @Override
    public void onItemClick(ProductBean.CategoriesBean.ProductsItemBean productsItemBean) {
        Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
        intent.putExtra("detail_url", productsItemBean.getProductDetails());
        startActivity(intent);
    }
}