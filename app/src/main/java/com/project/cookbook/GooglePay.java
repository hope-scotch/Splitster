/*package com.project.cookbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class GooglePay extends Activity implements View.OnClickListener {

    ImageButton GPay;
    private PaymentsClient paymentsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpay_layout);

        GPay = (ImageButton) findViewById(R.id.ibGPay);
        GPay.setVisibility(View.INVISIBLE);
        GPay.setOnClickListener(this);

        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build();

        paymentsClient = Wallet.getPaymentsClient(
                this, walletOptions);


        IsReadyToPayRequest readyToPayRequest = null;
        try {
            readyToPayRequest = IsReadyToPayRequest.fromJson(baseConfigurationJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Task<Boolean> task = paymentsClient.isReadyToPay(readyToPayRequest);
        task.addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            public void onComplete(Task<Boolean> completeTask) {
                if (completeTask.isSuccessful()) {
                    showGooglePayButton(completeTask.getResult());
                } else {
                    // Handle the error accordingly
                }
            }
        });
    }

    private JSONObject baseConfigurationJson() throws JSONException {
        return new JSONObject()
                .put("apiVersion", 21)
                .put("apiVersionMinor", 0)
                .put("allowedPaymentMethods",
                        new JSONArray().put(getCardPaymentMethod()));

    }

    private JSONObject getCardPaymentMethod() {
        final String[] networks = new String[]{"VISA", "AMEX"};
        final String[] authMethods =
                new String[]{"PANE_ONLY", "CRYPTOGRAM_3DS"};

        JSONObject card = new JSONObject();
        try {
            card.put("type", "CARD");
            card.put("tokeniztionSpecification", getTokenizationSpec());
            card.put("parameters", new JSONObject()
                    .put("allowedAuthMethods", new JSONArray(authMethods))
                    .put("allowedCardNetworks", new JSONArray(networks)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return card;
    }

    private void showGooglePayButton(Boolean userIsReadyToPay) {
        if (userIsReadyToPay) {
            GPay.setVisibility(View.VISIBLE);
            // Update your UI to show the Google Pay button
            // eg.: googlePayButton.setVisibility(View.VISIBLE);
        } else {
            // Google Pay is not supported. Do not show the button.
        }
    }


    @Override
    public void onClick(View v) {

        final JSONObject paymentRequestJson;

            paymentRequestJson = baseConfigurationJson();
            paymentRequestJson.put("transactionInfo", new JSONObject()
                    .put("totalPrice", "123.45")
                    .put("totalPriceStatus", "FINAL")
                    .put("currencyCode", "USD"));

            paymentRequestJson.put("merchantInfo", new JSONObject()
                    .put("merchantId", "01234567890123456789")
                    .put("merchantName", "Example Merchant"));

        final PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentRequestJson.toString());

        AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }
}
*/
