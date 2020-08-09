package com.lifereze.tweeps.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Kenneth on 01/06/2017.
 */

@Parcel
public class Tweet {
    String mTweetText;
    ArrayList<String> mHashTags;
    String mExternalLink;
    String mUser;

    public Tweet(){}

    public Tweet(String tweetText, ArrayList<String> hashTags,String externalLink,String user){
        this.mTweetText = tweetText;
        this.mHashTags = hashTags;
        this.mExternalLink = externalLink;
        this.mUser = user;
    }

    public String getTweetText(){
        return mTweetText;
    }

    public ArrayList<String> getHashTags(){
        return mHashTags;
    }

    public String getExternalLink(){
        return mExternalLink;
    }

    public String getUser(){
        return mUser;
    }
}
