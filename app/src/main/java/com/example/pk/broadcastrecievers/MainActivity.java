package com.example.pk.broadcastrecievers;

import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String WIFI_INTENT_FILTER = "com.example.pk.broadcastreceiver_wifi";
    public static final String AIRPLANE_MODE_INTENT_FILTER = "com.example.pk.broadcastreceiver_am";
    public static final String BLUETOOTH_INTENT_FILTER = "com.example.pk.broadcastreceiver_bluetooth";
    public static final String GPS_INTENT_FILTER = "com.example.pk.broadcastreceiver_gps";

    public static final String ENABLED = "enabled";
    public static final String DISABLED = "disabled";

    public static final String WIFI_STATE = "wifi state";
    public static final String BLUETOOTH_STATE = "bluetooth state";
    public static final String GPS_STATE = "gps state";
    public static final String AIRPLANE_MODE_STATE = "airplane mode state";

    public static final int WIFI_NOTIFICATION_ID = 1;
    public static final int AIRPLANE_MODE_NOTIFICATION_ID = 2;
    public static final int BLUETOOTH_NOTIFICATION_ID = 3;
    public static final int GPS_NOTIFICATION_ID = 4;

    public static final int WIFI_CONTENT_INTENT_ID = 5;
    public static final int AIRPLANE_MODE_CONTENT_INTENT_ID = 6;
    public static final int BLUETOOTH_CONTENT_INTENT_ID = 7;
    public static final int GPS_CONTENT_INTENT_ID = 8;

    @BindView(R.id.ma_wifi_state)
    TextView wifiState;
    @BindView(R.id.ma_airplane_mode)
    TextView airplaneModeState;
    @BindView(R.id.ma_bluetooth_state)
    TextView bluetoothState;
    @BindView(R.id.ma_gps_state)
    TextView gpsState;

    private WifiBroadcastReceiver wifiBroadcastReceiver;
    private AirplaneModeBroadcastReceiver airplaneModeBroadcastReceiver;
    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
    private GpsBroadcastReceiver gpsBroadcastReceiver;

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        wifiBroadcastReceiver = new WifiBroadcastReceiver();
        airplaneModeBroadcastReceiver = new AirplaneModeBroadcastReceiver();
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        gpsBroadcastReceiver = new GpsBroadcastReceiver();

        notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(wifiBroadcastReceiver
                , new IntentFilter(WIFI_INTENT_FILTER));
        LocalBroadcastManager.getInstance(this).registerReceiver(airplaneModeBroadcastReceiver
                , new IntentFilter(AIRPLANE_MODE_INTENT_FILTER));
        LocalBroadcastManager.getInstance(this).registerReceiver(bluetoothBroadcastReceiver
                , new IntentFilter(BLUETOOTH_INTENT_FILTER));
        LocalBroadcastManager.getInstance(this).registerReceiver(gpsBroadcastReceiver
                , new IntentFilter(GPS_INTENT_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(wifiBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(airplaneModeBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bluetoothBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(gpsBroadcastReceiver);
    }

    @OnClick(R.id.check_wifi)
    public void checkWifiState() {
        WifiManager wifiManager = (WifiManager)
                getSystemService(Context.WIFI_SERVICE);

        int wifiStateInteger = wifiManager.getWifiState();
        String wifiStateForIntent = "";

        switch (wifiStateInteger) {
            case WifiManager.WIFI_STATE_ENABLED:
                wifiStateForIntent = ENABLED;
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                wifiStateForIntent = DISABLED;
                break;
        }

        Intent intent = new Intent(WIFI_INTENT_FILTER);
        intent.putExtra(WIFI_STATE, wifiStateForIntent);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @OnClick(R.id.check_airplane_mode)
    public void checkAirplaneModeState() {
        String airplaneModeStateForIntent;

        if (Settings.System.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 0) {
            airplaneModeStateForIntent = DISABLED;
        } else {
            airplaneModeStateForIntent = ENABLED;
        }

        Intent intent = new Intent(AIRPLANE_MODE_INTENT_FILTER);
        intent.putExtra(AIRPLANE_MODE_STATE, airplaneModeStateForIntent);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @OnClick(R.id.check_bluetooth)
    public void checkBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        int bluetoothStateInteger = bluetoothAdapter.getState();
        String bluetoothStateForIntent = "";

        switch (bluetoothStateInteger) {
            case BluetoothAdapter.STATE_ON:
                bluetoothStateForIntent = ENABLED;
                break;
            case BluetoothAdapter.STATE_OFF:
                bluetoothStateForIntent = DISABLED;
                break;
        }

        Intent intent = new Intent(BLUETOOTH_INTENT_FILTER);
        intent.putExtra(BLUETOOTH_STATE, bluetoothStateForIntent);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @OnClick(R.id.check_gps)
    public void checkGps() {
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        String gpsStateForIntent;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsStateForIntent = ENABLED;
        } else {
            gpsStateForIntent = DISABLED;
        }

        Intent intent = new Intent(GPS_INTENT_FILTER);
        intent.putExtra(GPS_STATE, gpsStateForIntent);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private class WifiBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String wifiStateIntent = intent.getStringExtra(WIFI_STATE);

            wifiState.setText(String.format("Wifi state: %s", wifiStateIntent));

            if (wifiStateIntent.equals(ENABLED)) {
                Notification wifiNotification = NotificationCreator.createNotification(
                        context, WIFI_CONTENT_INTENT_ID, R.drawable.ic_perm_scan_wifi_white_48dp, true
                        , "Wifi", "Wifi is enabled"
                        , BitmapFactory.decodeResource(getResources(), R.drawable.ic_perm_scan_wifi_white_48dp)
                        , "Big brother is watching you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        , new long[]{0, 200, 500, 200, 500, 1000});

                notificationManager.notify(WIFI_NOTIFICATION_ID, wifiNotification);
            }
        }
    }

    private class AirplaneModeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String amStateIntent = intent.getStringExtra(AIRPLANE_MODE_STATE);

            airplaneModeState.setText(String.format("Airplane mode state: %s", amStateIntent));

            if (amStateIntent.equals(ENABLED)) {
                Notification amNotification = NotificationCreator.createNotification(
                        context, AIRPLANE_MODE_CONTENT_INTENT_ID
                        , R.drawable.ic_airplanemode_active_white_48dp, true
                        , "Airplane mode", "Airplane mode is enabled"
                        , BitmapFactory.decodeResource(getResources(), R.drawable.ic_airplanemode_active_white_48dp)
                        , "Big Brother protects you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                        , new long[]{0, 400, 600, 400, 600, 1000});

                notificationManager.notify(AIRPLANE_MODE_NOTIFICATION_ID, amNotification);
            }
        }
    }

    private class BluetoothBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String bluetoothStateIntent = intent.getStringExtra(BLUETOOTH_STATE);

            bluetoothState.setText(String.format("Bluetooth state: %s", bluetoothStateIntent));

            if (bluetoothStateIntent.equals(ENABLED)) {
                Notification bluetoothNotification = NotificationCreator.createNotification(
                        context, BLUETOOTH_CONTENT_INTENT_ID, R.drawable.ic_bluetooth_white_48dp, true
                        , "Bluetooth", "Bluetooth in enabled"
                        , BitmapFactory.decodeResource(getResources(), R.drawable.ic_bluetooth_white_48dp)
                        , "Big Brother wants to contact you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                        , new long[]{0, 100, 700, 100, 700});

                notificationManager.notify(BLUETOOTH_NOTIFICATION_ID, bluetoothNotification);
            }
        }
    }

    private class GpsBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String gpsIntent = intent.getStringExtra(GPS_STATE);

            gpsState.setText(String.format("Gps state: %s", gpsIntent));

            if (gpsIntent.equals(ENABLED)) {
                Notification gpsNotification = NotificationCreator.createNotification(
                        context, GPS_CONTENT_INTENT_ID, R.drawable.ic_gps_fixed_white_48dp, true
                        , "GPS", "GPS is enabled"
                        , BitmapFactory.decodeResource(getResources(), R.drawable.ic_gps_fixed_white_48dp)
                        , "Big Brother spying on you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
                        , new long[]{0, 100, 300, 100, 300, 100, 300});

                notificationManager.notify(GPS_NOTIFICATION_ID, gpsNotification);
            }
        }
    }
}
