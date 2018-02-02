package com.incture.mobility.imagecachingpoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.incture.mobility.imagecachingpoc.adapter.CardsAdapter;
import com.incture.mobility.imagecachingpoc.model.CardItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.am_cards_recycler_view);
        fab = findViewById(R.id.hc_floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddNewCardActivity.class);
                startActivity(intent);
            }
        });

        realmResults = realm.where(CardItem.class).findAllAsync();
        realmResults.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<CardItem>>() {
            @Override
            public void onChange(RealmResults<CardItem> cardItems, @Nullable OrderedCollectionChangeSet changeSet) {
                bindAdapter(realmResults);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //bindAdapter(realmResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void bindAdapter(RealmResults<CardItem> cardItems) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CardsAdapter(mContext, cardItems));
    }
}
