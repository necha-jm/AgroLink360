package com.app.agroli;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainDashboard extends AppCompatActivity {

    TextView  tvName;
    ImageView message;
    CardView AddProduct;

    RecyclerView recyclerView;
    ArrayList<Product> productList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_dashboard);

        // 🔹 INIT VIEWS FIRST
        tvName = findViewById(R.id.tvName);
        message = findViewById(R.id.chat);
        AddProduct = findViewById(R.id.product);
        recyclerView = findViewById(R.id.recyclerProducts);

        productList = ProductStore.productList;
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        // 🔹 SETUP RECYCLERVIEW
        recyclerView.setLayoutManager(
                new androidx.recyclerview.widget.LinearLayoutManager(this)
        );

        productList = new ArrayList<>();

        // 🔹 GET DATA FROM INTENT
        Intent intent = getIntent();
        if (intent != null) {
            double price = intent.getDoubleExtra("Price", 0.0);
            double quantity = intent.getDoubleExtra("Quantity", 0.0);
            String category = intent.getStringExtra("category");
            String productName = intent.getStringExtra("PROD_NAME");
            String location = intent.getStringExtra("Location");

            if (productName != null) {
                productList.add(
                        new Product(productName, category, price, quantity, location)
                );
            }

            String name = intent.getStringExtra("NAME");
            if (name != null) {
                tvName.setText(name);
            }
        }

        // 🔹 SET ADAPTER
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        // 🔹 ADD PRODUCT CLICK
        AddProduct.setOnClickListener(v -> {
            startActivity(new Intent(MainDashboard.this, AddProduct.class));
        });

        // 🔹 CHAT CLICK
        message.setOnClickListener(v -> {
            startActivity(new Intent(MainDashboard.this, SmsActivity.class));
        });
    }
}
