package com.incture.mobility.imagecachingpoc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class CardDetailsActivity extends AppCompatActivity {

    TextView descriptionTextView;
    Toolbar toolbar;
    ImageView appBarImageView;

    private String cardUid = null;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("uid")) {
            cardUid = (String) bundle.get("uid");
        }

        setContentView(R.layout.activity_card_details);
        descriptionTextView = findViewById(R.id.cd_description_textView);
        toolbar = findViewById(R.id.cd_toolbar);
        appBarImageView = findViewById(R.id.app_bar_image);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (cardUid != null) {
            CardItem cardItem = realm.where(CardItem.class).equalTo("id", cardUid).findFirst();
            bindView(cardItem);
        }
    }

    private void bindView(CardItem cardItem) {
        //appBarTitleTextView.setText(cardItem.getTitle());
        toolbar.setTitle(cardItem.getTitle());
        descriptionTextView.setText(cardItem.getDescription());
        Picasso.with(this).load(cardItem.getImageUris().get(3)).into(appBarImageView);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
