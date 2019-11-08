package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {                 // 4.B) NOW THAT I MADE VIEW HOLDER IN THIS CLASS, extend this class by extending the RecyclerView Adapter and parametrize it with the ViewHolder we just made
    Context context;
    List<Tweet> tweets;
// 1) Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;
    }
// 2) For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet,parent, false);
        return new ViewHolder(view);                                                                        //wrap this view into a ViewHolder we defines below
    }
// 3.1) Bind values based on the position of the elem
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);                                                         //get the data at the position
        holder.bind(tweet);                                                                         // Bind the tweet with the view holder. Use the tweet object to set the fields in the layout View
    }
    @Override
    public int getItemCount() {
        return tweets.size();                                                                       //*****VERY IMPORTANT! I had this to return 0 and nothing was being recycled!
    }
// 4) Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfilePic;
        TextView tvName;
        TextView tvBody;
        public ViewHolder(@NonNull View itemView) {                                                 // 4.A) MAKE THE VIEW HOLDER.  represents one row of the rv
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvName = itemView.findViewById((R.id.tvName));
            tvBody = itemView.findViewById(R.id.tvBody);
        }

        public void bind(Tweet tweet) {                                                             // 3.2) Will use the tweet object's attributes to bind values in the view holder
            tvBody.setText(tweet.body);
            tvName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.profileImageURL).into(ivProfilePic);                // 3.3) load in the profile pic with glide
        }
    }





    // Copy and Paste from "Pull to Refresh": Clean all elements of the tweets list when i swipe down
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }
    //  Copy and Paste from "Pull to Refresh": Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }


}
