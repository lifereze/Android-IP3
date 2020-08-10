package com.lifereze.tweeps.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lifereze.tweeps.R;
import com.lifereze.tweeps.models.Tweet;
import com.lifereze.tweeps.ui.SpoilerTweetsActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class TweetsListAdapter extends RecyclerView.Adapter<TweetsListAdapter.TweetViewHolder>{
    private ArrayList<Tweet> mTweets = new ArrayList<>();
    private Context mContext;

    public TweetsListAdapter(Context context,ArrayList<Tweet> tweets){
        mContext = context;
        mTweets = tweets;
    }

    @Override
    public TweetsListAdapter.TweetViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweets_list_item,parent,false);
        TweetViewHolder viewHolder = new TweetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetsListAdapter.TweetViewHolder holder,int position){
        holder.bindTweet(mTweets.get(position));
    }
    @Override
    public int getItemCount(){
        return mTweets.size();
    }

    public class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tweetTextView) TextView mTweetTextView;
        @BindView(R.id.tweetUserTextView) TextView mUserTextView;
        private Context mContext;

        public TweetViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext=itemView.getContext();
            itemView.setOnClickListener(this);
        }
        @SuppressLint("SetTextI18n")
        public void bindTweet(Tweet tweet){
            mUserTextView.setText("@"+tweet.getUser()+"\n says");
            mTweetTextView.setText(tweet.getTweetText());
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, SpoilerTweetsActivity.class);
            Tweet clickedTweet = mTweets.get(itemPosition);
            intent.putExtra("tweet", Parcels.wrap(clickedTweet));
            mContext.startActivity(intent);
        }
    }
}
