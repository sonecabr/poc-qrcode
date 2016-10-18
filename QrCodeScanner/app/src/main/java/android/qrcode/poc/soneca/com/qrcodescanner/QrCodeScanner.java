package android.qrcode.poc.soneca.com.qrcodescanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class QrCodeScanner extends Activity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the main content layout of the Activity
        setContentView(R.layout.activity_main);
    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            /*Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);*/
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            intentIntegrator.setPrompt("Scan a barcode");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(false);
            intentIntegrator.setBarcodeImageEnabled(true);
            new IntentIntegrator(this).initiateScan();
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(QrCodeScanner.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                //Toast toast = Toast.makeText(this, "You scanned this: " + result.getContents(), Toast.LENGTH_LONG);
                LinearLayout showJson = new LinearLayout(this);
                TextView text = new TextView(this);
                text.setText(result.getContents());
                showJson.addView(text);
                setContentView(showJson);
                //saveConfig(result.getContents(), this);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    /**
     * Save the result in a file
     * @param data
     * @param context
     */
    public void saveConfig(String data, Context context) {
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e){
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
