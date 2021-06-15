package com.example.bottomnavigation;

import android.net.Uri;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements NewsItemClicked{

    NewsAdapter newsAdapter;
    ArrayList<News> newsArray;
    News news;
    LinearLayoutManager layoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);


//            setContentView(R.layout.activity_main);
            View v = inflater.inflate(R.layout.news_test,container,false);
            RecyclerView newsList = v.findViewById(R.id.newsList);
            layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            newsList.setLayoutManager(layoutManager);
            fetchData();
            newsAdapter = new NewsAdapter(this);
            newsList.setAdapter(newsAdapter);
            return v;
        }




//    public ArrayList<String> fetchData(){
//        ArrayList<String> newslist = new ArrayList<>();
//        for(int i=0;i<100;i++){
//            newslist.add("Item "+i);
//        }
//        return newslist;
//    }



    private void fetchData() {
        String url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {//get data

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray newsJsonArray = response.getJSONArray("articles");//getting json array
                            newsArray = new ArrayList<News>();
                            for(int i=0;i<newsJsonArray.length();i++){
                                JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);
                                news = new News(
                                        newsJsonObject.getString("title"),
                                        newsJsonObject.getString("author"),
                                        newsJsonObject.getString("url"),
                                        newsJsonObject.getString("urlToImage")
                                );
                                newsArray.add(news);
                            }
                            newsAdapter.updateNews(newsArray);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Unable to Fetch data", Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);

    }
    public void onItemClicked(News item){
//        Toast.makeText(this,"Clicked item is "+ item,Toast.LENGTH_SHORT).show();
        String url = item.getmUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }
    }


//        return inflater.inflate(R.layout.fragment_home, container, false);


