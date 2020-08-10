package com.lifereze.tweeps.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifereze.tweeps.R;
import com.lifereze.tweeps.adapters.TweetsListAdapter;
import com.lifereze.tweeps.models.Tweet;
import com.lifereze.tweeps.services.TwitterService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SelectedCategoryActivity extends AppCompatActivity {
    @BindView(R.id.categoryTextView) TextView mTextView;
    @BindView(R.id.tweetsRecyclerView)
    RecyclerView mRecyclerView;
    public static final String TAG = SelectedCategoryActivity.class.getSimpleName();

    public ArrayList<Tweet> mTweets = new ArrayList<>();
    private TweetsListAdapter mAdapter;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        String categoryname = intent.getStringExtra("categoryname");

        getTweets(categoryname);
////        Typeface titleFont = Typeface.createFromAsset(getAssets(),"fonts/JOURNAL.TTF");
//
//        mTextView.setTypeface(titleFont);
        mTextView.setText("Tweets on "+categoryname);

    }

    private void getTweets(String topic){
        final TwitterService twitterService = new TwitterService();
        TwitterService.findTweets(topic, new Callback() {
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
