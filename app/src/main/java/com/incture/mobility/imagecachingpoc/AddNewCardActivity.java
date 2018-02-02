package com.incture.mobility.imagecachingpoc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.incture.mobility.imagecachingpoc.fragments.AddTitleFragment;
import com.incture.mobility.imagecachingpoc.fragments.ImageAttachmentFragment;
import com.incture.mobility.imagecachingpoc.model.CardItem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

public class AddNewCardActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 22;
    private static final int REQUEST_TAKE_PHOTO = 1;
    public String mCurrentPhotoPath;
    public List<Uri> uris = new ArrayList<>();
    public String title;
    public String description;

    private String itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.add_new_card_root_frame, new AddTitleFragment(), AddTitleFragment.class.toString())
                .commit();
        Bundle bundle = getIntent().getExtras();
        /*if (bundle.containsKey("attachment")) {

            itemId = bundle.getString("uid");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.add_new_card_root_frame, new ImageAttachmentFragment(), ImageAttachmentFragment.class.toString())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.add_new_card_root_frame, new AddTitleFragment(), AddTitleFragment.class.toString())
                    .commit();
        }*/

    }

    public void handleCameraEvent() throws IOException {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = Uri.fromFile(createImageFile());
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        BuildConfig.APPLICATION_ID + ".utils.GenericFileProvider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public void handleGalleryEvent() {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        imageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), PICK_IMAGE);
    }

    public void saveDocument() {

        Realm realm = Realm.getDefaultInstance();
        RealmList<String> images = new RealmList<>();
        if (!uris.isEmpty()) {

            for (Uri uri :
                    uris) {
                images.add(uri.toString());
            }
        }

        CardItem cardItem = new CardItem();
        cardItem.setId(UUID.randomUUID().toString());
        cardItem.setTitle(title);
        cardItem.setDescription(description);


        cardItem.setImageUris(images);

        realm.beginTransaction();
        realm.copyToRealm(cardItem);
        realm.commitTransaction();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        uris.add(imageUri);
                        currentItem = currentItem + 1;
                    }
                } else if (data.getData() != null) {
                    Uri imagePath = data.getData();//.getPath();
                    uris.add(imagePath);
                }
            }

            ImageAttachmentFragment fragment = (ImageAttachmentFragment) getSupportFragmentManager().findFragmentByTag(ImageAttachmentFragment.class.toString());
            if (fragment != null) {
                fragment.bindAttachedImageAdapter();
            }
        }

        if (requestCode == REQUEST_TAKE_PHOTO) {
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            uris.add(imageUri);
            ImageAttachmentFragment fragment = (ImageAttachmentFragment) getSupportFragmentManager().findFragmentByTag(ImageAttachmentFragment.class.toString());
            if (fragment != null) {
                fragment.bindAttachedImageAdapter();
            }
        }

    }
}
