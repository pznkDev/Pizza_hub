package com.kpi.slava.pizza_hub_v1.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpi.slava.pizza_hub_v1.JSON.JSONParser;
import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.adapters.CategoryListAdapter;
import com.kpi.slava.pizza_hub_v1.entity.Category;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {

    public static String TAG = "MenuFragment";
    public static int LAYOUT = R.layout.fragment_menu;

    private View view;

    private RecyclerView rvCategory;

    private ProgressDialog progressDialog;

    private JSONParser jsonParser = new JSONParser();

    ArrayList<Category> categoryList = new ArrayList<Category>();

    private static String url_select_all_categories = "http://192.168.1.103/category_select.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CATEGORIES = "categories";
    private static final String TAG_ID = "id_category";
    private static final String TAG_NAME = "name";

    JSONArray categories = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        new GetCategories().execute();

        return view;
    }

    private class GetCategories extends AsyncTask<String, String, String> {
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
            List<NameValuePair> getParams = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest(url_select_all_categories, "GET", getParams);

            try {
                int success = json.getInt(TAG_SUCCESS);
                // was successful connected to db
                if (success == 1) {
                    categories = json.getJSONArray(TAG_CATEGORIES);

                    //get all categories and store theme in arrayList
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject c = categories.getJSONObject(i);

                        categoryList.add(new Category(c.getString(TAG_ID), c.getString(TAG_NAME)));
                    }
                } else {
                    System.out.println("No Categories");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            progressDialog.dismiss();
            //create recyclerView for arrayList of category-objects
            rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);
            rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCategory.setAdapter(new CategoryListAdapter(categoryList));
        }
    }
}
