package com.example.framgia.imarketandroid.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

/**
 * Created by phongtran on 08/09/2016.
 */
public class DialogShareUtil {
    private static Activity mActivity;
    private static AlertDialog mAlertDialogShare;
    private static LoginManager mLogin;
    private static String mNameProduct;

    public static void dialogShare(Activity context, final int idImageView,
                                   final CallbackManager callbackManager) {
        mActivity = context;
        LayoutInflater li = LayoutInflater.from(mActivity);
        View promptsView = li.inflate(R.layout.dialog_share, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setView(promptsView);
        final EditText editTextShare = (EditText) promptsView.findViewById(R.id.edittext_share);
        ImageView imgShare = (ImageView) promptsView.findViewById(R.id.image_share);
        Button buttonCancel = (Button) promptsView.findViewById(R.id.button_cancel_share_dialog);
        Button buttonSuccess = (Button) promptsView.findViewById(R.id.button_success_share_dialog);
        imgShare.setImageResource(idImageView);
        alertDialogBuilder
            .setCancelable(false);
        mAlertDialogShare = alertDialogBuilder.create();
        mAlertDialogShare.show();
        mAlertDialogShare.setCanceledOnTouchOutside(true);
        editTextShare.requestFocus();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialogShare.dismiss();
            }
        });
        buttonSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionShare(idImageView, editTextShare.getText().toString(), callbackManager);
                mAlertDialogShare.dismiss();
            }
        });
    }

    private static void actionShare(final int idImageShare, final String textShare,
                                    CallbackManager callbackManager) {
        FacebookSdk.sdkInitialize(mActivity);
        List<String> permissionNeeds = Arrays.asList(Constants.PERMISSTION_SHARE);
        mLogin = LoginManager.getInstance();
        mLogin.logInWithPublishPermissions(mActivity, permissionNeeds);
        mLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharePhotoToFacebook(idImageShare, textShare);
                toastDialogMessage(mActivity.getString(R.string.success), mActivity);
            }

            @Override
            public void onCancel() {
                toastDialogMessage(mActivity.getString(R.string.cancle), mActivity);
            }

            @Override
            public void onError(FacebookException exception) {
                toastDialogMessage(mActivity.getString(R.string.error), mActivity);
            }
        });
    }

    private static void sharePhotoToFacebook(int idImageShare, String textShare) {
        Bitmap image = BitmapFactory.decodeResource(mActivity.getResources(), idImageShare);
        SharePhoto photo = new SharePhoto.Builder()
            .setBitmap(image)
            .setCaption(textShare)
            .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
            .addPhoto(photo)
            .build();
        ShareApi.share(content, null);
    }

    public static void toastDialogMessage(String mess, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.noti);
        builder.setMessage(mess);
        builder
            .setPositiveButton(R.string.ok_dialog_success, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        builder.create().show();
    }

    public static void dialogShareProduct(Activity context, final int idImageView,
                                          final CallbackManager callbackManager, String namePro) {
        mActivity = context;
        mNameProduct = namePro;
        LayoutInflater li = LayoutInflater.from(mActivity);
        View promptsView = li.inflate(R.layout.dialog_share, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setView(promptsView);
        final EditText editTextShare = (EditText) promptsView.findViewById(R.id.edittext_share);
        StringBuffer buffer = new StringBuffer()
            .append(mNameProduct.toString())
            .append(mActivity.getString(R.string.text_product_sale));
        editTextShare.setText(buffer);
        ImageView imgShare = (ImageView) promptsView.findViewById(R.id.image_share);
        Button buttonCancel = (Button) promptsView.findViewById(R.id.button_cancel_share_dialog);
        Button buttonSuccess = (Button) promptsView.findViewById(R.id.button_success_share_dialog);
        imgShare.setImageResource(idImageView);
        alertDialogBuilder
            .setCancelable(false);
        mAlertDialogShare = alertDialogBuilder.create();
        mAlertDialogShare.show();
        mAlertDialogShare.setCanceledOnTouchOutside(true);
        editTextShare.requestFocus();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialogShare.dismiss();
            }
        });
        buttonSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionShare(idImageView, editTextShare.getText().toString(), callbackManager);
                mAlertDialogShare.dismiss();
            }
        });
    }

    public static void initAlertContinueBooking(final Activity activity) {
        mActivity = activity;
        LayoutInflater li = LayoutInflater.from(mActivity);
        View promptsView = li.inflate(R.layout.continue_book_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setView(promptsView);
        Button butCancel = (Button) promptsView.findViewById(R.id.buttn_cancel_dialog_confirm_book);
        Button butOk = (Button) promptsView.findViewById(R.id.buttn_ok_dialog_confirm_book);
        TextView textInfo = (TextView) promptsView.findViewById(R.id.text_marque);
        textInfo.setSelected(true);
        textInfo.setMarqueeRepeatLimit(-1);
        alertDialogBuilder
            .setCancelable(false);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogShare.dismiss();
                toastDialogMessage(mActivity.getString(R.string.transaction_fails), mActivity);
            }
        });
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogShare.dismiss();
                toastDialogMessage(mActivity.getString(R.string.transaction_done), mActivity);
            }
        });
        mAlertDialogShare = alertDialogBuilder.create();
        mAlertDialogShare.show();
        mAlertDialogShare.setCanceledOnTouchOutside(true);
    }

    public static void getSmallBang(Activity activity, View view) {
        SmallBang mSmallBang = SmallBang.attach2Window(activity);
        mSmallBang.bang(view, Constants.RADIUS_SMALL_BANG, new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }
        });
    }

    public static void startDilog(final Activity activity, final View view) {
        AlertDialog.Builder myAlertDilog = new AlertDialog.Builder(activity);
        myAlertDilog.setTitle(R.string.dialog_picture);
        myAlertDilog.setMessage(R.string.message_picture);
        myAlertDilog.setPositiveButton(R.string.gallery, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent picIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                picIntent.setType(Constants.SETTYPEDATA);
                picIntent.putExtra(Constants.BUNDLE_DATA, true);
                activity.startActivityForResult(picIntent, Constants.GALLERY_REQUEST);
            }
        });
        myAlertDilog
            .setNegativeButton(R.string.dialog_camera, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!RequestPermissionUtil.checkIfAlreadyhavePermission(activity, Manifest
                        .permission.CAMERA)) {
                        ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.CAMERA
                        }, Constants
                            .REQUEST_PERMISSION);
                    } else {
                        Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        activity.startActivityForResult(picIntent, Constants.CAMERA_REQUEST);
                    }
                }
            });
        myAlertDilog.show();
    }
}
