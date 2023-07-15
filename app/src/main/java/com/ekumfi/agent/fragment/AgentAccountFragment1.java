package com.ekumfi.agent.fragment;

import static com.ekumfi.agent.activity.AgentAccountActivity.realmAgent;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ApplicationVersionSignature;
import com.ekumfi.agent.R;
import com.ekumfi.agent.activity.PictureActivity;
import com.ekumfi.agent.constants.Const;
import com.ekumfi.agent.util.PixelUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.makeramen.roundedimageview.RoundedDrawable;
import com.makeramen.roundedimageview.RoundedImageView;
import com.noelchew.multipickerwrapper.library.MultiPickerWrapper;
import com.noelchew.multipickerwrapper.library.ui.MultiPickerWrapperSupportFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

/**
 * Created by 2CLearning on 12/13/2017.
 */

public class AgentAccountFragment1 extends MultiPickerWrapperSupportFragment {
    public static final String PICTURE_TYPE = "PICTURE_TYPE";
    public static final String TYPE_PROFILE_PIC = "TYPE_PROFILE_PIC";
    private static final String TAG = "PersonalProviderAccountFragment1";
    public static RoundedImageView profile_image;
    Context mContext;
    LinearLayout controls;
    TextView image_not_set;
    public static File profile_image_file = null;
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
            profile_image.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            profile_image_file = new File(list.get(0).getOriginalPath());
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
    public static EditText first_name, last_name, other_names;
    private FloatingActionButton addimage, gal, cam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();

        final View rootView = inflater.inflate(R.layout.fragment_agent_account1, container, false);
        profile_image = rootView.findViewById(R.id.shop_image);
        first_name = rootView.findViewById(R.id.first_name);
        last_name = rootView.findViewById(R.id.last_name);
        other_names = rootView.findViewById(R.id.other_names);
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
        cam.setOnClickListener(v -> multiPickerWrapper.getPermissionAndTakePictureAndCrop(imgOptions(), 1, 1));

        profile_image.setOnClickListener(view -> {
            if (profile_image.getDrawable() == null) {
                //Toast.makeText(mContext, getString(R.string.image_not_set), Toast.LENGTH_SHORT).show();
            } else {
                PictureActivity.idPicBitmap = ((RoundedDrawable) profile_image.getDrawable()).getSourceBitmap();
                Intent intent = new Intent(getActivity(), PictureActivity.class);
                intent.putExtra(PICTURE_TYPE, TYPE_PROFILE_PIC);
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
;        if (realmAgent != null) {
            String picture = realmAgent.getProfile_image_url();
            boolean pictureExists = picture != null;
            if (pictureExists) {
                Glide.with(getContext())
                        .load(realmAgent.getProfile_image_url())
                        .apply(new RequestOptions().centerCrop())
                        .apply(RequestOptions.signatureOf(ApplicationVersionSignature.obtain(getContext())))
                        .into(profile_image);
            } else {
                profile_image.setImageBitmap(null);
            }

            first_name.setText(realmAgent.getFirst_name());
            last_name.setText(realmAgent.getLast_name());
            other_names.setText(realmAgent.getOther_names());
        }
    }

    public boolean validate() {
        boolean validated = true;

        if (TextUtils.isEmpty(first_name.getText())) {
            first_name.setError(getString(R.string.error_field_required));
            validated = false;
        }
        if (TextUtils.isEmpty(last_name.getText())) {
            last_name.setError(getString(R.string.error_field_required));
            validated = false;
        }
        if (profile_image.getDrawable() == null && realmAgent.getProfile_image_url() == null) {
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