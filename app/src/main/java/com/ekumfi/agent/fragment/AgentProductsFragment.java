package com.ekumfi.agent.fragment;

import static com.ekumfi.agent.activity.GetAuthActivity.APITOKEN;
import static com.ekumfi.agent.constants.keyConst.API_URL;
import static com.ekumfi.agent.constants.Const.myVolleyError;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ekumfi.agent.R;
import com.ekumfi.agent.adapter.AgentProductAdapter;
import com.ekumfi.agent.materialDialog.AgentProductMaterialDialog;
import com.ekumfi.agent.other.InitApplication;
import com.ekumfi.agent.realm.RealmWholesaler;
import com.ekumfi.agent.realm.RealmAgentProduct;
import com.ekumfi.agent.util.RealmUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 2CLearning on 12/13/2017.
 */

public class AgentProductsFragment extends Fragment {
    private static final String TAG = "ProductsFragment";
    public static RecyclerView recyclerView;
    Context mContext;
    LinearLayout clickToAdd;
    CardView cardView;
    public static ArrayList<RealmAgentProduct> productArrayList;
    public static AgentProductAdapter productAdapter;
    public static RecyclerView.LayoutManager layoutManager;
    public static AgentProductMaterialDialog agentProductMaterialDialog = new AgentProductMaterialDialog();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_agent_products, container, false);


        productArrayList = new ArrayList<>();
        mContext= getContext();
        cardView = rootView.findViewById(R.id.cardView);
        clickToAdd = rootView.findViewById(R.id.clickToAdd);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(getActivity());

        init();

        clickToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agentProductMaterialDialog != null && agentProductMaterialDialog.isAdded()) {

                } else {
                    agentProductMaterialDialog.setName("");
                    agentProductMaterialDialog.setUnit_price("");
                    agentProductMaterialDialog.setQuantity_available("");
                    agentProductMaterialDialog.setCancelable(false);
                    agentProductMaterialDialog.show(getFragmentManager(), "addProductMaterialDialog");
                    agentProductMaterialDialog.setCancelable(true);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void init() {
        Realm.init(getContext());
        Realm.getInstance(RealmUtility.getDefaultConfig(getActivity())).executeTransaction(realm -> {

            String agent_id = Realm.getInstance(RealmUtility.getDefaultConfig(getActivity())).where(RealmWholesaler.class).findFirst().getAgent_id();

            RealmResults<RealmAgentProduct> realmAgentProducts = realm.where(RealmAgentProduct.class).equalTo("agent_id", agent_id).findAll();
            productArrayList.clear();
            for (RealmAgentProduct realmAgentProduct : realmAgentProducts) {
                productArrayList.add(realmAgentProduct);
            }
        });

        productAdapter = new AgentProductAdapter(new AgentProductAdapter.AgentProductAdapterInterface() {
            @Override
            public void onListItemClick(ArrayList<RealmAgentProduct> realmAgentProducts, int position, AgentProductAdapter.ViewHolder holder) {
                RealmAgentProduct realmAgentProduct = realmAgentProducts.get(position);

                PopupMenu popup = new PopupMenu(mContext, holder.more_details);

                popup.inflate(R.menu.product_menu);

                popup.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.edit) {
                        AgentProductMaterialDialog productMaterialDialog = new AgentProductMaterialDialog();
                        if (productMaterialDialog != null && productMaterialDialog.isAdded()) {

                        } else {
                            productMaterialDialog.setProduct_id(realmAgentProduct.getProduct_id());
                            productMaterialDialog.setAgent_product_id(realmAgentProduct.getAgent_product_id());
                            productMaterialDialog.setName(realmAgentProduct.getProduct_name());
                            productMaterialDialog.setUnit_price(String.format("%.2f", realmAgentProduct.getUnit_price()));
                            productMaterialDialog.setQuantity_available(String.valueOf(realmAgentProduct.getQuantity_available()));

                            productMaterialDialog.setCancelable(false);
                            productMaterialDialog.show(getFragmentManager(), "editProductMaterialDialog");
                            productMaterialDialog.setCancelable(true);
                        }
                        return true;
                    } else if (itemId == R.id.remove) {
                        String product_id = realmAgentProduct.getProduct_id();
                        StringRequest stringRequestDelete = new StringRequest(
                                Request.Method.DELETE,
                                API_URL + "agent-products/" + realmAgentProduct.getAgent_product_id(),
                                response -> {
                                    if (response != null) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject.getBoolean("status")) {
                                                Realm.init(getActivity());
                                                Realm.getInstance(RealmUtility.getDefaultConfig(getActivity())).executeTransaction(realm -> {
                                                    realmAgentProducts.get(position).deleteFromRealm();
                                                });
                                                realmAgentProducts.remove(position);
                                                productAdapter.notifyDataSetChanged();
                                                Toast.makeText(mContext, "Successfully deleted.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mContext, "Error deleting.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                error -> {
                                    error.printStackTrace();
                                    myVolleyError(mContext, error);
                                    Log.d("Cyrilll", error.toString());
                                }
                        ) {
                            @Override
                            public Map getHeaders() throws AuthFailureError {
                                HashMap headers = new HashMap();
                                headers.put("accept", "application/json");
                                headers.put("Authorization", "Bearer " + PreferenceManager.getDefaultSharedPreferences(mContext).getString("com.ekumfi.juice" + APITOKEN, ""));
                                return headers;
                            }

                            @Override
                            public Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("product_id", product_id);
                                return params;
                            }
                        };
                        stringRequestDelete.setRetryPolicy(new DefaultRetryPolicy(
                                0,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        InitApplication.getInstance().addToRequestQueue(stringRequestDelete);
                        return true;
                    }
                    return false;
                });
                popup.show();
            }
        }, productArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //  myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(productAdapter);
    }

    public boolean validate (){
        boolean validated = true;
        return validated;
    }
}