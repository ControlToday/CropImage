package example.crop.jian.cropimagelib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.File;

/**
 * @author jian
 * @ClassName: ClipImageLayout
 * @Description: 被修改再次修改过的一个控件
 */
public class CropImageLayout extends FrameLayout {
    private CropZoomImageView mZoomImageView;
    private CoverView mCoverView;

    public CropImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomImageView = new CropZoomImageView(context);
        mCoverView = new CoverView(context);

        LayoutParams zoomImageViewLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        this.addView(mZoomImageView, zoomImageViewLayoutParams);
        this.addView(mCoverView, lp);

        mCoverView.setFocusableInTouchMode(false);
    }

//	public void setImageBitmap(Bitmap bitmap) {
//		mZoomImageView.setImageBitmap(bitmap);
//	}


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(changed) {
            int paddingTop = getHeight() * 256 / 1920;
            mZoomImageView.setHVerticalPadding(paddingTop);
            mCoverView.setHVerticalPadding(paddingTop);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public CropZoomImageView getZoomImageView() {
		return mZoomImageView;
    }

    /**
     * 裁切图片
     */
    public boolean clipToFile(File file) {
        return mZoomImageView.clipToFile(file);
    }
}
