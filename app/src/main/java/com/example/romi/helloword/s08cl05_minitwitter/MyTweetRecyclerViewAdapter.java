package com.example.romi.helloword.s08cl05_minitwitter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;
import com.example.romi.helloword.s08cl05_minitwitter.common.SharedPreferencesManager;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Like;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;
import com.google.gson.TypeAdapterFactory;

import java.util.List;


public class MyTweetRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder> {

    private final List<Tweets> mValues;
    private Context ctx;
    private String username;

    public MyTweetRecyclerViewAdapter(Context contexto ,List<Tweets> items ) {
        mValues = items;
        ctx = contexto;
        username = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USERNAME);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tweet_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.tvUserName.setText(holder.mItem.getUser().getUsername());
        holder.tvLikesCount.setText(String.valueOf(holder.mItem.getLikes().size()));
        holder.tvMessage.setText(holder.mItem.getMensaje());

        String photo = holder.mItem.getUser().getPhotoUrl();

        if(!photo.equals("")){
            Glide.with(ctx)
                    .load("https//www.minitwitter.com/apiv1/uploads/photos/" + photo)
                    .into(holder.ivAvatar);



            for(Like like: holder.mItem.getLikes()){
                if(like.getUsername().equals(username)){
                    Glide.with(ctx)
                            .load(R.drawable.ic_like_pink)
                            .into(holder.ivLike);
                    holder.tvLikesCount.setTextColor(ctx.getResources().getColor(R.color.ColorPink));
                    holder.tvLikesCount.setTypeface(null, Typeface.BOLD);
                    break;
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivAvatar;
        public final ImageView ivLike;
        public final TextView tvUserName;
        public final TextView tvMessage;
        public final TextView tvLikesCount;
        public Tweets mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvUserName = view.findViewById(R.id.textViewUserName);
            tvMessage = view.findViewById(R.id.textViewMessage);
            tvLikesCount = view.findViewById(R.id.textViewLikes);
            ivAvatar    = view.findViewById(R.id.imageViewAvatar);
            ivLike         = view.findViewById(R.id.imageViewLike);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvUserName.getText() + "'";
        }
    }
}
