package com.app.agroli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = productList.get(position);

        holder.tvName.setText(product.name);
        holder.tvCategory.setText("Category: " + product.category);
        holder.tvQtyPrice.setText(product.quantity + " kg · TZS " + product.price + "/kg");
        holder.tvLocation.setText("📍 " + product.location);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDetails;
        TextView tvCategory, tvQtyPrice, tvLocation;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCropName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvQtyPrice = itemView.findViewById(R.id.tvQtyPrice);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvName = itemView.findViewById(R.id.tvCropName);
        }
    }
}
