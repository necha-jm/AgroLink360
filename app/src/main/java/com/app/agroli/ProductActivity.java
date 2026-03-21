package com.app.agroli;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";

    private RecyclerView recyclerView;
    private ArrayList<Product> productList;
    private ProductAdapter adapter;
    private DatabaseReference databaseReference;
    private ValueEventListener productListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        // Initialize product list
        productList = new ArrayList<>();

        // Setup RecyclerView
        adapter = new ProductAdapter(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load products from Firebase
        loadProductsFromFirebase();
    }

    private void loadProductsFromFirebase() {
        // Show loading message
        Toast.makeText(this, "Loading products...", Toast.LENGTH_SHORT).show();

        // Set up listener for real-time updates
        productListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing list
                productList.clear();

                // Check if there are any products
                if (!dataSnapshot.exists()) {
                    Toast.makeText(ProductActivity.this,
                            "No products found. Add some products!",
                            Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                    return;
                }

                // Loop through all products in Firebase
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    try {
                        // Get product from Firebase
                        Product product = productSnapshot.getValue(Product.class);

                        if (product != null) {
                            // Set the Firebase key as product ID
                            product.setId(productSnapshot.getKey());
                            productList.add(product);

                            Log.d(TAG, "Loaded product: " + product.getProductName() +
                                    " | Price: " + product.getPrice());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing product: " + e.getMessage());
                    }
                }

                // Notify adapter that data has changed
                adapter.notifyDataSetChanged();

                // Show success message
                Toast.makeText(ProductActivity.this,
                        "Loaded " + productList.size() + " product(s)",
                        Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Total products loaded: " + productList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e(TAG, "Failed to load products: " + databaseError.getMessage());
                Toast.makeText(ProductActivity.this,
                        "Failed to load products: " + databaseError.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        };

        // Attach the listener to Firebase
        databaseReference.addValueEventListener(productListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove listener to prevent memory leaks
        if (databaseReference != null && productListener != null) {
            databaseReference.removeEventListener(productListener);
        }
    }
}