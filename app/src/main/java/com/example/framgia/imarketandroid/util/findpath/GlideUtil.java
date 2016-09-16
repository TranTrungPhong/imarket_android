package com.example.framgia.imarketandroid.util.findpath;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.framgia.imarketandroid.R;

/**
 * Created by phongtran on 16/09/2016.
 */
public class GlideUtil {
    public static void getImage(Activity activity, ImageView view, String url) {
        Glide.with(activity)
            .load(url)
            .centerCrop()
            .crossFade()
            .thumbnail(1f)
            .placeholder(R.drawable.ic_not_internet)
            .error(R.drawable.ic_not_internet)
            .into(view);
    }

    public static void getImageButton(Activity activity, ImageButton view, String url) {
        Glide.with(activity)
            .load(url)
            .crossFade()
            .placeholder(R.drawable.ic_not_internet)
            .error(R.drawable.ic_not_internet)
            .into(view);
    }
}
