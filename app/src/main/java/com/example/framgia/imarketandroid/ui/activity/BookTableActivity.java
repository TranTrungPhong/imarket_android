package com.example.framgia.imarketandroid.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.Showcase;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;
import com.example.framgia.imarketandroid.util.ShowcaseGuideUtil;
import com.example.framgia.imarketandroid.util.findpath.GlideUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by phongtran on 24/08/2016.
 */
public class BookTableActivity extends Activity implements View.OnClickListener {
    private Button mButtonCallCenter;
    private Button mButtonLeftBack;
    private Button mButtonRightBack;
    private Button mButtonLeftBackKid;
    private Button mButtonRightBackKid;
    private Button mButtonLeftBackDateIn;
    private Button mButtonRightBackDateIn;
    private Button mButtonLeftBackTimeIn;
    private Button mButtonRightBackTimeIn;
    private Button mButtonLeftContinue;
    private TextView mTextViewDateIn;
    private TextView mTextViewTimeIn;
    private TextView mTextViewCount;
    private TextView mTextViewCountKid;
    private TextView mTextViewPhoneNumber;
    private EditText mEditTextNote;
    private int mCountPeople = Constants.MIN_COUNT_PEOPLE, mCountKid = Constants.MIN_COUNT_KID;
    private int mDay, mMonth, mYear, mHour, mMinute;
    private SharedPreferences mPreferences;
    private TextView mLogin;
    private ImageView mImageViewRestaurantCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booktable_layout);
        initView();
        mPreferences = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        initGuide();
        initImageByGlide();
    }

    private void initView() {
        mButtonCallCenter = (Button) findViewById(R.id.button_call_center);
        mButtonCallCenter.setOnClickListener(this);
        mButtonLeftBack = (Button) findViewById(R.id.button_left_back);
        mButtonLeftBack.setOnClickListener(this);
        mButtonRightBack = (Button) findViewById(R.id.button_right_back);
        mButtonRightBack.setOnClickListener(this);
        mButtonLeftBackKid = (Button) findViewById(R.id.button_left_back_kid);
        mButtonLeftBackKid.setOnClickListener(this);
        mButtonRightBackKid = (Button) findViewById(R.id.button_right_back_kid);
        mButtonRightBackKid.setOnClickListener(this);
        mButtonLeftBackDateIn = (Button) findViewById(R.id.button_left_back_dayin);
        mButtonLeftBackDateIn.setOnClickListener(this);
        mButtonRightBackDateIn = (Button) findViewById(R.id.button_right_back_dayin);
        mButtonRightBackDateIn.setOnClickListener(this);
        mButtonLeftBackTimeIn = (Button) findViewById(R.id.button_left_back_timein);
        mButtonLeftBackTimeIn.setOnClickListener(this);
        mButtonRightBackTimeIn = (Button) findViewById(R.id.button_right_back_timein);
        mButtonRightBackTimeIn.setOnClickListener(this);
        mButtonLeftContinue = (Button) findViewById(R.id.button_continuee);
        mButtonLeftContinue.setOnClickListener(this);
        mEditTextNote = (EditText) findViewById(R.id.edit_note);
        mEditTextNote.setFocusable(false);
        mEditTextNote.setOnClickListener(this);
        mTextViewDateIn = (TextView) findViewById(R.id.text_dayin);
        mTextViewDateIn.setOnClickListener(this);
        mTextViewTimeIn = (TextView) findViewById(R.id.text_time_in);
        mTextViewTimeIn.setOnClickListener(this);
        mTextViewCount = (TextView) findViewById(R.id.text_count_people);
        mTextViewCount.setOnClickListener(this);
        mTextViewCountKid = (TextView) findViewById(R.id.text_count_kid);
        mTextViewCountKid.setOnClickListener(this);
        mTextViewPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
        mTextViewPhoneNumber.setOnClickListener(this);
        mLogin = (TextView) findViewById(R.id.login_other_booktable);
        mLogin.setOnClickListener(this);
        mImageViewRestaurantCover = (ImageView)findViewById(R.id.image_restaurant);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_call_center:
                StringBuffer buffer = new StringBuffer()
                    .append(getString(R.string.tel))
                    .append(mTextViewPhoneNumber.getText());
                Uri call = Uri.parse(buffer.toString());
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(surf);
                break;
            case R.id.button_left_back:
                mCountPeople--;
                if (mCountPeople > 0) {
                    mTextViewCount.setText(mCountPeople);
                } else {
                    mCountPeople = Constants.MIN_COUNT_PEOPLE;
                }
                break;
            case R.id.button_right_back:
                mCountPeople++;
                mTextViewCount.setText(mCountPeople);
                break;
            case R.id.button_left_back_kid:
                mCountKid--;
                if (mCountKid >= 0) {
                    mTextViewCountKid.setText(mCountKid);
                } else {
                    mCountKid = Constants.MIN_COUNT_KID;
                }
                break;
            case R.id.button_right_back_kid:
                mCountKid++;
                mTextViewCountKid.setText(mCountKid);
                break;
            case R.id.button_left_back_dayin:
                changeDay(Constants.CHANGE_TIME_DOWN);
                break;
            case R.id.button_right_back_dayin:
                changeDay(Constants.CHANGE_TIME_UP);
                break;
            case R.id.button_left_back_timein:
                changeTime(Constants.CHANGE_TIME_DOWN);
                break;
            case R.id.button_right_back_timein:
                changeTime(Constants.CHANGE_TIME_UP);
                break;
            case R.id.button_continuee:
                Intent intentt = new Intent(this, BookProductActivity.class);
                startActivity(intentt);
                finish();
                break;
            case R.id.text_dayin:
                getShowDate();
                break;
            case R.id.text_time_in:
                getTime();
                break;
            case R.id.login_other_booktable:
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

    private void changeTime(int i) {
        if (i == Constants.CHANGE_TIME_UP) {
            if (mMinute < Constants.PHUT_30) {
                mMinute += Constants.PHUT_30;
                StringBuffer buffer = new StringBuffer()
                    .append(mHour)
                    .append(Constants.COLON)
                    .append(mMinute);
                mTextViewTimeIn.setText(buffer);
            } else {
                mMinute -= Constants.PHUT_30;
                mHour++;
                StringBuffer buffer = new StringBuffer()
                    .append(mHour)
                    .append(Constants.COLON)
                    .append(mMinute);
                mTextViewTimeIn.setText(buffer);
            }
        } else {
            if (mMinute > Constants.PHUT_30) {
                mMinute -= Constants.PHUT_30;
                StringBuffer buffer = new StringBuffer()
                    .append(mHour)
                    .append(Constants.COLON)
                    .append(mMinute);
                mTextViewTimeIn.setText(buffer);
            } else {
                mMinute = Constants.PHUT_30 + mMinute;
                mHour--;
                StringBuffer buffer = new StringBuffer()
                    .append(mHour)
                    .append(Constants.COLON)
                    .append(mMinute);
                mTextViewTimeIn.setText(buffer);
            }
        }
    }

    private void changeDay(int i) {
        if (mDay != 0) {
            if (mDay > Constants.FIRST_DAY && i == Constants.CHANGE_TIME_DOWN) {
                mDay += i;
                StringBuffer buffer = new StringBuffer()
                    .append(mDay)
                    .append(Constants.SEPARATOR)
                    .append(mMonth)
                    .append(Constants.SEPARATOR)
                    .append(mYear);
                mTextViewDateIn.setText(buffer);
            } else if (mDay == Constants.FIRST_DAY && i == Constants.CHANGE_TIME_DOWN) {
                mDay = Constants.COUNT_DAY_OF_MONTH;
                if (mMonth > Constants.FIRST_MONTH) {
                    mMonth--;
                    StringBuffer buffer = new StringBuffer()
                        .append(mDay)
                        .append(Constants.SEPARATOR)
                        .append(mMonth)
                        .append(Constants.SEPARATOR)
                        .append(mYear);
                    mTextViewDateIn.setText(buffer);
                } else if (mMonth == Constants.FIRST_MONTH) {
                    mMonth = Constants.COUNT_MONTH_OF_YEAR;
                    mYear--;
                    StringBuffer buffer = new StringBuffer()
                        .append(mDay)
                        .append(Constants.SEPARATOR)
                        .append(mMonth)
                        .append(Constants.SEPARATOR)
                        .append(mYear);
                    mTextViewDateIn.setText(buffer);
                }
            } else if (mDay < Constants.COUNT_DAY_OF_MONTH && i == Constants.CHANGE_TIME_UP) {
                mDay += i;
                StringBuffer buffer = new StringBuffer()
                    .append(mDay)
                    .append(Constants.SEPARATOR)
                    .append(mMonth)
                    .append(Constants.SEPARATOR)
                    .append(mYear);
                mTextViewDateIn.setText(buffer);
            } else if (mDay == Constants.COUNT_DAY_OF_MONTH && i == Constants.CHANGE_TIME_UP) {
                mDay = Constants.FIRST_DAY;
                if (mMonth < Constants.COUNT_MONTH_OF_YEAR) {
                    mMonth++;
                    StringBuffer buffer = new StringBuffer()
                        .append(mDay)
                        .append(Constants.SEPARATOR)
                        .append(mMonth)
                        .append(Constants.SEPARATOR)
                        .append(mYear);
                    mTextViewDateIn.setText(buffer);
                } else if (mMonth == Constants.COUNT_MONTH_OF_YEAR) {
                    mMonth = Constants.FIRST_MONTH;
                    mYear++;
                    StringBuffer buffer = new StringBuffer()
                        .append(mDay)
                        .append(Constants.SEPARATOR)
                        .append(mMonth)
                        .append(Constants.SEPARATOR)
                        .append(mYear);
                    mTextViewDateIn.setText(buffer);
                }
            }
        }
    }

    public void getShowDate() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                StringBuffer buffer = new StringBuffer()
                    .append(day)
                    .append(Constants.SEPARATOR)
                    .append(month + 1)
                    .append(Constants.SEPARATOR)
                    .append(year);
                mTextViewDateIn.setText(buffer);
            }
        };
        String s = mTextViewDateIn.getText().toString();
        String strArrtmp[] = s.split(Constants.SEPARATOR);
        mDay = Integer.parseInt(strArrtmp[0]);
        mMonth = Integer.parseInt(strArrtmp[1]) - 1;
        mYear = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
            this,
            callback, mYear, mMonth, mDay);
        pic.setTitle(getString(R.string.select_day));
        pic.show();
    }

    public void getTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        mMinute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                StringBuffer buffer = new StringBuffer()
                    .append(selectedHour)
                    .append(Constants.COLON)
                    .append(selectedMinute);
                mTextViewTimeIn.setText(buffer);
            }
        }, mHour, mMinute, true);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }

    private void initGuide() {
        List<Showcase> showList = new ArrayList<>();
        showList.add(new Showcase(mButtonCallCenter, getString(R.string.sequence_call_canter)));
        showList.add(new Showcase(mButtonLeftContinue, getString(R.string.click_continue_book_table)));
        ShowcaseGuideUtil.mutilShowcase(BookTableActivity.this, Constants
            .SHOWCASE_ID_BOOK_TABLE, showList);
    }


    private void initImageByGlide() {
        GlideUtil.getImage(this,mImageViewRestaurantCover, FakeContainer.URL_BG_PROFILE);
    }

}
