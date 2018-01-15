package com.incture.mobility.imagecachingpoc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

public class NewRecordActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 22;
    TextView titleTextView;
    TextView descTextView;
    Button attachButton;
    Button submitButton;
    RecyclerView recyclerView;
    List<Uri> uris = new ArrayList<>();
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_new_record);

        titleTextView = findViewById(R.id.an_title_editText);
        descTextView = findViewById(R.id.an_description_editText);
        attachButton = findViewById(R.id.attach_button);
        submitButton = findViewById(R.id.submit_button);
        recyclerView = findViewById(R.id.an_selectedImagesRecyclerView);

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                imageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), PICK_IMAGE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleTextView != null && !titleTextView.getText().toString().isEmpty()){

                    CardItem cardItem = new CardItem();
                    cardItem.setId(UUID.randomUUID().toString());
                    cardItem.setTitle(titleTextView.getText().toString());
                    cardItem.setDescription(descTextView.getText().toString());

                    RealmList<String> images = new RealmList<>();
                    if (!uris.isEmpty()) {

                        for (Uri uri :
                                uris) {
                            images.add(uri.getPath());
                        }
                    }
                    cardItem.setImageUris(images);

                    realm.beginTransaction();
                    realm.copyToRealm(cardItem);
                    realm.commitTransaction();

                    App.isSync = true;
                    Toast.makeText(NewRecordActivity.this, "Successfully added.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while(currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        uris.add(imageUri);
                        currentItem = currentItem + 1;
                    }
                } else if(data.getData() != null) {
                    Uri imagePath = data.getData();//.getPath();
                    uris.add(imagePath);
                }
            }
            bindImageAttachmentAdapter(uris);
        }
    }

    private void bindImageAttachmentAdapter(List<Uri> uris) {
        AttachmentAdapter adapter = new AttachmentAdapter(this, uris);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        recyclerView.setAdapter(adapter);
    }
}
