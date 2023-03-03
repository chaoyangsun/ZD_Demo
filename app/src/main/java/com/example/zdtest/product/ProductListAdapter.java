package com.example.zdtest.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zdtest.R;
import com.example.zdtest.data.ProductBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<ProductBean.CategoriesBean.ProductsItemBean> beanList;
    private OnItemClickListener listener;

    public ProductListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        beanList = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(ProductBean.CategoriesBean.ProductsItemBean productsItemBean);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        final ProductBean.CategoriesBean.ProductsItemBean bean = beanList.get(position);
        if (bean.isTitleItem()) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new TitleViewHolder(layoutInflater.inflate(R.layout.item_title, parent, false));
        }
        return new ContentViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ProductBean.CategoriesBean.ProductsItemBean productsItemBean = beanList.get(position);
        if (holder instanceof TitleViewHolder) {
            setTitle(productsItemBean, (TitleViewHolder) holder);
        } else {
            setContent(productsItemBean, (ContentViewHolder) holder);
        }
    }

    private void setTitle(ProductBean.CategoriesBean.ProductsItemBean productsItemBean, TitleViewHolder titleViewHolder) {
        titleViewHolder.tvName.setText(productsItemBean.getCategoryName());
        titleViewHolder.tvProductCount.setText(titleViewHolder.tvProductCount.getContext().getString(R.string.product_count, productsItemBean.getTotal()));
    }

    private void setContent(ProductBean.CategoriesBean.ProductsItemBean productsItemBean, ContentViewHolder contentViewHolder) {
        contentViewHolder.tvProductContent.setText(productsItemBean.getProductName());
        contentViewHolder.tvProductContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(productsItemBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }

    public void addAll(List<ProductBean.CategoriesBean.ProductsItemBean> beans) {
        beanList.clear();
        beanList.addAll(beans);
        notifyDataSetChanged();
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final TextView tvProductCount;

        TitleViewHolder(View itemView) {
            super(itemView);
            tvProductCount = itemView.findViewById(R.id.tv_size);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvProductContent;

        ContentViewHolder(View itemView) {
            super(itemView);
            tvProductContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
