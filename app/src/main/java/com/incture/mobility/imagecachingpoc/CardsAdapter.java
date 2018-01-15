package com.incture.mobility.imagecachingpoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

        View rowView = LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false);
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
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        View cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView;
            titleTextView = itemView.findViewById(R.id.title_textView);
            descriptionTextView = itemView.findViewById(R.id.description_textView);
            imageView1 = itemView.findViewById(R.id.card_imageView1);
            imageView2 = itemView.findViewById(R.id.card_imageView2);
            imageView3 = itemView.findViewById(R.id.card_imageView3);
            imageView4 = itemView.findViewById(R.id.card_imageView4);
            imageView5 = itemView.findViewById(R.id.card_imageView5);
        }

        public void bind(int position) {

            final CardItem item = mCardItems.get(position);
            titleTextView.setText(item.getTitle());
            descriptionTextView.setText(item.getDescription());

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent detailIntent = new Intent(mContext, CardDetailsActivity.class);
                    detailIntent.putExtra("uid", item.getId());
                    mContext.startActivity(detailIntent);

                    Toast.makeText(mContext, item.getId(), Toast.LENGTH_SHORT).show();
                }
            });

            Picasso.with(mContext).load(item.getImageUris().get(0)).resize(50, 50).placeholder(R.drawable.ic_picture).into(this.imageView1);
            Picasso.with(mContext).load(item.getImageUris().get(1)).resize(50, 50).placeholder(R.drawable.ic_picture).into(this.imageView2);
            Picasso.with(mContext).load(item.getImageUris().get(2)).resize(50, 50).placeholder(R.drawable.ic_picture).into(this.imageView3);
            Picasso.with(mContext).load(item.getImageUris().get(3)).resize(50, 50).placeholder(R.drawable.ic_picture).into(this.imageView4);
            Picasso.with(mContext).load(item.getImageUris().get(4)).resize(50, 50).placeholder(R.drawable.ic_picture).into(this.imageView5);

        }
    }
}
