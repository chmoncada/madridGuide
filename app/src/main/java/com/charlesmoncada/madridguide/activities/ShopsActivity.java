package com.charlesmoncada.madridguide.activities;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.charlesmoncada.madridguide.R;
import com.charlesmoncada.madridguide.adapters.ShopsAdapter;
import com.charlesmoncada.madridguide.fragments.ShopsFragment;
import com.charlesmoncada.madridguide.manager.db.DBConstants;
import com.charlesmoncada.madridguide.manager.db.ShopDAO;
import com.charlesmoncada.madridguide.manager.db.provider.MadridGuideProvider;
import com.charlesmoncada.madridguide.model.Shop;
import com.charlesmoncada.madridguide.model.Shops;

import java.util.List;

public class ShopsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ShopsFragment shopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shops_fragment_shops);

        //getShops();

        shopsFragment.setOnElementClickListener(new ShopsAdapter.OnElementClick<Shop>() {
            @Override
            public void elementClicked(Shop element, int position) {

            }
        });

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    // 1st attempt at async cursor load: works!
    public void getShops() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShopDAO dao = new ShopDAO(ShopsActivity.this);

                List<Shop> shopList = dao.query();
                final Shops shops = Shops.build(shopList);

                ShopsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shopsFragment.setShops(shops);
                    }
                });
            }
        });
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                MadridGuideProvider.SHOPS_URI,
                DBConstants.ALL_COLUMNS,            // projection
                null,                               // where
                null,                               // campos del where
                null);                              // order

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        final Shops shops = ShopDAO.getShops(data);

        shopsFragment.setShops(shops);
    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
