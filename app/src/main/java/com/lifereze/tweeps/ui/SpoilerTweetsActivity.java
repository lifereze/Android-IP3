package com.lifereze.tweeps.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kenneth.spoileralert.adapters.CustomArrayAdapter;
import com.kenneth.spoileralert.R;
import com.kenneth.spoileralert.models.Tweet;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpoilerTweetsActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.tweeterTextView) TextView mTweeterTextView;
    @Bind(R.id.extLinkTextView) TextView mLinkTextView;
    @Bind(R.id.tweetContentTextView) TextView mTweetContextTextView;
    @Bind(R.id.tweetsHashTagTextView) TextView mTweetHashTagTextView;
    private Tweet mTweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spoiler_tweets);

        ButterKnife.bind(this);

        Typeface titleFont = Typeface.createFromAsset(getAssets(),"fonts/JOURNAL.TTF");

        Intent intent = getIntent();
        mTweet = Parcels.unwrap(intent.getParcelableExtra("tweet"));
        mTweeterTextView.setText("@"+mTweet.getUser());
        mTweetHashTagTextView.setText("#"+android.text.TextUtils.join(" #",mTweet.getHashTags()));
        mTweetContextTextView.setText(mTweet.getTweetText());
        mTweeterTextView.setTypeface(titleFont);
        mLinkTextView.setTypeface(titleFont);
        mLinkTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v==mLinkTextView){
            if (mTweet.getExternalLink().equals("No external links")){
                Toast.makeText(this, "No external link available", Toast.LENGTH_SHORT).show();
            }else {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTweet.getExternalLink()));
                startActivity(webIntent);
            }
        }
    }
}
