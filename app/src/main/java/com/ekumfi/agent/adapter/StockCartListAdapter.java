package com.ekumfi.agent.adapter;

/**
 * Created by Nana on 11/10/2017.
 */

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

public class StockCartListAdapter extends RecyclerView.Adapter<StockCartListAdapter.ViewHolder> {

    private static final String YOUR_DIALOG_TAG = "";
    StockCartAdapterInterface stockCartAdapterInterface;
    Activity mActivity;
    private ArrayList<RealmWholesalerCart> realmStockCarts;

    public StockCartListAdapter(StockCartAdapterInterface stockCartAdapterInterface, Activity mActivity, ArrayList<RealmWholesalerCart> realmStockCarts) {
        this.stockCartAdapterInterface = stockCartAdapterInterface;
        this.mActivity = mActivity;
        this.realmStockCarts = realmStockCarts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_stock_cart_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        RealmWholesalerCart realmStockCart = realmStockCarts.get(position);


        if (realmStockCart.getProfile_image_url() != null && !realmStockCart.getProfile_image_url().equals("")) {
            Glide.with(mActivity).
                    load(realmStockCart.getProfile_image_url())
                    .into(holder.image);
        }
        holder.seller.setText(realmStockCart.getFirst_name());
        holder.order_id.setText(realmStockCart.getOrder_id());
        if (realmStockCart.getItem_count() > 1) {
            holder.items_in_cart.setText(realmStockCart.getItem_count() + " items in cart");
        } else {
            holder.items_in_cart.setText(realmStockCart.getItem_count() + " item in cart");
        }

        /*if (realmStockCart.getVerified() == 1) {
            holder.verifiedImage.setVisibility(View.VISIBLE);
        }
        else {
            holder.verifiedImage.setVisibility(View.INVISIBLE);
        }*/

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onViewClick(realmStockCarts, position, holder);
            }
        });

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onContactClick(realmStockCarts, position, holder);
            }
        });

        holder.delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onDeliveryClick(realmStockCarts, position, holder);
            }
        });

        holder.markasdelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onMarkAsDeliveredClick(realmStockCarts, position, holder);
            }
        });

        holder.mark_as_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onMarkAsPaidClick(realmStockCarts, position, holder);
            }
        });

        holder.credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockCartAdapterInterface.onCreditClick(realmStockCarts, position, holder);
            }
        });

        if (realmStockCart.getDelivered() == 1) {
            holder.delivery.setVisibility(View.GONE);
            holder.markasdelivered.setVisibility(View.GONE);
            holder.mark_as_paid.setVisibility(View.GONE);
            holder.credit.setVisibility(View.GONE);
        } else {
            if ((realmStockCart.getStatus() != null && realmStockCart.getStatus().contains("SUCCESS")) || realmStockCart.getPaid() == 1 || realmStockCart.getCredited() == 1) {
                holder.delivery.setVisibility(View.VISIBLE);
                holder.markasdelivered.setVisibility(View.VISIBLE);
                holder.mark_as_paid.setVisibility(View.GONE);
                holder.credit.setVisibility(View.GONE);
            }
            else {
                holder.delivery.setVisibility(View.GONE);
                holder.markasdelivered.setVisibility(View.GONE);
                holder.mark_as_paid.setVisibility(View.VISIBLE);
                holder.credit.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return realmStockCarts.size();
    }

    public interface StockCartAdapterInterface {
        void onViewClick(ArrayList<RealmWholesalerCart> realmStockCarts, int position, ViewHolder holder);
        void onContactClick(ArrayList<RealmWholesalerCart> realmStockCarts, int position, ViewHolder holder);
        void onDeliveryClick(ArrayList<RealmWholesalerCart> realmStockCarts, int position, ViewHolder holder);
        void onMarkAsDeliveredClick(ArrayList<RealmWholesalerCart> realmStockCarts, int position, ViewHolder holder);
        void onMarkAsPaidClick(ArrayList<RealmWholesalerCart> realmStockCarts, int position, ViewHolder holder);
        void onCreditClick(ArrayList<RealmWholesalerCart> realmStockCarts, int position, ViewHolder holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView items_in_cart, seller, view, contact, delivery, order_id, markasdelivered, credit, mark_as_paid;
        public ImageView image, verifiedImage;

        public ViewHolder(View itemView) {
            super(itemView);
            items_in_cart = itemView.findViewById(R.id.items_in_cart);
            seller = itemView.findViewById(R.id.seller);
            image = itemView.findViewById(R.id.image);
            verifiedImage = itemView.findViewById(R.id.verifiedImage);
            view = itemView.findViewById(R.id.view);
            contact = itemView.findViewById(R.id.contact);
            order_id = itemView.findViewById(R.id.order_id);
            delivery = itemView.findViewById(R.id.delivery);
            markasdelivered = itemView.findViewById(R.id.markasdelivered);
            credit = itemView.findViewById(R.id.credit);
            mark_as_paid = itemView.findViewById(R.id.mark_as_paid);
        }
    }
}
