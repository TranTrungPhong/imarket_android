package com.example.framgia.imarketandroid.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;

import java.util.Calendar;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by phongtran on 25/08/2016.
 */
public class BookProductActivity extends Activity implements View.OnClickListener {
    private EditText mEditTextEmail, mEditTextAddress, mEditTextPhoneNumber;
    private Button mButtonClearEmail, mButtonClearAddress, mButtonClearPhoneNumber;
    private TextView mTextViewTimeShip, mTextViewDateShip;
    private TextView mTextViewTimeGoToShop, mTextViewDateGoToShop;
    private TextView mTextViewForgetPass;
    private Button mButtonContinue;
    private RadioButton mRadioButtonOnline, mRadioButtonOffline;
    private LinearLayout mLinearLayoutRadioOnline, mLinearLayoutRadioOffline;
    private SharedPreferences mPreferences;
    private Button mButtonLoginByFace;
    private Button mButtonLoginByGoogle;
    private TextView mLoginOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookproduct_layout);
        initView();
        mPreferences = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        initGuide();
    }

    private void initView() {
        mLinearLayoutRadioOnline = (LinearLayout) findViewById(R.id.linear_buy_online);
        mLinearLayoutRadioOnline.setVisibility(View.GONE);
        mLinearLayoutRadioOffline = (LinearLayout) findViewById(R.id.linear_buy_shop);
        mLinearLayoutRadioOffline.setVisibility(View.GONE);
        mEditTextEmail = (EditText) findViewById(R.id.edit_email_book_product);
        mEditTextEmail.setFocusable(false);
        mEditTextPhoneNumber = (EditText) findViewById(R.id.edit_phone_book_product);
        mEditTextPhoneNumber.setFocusable(false);
        mEditTextAddress = (EditText) findViewById(R.id.edit_address_ship);
        mButtonClearEmail = (Button) findViewById(R.id.button_clear_email);
        mButtonClearEmail.setOnClickListener(this);
        mButtonClearPhoneNumber = (Button) findViewById(R.id.button_clear_phonenumber);
        mButtonClearPhoneNumber.setOnClickListener(this);
        mButtonClearAddress = (Button) findViewById(R.id.button_clear_address_ship);
        mButtonClearAddress.setOnClickListener(this);
        mTextViewTimeShip = (TextView) findViewById(R.id.text_hour_ship);
        mTextViewTimeShip.setOnClickListener(this);
        mTextViewDateShip = (TextView) findViewById(R.id.text_day_ship);
        mTextViewDateShip.setOnClickListener(this);
        mButtonContinue = (Button) findViewById(R.id.button_continue_book_product);
        mButtonContinue.setOnClickListener(this);
        mRadioButtonOnline = (RadioButton) findViewById(R.id.radioButtonOnline);
        mRadioButtonOnline.setOnClickListener(this);
        mRadioButtonOffline = (RadioButton) findViewById(R.id.radioButtonOff);
        mRadioButtonOffline.setOnClickListener(this);
        mTextViewTimeGoToShop = (TextView) findViewById(R.id.text_time_in);
        mTextViewTimeGoToShop.setOnClickListener(this);
        mTextViewDateGoToShop = (TextView) findViewById(R.id.text_day_in);
        mTextViewDateGoToShop.setOnClickListener(this);
        mLoginOther = (TextView)findViewById(R.id.login_other);
        mLoginOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear_email:
                mEditTextEmail.setText(Constants.CLEAR_EDITTEXT);
                break;
            case R.id.button_clear_phonenumber:
                mEditTextPhoneNumber.setText(Constants.CLEAR_EDITTEXT);
                break;
            case R.id.button_clear_address_ship:
                mEditTextAddress.setText(Constants.CLEAR_EDITTEXT);
                break;
            case R.id.text_hour_ship:
                getTime(Constants.SHIP);
                break;
            case R.id.text_day_ship:
                getShowDate(Constants.SHIP);
                break;
            case R.id.button_continue_book_product:
                // TODO continue
                Intent intent = new Intent(this, BookTableActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.radioButtonOnline:
                mLinearLayoutRadioOnline.setVisibility(View.VISIBLE);
                mLinearLayoutRadioOffline.setVisibility(View.GONE);
                mRadioButtonOffline.setChecked(false);
                break;
            case R.id.radioButtonOff:
                mLinearLayoutRadioOnline.setVisibility(View.GONE);
                mLinearLayoutRadioOffline.setVisibility(View.VISIBLE);
                mRadioButtonOnline.setChecked(false);
                break;
            case R.id.text_time_in:
                getTime(Constants.GOTOSHOP);
                break;
            case R.id.text_day_in:
                getShowDate(Constants.GOTOSHOP);
                break;
            case R.id.login_other :
                Session session = (Session) SharedPreferencesUtil.getInstance().getValue
                    (Constants.SESSION,
                        Session.class);
                if (session.getId() == null) {
                    Intent intentLogin = new Intent(this, LoginActivity.class);
                    startActivity(intentLogin);
                } else {
                    DialogShareUtil.toastDialogMessage(getString(R.string.login_befor), this);
                }
                break;
        }
    }

    public void getShowDate(final int i) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                StringBuffer buffer = new StringBuffer()
                    .append(day)
                    .append(Constants.SEPARATOR)
                    .append(month + 1)
                    .append(Constants.SEPARATOR)
                    .append(year);
                if (i == Constants.SHIP) {
                    mTextViewDateShip.setText(buffer);
                } else if (i == Constants.GOTOSHOP) {
                    mTextViewDateGoToShop.setText(buffer);
                }
            }
        };
        if (i == Constants.SHIP) {
            String s = mTextViewDateShip.getText().toString();
            String strArrtmp[] = s.split(Constants.SEPARATOR);
            int day = Integer.parseInt(strArrtmp[0]);
            int month = Integer.parseInt(strArrtmp[1]) - 1;
            int year = Integer.parseInt(strArrtmp[2]);
            DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, year, month, year);
            pic.setTitle(getString(R.string.select_day));
            pic.show();
        } else if (i == Constants.GOTOSHOP) {
            String s = mTextViewDateGoToShop.getText().toString();
            String strArrtmp[] = s.split(Constants.SEPARATOR);
            int day = Integer.parseInt(strArrtmp[0]);
            int month = Integer.parseInt(strArrtmp[1]) - 1;
            int year = Integer.parseInt(strArrtmp[2]);
            DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, year, month, day);
            pic.setTitle(getString(R.string.select_day));
            pic.show();
        }
    }

    public void getTime(final int i) {
        Calendar mcurrentTime = Calendar.getInstance();
        int gio = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int phut = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                StringBuffer buffer = new StringBuffer()
                    .append(selectedHour)
                    .append(Constants.COLON)
                    .append(selectedMinute);
                if (i == Constants.SHIP) {
                    mTextViewTimeShip.setText(buffer);
                } else if (i == Constants.GOTOSHOP) {
                    mTextViewTimeGoToShop.setText(buffer);
                }
            }
        }, gio, phut, true);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }

    private void initGuide() {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(Constants.TIME_DELAY_GUIDE); // half second between each showcase view
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this,
            Constants.SHOWCASE_ID_BOOK_PRODUCT);
        sequence.setConfig(config);
        sequence.addSequenceItem(mRadioButtonOnline, getString(R.string.sequence_radio_online),
            Constants.GOT_IT);
        sequence.addSequenceItem(mRadioButtonOffline, getString(R.string.sequence_radio_offline),
            Constants.GOT_IT);
        sequence.addSequenceItem(mButtonContinue, getString(R.string.click_continue_book_table),
            Constants.GOT_IT);
        sequence.start();
    }
}
