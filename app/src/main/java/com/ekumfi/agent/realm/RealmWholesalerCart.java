package com.ekumfi.agent.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 2CLearning on 12/16/2017.
 */

public class RealmWholesalerCart extends RealmObject {

    private int id;
    @PrimaryKey
    private String wholesaler_cart_id;
    private String order_id;
    private String wholesaler_id;
    private String agent_id;
    private double shipping_fee;
    private int delivered;
    private int paid;
    private int credited;
    private int credit_paid;
    private String created_at;
    private String updated_at;
    private String status;
    private double wholesaler_longitude;
    private double wholesaler_latitude;
    private String shop_name;
    private int wholesaler_verified;
    private String shop_image_url;
    private String wholesaler_availability;
    private double agent_longitude;
    private double agent_latitude;
    private String title;
    private String first_name;
    private String last_name;
    private String other_names;
    private int agent_verified;
    private String profile_image_url;
    private String agent_availability;

    private int item_count;

    public RealmWholesalerCart() {

    }

    public RealmWholesalerCart(int id, String wholesaler_cart_id, String order_id, String wholesaler_id, String agent_id, double shipping_fee, int delivered, int paid, int credited, int credit_paid, String created_at, String updated_at, String status, double wholesaler_longitude, double wholesaler_latitude, String shop_name, int wholesaler_verified, String shop_image_url, String wholesaler_availability, double agent_longitude, double agent_latitude, String title, String first_name, String last_name, String other_names, int agent_verified, String profile_image_url, String agent_availability, int item_count) {
        this.id = id;
        this.wholesaler_cart_id = wholesaler_cart_id;
        this.order_id = order_id;
        this.wholesaler_id = wholesaler_id;
        this.agent_id = agent_id;
        this.shipping_fee = shipping_fee;
        this.delivered = delivered;
        this.paid = paid;
        this.credited = credited;
        this.credit_paid = credit_paid;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.wholesaler_longitude = wholesaler_longitude;
        this.wholesaler_latitude = wholesaler_latitude;
        this.shop_name = shop_name;
        this.wholesaler_verified = wholesaler_verified;
        this.shop_image_url = shop_image_url;
        this.wholesaler_availability = wholesaler_availability;
        this.agent_longitude = agent_longitude;
        this.agent_latitude = agent_latitude;
        this.title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.other_names = other_names;
        this.agent_verified = agent_verified;
        this.profile_image_url = profile_image_url;
        this.agent_availability = agent_availability;
        this.item_count = item_count;
    }

    public RealmWholesalerCart(int id, String wholesaler_cart_id, String order_id, String wholesaler_id, String agent_id, double shipping_fee, int delivered, int paid, int credited, int credit_paid, String created_at, String updated_at, String status, double agent_longitude, double agent_latitude, String title, String first_name, String last_name, String other_names, int agent_verified, String profile_image_url, String agent_availability) {
        this.id = id;
        this.wholesaler_cart_id = wholesaler_cart_id;
        this.order_id = order_id;
        this.wholesaler_id = wholesaler_id;
        this.agent_id = agent_id;
        this.shipping_fee = shipping_fee;
        this.delivered = delivered;
        this.paid = paid;
        this.credited = credited;
        this.credit_paid = credit_paid;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.agent_longitude = agent_longitude;
        this.agent_latitude = agent_latitude;
        this.title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.other_names = other_names;
        this.agent_verified = agent_verified;
        this.profile_image_url = profile_image_url;
        this.agent_availability = agent_availability;
    }

    public RealmWholesalerCart(int id, String wholesaler_cart_id, String order_id, String wholesaler_id, String agent_id, double shipping_fee, int delivered, int paid, int credited, int credit_paid, String created_at, String updated_at, String status, double wholesaler_longitude, double wholesaler_latitude, String shop_name, int wholesaler_verified, String shop_image_url, String wholesaler_availability, int item_count) {
        this.id = id;
        this.wholesaler_cart_id = wholesaler_cart_id;
        this.order_id = order_id;
        this.wholesaler_id = wholesaler_id;
        this.agent_id = agent_id;
        this.shipping_fee = shipping_fee;
        this.delivered = delivered;
        this.paid = paid;
        this.credited = credited;
        this.credit_paid = credit_paid;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.wholesaler_longitude = wholesaler_longitude;
        this.wholesaler_latitude = wholesaler_latitude;
        this.shop_name = shop_name;
        this.wholesaler_verified = wholesaler_verified;
        this.shop_image_url = shop_image_url;
        this.wholesaler_availability = wholesaler_availability;
        this.item_count = item_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWholesaler_cart_id() {
        return wholesaler_cart_id;
    }

    public void setWholesaler_cart_id(String wholesaler_cart_id) {
        this.wholesaler_cart_id = wholesaler_cart_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getWholesaler_id() {
        return wholesaler_id;
    }

    public void setWholesaler_id(String wholesaler_id) {
        this.wholesaler_id = wholesaler_id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getCredited() {
        return credited;
    }

    public void setCredited(int credited) {
        this.credited = credited;
    }

    public int getCredit_paid() {
        return credit_paid;
    }

    public void setCredit_paid(int credit_paid) {
        this.credit_paid = credit_paid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getWholesaler_longitude() {
        return wholesaler_longitude;
    }

    public void setWholesaler_longitude(double wholesaler_longitude) {
        this.wholesaler_longitude = wholesaler_longitude;
    }

    public double getWholesaler_latitude() {
        return wholesaler_latitude;
    }

    public void setWholesaler_latitude(double wholesaler_latitude) {
        this.wholesaler_latitude = wholesaler_latitude;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getWholesaler_verified() {
        return wholesaler_verified;
    }

    public void setWholesaler_verified(int wholesaler_verified) {
        this.wholesaler_verified = wholesaler_verified;
    }

    public String getShop_image_url() {
        return shop_image_url;
    }

    public void setShop_image_url(String shop_image_url) {
        this.shop_image_url = shop_image_url;
    }

    public String getWholesaler_availability() {
        return wholesaler_availability;
    }

    public void setWholesaler_availability(String wholesaler_availability) {
        this.wholesaler_availability = wholesaler_availability;
    }

    public double getAgent_longitude() {
        return agent_longitude;
    }

    public void setAgent_longitude(double agent_longitude) {
        this.agent_longitude = agent_longitude;
    }

    public double getAgent_latitude() {
        return agent_latitude;
    }

    public void setAgent_latitude(double agent_latitude) {
        this.agent_latitude = agent_latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getOther_names() {
        return other_names;
    }

    public void setOther_names(String other_names) {
        this.other_names = other_names;
    }

    public int getAgent_verified() {
        return agent_verified;
    }

    public void setAgent_verified(int agent_verified) {
        this.agent_verified = agent_verified;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getAgent_availability() {
        return agent_availability;
    }

    public void setAgent_availability(String agent_availability) {
        this.agent_availability = agent_availability;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }
}
