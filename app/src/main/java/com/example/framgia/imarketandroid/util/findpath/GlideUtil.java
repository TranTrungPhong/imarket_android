package com.example.framgia.imarketandroid.util.findpath;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by phongtran on 16/09/2016.
 */
public class GlideUtil {
    public static void getImage(Activity activity, ImageView view, String url) {
        Glide.with(activity)
            .load(url)
            .into(view);
    }

    public static void getImageButton(Activity activity, ImageButton view, String url) {
        Glide.with(activity)
            .load(url)
            .into(view);
    }
}
