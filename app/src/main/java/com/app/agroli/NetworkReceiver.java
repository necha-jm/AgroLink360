package com.app.agroli;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiver";
    private static NetworkChangeListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Network change detected! Action: " + intent.getAction());

        boolean isConnected = isNetworkAvailable(context);
        Log.d(TAG, "Network connected: " + isConnected);

        if (listener != null) {
            listener.onNetworkChanged(isConnected);
        } else {
            Log.d(TAG, "Listener is null! Make sure to call setNetworkChangeListener");
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        // For Android 10 (API 29) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null) {
                return false;
            }

            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        }
        // For older Android versions
        else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }

    // Interface for callback
    public interface NetworkChangeListener {
        void onNetworkChanged(boolean isConnected);
    }

    // Method to set listener
    public static void setNetworkChangeListener(NetworkChangeListener networkChangeListener) {
        listener = networkChangeListener;
        Log.d(TAG, "Listener set: " + (listener != null));
    }

    // Method to remove listener
    public static void removeNetworkChangeListener() {
        listener = null;
        Log.d(TAG, "Listener removed");
    }
}