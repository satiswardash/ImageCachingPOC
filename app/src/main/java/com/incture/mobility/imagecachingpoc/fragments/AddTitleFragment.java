package com.incture.mobility.imagecachingpoc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.incture.mobility.imagecachingpoc.AddNewCardActivity;
import com.incture.mobility.imagecachingpoc.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTitleFragment extends Fragment {

    private AddNewCardActivity mActivity;
    private MaterialEditText mEditText;
    private Button mButton;
    private ImageView mNavBack;

    public AddTitleFragment() {
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
        return inflater.inflate(R.layout.fragment_add_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mEditText = view.findViewById(R.id.title_materialEditText);
        mButton = view.findViewById(R.id.title_continue_button);
        mNavBack = view.findViewById(R.id.title_nav_back);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditText.getText().toString().isEmpty()) {

                    mActivity.title = mEditText.getText().toString();
                    mActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.add_new_card_root_frame, new TextEditorFragment())
                            .addToBackStack(TextEditorFragment.class.toString())
                            .commit();
                } else {
                    Toast.makeText(mActivity, "Title can not be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager()
                        .popBackStackImmediate();
            }
        });
    }
}
