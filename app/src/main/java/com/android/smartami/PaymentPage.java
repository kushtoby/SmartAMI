package com.android.smartami;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;


public class PaymentPage extends AppCompatActivity{


/*    Before you continue, make sure you have done the following:
1.  Create an account on http://paystack.com, complete the registration, create a new project and generate your public key.
2.  Add the line: ~implementation 'co.paystack.android:paystack:3.0.10'~ to your app-level gradle dependency.
3.  Add the line: ~<uses-permission android:name="android.permission.INTERNET" />~ to your manifest.
*/


    //paste the API key generated from step 1 in the line below:
   //private String your_api_key = "pk_test_cd9f7830aa1832c03f09880cb23b8886e3820038";

    public EditText cardNum, cardMonth,cardYear, CVV, payAmount;
    public TextView payRate;
    public Button enterTransaction;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);



        //This following line is very important, you must initialize the Paystack SDK before using any Paystack class or interface.
        PaystackSdk.initialize(this.getApplicationContext());



        //Also, set your public key (from step 1 above) like this:
        //PaystackSdk.setPublicKey(your_api_key);



        //initialize views
        cardNum = findViewById(R.id.cardNumET);
        cardMonth = findViewById(R.id.cardMonET);
        cardYear = findViewById(R.id.cardYearET);
        CVV = findViewById(R.id.cvvET);
        payAmount = findViewById(R.id.payAmountET);
        payRate = findViewById(R.id.payAmountTV);
        enterTransaction = findViewById(R.id.paystackBtn);


            payAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String conversion = payAmount.getText().toString();
                enterTransaction.setEnabled(!conversion.isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!payAmount.getText().toString().isEmpty()) {
                    final int pricePower = Integer.parseInt(payAmount.getText().toString());
                    payRate.setText((pricePower * 0.1) + " kWh");
                }
            }
        });


        //handle the onClick when the 'pay-button' is pressed
        enterTransaction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //check whether user filled all the fields and there is internet connection

                if(isUserEntryValid() && isInternetAvailable()){
                 //   prepareToChargeUser(2000 * 100);//In this case, we're charging the user #2000
                    String cardNumber = cardNum.getText().toString().trim();
                    int expiryMonth = Integer.parseInt(cardMonth.getText().toString().trim());
                    int expiryYear = Integer.parseInt(cardYear.getText().toString().trim());
                    String cvv = CVV.getText().toString().trim();
                    int amount = Integer.parseInt(payAmount.getText().toString().trim() + "00");


            /*
            For testing purposes, Paystack provides a test card.. If you're still in the testing stage of your project,
            consider using the test cards, they are awesome!
            String cardNumber = "4084084084084081";
            int expiryMonth = 11; //any month in the future
            int expiryYear = 20; // any year in the future. '2020' would work also!
            String cvv = "408";  // cvv of the test card
            */


                    //Create a new Card object based on the card details
                    Card card = new Card(cardNumber, expiryMonth, expiryYear, cvv);

                    //check whether the card is valid like this:
                    if (!(card.isValid())) {
                        Toast.makeText(getApplicationContext(), "Card is not Valid", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {
                        //Create a new Charge object
                        Charge charge = new Charge();

                        charge.setCard(card);
                        charge.setEmail("kushtoby@gmail.com");
                        charge.setAmount(amount);

                        //proceed to charge the user
                        PaystackSdk.chargeCard(PaymentPage.this, charge, new Paystack.TransactionCallback(){
                            @Override
                            public void beforeValidate(Transaction transaction){
                                //You can set a progress bar or progress dialog so that the user knows something is going on
                            }

                            @Override
                            public void onSuccess(Transaction transaction){
                                final int payPower = Integer.parseInt(payAmount.getText().toString());
                                Toast.makeText(getApplicationContext(), "Payment successful!!!", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), "You have purchased " + (payPower *0.1) + " kWh", Toast.LENGTH_LONG).show();
                                final String newPow = String.valueOf(payPower *0.1);

                             ref.child("Energy_Data1").child("User_1").addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                      String previousPower = dataSnapshot.child("energyAvailable").getValue().toString();
                                      float newPower = Float.parseFloat(previousPower);
                                      float finalPower = newPower + Float.parseFloat(newPow);
                                      ref.child("Energy_Data").child("User_1").child("energyAvailable").setValue(finalPower);
                                      finish();
                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                  }
                              });
                                //Payment successful; proceed to give your user the product/service they paid for.*/
                            }

                            @Override
                            public void onError(Throwable error, Transaction transaction){
                                new AlertDialog.Builder(getApplicationContext())
                                        .setMessage("Payment unsuccessful, please try again later")
                                        .setCancelable(false)
                                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //try again
                                            }
                                        })
                                        .show();

                            }
                        });
                    }


                }

                else{
//                    Notify the user to check his internet connection or validate his entries
                }
            }
        });
    }


    private boolean isUserEntryValid(){
        boolean isValid = true;

        //check whether the card-number field is empty
        if(TextUtils.isEmpty(cardNum.getText().toString())){
            cardNum.setError("This field is required");
            isValid = false;
        }

        //check whether the expiry-month field is empty
        else if(TextUtils.isEmpty(cardMonth.getText().toString())){
            cardMonth.setError("This field is required");
            isValid = false;
        }

        //check whether the expiry-year field is empty
        else if(TextUtils.isEmpty(cardYear.getText().toString())){
            cardYear.setError("This field is required");
            isValid = false;
        }

        //check whether the card-cvv field is empty
        else if(TextUtils.isEmpty(CVV.getText().toString())){
            CVV.setError("This field is required");
            isValid = false;
        }


        return isValid;
    }


    private boolean isInternetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
