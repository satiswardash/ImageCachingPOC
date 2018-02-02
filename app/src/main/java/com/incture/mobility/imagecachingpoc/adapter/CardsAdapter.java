package com.incture.mobility.imagecachingpoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.incture.mobility.imagecachingpoc.AddNewCardActivity;
import com.incture.mobility.imagecachingpoc.ViewCardDetailsActivity;
import com.incture.mobility.imagecachingpoc.model.CardItem;
import com.incture.mobility.imagecachingpoc.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by satiswardash on 12/01/18.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    Context mContext;
    RealmResults<CardItem> mCardItems;

    public CardsAdapter(Context mContext, RealmResults<CardItem> mCardItems) {
        this.mContext = mContext;
        this.mCardItems = mCardItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(mContext).inflate(R.layout.layout_card_item, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mCardItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        View cardView;
        RecyclerView mImagesRecyclerView;
        ImageView attachImageButton;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView;
            titleTextView = itemView.findViewById(R.id.title_textView);
            descriptionTextView = itemView.findViewById(R.id.description_textView);
            mImagesRecyclerView = itemView.findViewById(R.id.attached_image_recycler_view);
            //attachImageButton = itemView.findViewById(R.id.attach_new_image_button);

        }

        public void bind(int position) {

            final CardItem item = mCardItems.get(position);
            titleTextView.setText(item.getTitle());
            descriptionTextView.setText(item.getDescription());

            List<Uri> uris = new ArrayList<>();
            for (String value :
                    item.getImageUris()) {
                uris.add(Uri.parse(value));
            }
            AttachmentAdapter adapter = new AttachmentAdapter(mContext, uris);
            mImagesRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            mImagesRecyclerView.setAdapter(adapter);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent detailIntent = new Intent(mContext, ViewCardDetailsActivity.class);
                    detailIntent.putExtra("uid", item.getId());
                    mContext.startActivity(detailIntent);

                    Toast.makeText(mContext, item.getId(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
