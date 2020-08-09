package com.lifereze.tweeps.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.kenneth.spoileralert.R;
import com.kenneth.spoileralert.adapters.TweetsListAdapter;
import com.kenneth.spoileralert.models.Tweet;
import com.kenneth.spoileralert.services.TwitterService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SelectedCategoryActivity extends AppCompatActivity {
    @Bind(R.id.categoryTextView) TextView mTextView;
    @Bind(R.id.tweetsRecyclerView) RecyclerView mRecyclerView;
    public static final String TAG = SelectedCategoryActivity.class.getSimpleName();

    public ArrayList<Tweet> mTweets = new ArrayList<>();
    private TweetsListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        String categoryname = intent.getStringExtra("categoryname");

        getTweets(categoryname);
        Typeface titleFont = Typeface.createFromAsset(getAssets(),"fonts/JOURNAL.TTF");

        mTextView.setTypeface(titleFont);
        mTextView.setText("Tweets on "+categoryname);

    }

    private void getTweets(String topic){
        final TwitterService twitterService = new TwitterService();
        twitterService.findTweets(topic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mTweets = twitterService.processResults(response);

                SelectedCategoryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new TweetsListAdapter(getApplicationContext(),mTweets);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectedCategoryActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
