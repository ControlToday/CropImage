package com.crop;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import java.io.File;

import example.crop.jian.cropimagelib.R;

/**
 * 图片剪裁界面 <br/>
 * 运行在独立的进程中，以保证主进程不会OOM <br/>
 * 通过Intent进行数据交互，剪裁结束后返回图片的uri <br/>
 */

public class CropImageActivity extends Activity {
    private static final String ORIGINAL_FILE_PATH = "path";
    public static final String RESULT_PATH = "crop_image_path";
    public CropImageLayout mClipImageLayout;

    public static void startActivity(Activity activity, String path, int code) {
        Intent intent = new Intent(activity, CropImageActivity.class);
        intent.putExtra(ORIGINAL_FILE_PATH, path);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        findViews();
        initView();
    }

    private void findViews() {
        mClipImageLayout = (CropImageLayout) findViewById(R.id.clipImageLayout);

        View ok = findViewById(R.id.okBtn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOKClick();
            }
        });

        View close = findViewById(R.id.close_icon);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnimation();
            }

        });
    }

    private void initView() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(0xff000000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = getIntent().getStringExtra(ORIGINAL_FILE_PATH);
        Glide.with(this).load(path).dontAnimate().into(mClipImageLayout.getZoomImageView());
    }


    public void onOKClick() {
        File file = FileUtils.getOutputMediaFileUri();
        boolean success = mClipImageLayout.clipToFile(file);
        if (success) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_PATH, file.getAbsolutePath());
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finishWithAnimation();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void finishWithAnimation() {
        finish();
        overridePendingTransition(0, R.anim.push_out_down);
    }
}
