package com.jathiswarbhaskar.myfitpal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import org.w3c.dom.Text;

import java.util.Queue;

public class FoodFragment extends Fragment {
    private View foodFragmentView;
    TextView displayText;
    private ProgressDialog dialog;
    private Button button;
    private RequestQueue Queue;
    String jsonUrl = "https://myjson.dit.upm.es/api/bins/u3k";




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        foodFragmentView = inflater.inflate(R.layout.fragment_food,container,false);
        Queue = Volley.newRequestQueue(container.getContext());
        dialog = new ProgressDialog(container.getContext());
        displayText = (TextView) foodFragmentView.findViewById(R.id.food_textview);
        displayText.setMovementMethod(new ScrollingMovementMethod());
        Queue = Volley.newRequestQueue(container.getContext());
        button = (Button) foodFragmentView.findViewById(R.id.searchbtn);
        new NukeSSLCerts().nuke();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoAsyncTask task = new DemoAsyncTask();
                task.execute(jsonUrl);
            }
        });




        return foodFragmentView;
    }




    private class DemoAsyncTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Wait,doing something");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
        }

        @Override
        protected String doInBackground(String... strings) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("employees");
                        for(int count=0;count<jsonArray.length();count++){
                            JSONObject companies = jsonArray.getJSONObject(count);
                            String name =  companies.getString("name");
                            String jobs = companies.getString("email");
                            displayText.append("name: "+name+"\n"+"email:"+jobs);
                        }
                    } catch (JSONException e) {

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                }
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Queue.add(request);

            return "process finished";
        }
    }







}
