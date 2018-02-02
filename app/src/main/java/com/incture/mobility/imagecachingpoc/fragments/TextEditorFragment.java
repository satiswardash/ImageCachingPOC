package com.incture.mobility.imagecachingpoc.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.incture.mobility.imagecachingpoc.AddNewCardActivity;
import com.incture.mobility.imagecachingpoc.MainActivity;
import com.incture.mobility.imagecachingpoc.R;
import com.incture.mobility.imagecachingpoc.model.CardItem;

import java.util.UUID;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextEditorFragment extends Fragment {

    private AddNewCardActivity mActivity;
    private FloatingActionButton mSaveButton;
    private EditText mDescriptionEditText;
    private ImageView mCloseEditorButton;

    public TextEditorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddNewCardActivity) {
            mActivity = (AddNewCardActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mDescriptionEditText = view.findViewById(R.id.description_editText);
        mSaveButton = view.findViewById(R.id.description_save_fab);
        mCloseEditorButton = view.findViewById(R.id.description_edit_text_close);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mDescriptionEditText.getText().toString().isEmpty()) {

                    mActivity.description = mDescriptionEditText.getText().toString();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
                    builder1.setMessage("Would you like to attach some images to your card?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    mActivity.getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.add_new_card_root_frame, new ImageAttachmentFragment(), ImageAttachmentFragment.class.toString())
                                            .addToBackStack(ImageAttachmentFragment.class.toString())
                                            .commit();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    saveDocument();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    Toast.makeText(mActivity, "Card description can not be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCloseEditorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager()
                        .popBackStackImmediate();
            }
        });
    }

    private void saveDocument() {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CardItem cardItem = realm.createObject(CardItem.class, UUID.randomUUID().toString());
        cardItem.setTitle(mActivity.title);
        cardItem.setDescription(mActivity.description);
        realm.copyToRealm(cardItem);
        realm.commitTransaction();

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
