package com.app.agroli;



import android.content.Intent;
import android.os.Bundle;;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class Register extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPhone, editTextPassword;
    TextView  jina;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get selected role from intent
        String selectedRole = getIntent().getStringExtra("SELECTED_ROLE");

        // Initialize views
        TextView roleTitle = findViewById(R.id.role_title);
        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPhone = findViewById(R.id.edit_text_phone);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonRegister = findViewById(R.id.button_register);


        // Set role-specific title and form
        if (selectedRole != null) {
            switch (selectedRole) {
                case "mkulima":
                    roleTitle.setText("Usajili wa Mkulima");
                    break;
                case "mfanyabiashara":
                    roleTitle.setText("Usajili wa Mfanyabiashara");
                    break;
                case "mtaalamu":
                    roleTitle.setText("Usajili wa Mtaalamu");
                    setupExpertRegistration();
                    break;
            }
        }

        // Register button click listener
        buttonRegister.setOnClickListener(view -> {
            if (validateForm()) {
                registerUser(selectedRole);
            }
        });
    }

    private void setupExpertRegistration() {
        // Add extra fields for experts if needed
        // For example: qualification, experience, etc.
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (editTextName.getText().toString().trim().isEmpty()) {
            editTextName.setError("Jina linahitajika");
            isValid = false;
        }

        if (editTextEmail.getText().toString().trim().isEmpty()) {
            editTextEmail.setError("Barua pepe inahitajika");
            isValid = false;
        }

        if (editTextPhone.getText().toString().trim().isEmpty()) {
            editTextPhone.setError("Nambari ya simu inahitajika");
            isValid = false;
        }

        if (editTextPassword.getText().toString().trim().isEmpty()) {
            editTextPassword.setError("Nenosiri linahitajika");
            isValid = false;
        }

        return isValid;
    }

    private void registerUser(String role) {
        // Get form data
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // TODO: Implement actual registration logic
        // For now, just show a success message
        Toast.makeText(this, "Usajili umekamilika kama " + getRoleName(role), Toast.LENGTH_SHORT).show();


        // Navigate to appropriate dashboard based on role
        navigateToDashboard(role);
    }

    private String getRoleName(String role) {
        switch (role) {
            case "mkulima": return "Mkulima";
            case "mfanyabiashara": return "Mfanyabiashara";
            case "mtaalamu": return "Mtaalamu";
            default: return "Mtumiaji";
        }
    }

    private void navigateToDashboard(String role) {

        Intent intent;

        switch (role) {
            case "mkulima":
            case "mfanyabiashara":
            case "mtaalamu":
                intent = new Intent(this, MainDashboard.class);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
        }

        // ✅ PASS DATA
        intent.putExtra("NAME", editTextName.getText().toString().trim());
        intent.putExtra("EMAIL", editTextEmail.getText().toString().trim());
        intent.putExtra("PHONE", editTextPhone.getText().toString().trim());
        intent.putExtra("ROLE", role);

        startActivity(intent);
        finish();
    }
}