package com.ekumfi.agent.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ApplicationVersionSignature;
import com.ekumfi.agent.activity.PictureActivity;
import com.ekumfi.agent.activity.RiderProviderAccountActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.makeramen.roundedimageview.RoundedDrawable;
import com.makeramen.roundedimageview.RoundedImageView;
import com.noelchew.multipickerwrapper.library.MultiPickerWrapper;
import com.noelchew.multipickerwrapper.library.ui.MultiPickerWrapperSupportFragment;
import com.ekumfi.agent.R;
import com.ekumfi.agent.constants.Const;
import com.ekumfi.agent.util.PixelUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

/**
 * Created by 2CLearning on 12/13/2017.
 */

public class RiderProviderAccountFragment8 extends MultiPickerWrapperSupportFragment {
    public static final String PICTURE_TYPE = "PICTURE_TYPE";
    public static final String TYPE_ID_PIC = "TYPE_ID_PIC";
    private static final String TAG = "PersonalProviderAccountFragment4";
    public RoundedImageView insurance_sticker_image;
    Context mContext;
    LinearLayout controls;
    TextView image_not_set;
    public static File insurance_sticker_image_file = null;
    MultiPickerWrapper.PickerUtilListener multiPickerWrapperListener = new MultiPickerWrapper.PickerUtilListener() {
        @Override
        public void onPermissionDenied() {
            // do something here
        }

        @Override
        public void onImagesChosen(List<ChosenImage> list) {
            image_not_set.setVisibility(View.GONE);
            controls.setVisibility(View.GONE);
            String imagePath = list.get(0).getOriginalPath();
            insurance_sticker_image.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            insurance_sticker_image_file = new File(list.get(0).getOriginalPath());
        }

        @Override
        public void onVideosChosen(List<ChosenVideo> list) {
            Const.showToast(getContext(), mContext.getString(R.string.unsupported_file_format));
        }

        @Override
        public void onError(String s) {
            Toast.makeText(getContext(), getString(R.string.error_choosing_image), Toast.LENGTH_SHORT).show();
        }
    };

    private FloatingActionButton addimage, gal, cam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();

        final View rootView = inflater.inflate(R.layout.fragment_rider_provider_account8, container, false);
        insurance_sticker_image = rootView.findViewById(R.id.insurance_sticker_image);
        addimage = rootView.findViewById(R.id.addimage);
        controls = rootView.findViewById(R.id.add);
        gal = rootView.findViewById(R.id.gal);
        cam = rootView.findViewById(R.id.cam);
        image_not_set = rootView.findViewById(R.id.image_not_set);


        addimage.setOnClickListener(v -> {
            if (controls.getVisibility() == View.VISIBLE) {
                controls.setVisibility(View.GONE);

            } else {
                controls.setVisibility(View.VISIBLE);
            }
        });
        gal.setOnClickListener(v -> multiPickerWrapper.getPermissionAndPickSingleImageAndCrop(imgOptions(), 1, 1));
        cam.setOnClickListener(v -> multiPickerWrapper.getPermissionAndTakePictureAndCrop(imgOptions(), 3, 2));

        insurance_sticker_image.setOnClickListener(view -> {
            if (insurance_sticker_image.getDrawable() == null) {
                //Toast.makeText(mContext, getString(R.string.image_not_set), Toast.LENGTH_SHORT).show();
            } else {
                PictureActivity.idPicBitmap = ((RoundedDrawable) insurance_sticker_image.getDrawable()).getSourceBitmap();
                Intent intent = new Intent(getActivity(), PictureActivity.class);
                intent.putExtra(PICTURE_TYPE, TYPE_ID_PIC);
                getActivity().startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // txtData = (TextView)view.findViewById(R.id.txtData);
    }

    @Override
    protected MultiPickerWrapper.PickerUtilListener getMultiPickerWrapperListener() {
        return multiPickerWrapperListener;
    }

    public void init() {
        if (RiderProviderAccountActivity.realmProvider != null) {
            String picture = RiderProviderAccountActivity.realmProvider.getInsurance_sticker_image_url();
            boolean pictureExists = picture != null;
            if (pictureExists) {
                Glide.with(getContext())
                        .load(RiderProviderAccountActivity.realmProvider.getInsurance_sticker_image_url())
                        .apply(new RequestOptions().centerCrop())
                        .apply(RequestOptions.signatureOf(ApplicationVersionSignature.obtain(getContext())))
                        .into(insurance_sticker_image);
            } else {
                insurance_sticker_image.setImageBitmap(null);
            }
        }
    }

    public boolean validate() {
        boolean validated = true;
        if (insurance_sticker_image.getDrawable() == null) {
            image_not_set.setVisibility(View.VISIBLE);
            validated = false;
        }
        return validated;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private UCrop.Options imgOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setCropFrameColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        options.setCropFrameStrokeWidth(PixelUtil.dpToPx(getContext(), 4));
        options.setCropGridColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setCropGridStrokeWidth(PixelUtil.dpToPx(getContext(), 2));
        options.setActiveWidgetColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setToolbarTitle(getString(R.string.crop_image));

        // set rounded cropping guide
        options.setCircleDimmedLayer(true);
        return options;
    }
}