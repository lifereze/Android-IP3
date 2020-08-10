package com.lifereze.tweeps.services;

import com.lifereze.tweeps.Constants;
import com.lifereze.tweeps.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;



public class TwitterService {
    public static void findTweets(String topic, Callback callback){
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.TOKEN,Constants.TOKEN_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMETERS,topic);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Tweet> processResults(Response response){
        ArrayList<Tweet> tweets = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()){
                JSONObject twitterJSON = new JSONObject(jsonData);
                JSONArray statusesJSON = twitterJSON.getJSONArray("statuses");
                for (int i = 0; i <statusesJSON.length() ; i++) {
                    JSONObject tweetObject = statusesJSON.getJSONObject(i);
                    String tweetText = tweetObject.getString("text");
                    ArrayList<String> hashTags = new ArrayList<>();
                    JSONArray hashTagsArray = tweetObject.getJSONObject("entities").getJSONArray("hashtags");
                    if (hashTagsArray.length()>0){
                        for (int j = 0; j <hashTagsArray.length() ; j++) {
                            hashTags.add(hashTagsArray.getJSONObject(j).getString("text"));
                        }
                    }else {
                        hashTags.add("No HashTags!");
                    }
                    String externalLink;
                    if (tweetObject.getJSONObject("entities").getJSONArray("urls").length()>0){
                        externalLink = tweetObject.getJSONObject("entities").getJSONArray("urls").getJSONObject(0).getString("url");
                    }else {
                        externalLink = "No external links";
                    }
                    String user = tweetObject.getJSONObject("user").getString("name");
                    Tweet tweet = new Tweet(tweetText,hashTags,externalLink,user);
                    tweets.add(tweet);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return tweets;
    }
}