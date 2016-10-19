package com.example.framgia.imarketandroid.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.data.model.CommerceCanter;
import com.example.framgia.imarketandroid.data.model.DrawerItem;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.ui.adapter.HistoryTimeAdapter;
import com.example.framgia.imarketandroid.ui.adapter.RecyclerDrawerAdapter;
import com.example.framgia.imarketandroid.ui.adapter.RecyclerMarketAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.DialogShareUtil;
import com.example.framgia.imarketandroid.util.Flog;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;
import com.example.framgia.imarketandroid.util.findpath.InternetUtil;
import com.example.framgia.imarketandroid.util.findpath.LoadDataUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hoavt on 11/10/2016.
 */
public class ChooseMarketActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    SearchView.OnQueryTextListener, OnRecyclerItemInteractListener, TextWatcher {
    private RecyclerMarketAdapter mMarketAdapter;
    public static List<CommerceCanter> sMarkets = new ArrayList<>();
    private List<CommerceCanter> mTraceMarkets = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerMarket;
    private CursorAdapter mSearchSuggestionAdapter;
    private TextView mTextEmail;
    private List<DrawerItem> mDrawerItems = new ArrayList<>();
    private RecyclerView mRecyclerDrawer;
    private RecyclerDrawerAdapter mRecyclerDrawerAdapter;
    private View mStrokeLine1, mStrokeLine2, mStrokeLine3;
    private View mLinearMenu;
    private TextView mTextSignIn, mTextSignOut, mTextProfile;
    private TextView mTextUsername;
    private CircleImageView mCircleImageView;
    private List<CartItem> mCartItems = new ArrayList<>();
    private List<String> mHeaderNames = new ArrayList<>();
    private HistoryTimeAdapter mHistoryTimeAdapter;
    private RelativeLayout mReFavorite, mReBound, mReFollow;
    private LinearLayoutCompat mAcibSearch;
    private RadioButton mRbName, mRbLocation;
    private AutoCompleteTextView mAcactvSearchInput;
    private AppCompatTextView mActvNamePreview, mActvLocationPreview;
    private String[] mAutoCompleteList, mAutoCompleteNameList, mAutoCompleteLocationList;
    private ArrayAdapter<String> mAutoCompleteAdapter;
    public static List<String> sListAutoSearchName = new ArrayList<>();
    public static List<String> sListAutoSearchLocation = new ArrayList<>();
    private Button mButtonSearch;
    private String mCurrentTextSearchName, mCurrentTextSearchLocation;
    private List<CommerceCanter> mListChooseCenter = new ArrayList<>();
    private boolean mFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_market);
        findViews();
        setListeners();
        supportActionBar();
        setSearchSuggestionAdapter();
        initRececlerView();
    }

    private void initRececlerView() {
        // recyclerview navigation drawer
        mRecyclerDrawer.setLayoutManager(new LinearLayoutManager(this));
        getRecycleFavorite();
        mRecyclerDrawer.addItemDecoration(new LinearItemDecoration(this));
        mRecyclerDrawerAdapter.setOnClick(this);
        setListeners();
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mRecyclerMarket.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerMarket.addItemDecoration(new LinearItemDecoration(this));
        LoadDataUtils.loadCommerce(this);
        mMarketAdapter = new RecyclerMarketAdapter(this, sMarkets);
        mTraceMarkets.addAll(sMarkets);
        mRecyclerMarket.setAdapter(mMarketAdapter);
        mMarketAdapter.setOnRecyclerItemInteractListener(this);
    }

    private void supportActionBar() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setSearchSuggestionAdapter() {
        final String[] columns = new String[]{Constants.MARKET_SUGGESTION};
        final int[] displayViews = new int[]{android.R.id.text1};
        mSearchSuggestionAdapter = new SimpleCursorAdapter(this,
            android.R.layout.simple_list_item_1,
            null,
            columns,
            displayViews,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        getInfo();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {
        if (InternetUtil.isInternetConnected(ChooseMarketActivity.this)) {
            switch (view.getId()) {
                case R.id.button_favorite:
                    getRecycleFavorite();
                    getVisible(mStrokeLine1, mStrokeLine2, mStrokeLine3, mLinearMenu);
                    DialogShareUtil.getSmallBang(ChooseMarketActivity.this, mReFavorite);
                    break;
                case R.id.button_bought:
                    DialogShareUtil.getSmallBang(ChooseMarketActivity.this, mReBound);
                    getVisible(mStrokeLine2, mStrokeLine1, mStrokeLine3, mLinearMenu);
                    mHeaderNames = FakeContainer.getListHeader();
                    mCartItems = FakeContainer.getListCartItem();
                    if (mHistoryTimeAdapter == null) {
                        mHistoryTimeAdapter =
                            new HistoryTimeAdapter(mHeaderNames, mCartItems, this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        mRecyclerDrawer.setLayoutManager(linearLayoutManager);
                        mRecyclerDrawer.addItemDecoration(new LinearItemDecoration(this));
                        mRecyclerDrawer.setAdapter(mHistoryTimeAdapter);
                    } else {
                        mRecyclerDrawer.setAdapter(mHistoryTimeAdapter);
                    }
                    break;
                case R.id.button_follow:
                    DialogShareUtil.getSmallBang(ChooseMarketActivity.this, mReFollow);
                    getVisible(mStrokeLine3, mStrokeLine2, mStrokeLine1, mLinearMenu);
                    break;
                case R.id.button_more:
                    if (mLinearMenu.getVisibility() == View.GONE) {
                        mLinearMenu.setVisibility(View.VISIBLE);
                    } else {
                        mLinearMenu.setVisibility(View.GONE);
                    }
                    break;
                case R.id.button_sign_in:
                    mLinearMenu.setVisibility(View.GONE);
                    startActivity(new Intent(this, LoginActivity.class));
                    getInfo();
                    break;
                case R.id.button_sign_out:
                    SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
                    Session session = (Session) SharedPreferencesUtil
                        .getInstance()
                        .getValue(Constants.SESSION, Session.class);
                    if (session != null) {
                        actionSignout();
                    } else {
                        DialogShareUtil
                            .toastDialogMessage(getString(R.string.signout_fails_message),
                                ChooseMarketActivity.this);
                    }
                    mLinearMenu.setVisibility(View.GONE);
                    break;
                case R.id.button_profile:
                    SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
                    Session session2 = (Session) SharedPreferencesUtil
                        .getInstance()
                        .getValue(Constants.SESSION, Session.class);
                    if (session2 != null)
                        startActivity(new Intent(this, UpdateProfileActivity.class));
                    else {
                        mLinearMenu.setVisibility(View.GONE);
                        startActivity(new Intent(this, LoginActivity.class));
                        getInfo();
                    }
                    break;
                case R.id.image_avatar:
                    mLinearMenu.setVisibility(View.GONE);
                    break;
//                case R.id.acib_search_action:
//                    searchAction();
//                    break;
                case R.id.radio_name:
                    if (mAcactvSearchInput.getText() != null) {
                        mCurrentTextSearchLocation = mAcactvSearchInput.getText().toString();
                        mAcactvSearchInput.setHint(getString(R.string.input_name));
                        mAcactvSearchInput.setText("");
                    }
                    mAcactvSearchInput.setAdapter(new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        sListAutoSearchName));
                    if (mCurrentTextSearchName != null && !mCurrentTextSearchName.equals("")) {
                        mActvNamePreview.setText(mCurrentTextSearchName);
                    }
                    if (mCurrentTextSearchLocation != null && !mCurrentTextSearchLocation.equals("")) {
                        mActvLocationPreview.setText(mCurrentTextSearchLocation);
                        sListAutoSearchName.clear();
                        for (int i = 0; i < sMarkets.size(); i++) {
                            CommerceCanter center = sMarkets.get(i);
                            if (center.getAddress().equalsIgnoreCase(mCurrentTextSearchLocation)) {
                                sListAutoSearchName.add(center.getName());
                            }
                            mAcactvSearchInput.setHint(getString(R.string.input_location));
                            mAcactvSearchInput.setAdapter(new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1,
                                sListAutoSearchName));
                        }
                    } else {
                        sListAutoSearchName.clear();
                        for (int i = 0; i < sMarkets.size(); i++) {
                            CommerceCanter center = sMarkets.get(i);
                                sListAutoSearchName.add(center.getName());
                            mAcactvSearchInput.setHint(getString(R.string.input_location));
                            mAcactvSearchInput.setAdapter(new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1,
                                sListAutoSearchName));
                        }
                    }
                    break;
                case R.id.radio_location:
                    if (mAcactvSearchInput.getText() != null) {
                        mCurrentTextSearchName = mAcactvSearchInput.getText().toString();
                        mAcactvSearchInput.setText("");
                    }
                    if (mCurrentTextSearchLocation != null &&
                        !mCurrentTextSearchLocation.equals("")) {
                        mActvLocationPreview.setText(mCurrentTextSearchLocation);
                    }
                    if (mCurrentTextSearchName != null && !mCurrentTextSearchName.equals("")) {
                        sListAutoSearchLocation.clear();
                        for (int i = 0; i < sMarkets.size(); i++) {
                            CommerceCanter center = sMarkets.get(i);
                            if (center.getName().equalsIgnoreCase(mCurrentTextSearchName)) {
                                sListAutoSearchLocation.add(center.getAddress());
                            }
                            mAcactvSearchInput.setHint(getString(R.string.input_location));
                            mAcactvSearchInput.setAdapter(new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1,
                                sListAutoSearchLocation));
                        }
                        mActvNamePreview.setText(mCurrentTextSearchName);
                    } else {
                        sListAutoSearchLocation.clear();
                        for (int i = 0; i < sMarkets.size(); i++) {
                            CommerceCanter center = sMarkets.get(i);
                            if (center.getName().equalsIgnoreCase(mCurrentTextSearchName)) {
                                sListAutoSearchLocation.add(center.getAddress());
                            }
                            mAcactvSearchInput.setHint(getString(R.string.input_location));
                            mAcactvSearchInput.setAdapter(new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1,
                                sListAutoSearchLocation));
                        }
                    }
                    break;
                case R.id.img_button_search:
                    clickSearch();
                    break;
                case R.id.actv_search_input:
                    mAcactvSearchInput.setAdapter(new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        sListAutoSearchName));
                    initDataAutoCompleteTextView();
                    break;
                default:
                    break;
            }
        } else {
            Flog.toast(this, R.string.no_internet);
        }
    }
//    private void filterList(boolean byName, boolean byLocation) {
//        List<CommerceCanter> tempMarkets = new ArrayList<>();
//        if (byName && byLocation) {
//            for (int i = mTraceMarkets.size() - 1; i >= 0; i--) {
//                if (SystemUtil.deAccent(mTraceMarkets.get(i).getName().trim().toLowerCase())
//                    .contains(SystemUtil
//                        .deAccent(mActvNamePreview.getText().toString().trim().toLowerCase()))
//                    && SystemUtil.deAccent(mTraceMarkets.get(i).getAddress().trim().toLowerCase())
//                    .contains(SystemUtil.deAccent(
//                        mActvLocationPreview.getText().toString().trim().toLowerCase()))) {
//                    tempMarkets.add(mTraceMarkets.get(i));
//                }
//            }
//        } else if (!byName && byLocation) {
//            for (int i = mTraceMarkets.size() - 1; i >= 0; i--) {
//                if (SystemUtil.deAccent(mTraceMarkets.get(i).getAddress().trim().toLowerCase())
//                    .contains(SystemUtil.deAccent(
//                        mActvLocationPreview.getText().toString().trim().toLowerCase()))) {
//                    tempMarkets.add(mTraceMarkets.get(i));
//                }
//            }
//        } else if (byName && !byLocation) {
//            for (int i = mTraceMarkets.size() - 1; i >= 0; i--) {
//                if (SystemUtil.deAccent(mTraceMarkets.get(i).getName().trim().toLowerCase())
//                    .contains(SystemUtil
//                        .deAccent(mActvNamePreview.getText().toString().trim().toLowerCase()))) {
//                    tempMarkets.add(mTraceMarkets.get(i));
//                }
//            }
//        } else {
//            tempMarkets.addAll(mTraceMarkets);
//        }
//        sMarkets.clear();
//        sMarkets.addAll(tempMarkets);
//        mMarketAdapter.notifyDataSetChanged();
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        populateSuggestionAdapter(newText);
        return false;
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerMarket = (RecyclerView) findViewById(R.id.recycler_market);
        mRecyclerDrawer = (RecyclerView) findViewById(R.id.recycler_navigation_drawer);
        mStrokeLine1 = findViewById(R.id.nav_drawer_stroke_1);
        mStrokeLine2 = findViewById(R.id.nav_drawer_stroke_2);
        mStrokeLine3 = findViewById(R.id.nav_drawer_stroke_3);
        mLinearMenu = findViewById(R.id.linear_menu);
        mTextProfile = (TextView) findViewById(R.id.button_profile);
        mTextSignIn = (TextView) findViewById(R.id.button_sign_in);
        mTextSignOut = (TextView) findViewById(R.id.button_sign_out);
        mTextUsername = (TextView) findViewById(R.id.text_user_name);
        mTextEmail = (TextView) findViewById(R.id.text_email);
        mCircleImageView = (CircleImageView) findViewById(R.id.image_avatar);
        mReFavorite = (RelativeLayout) findViewById(R.id.button_favorite);
        mReFavorite.setOnClickListener(this);
        mReBound = (RelativeLayout) findViewById(R.id.button_bought);
        mReBound.setOnClickListener(this);
        mReFollow = (RelativeLayout) findViewById(R.id.button_follow);
        mReFollow.setOnClickListener(this);
        mAcibSearch = (LinearLayoutCompat) findViewById(R.id.acib_search_action);
        mAcibSearch.setOnClickListener(this);
        mRbName = (RadioButton) findViewById(R.id.radio_name);
        mRbName.setChecked(true);
        mRbName.setOnClickListener(this);
        mRbLocation = (RadioButton) findViewById(R.id.radio_location);
        mRbLocation.setOnClickListener(this);
        mAcactvSearchInput = (AutoCompleteTextView) findViewById(R.id.actv_search_input);
        mAcactvSearchInput.setHint(getString(R.string.input_name));
        mActvNamePreview = (AppCompatTextView) findViewById(R.id.actv_preview_name_market);
        mActvLocationPreview = (AppCompatTextView) findViewById(R.id.actv_preview_place_market);
        mAcactvSearchInput.addTextChangedListener(this);
        mButtonSearch = (Button) findViewById(R.id.img_button_search);
        mButtonSearch.setOnClickListener(this);
        mAcactvSearchInput.setOnClickListener(this);
    }
//    private void initAutoCompleteList() {
//        int len = sMarkets.size();
//        mAutoCompleteList = new String[len];
//        mAutoCompleteNameList = new String[len];
//        mAutoCompleteLocationList = new String[len];
//        for (int i = 0; i < len; i++) {
//            CommerceCanter commerceCanter = sMarkets.get(i);
//            mAutoCompleteNameList[i] = commerceCanter.getName();
//            mAutoCompleteLocationList[i] = commerceCanter.getAddress();
//        }
//    }
//    private void setAutoCompleteList(boolean notifyChanged) {
//        if (mRbName.isChecked()) {
//            mAutoCompleteList = mAutoCompleteNameList;
//        } else {
//            mAutoCompleteList = mAutoCompleteLocationList;
//        }
//        int layoutItemId = android.R.layout.simple_dropdown_item_1line;
//        mAutoCompleteAdapter = new ArrayAdapter<>(this, layoutItemId, mAutoCompleteList);
//        mAcactvSearchInput.setAdapter(mAutoCompleteAdapter);
//        if (notifyChanged)
//            mAutoCompleteAdapter.notifyDataSetChanged();
//    }

    private void setListeners() {
        findViewById(R.id.button_favorite).setOnClickListener(this);
        findViewById(R.id.button_bought).setOnClickListener(this);
        findViewById(R.id.button_follow).setOnClickListener(this);
        findViewById(R.id.button_more).setOnClickListener(this);
        mTextProfile.setOnClickListener(this);
        mTextSignIn.setOnClickListener(this);
        mTextSignOut.setOnClickListener(this);
    }

    private void populateSuggestionAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID,
            Constants.MARKET_SUGGESTION});
        int length = FakeContainer.SUGGESTIONS.length;
        for (int i = 0; i < length; i++) {
            if (FakeContainer.SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[]{i, FakeContainer.SUGGESTIONS[i]});
            mSearchSuggestionAdapter.changeCursor(c);
        }
    }

    private void getInfo() {
        SharedPreferencesUtil.getInstance().init(this, Constants.PREFS_NAME);
        UserModel userModel = (UserModel) SharedPreferencesUtil
            .getInstance()
            .getValue(Constants.SESSION, UserModel.class);
        if (userModel != null && userModel.getSession() != null) {
            if (userModel.getSession().getFullname() != null) {
                mTextUsername.setText(userModel.getSession().getFullname().toString());
            }
            if (userModel.getSession().getUsername() != null) {
                mTextEmail.setText(userModel.getSession().getUsername().toString());
            }
            if (userModel.getSession().getUrlImage() != null) {
                String url =
                    Constants.HEAD_URL + userModel.getSession().getUrlImage();
                Glide.with(this).load(url).into(mCircleImageView);
            } else {
                mCircleImageView.setImageResource(R.drawable.ic_framgia);
            }
        } else {
            mTextUsername.setText(R.string.login_hint_username);
            mTextEmail.setText(R.string.email);
            mCircleImageView.setImageResource(R.drawable.ic_framgia);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLinearMenu.setVisibility(View.GONE);
        getInfo();
    }

    private void actionSignout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.noti);
        builder.setMessage(R.string.confirm_signout);
        builder
            .setPositiveButton(R.string.ok_dialog_success, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferencesUtil.getInstance().clearSharedPreference();
                    dialog.dismiss();
                    Toast.makeText(ChooseMarketActivity.this,
                        getString(R.string.signout_done_message), Toast.LENGTH_SHORT).show();
                    getInfo();
                }
            });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
        SharedPreferencesUtil.getInstance().clearSharedPreference();
    }

    public void getVisible(View view1, View view2, View view3, View view4) {
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View v, int position) {
        switch (v.getId()) {
            case R.id.image_btn_delete:
                mDrawerItems.remove(position);
                mRecyclerDrawerAdapter.notifyDataSetChanged();
                break;
            case R.id.linear_navigation_drawer:
                startActivity(new Intent(this, DetailsProductActivity.class));
                break;
            case R.id.item_recycler_market:
                mLinearMenu.setVisibility(View.GONE);
                Intent intent = new Intent(this, FloorActivity.class);
                intent.putExtra(Constants.COMMERCE_INTENT, sMarkets.get(position));
                startActivity(intent);
                break;
        }
    }

    private void getRecycleFavorite() {
        mDrawerItems = FakeContainer.initDrawerItems();
        mRecyclerDrawerAdapter = new RecyclerDrawerAdapter(this, mDrawerItems);
        mRecyclerDrawer.setAdapter(mRecyclerDrawerAdapter);
    }
//    @Override
//    public void onLoadMarketsDone(List<CommerceCanter> commerceList) {
//        if (commerceList.size() <= 0)
//            return;
//        sMarkets.clear();
//        mTraceMarkets.clear();
//        int size = commerceList.size();
//        for (int i = 0; i < size; i++) {
//            CommerceCanter commerceCanter = commerceList.get(i);
//            sMarkets.add(commerceCanter);
//        }
//        mTraceMarkets.addAll(sMarkets);
//        mMarketAdapter.notifyDataSetChanged();
////        setCompleteEvent();
//    }
//    private void setCompleteEvent() {
//        initAutoCompleteList();
////        setAutoCompleteList(false);
//        mAcactvSearchInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                setAutoCompleteList(true);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (mRbName.isChecked()) {
//                    mActvNamePreview.setText(s);
//                } else {
//                    mActvLocationPreview.setText(s);
//                }
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (LoadDataUtils.mReceiver != null) {
            unregisterReceiver(LoadDataUtils.mReceiver);
        }
    }

    public static void initDataAutoCompleteTextView() {
        sListAutoSearchName.clear();
        sListAutoSearchLocation.clear();
        if (sMarkets == null || sMarkets.size() == 0) {
            sListAutoSearchName.add(Constants.COM_NAME);
            sListAutoSearchLocation.add(Constants.COM_LOCATION);
        } else {
            for (CommerceCanter center : sMarkets) {
                sListAutoSearchName.add(center.getName());
                sListAutoSearchLocation.add(center.getAddress());
            }
        }
//        mAutoCompleteAdapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!s.equals("") && !mFlag) {
//            Flog.toast(this,"show");
            mAcactvSearchInput.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                sListAutoSearchName));
            initDataAutoCompleteTextView();
            mFlag = true;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void clickSearch() {
        String textSearch = mAcactvSearchInput.getText().toString();
        if (!textSearch.equals("")) {
            if (mRbName.isChecked()) {
                mActvNamePreview.setText(textSearch);
            } else {
                mActvLocationPreview.setText(textSearch);
            }
            mListChooseCenter.clear();
            String name = mActvNamePreview.getText().toString();
            String address = mActvLocationPreview.getText().toString();
            for (CommerceCanter center : sMarkets) {
                if (center.getName().equals(name) || center.getAddress().equals(address)) {
                    mListChooseCenter.add(center);
                }
            }
            mMarketAdapter = new RecyclerMarketAdapter(this, mListChooseCenter);
            mRecyclerMarket.setAdapter(mMarketAdapter);
            mMarketAdapter.notifyDataSetChanged();
        } else {
            mMarketAdapter = new RecyclerMarketAdapter(this, sMarkets);
            mRecyclerMarket.setAdapter(mMarketAdapter);
            mMarketAdapter.notifyDataSetChanged();
        }
    }
}
