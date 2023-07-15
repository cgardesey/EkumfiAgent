package com.ekumfi.agent.adapter;

/**
 * Created by Nana on 11/10/2017.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ekumfi.agent.R;
import com.ekumfi.agent.realm.RealmWholesalerCart;

import java.util.ArrayList;

/**
 * Created by Belal on 6/6/2017.
 */

public class WholesalerCartListAdapter extends RecyclerView.Adapter<WholesalerCartListAdapter.ViewHolder> {

    private static final String YOUR_DIALOG_TAG = "";
    WholesalerCartAdapterInterface stockCartAdapterInterface;
    Activity mActivity;
    private ArrayList<RealmWholesalerCart> realmWholesalerCarts;

    public WholesalerCartListAdapter(WholesalerCartAdapterInterface stockCartAdapterInterface, Activity mActivity, ArrayList<RealmWholesalerCart> realmWholesalerCarts) {
        this.stockCartAdapterInterface = stockCartAdapterInterface;
        this.mActivity = mActivity;
        this.realmWholesalerCarts = realmWholesalerCarts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_stock_cart_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        RealmWholesalerCart realmWholesalerCart = realmWholesalerCarts.get(position);


        if (realmWholesalerCart.getProfile_image_url() != null && !realmWholesalerCart.getProfile_image_url().equals("")) {
            Glide.with(mActivity).
                    load(realmWholesalerCart.getProfile_image_url())
                    .into(holder.image);
        }
        holder.seller.setText(realmWholesalerCart.getShop_name());
        holder.order_id.setText(realmWholesalerCart.getOrder_id());
        if (realmWholesalerCart.getItem_count() > 1) {
            holder.items_in_cart.setText(realmWholesalerCart.getItem_count() + " items in cart");
        } else {
            holder.items_in_cart.setText(realmWholesalerCart.getItem_count() + " item in cart");
        }

        /*if (realmWholesalerCart.getVerified() == 1) {
            holder.verifiedImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.verifiedImage.setVisibility(View.INVISIBLE);
        }*/

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onViewClick(realmWholesalerCarts, position, holder);
            }
        });

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onContactClick(realmWholesalerCarts, position, holder);
            }
        });

        holder.delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onDeliveryClick(realmWholesalerCarts, position, holder);
            }
        });

        if (realmWholesalerCart.getDelivered() == 1) {
            holder.order.setVisibility(View.GONE);
//            holder.delivery.setVisibility(View.GONE);
        } else {
            if (realmWholesalerCart.getStatus() != null && realmWholesalerCart.getStatus().contains("SUCCESS")) {
                holder.order.setVisibility(View.GONE);
//                holder.delivery.setVisibility(View.VISIBLE);
            }
            else {
                holder.order.setVisibility(View.VISIBLE);
//                holder.delivery.setVisibility(View.GONE);
            }
        }

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onOrderClick(realmWholesalerCarts, position, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return realmWholesalerCarts.size();
    }

    public interface WholesalerCartAdapterInterface {
        void onViewClick(ArrayList<RealmWholesalerCart> realmWholesalerCarts, int position, ViewHolder holder);
        void onContactClick(ArrayList<RealmWholesalerCart> realmWholesalerCarts, int position, ViewHolder holder);
        void onOrderClick(ArrayList<RealmWholesalerCart> realmWholesalerCarts, int position, ViewHolder holder);
        void onDeliveryClick(ArrayList<RealmWholesalerCart> realmWholesalerCarts, int position, ViewHolder holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView items_in_cart, seller, view, contact, delivery, order, order_id;
        public ImageView image, verifiedImage;

        public ViewHolder(View itemView) {
            super(itemView);
            items_in_cart = itemView.findViewById(R.id.items_in_cart);
            seller = itemView.findViewById(R.id.provider);
            image = itemView.findViewById(R.id.image);
            verifiedImage = itemView.findViewById(R.id.verifiedImage);
            view = itemView.findViewById(R.id.view);
            contact = itemView.findViewById(R.id.contact);
            order = itemView.findViewById(R.id.order);
            order_id = itemView.findViewById(R.id.order_id);
            delivery = itemView.findViewById(R.id.delivery);
        }
    }
}
