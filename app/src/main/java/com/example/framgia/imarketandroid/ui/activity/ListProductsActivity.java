package com.example.framgia.imarketandroid.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.remote.DatabaseTable;
import com.example.framgia.imarketandroid.data.model.ItemProduct;
import com.example.framgia.imarketandroid.ui.adapter.ListProductsAdapter;
import com.example.framgia.imarketandroid.util.Constants;

import java.util.ArrayList;

/**
 * Created by hoavt on 19/07/2016.
 */
public class ListProductsActivity extends AppCompatActivity implements SearchView
    .OnQueryTextListener{
    private RecyclerView mRvListProducts;
    private static String NAMECATEGORY="Apple";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ItemProduct> mItemProducts = new ArrayList<>();
    private DatabaseTable mDataBase;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_layout);
        mDataBase = new DatabaseTable(this);
        Intent intent = getIntent();
        if (intent != null)
            handleIntent(intent);
        loadDataFromServer();
        initViews();
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            updateListProducts(query);
        } else
            mItemProducts = FakeContainer.getListProducts();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void updateListProducts(String query) {
        Cursor cursor = mDataBase.getProductMatches(query, null);
        if (cursor != null) {
            mItemProducts.clear();
            //process Cursor and display results
            cursor.moveToFirst();
            try {
                while (!cursor.isAfterLast()) {
                    String nameProduct = cursor.getString(cursor.getColumnIndex(DatabaseTable.COL_NAME_PRODUCT));
                    String percentSale = cursor.getString(cursor.getColumnIndex(DatabaseTable.COL_PERCENTPROMOTION));
                    int idRes = FakeContainer.getPresentIconProduct(nameProduct);
                    ItemProduct itemProduct = new ItemProduct(nameProduct, percentSale, idRes);
                    mItemProducts.add(itemProduct);
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        } else {
            mItemProducts.clear();
        }

        ((ListProductsAdapter) mAdapter).setItems(mItemProducts);
        mAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        mRvListProducts = (RecyclerView) findViewById(R.id.rv_list_products);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRvListProducts.setHasFixedSize(true);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        // use a gridview layout manager
        mLayoutManager = new GridLayoutManager(this, Constants.COLS_LIST_PRODUCT);
        mRvListProducts.setLayoutManager(mLayoutManager);
        mAdapter = new ListProductsAdapter(this, mItemProducts);
        mRvListProducts.setAdapter(mAdapter);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(NAMECATEGORY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_searchview, menu);

        // Associate searchable configuration with the SearchView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        updateListProducts(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Here is where we are going to implement our filter logic
        if (newText.isEmpty()) {
            mItemProducts = FakeContainer.getListProducts();
            ((ListProductsAdapter) mAdapter).setItems(mItemProducts);
            mAdapter.notifyDataSetChanged();
            return true;
        } else
            updateListProducts(newText);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadDataFromServer() {

    }


}
