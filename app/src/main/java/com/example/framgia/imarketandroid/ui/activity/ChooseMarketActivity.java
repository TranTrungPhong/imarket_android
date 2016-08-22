package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.models.DrawerItem;
import com.example.framgia.imarketandroid.models.Market;
import com.example.framgia.imarketandroid.ui.adapter.RecyclerDrawerAdapter;
import com.example.framgia.imarketandroid.ui.adapter.RecyclerMarketAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yue on 20/07/2016.
 */
public class ChooseMarketActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    SearchView.OnQueryTextListener, OnRecyclerItemInteractListener {
    private static final String MARKET_SUGGESTION = "marketName";
    private static final String[] SUGGESTIONS = new String[]{
        "Belgium", "France", "Italy", "Germany", "Spain", "Viet Nam"
    };
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerMarket;
    private RecyclerMarketAdapter mAdapter;
    private List<Market> mMarkets = new ArrayList<>();
    private CursorAdapter mSearchSuggestionAdapter;
    private TextView mTextEmail;
    private ImageView mImageAvatar;
    private List<DrawerItem> mDrawerItems = new ArrayList<>();
    private RecyclerView mRecyclerDrawer;
    private RecyclerDrawerAdapter mRecyclerDrawerAdapter;
    private View mStrokeLine1, mStrokeLine2, mStrokeLine3;
    private View mLinearMenu;
    private View mButtonSignIn, mButtonProfile, mButtonSignOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_market);
        findViews();
        mRecyclerDrawer.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 16; i++) {
            if (i != 15) {
                mDrawerItems.add(new DrawerItem("Test " + (i + 1), "test"));
            } else {
                mDrawerItems.add(new DrawerItem("Test 16", "test", true));
            }
        }
        mRecyclerDrawerAdapter = new RecyclerDrawerAdapter(mDrawerItems);
        mRecyclerDrawer.setAdapter(mRecyclerDrawerAdapter);
        setListeners();
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mRecyclerMarket.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerMarket.addItemDecoration(new LinearItemDecoration(this));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mMarkets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        mAdapter = new RecyclerMarketAdapter(mMarkets);
        mRecyclerMarket.setAdapter(mAdapter);
        final String[] columns = new String[]{MARKET_SUGGESTION};
        final int[] displayViews = new int[]{android.R.id.text1};
        mSearchSuggestionAdapter = new SimpleCursorAdapter(this,
            android.R.layout.simple_list_item_1,
            null,
            columns,
            displayViews,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mAdapter.setOnRecyclerItemInteractListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_market, menu);
        SearchView searchView = (SearchView)
            MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setSuggestionsAdapter(mSearchSuggestionAdapter);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
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
        switch (view.getId()) {
            case R.id.button_favorite:
                mStrokeLine1.setVisibility(View.VISIBLE);
                mStrokeLine2.setVisibility(View.GONE);
                mStrokeLine3.setVisibility(View.GONE);
                break;
            case R.id.button_bought:
                mStrokeLine1.setVisibility(View.GONE);
                mStrokeLine2.setVisibility(View.VISIBLE);
                mStrokeLine3.setVisibility(View.GONE);
                break;
            case R.id.button_follow:
                mStrokeLine1.setVisibility(View.GONE);
                mStrokeLine2.setVisibility(View.GONE);
                mStrokeLine3.setVisibility(View.VISIBLE);
                break;
            case R.id.button_more:
                if (mLinearMenu.getVisibility() == View.GONE) {
                    mLinearMenu.setVisibility(View.VISIBLE);
                } else {
                    mLinearMenu.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        populateSuggestionAdapter(newText);
        return false;
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this, CategoryStallActivity.class));
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
        mButtonSignIn = findViewById(R.id.button_sign_in);
        mButtonProfile = findViewById(R.id.button_profile);
        mButtonSignOut = findViewById(R.id.button_sign_out);
    }

    private void setListeners() {
        findViewById(R.id.button_favorite).setOnClickListener(this);
        findViewById(R.id.button_bought).setOnClickListener(this);
        findViewById(R.id.button_follow).setOnClickListener(this);
        findViewById(R.id.button_more).setOnClickListener(this);
    }

    private void populateSuggestionAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, MARKET_SUGGESTION});
        int length = SUGGESTIONS.length;
        for (int i = 0; i < length; i++) {
            if (SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[]{i, SUGGESTIONS[i]});
        }
        mSearchSuggestionAdapter.changeCursor(c);
    }
}
