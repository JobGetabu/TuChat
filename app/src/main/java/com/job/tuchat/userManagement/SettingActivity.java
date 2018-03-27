package com.job.tuchat.userManagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.job.tuchat.R;
import com.theartofdev.edmodo.cropper.CropImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;
    @BindView(R.id.settings_displayname)
    TextView displayName;
    @BindView(R.id.settings_status)
    TextView statustxt;
    @BindView(R.id.settings_image)
    CircleImageView profImage;

    Uri imageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        imageuri = null;

    }

    @OnClick(R.id.settings_changeimage)
    public void changeImageBtnclick(){
        //start image intent
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (PICK_IMAGE_REQUEST == requestCode){

            imageuri = data.getData();

            // start cropping activity for pre-acquired image saved on the device
            CropImage.activity(imageuri)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                //test
                profImage.setImageURI(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @OnClick(R.id.settings_status)
    public void changeBtnStatus(){

    }


    @Override
    protected void onStart() {
        super.onStart();

        //listen to firebase
    }
}
