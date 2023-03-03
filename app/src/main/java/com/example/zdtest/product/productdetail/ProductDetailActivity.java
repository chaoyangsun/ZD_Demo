package com.example.zdtest.product.productdetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.zdtest.R;
import com.example.zdtest.WebViewActivity;
import com.example.zdtest.data.ProductDetailBean;
import com.example.zdtest.databinding.ActivityProductDetailBinding;
import com.example.zdtest.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProductDetailBinding binding;
    private String productUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivProduct.setOnClickListener(this);
        binding.detailHintLayout.setOnClickListener(this);
        requestData();
    }

    private void requestData() {
        binding.detailHintLayout.setVisibility(View.GONE);
        binding.loading.setVisibility(View.VISIBLE);
        String productUrl = getIntent().getStringExtra("detail_url");
        HttpUtil.sendOkHttpRequest(productUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("requestdata", e.getMessage());
                runOnUiThread(() -> {
                    binding.loading.setVisibility(View.GONE);
                    binding.detailHintLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(ProductDetailActivity.this, R.string.loaddata_failed, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                ProductDetailBean productDetailBean = new Gson().fromJson(responseText, ProductDetailBean.class);
                if (productDetailBean != null) {
                    runOnUiThread(() -> {
                        binding.loading.setVisibility(View.GONE);
                        setData(productDetailBean);
                    });
                }
            }
        });
    }

    private void setData(ProductDetailBean productDetailBean) {
        ProductDetailBean.DataBean.Product product = productDetailBean.getData().getProducts().get(0);
        Glide.with(this).load(product.getProductimagelandscape()).placeholder(R.drawable.default_image).into(binding.ivProduct);
        binding.productName.setText(product.getCategoryname());
        binding.productDes.setText(product.getProductdescription());
        productUrl = product.getProducturl();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_product:
                if (!TextUtils.isEmpty(productUrl)) {
                    Intent intent = new Intent(ProductDetailActivity.this, WebViewActivity.class);
                    intent.putExtra("product_url", productUrl);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductDetailActivity.this, R.string.data_exception, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.detail_hint_layout:
                requestData();
                break;
            default:
                break;
        }
    }
}