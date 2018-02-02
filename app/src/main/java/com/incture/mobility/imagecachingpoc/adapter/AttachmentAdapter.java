package com.incture.mobility.imagecachingpoc.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.incture.mobility.imagecachingpoc.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by satiswardash on 12/01/18.
 */

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {

    Context mContext;
    List<Uri> mImageUriList;

    public AttachmentAdapter(Context mContext, List<Uri> mImageUriList) {
        this.mContext = mContext;
        this.mImageUriList = mImageUriList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.layout_attached_image, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mImageUriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.attached_imageView);
        }

        public void bind(int position) {
            Uri uri = mImageUriList.get(position);
            Picasso.with(mContext).load(uri).placeholder(R.drawable.ic_picture).into(imageView);

        }
    }
}
