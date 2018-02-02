package com.incture.mobility.imagecachingpoc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.incture.mobility.imagecachingpoc.AddNewCardActivity;
import com.incture.mobility.imagecachingpoc.R;
import com.incture.mobility.imagecachingpoc.adapter.AttachmentAdapter;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageAttachmentFragment extends Fragment {

    private AddNewCardActivity mActivity;
    private Button attachButton;
    private Button saveButton;
    private RecyclerView mRecyclerView;
    private ImageView backNav;

    public ImageAttachmentFragment() {
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
        return inflater.inflate(R.layout.fragment_image_attachment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        attachButton = view.findViewById(R.id.im_attach_button);
        saveButton = view.findViewById(R.id.im_save_button);
        mRecyclerView = view.findViewById(R.id.im_attached_image_recycler_view);
        backNav = view.findViewById(R.id.im_attached_nav_back);

        backNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager()
                        .popBackStackImmediate();
            }
        });

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAttachImageDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.saveDocument();
            }
        });

    }

    private void showAttachImageDialog() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.layout_attach_image, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        TextView camera = sheetView.findViewById(R.id.choose_camera_button);
        TextView gallery = sheetView.findViewById(R.id.choose_gallery_button);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mActivity.handleCameraEvent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mBottomSheetDialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.handleGalleryEvent();
                mBottomSheetDialog.dismiss();
            }
        });
    }

    public void bindAttachedImageAdapter() {
        AttachmentAdapter adapter = new AttachmentAdapter(getContext(), mActivity.uris);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecyclerView.setAdapter(adapter);
    }
}
