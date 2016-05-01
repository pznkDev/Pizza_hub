package com.kpi.slava.pizza_hub_v1.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kpi.slava.pizza_hub_v1.JSON.JSONParser;
import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.URLConstants;
import com.kpi.slava.pizza_hub_v1.adapters.ItemListAdapter;
import com.kpi.slava.pizza_hub_v1.entity.Item;
import com.kpi.slava.pizza_hub_v1.listener.RecyclerItemClickListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment {

    public static String TAG = "ItemListFragment";
    public static int LAYOUT = R.layout.fragment_item_list;

    private View view;

    private String idCategory;

    private RecyclerView rvItems;

    private ProgressDialog progressDialog;

    private JSONParser jsonParser = new JSONParser();

    ArrayList<Item> itemList = new ArrayList<Item>();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ITEMS = "items";
    private static final String TAG_ID = "id_item";
    private static final String TAG_NAME = "name";
    private static final String TAG_ID_CATEGORY = "id_category";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_WEIGHT = "weight";
    private static final String TAG_PRICE = "price";

    JSONArray itemArray = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            idCategory = bundle.getString("id_category");
        }

        view = inflater.inflate(LAYOUT, container, false);

        if(itemList.size()==0)
            new GetItems().execute();
        else fillRV();

        return view;
    }


    private class GetItems extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> idCategoryParam = new ArrayList<NameValuePair>();
            idCategoryParam.add(new BasicNameValuePair("id_category", idCategory));

            JSONObject json = jsonParser.makeHttpRequest(URLConstants.URL_SELECT_ITEMS_WHERE_CATEGORY, "POST", idCategoryParam);

            try {
                int success = json.getInt(TAG_SUCCESS);
                // was successful connected to db
                if (success == 1) {
                    itemArray = json.getJSONArray(TAG_ITEMS);

                    //get all items with current id_category and store them in arrayList
                    for (int i = 0; i < itemArray.length(); i++) {
                        JSONObject c = itemArray.getJSONObject(i);

                        itemList.add(new Item(c.getString(TAG_ID), c.getString(TAG_NAME),
                                c.getString(TAG_ID_CATEGORY), c.getString(TAG_DESCRIPTION),
                                c.getString(TAG_WEIGHT), c.getString(TAG_PRICE)));
                    }
                } else {
                    System.out.println("No items");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            progressDialog.dismiss();
            fillRV();
        }
    }

    void fillRV(){
        //create recyclerView for arrayList of items
        rvItems = (RecyclerView) view.findViewById(R.id.rv_item_list);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItems.setAdapter(new ItemListAdapter(itemList));

        rvItems.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        ItemFragment itemFragment = new ItemFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("item", itemList.get(position));

                        itemFragment.setArguments(bundle);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.layout_container,  itemFragment).addToBackStack("Menu").commit();

                    }
                })
        );
    }

}
