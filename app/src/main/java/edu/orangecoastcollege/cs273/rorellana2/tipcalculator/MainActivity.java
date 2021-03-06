package edu.orangecoastcollege.cs273.rorellana2.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Associate the controller with the needed views
    private EditText amountEditText;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView totalTextView;
    private TextView tipTextView;
    private SeekBar percentSeekBar;

    private static NumberFormat currency = NumberFormat.getCurrencyInstance();
    private static NumberFormat percent = NumberFormat.getPercentInstance();

    // Associate the controller with the needed model
    RestaurantBill currentBill = new RestaurantBill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Connect the controller with the widgets in our app
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);

        // Define a listener for the amountEditText (onTextChange)
        amountEditText.addTextChangedListener(amountTextChangedListener);

        // Define a listener for the percentSeekBar (onProgressChanged)
        percentSeekBar.setOnSeekBarChangeListener(percentChangedListener);
    }

    private TextWatcher amountTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Try to get the amount from amountEditText:
            try {
                double amount = Double.parseDouble(charSequence.toString()) / 100.0;
                currentBill.setAmount(amount);
            }
            catch(NumberFormatException e)
            {
                amountEditText.setText("");
            }

            // No exception, input is valid:
            // 1) Set the bill amount (amountTextView)
            amountTextView.setText(currency.format(currentBill.getAmount()));
            updateViews();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private SeekBar.OnSeekBarChangeListener percentChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            // Update the model with the new tip amount
            currentBill.setTipPercent(i / 100.0);

            // Update the percentTextView
            percentTextView.setText(percent.format(currentBill.getTipPercent()));

            // Update the views
            updateViews();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void updateViews()
    {
        // 2) Set the tip amount (tipTextView)
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        // 3) Set the total amount (totalAmountTextView)
        totalTextView.setText(currency.format(currentBill.getTotalAmount()));
    }
}
