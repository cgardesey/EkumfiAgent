package com.ekumfi.agent.materialDialog;

import static com.ekumfi.agent.activity.GetAuthActivity.APITOKEN;
import static com.ekumfi.agent.constants.keyConst.API_URL;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ekumfi.agent.R;
import com.ekumfi.agent.activity.MessageActivity;
import com.ekumfi.agent.other.InitApplication;
import com.ekumfi.agent.realm.RealmAgent;
import com.ekumfi.agent.realm.RealmWholesaler;
import com.ekumfi.agent.realm.RealmChat;
import com.ekumfi.agent.realm.RealmWholesalerCart;
import com.ekumfi.agent.util.RealmUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ChooseServiceContactMethodMaterialDialog extends DialogFragment {
    public static ProgressDialog dialog1;
    private static final String MY_LOGIN_ID = "MY_LOGIN_ID";
    LinearLayout chat, call;

    String seller_id = "";
    String consumer_id = "";
    String wholesaler_id = "";
    String agent_id = "";
    String order_id;

    public static ProgressDialog getDialog1() {
        return dialog1;
    }

    public static void setDialog1(ProgressDialog dialog1) {
        ChooseServiceContactMethodMaterialDialog.dialog1 = dialog1;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.list_item_choose_contact_method, null);
        chat = view.findViewById(R.id.chat);
        call = view.findViewById(R.id.call);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // view.setVisibility(View.GONE);

                ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please wait...");
                dialog.setCancelable(false);
                dialog.setIndeterminate(true);
                dialog.show();

                final String[] wholesaler_name = new String[1];
                final String[] shop_image_url = new String[1];

                Realm.init(getActivity());
                final RealmWholesaler[] realmWholesaler = new RealmWholesaler[1];
                final RealmWholesalerCart[] realmwholesalerCart = {Realm.getInstance(RealmUtility.getDefaultConfig(getActivity())).where(RealmWholesalerCart.class).findFirst()};
                wholesaler_name[0] = realmwholesalerCart[0].getShop_name();
                shop_image_url[0] = realmwholesalerCart[0].getShop_image_url();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        API_URL + "agent-chat-data-with-wholesaler",
                        response -> {
                            if (response != null) {
                                dialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Realm.init(getActivity());
                                    Realm.getInstance(RealmUtility.getDefaultConfig(getActivity())).executeTransaction(realm -> {
                                        try {
                                            realmWholesaler[0] = realm.createOrUpdateObjectFromJson(RealmWholesaler.class, jsonObject.getJSONObject("wholesaler"));
                                            realm.createOrUpdateAllFromJson(RealmChat.class, jsonObject.getJSONArray("chats"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        wholesaler_name[0] = realmwholesalerCart[0].getShop_name();
                                        shop_image_url[0] = realmwholesalerCart[0].getShop_image_url();
                                    });

                                    startActivity(new Intent(getActivity(), MessageActivity.class)
                                            .putExtra("WHOLESALER_ID", wholesaler_id)
                                            .putExtra("WHOLESALER_NAME", wholesaler_name[0])
                                            .putExtra("SHOP_IMAGE_URL", shop_image_url[0])
                                    );
                                    dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            startActivity(new Intent(getActivity(), MessageActivity.class)
                                    .putExtra("WHOLESALER_ID", wholesaler_id)
                                    .putExtra("WHOLESALER_NAME", wholesaler_name[0])
                                    .putExtra("SHOP_IMAGE_URL", shop_image_url[0])
                            );
                            dialog.dismiss();
                            Log.d("Cyrilll", error.toString());
                        }
                ) {
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("wholesaler_id", wholesaler_id);
                        params.put("agent_id", PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("com.ekumfi.agent" + "AGENT_ID", ""));
                        Realm.init(getActivity());
                        Realm.getInstance(RealmUtility.getDefaultConfig(getActivity())).executeTransaction(realm -> {
                            RealmResults<RealmChat> results = realm.where(RealmChat.class)
                                    .sort("id", Sort.DESCENDING)
                                    .equalTo("agent_id", PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("com.ekumfi.agent" + "AGENT_ID", ""))
                                    .equalTo("wholesaler_id", wholesaler_id)
                                    .findAll();
                            ArrayList<RealmChat> myArrayList = new ArrayList<>();
                            for (RealmChat realmChat : results) {
                                if (!(realmChat.getChat_id().startsWith("z"))) {
                                    myArrayList.add(realmChat);
                                }
                            }
                            if (results.size() < 3) {
                                params.put("id", "0");
                            }
                            else{
                                params.put("id", String.valueOf(myArrayList.get(0).getId()));
                            }
                        });
                        return params;
                    }
                    /** Passing some request headers* */
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("accept", "application/json");
                        headers.put("Authorization", "Bearer " + PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("com.ekumfi.agent" + APITOKEN, ""));
                        return headers;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                InitApplication.getInstance().addToRequestQueue(stringRequest);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCallMaterialDialog groupCall = new GroupCallMaterialDialog();
                if (groupCall != null && groupCall.isAdded()) {

                } else {
                    String primary_contact = Realm.getInstance(RealmUtility.getDefaultConfig(getActivity())).where(RealmAgent.class).equalTo("agent_id", androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("com.ekumfi.agent" + "AGENT_ID", "")).findFirst().getPrimary_contact();
                    groupCall.setPhone_number(primary_contact);
                    groupCall.setWholesaler_id(wholesaler_id);
                    groupCall.show(getFragmentManager(), "");

                    dismiss();
                }
            }
        });
        // doneBtn.setOnClickListener(doneAction);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                });
            }
        }, 5);
        return builder.create();

    }


}