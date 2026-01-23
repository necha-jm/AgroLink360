package com.app.agroli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {




    private CardView farmerCard, businessCard, expertCard;
    private Button continueButton;
    private TextView selectedRoleText;

    private String selectedRole = "";
    private static final String ROLE_FARMER = "mkulima";
    private static final String ROLE_BUSINESS = "mfanyabiashara";
    private static final String ROLE_EXPERT = "mtaalamu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        farmerCard = findViewById(R.id.card_farmer);
        businessCard = findViewById(R.id.card_business);
        expertCard = findViewById(R.id.card_expert);
        continueButton = findViewById(R.id.btn_continue);
        selectedRoleText = findViewById(R.id.selected_role_text);

        // Set click listeners for cards
        farmerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole(ROLE_FARMER);
            }
        });

        businessCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole(ROLE_BUSINESS);
            }
        });

        expertCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole(ROLE_EXPERT);
            }
        });

        // Continue button click listener
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedRole.isEmpty()) {
                    navigateToRegistration();
                } else {
                    // Show error message
                    selectedRoleText.setText("Tafadhali chagua jukumu lako");
                    selectedRoleText.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }

    private void selectRole(String role) {
        // Reset all cards to default state
        resetAllCards();

        // Set the selected role
        selectedRole = role;

        // Highlight the selected card
        switch (role) {
            case ROLE_FARMER:
                selectedRoleText.setText("Umechagua: Mkulima");
                break;
            case ROLE_BUSINESS:
                selectedRoleText.setText("Umechagua: Mfanyabiashara");
                break;
            case ROLE_EXPERT:
                selectedRoleText.setText("Umechagua: Mtaalamu");
                break;
        }

        selectedRoleText.setTextColor(getResources().getColor(R.color.green_dark));
        continueButton.setEnabled(true);
        continueButton.setBackgroundColor(getResources().getColor(R.color.green_dark));
    }

    private void resetAllCards() {
        // Reset all cards to their default colors
        farmerCard.setCardBackgroundColor(getResources().getColor(R.color.green_light));
        businessCard.setCardBackgroundColor(getResources().getColor(R.color.blue_light));
        expertCard.setCardBackgroundColor(getResources().getColor(R.color.orange_light));
    }

    private void navigateToRegistration() {
        Intent intent = new Intent(Dashboard.this, Register.class);
        intent.putExtra("SELECTED_ROLE", selectedRole);
        startActivity(intent);
        finish(); // Optional: close this activity
    }


}