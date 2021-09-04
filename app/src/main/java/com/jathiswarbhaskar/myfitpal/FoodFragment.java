package com.jathiswarbhaskar.myfitpal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.select.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import org.w3c.dom.Text;

import java.util.Queue;

public class FoodFragment extends Fragment {
    private View foodFragmentView;
    private TextView displayText;
    private Button button;
    private RequestQueue Queue;
    private EditText getsearch;
    private int count = 0;







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        foodFragmentView = inflater.inflate(R.layout.fragment_food,container,false);
        Queue = Volley.newRequestQueue(container.getContext());
        displayText = (TextView) foodFragmentView.findViewById(R.id.food_textview);
        displayText.setMovementMethod(new ScrollingMovementMethod());
        Queue = Volley.newRequestQueue(container.getContext());
        button = (Button) foodFragmentView.findViewById(R.id.searchbtn);
        getsearch = (EditText) foodFragmentView.findViewById(R.id.search_input);



        new NukeSSLCerts().nuke();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayText.setText(" ");
             jsonParse();

            }

        });




        return foodFragmentView;
    }
    private void jsonParse() {

        String url = "https://myjson.dit.upm.es/api/bins/wkg";
        String search_item = getsearch.getText().toString().toLowerCase();
        String ua = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("FoodItems");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);


                                String food = employee.getString("Food");
                                String searchfood = food.toLowerCase();
                                String measure = employee.getString("Measure");
                                String grams = employee.getString("Grams");
                                String calories = employee.getString("Calories");
                                String protein = employee.getString("Protein");
                                String fat = employee.getString("Fat");
                                String sat_fat = employee.getString("Sat.Fat");
                                String fiber = employee.getString("Fiber");
                                String carbs = employee.getString("Carbs");



                                if(searchfood.equals(search_item)||searchfood.contains(search_item)) {
                                    displayText.append("Item name: " + food + "\n Item measure: " + measure
                                            + "\n Item grams: " + grams+ "\n Item calories: " + calories
                                            + "\n Item protein: " + protein + "\n Item fat: " + fat
                                            + "\n Item saturated fat: " + sat_fat + "\n Item fiber: " + fiber
                                            + "\n Item carbohydrates: " + carbs+"\n\n");
                                    count=1;
                                    //String res = getImage(search_item, ua);
                                   // displayText.append(res);
                                }


                            }






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Queue.add(request);
    }


    public static String getImage(String question, String ua) {
        String finRes = "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            String googleUrl = "https://www.google.com/search?tbm=isch&q=" + question.replace(",", "");
            Document doc1 =  Jsoup.connect(googleUrl).userAgent(ua).timeout(10 * 1000).get();
            Element media = doc1.select("[data-src]").first();
            String finUrl = media.attr("abs:data-src");

            finRes= "<a href=\"http://images.google.com/search?tbm=isch&q=" + question + "\"><img src=\"" + finUrl.replace("&quot", "") + "\" border=1/></a>";

        } catch (Exception e) {
            System.out.println(e);
        }

        return finRes;
    }






}
