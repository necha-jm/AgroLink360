package com.app.agroli;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity implements NetworkReceiver.NetworkChangeListener {

    private LottieAnimationView lottieAnimationView;
    private NetworkReceiver networkReceiver;
    private boolean hasInternet = false;
    private boolean hasNavigated = false; // Prevent multiple navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize Lottie view
        lottieAnimationView = findViewById(R.id.lottieAnimation);

        // Start animation
        if (lottieAnimationView != null) {
            lottieAnimationView.playAnimation();
        }

        // Create NetworkReceiver
        networkReceiver = new NetworkReceiver();
        NetworkReceiver.setNetworkChangeListener(this);

        // Register receiver dynamically (works on all Android versions)
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(networkReceiver, intentFilter);

        // Check initial internet status
        checkInitialInternetStatus();
    }

    private void checkInitialInternetStatus() {
        // First, check if we already have internet
        boolean isConnected = NetworkUtils.isNetworkAvailable(this);

        if (isConnected) {
            hasInternet = true;
            navigateToMain();
        } else {
            Toast.makeText(this, "Waiting for internet connection...", Toast.LENGTH_LONG).show();

            // Keep checking every 3 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!hasNavigated) {
                        boolean connected = NetworkUtils.isNetworkAvailable(SplashActivity.this);
                        if (connected) {
                            navigateToMain();
                        } else {
                            // Check again after 3 seconds
                            new Handler(Looper.getMainLooper()).postDelayed(this, 3000);
                        }
                    }
                }
            }, 3000);
        }
    }

    private void navigateToMain() {
        if (hasNavigated) return; // Prevent multiple navigation

        hasNavigated = true;

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (hasNavigated) return; // Already navigated

        hasInternet = isConnected;

        if (isConnected) {
            Toast.makeText(this, "Internet Connected ✅", Toast.LENGTH_SHORT).show();
            // Navigate immediately when internet becomes available
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                navigateToMain();
            }, 500);
        } else {
            Toast.makeText(this, "No Internet Connection ❌", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the receiver to prevent memory leaks
        try {
            if (networkReceiver != null) {
                unregisterReceiver(networkReceiver);
            }
        } catch (IllegalArgumentException e) {
            // Receiver was not registered
            Log.e("SplashActivity", "Receiver not registered", e);
        }

        // Remove listener
        NetworkReceiver.removeNetworkChangeListener();

        // Clean up animation
        if (lottieAnimationView != null) {
            lottieAnimationView.cancelAnimation();
        }
    }
}