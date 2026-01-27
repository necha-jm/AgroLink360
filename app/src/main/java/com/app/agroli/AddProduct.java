package com.app.agroli;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.datatransport.BuildConfig;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

public class AddProduct extends AppCompatActivity {

    Button btnUploadImage,btnAddProduct;

    TextInputEditText etLocation, etPrice, etQuantity,etCategory,etProductname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnUploadImage = findViewById(R.id.btnUploadImage);
        etLocation = findViewById(R.id.etLocation);
        etPrice = findViewById(R.id.etPrice);
        etQuantity = findViewById(R.id.etQuantity);
        etCategory = findViewById(R.id.etCategory);
        etProductname = findViewById(R.id.etProductName);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(v -> {
            String price = etPrice.getText().toString().trim();
            String quantity = etQuantity.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String productName = etProductname.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (price.isEmpty() || quantity.isEmpty() || productName.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Product product = new Product(productName, category, Double.parseDouble(price),
                    Double.parseDouble(quantity), location);

            // Save product to static list
            ProductStore.productList.add(product);

            // Open MainDashboard
            startActivity(new Intent(AddProduct.this, MainDashboard.class));
            finish();
        });





        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. File location
                File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myphoto.jpg");

                // 2. Get content Uri using FileProvider
                Uri photoUri = FileProvider.getUriForFile(
                        AddProduct.this,
                        getPackageName() + ".provider",
                        photoFile
                );

                // 3. Camera intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                // 4. Grant temporary permissions
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                // 5. Start camera
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(AddProduct.this, "No camera app found", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}