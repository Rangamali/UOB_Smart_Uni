package com.app.smartuni;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.smartuni.base.BaseActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class EventActivity extends BaseActivity implements OnMapReadyCallback {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextView tvEventDetails;
    private MapView mMapView;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        tvEventDetails = findViewById(R.id.tv_event_details);
        mMapView=findViewById(R.id.fl_mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        //if (nfcAdapter == null) {
        //  showWarning("This device doesn't support NFC.");
        //  finish();
        //}
        //if (!nfcAdapter.isEnabled()) {
        //  showWarning("NFC is disabled.");
        //}

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, this.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled())
                showWirelessSettings();

            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    private void showWirelessSettings() {
        showWarning("You need to enable NFC");
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;

            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];

                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }

            } else {
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                msgs = new NdefMessage[]{msg};
            }
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                final Ndef ndef = Ndef.get(tag);
                try {
                    ndef.connect();
                    NdefMessage ndefMessage = ndef.getNdefMessage();
                    Log.d("", "");
                    showSuccess("New NFC Tag Detected!");
                    displayMsgs(ndefMessage);
                } catch (IOException | FormatException e) {
                    e.printStackTrace();
                }
            }

            //displayMsgs(ndef);
        }
    }

    private String dumpTagData(Tag tag) {
        return null;
    }

    private void displayMsgs(NdefMessage ndefMessages) {
        //String locationName;
//    for(int i=0;i<=ndefMessages.getRecords().length;i++){
//
//    }
        tvEventDetails.setVisibility(View.VISIBLE);
        String[] locationName = new String(ndefMessages.getRecords()[0].getPayload()).split(",", 3);
        tvEventDetails.setText(locationName[2]);
        String[] coordinates = new String(ndefMessages.getRecords()[1].getPayload()).split(":", 2);
        String[] latlng = coordinates[1].split(",", 2);
        LatLng location = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));
        mMap.addMarker(new MarkerOptions().position(location).title(locationName[2]));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                location, 15);
        mMap.animateCamera(cameraUpdate);

        //
        //if (msgs == null || msgs.length == 0)
        //  return;
        //
        //StringBuilder builder = new StringBuilder();
        //List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
        //final int size = records.size();
        //
        //for (int i = 0; i < size; i++) {
        //  ParsedNdefRecord record = records.get(i);
        //  String str = record.str();
        //  builder.append(str).append("\n");
        //}
        //
        //text.setText(builder.toString());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public final void onDestroy()
    {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public final void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public final void onPause()
    {
        super.onPause();
        mMapView.onPause();
    }
}
