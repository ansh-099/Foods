package com.example.anshulsingh.foodpilu;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String custid="", orderId="", mid="";
    Double cost;
    Handler h = new Handler();
    Runnable rn;
    sendUserDetailTOServerdd dl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksum);
        getSupportActionBar().setTitle("Paytm Payment");
        //        setContentView(R.layout.activity_main);
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        cost = getIntent().getExtras().getDouble("cost");
//        DecimalFormat form = new DecimalFormat("0.00");
//        cost = Double.valueOf(form.format(cost));

//        cost = 100.0;


        Random r = new Random();
        orderId = String.valueOf(r.nextInt(2000000000));
        custid = String.valueOf(r.nextInt(2000000000));

        cost = 350.0;
        DecimalFormat form = new DecimalFormat("0.0");
        cost = Double.valueOf(form.format(cost));

        Log.d("heythere","cost"+cost);

//        Log.d("heychecksum",cost.toString());


        mid = "YtcawT76758568217740"; // your marchant key
        dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
// vollye , retrofit, asynch



    }
    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(checksum.this);

        //private String orderId , mid, custid, amt;
        String url ="http://icosrentals.com/payment/generateChecksum.php";
        //        String websiteurl = "TECHweb";
        String varifyurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH ="";

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(checksum.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" + orderId+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+cost+"&WEBSITE=DEFAULT"+
                            "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {

                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.d("heythere",jsonObject.getString("ORDER_ID"));
                    Log.e("CheckSum result >>",CHECKSUMHASH);
                    Log.d("heythere",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("heychecksum",e.toString());
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            final PaytmPGService  Service = PaytmPGService.getProductionService();
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", cost.toString());
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL" ,varifyurl);
//            paramMap.put( "EMAIL" , "ansh.099@gmail.com");   // no need
//            paramMap.put( "MOBILE_NO" , "7838447218");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );




            rn = new Runnable() {
                @Override
                public void run() {
                    dl.cancel(true);
                    finish();

                    Service.notify();


                }
            };

            h.postDelayed(rn,1800000);




        }

    }


    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksumresponse", " response is " + bundle.toString());


        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                // json.put(key, bundle.get(key)); see edit below
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    json.put(key, JSONObject.wrap(bundle.get(key)));
                    Log.d("checksumresponse",json.toString());
                    Log.d("checksumresponse",json.getString("STATUS"));
                    if(json.getString("STATUS").equals("TXN_SUCCESS")){
                        h.removeCallbacksAndMessages(null);
//                        Intent i = new Intent(checksum.this, Payment.class);
//                        i.putExtra("cost",cost);
//                        i.putExtra("prevIntent","checksum");
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(i);
//                        finish();

                    }else{
                        h.removeCallbacksAndMessages(null);
                        Toast.makeText(this, "Minimum Paytm Payment is ₹ 5", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            } catch(JSONException e) {
                //Handle exception here
            }
        }


//        Intent i = new Intent(checksum.this, Payment.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);

    }

    @Override
    public void networkNotAvailable() {
        h.removeCallbacksAndMessages(null);
        finish();


    }

    @Override
    public void clientAuthenticationFailed(String s) {
        h.removeCallbacksAndMessages(null);
        finish();


    }

    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
        h.removeCallbacksAndMessages(null);
        finish();

    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
        h.removeCallbacksAndMessages(null);
        finish();

    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );
        h.removeCallbacksAndMessages(null);
        finish();

    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
        h.removeCallbacksAndMessages(null);
        finish();

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please Wait Transaction is in process...", Toast.LENGTH_SHORT).show();
    }

}
