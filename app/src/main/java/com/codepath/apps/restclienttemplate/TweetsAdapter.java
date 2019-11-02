package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Tweet> tweets;
    //pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;
    }

    //for each row, inflate the layout
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet,parent, false);
        return new ViewHolder(view);                                        //wrap this view into a ViewHolder we defines below
    }

    //bind values based on the position of the elem
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //get the data at the position
        //
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfilePic;
        TextView tvBody;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {                                                    //represents one row of the rv
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById((R.id.tvName));
        }
    }
}
