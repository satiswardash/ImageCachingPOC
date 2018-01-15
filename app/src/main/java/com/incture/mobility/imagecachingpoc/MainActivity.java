package com.incture.mobility.imagecachingpoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import javax.annotation.Nullable;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Context mContext = MainActivity.this;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private static RealmResults<CardItem> realmResults;
    private Realm realm;

    private FloatingActionButton.OnClickListener mFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, NewRecordActivity.class);
            startActivity(intent);
        }
    };

    private OrderedRealmCollectionChangeListener<RealmResults<CardItem>> mChangeListener = new OrderedRealmCollectionChangeListener<RealmResults<CardItem>>() {
        @Override
        public void onChange(RealmResults<CardItem> cardItems, @Nullable OrderedCollectionChangeSet changeSet) {
            bindAdapter(cardItems);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.hc_recyclerView);
        fab = findViewById(R.id.hc_floatingActionButton);

        if (App.isSync) {
            realmResults = realm.where(CardItem.class).findAllAsync();
            realmResults.addChangeListener(mChangeListener);
            App.isSync = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (realm.where(CardItem.class).findFirst() == null) {
            //SampleDataProvider.loadSampleDataToRealm(mContext);
        }

        if (App.isSync == false && realmResults == null) {
            realmResults = realm.where(CardItem.class).findAllAsync();
        }
        realmResults.addChangeListener(mChangeListener);
        fab.setOnClickListener(mFabListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        realmResults.removeChangeListener(mChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void bindAdapter(RealmResults<CardItem> cardItems) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CardsAdapter(mContext, cardItems));

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });
    }
}
