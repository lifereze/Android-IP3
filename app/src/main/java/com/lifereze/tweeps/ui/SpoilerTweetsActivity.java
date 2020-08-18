package com.lifereze.tweeps.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lifereze.tweeps.R;
import com.lifereze.tweeps.models.Tweet;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpoilerTweetsActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.tweeterTextView) TextView mTweeterTextView;
    @BindView(R.id.extLinkTextView) TextView mLinkTextView;
    @BindView(R.id.tweetContentTextView) TextView mTweetContextTextView;
    @BindView(R.id.tweetsHashTagTextView) TextView mTweetHashTagTextView;
    private Tweet mTweet;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spoiler_tweets);

        ButterKnife.bind(this);

//        Typeface titleFont = Typeface.createFromAsset(getAssets(),"fonts/JOURNAL.TTF");

        Intent intent = getIntent();
        mTweet = Parcels.unwrap(intent.getParcelableExtra("tweet"));
        mTweeterTextView.setText("@"+mTweet.getUser());
        mTweetHashTagTextView.setText("#"+android.text.TextUtils.join(" #",mTweet.getHashTags()));
        mTweetContextTextView.setText(mTweet.getTweetText());
//        mTweeterTextView.setTypeface(titleFont);
//        mLinkTextView.setTypeface(titleFont);
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
