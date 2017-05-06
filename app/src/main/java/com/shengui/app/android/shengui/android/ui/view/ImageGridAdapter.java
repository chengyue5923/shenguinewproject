package com.shengui.app.android.shengui.android.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.Image;
import com.shengui.app.android.shengui.utils.im.ImageLoader;
import com.shengui.app.android.shengui.utils.im.UISquaredImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * 图片Adapter
 */
public class ImageGridAdapter extends BaseAdapter {

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;

    private Context mContext;

    private LayoutInflater mInflater;
    private boolean showCamera = true;
    private boolean showSelectIndicator = true;

    private List<Image> mImages = new ArrayList<Image>();
    private List<Image> mSelectedImages = new ArrayList<Image>();

    private int mItemSize;
    private GridView.LayoutParams mItemLayoutParams;


    public ImageGridAdapter(Activity context, boolean showCamera) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.showCamera = showCamera;
        mItemLayoutParams = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT);
    }

    /**
     * 显示选择指示器
     *
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b) {
        if (showCamera == b) return;

        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     *
     * @param image
     */
    public void select(ImageView checkIv, Image image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
            checkIv.setVisibility(View.GONE);
        } else {
            mSelectedImages.add(image);
            checkIv.setVisibility(View.VISIBLE);

        }

    }

    /**
     * 选择某个图片，改变选择状态
     *
     * @param image
     */
    public void select2(Image image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);

        } else {
            mSelectedImages.add(image);
        }
        notifyDataSetChanged();

    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for (String path : resultList) {
            Image image = getImageByPath(path);
            if (image != null) {
                mSelectedImages.add(image);
            }
        }
        if (mSelectedImages.size() > 0) {
            notifyDataSetChanged();
        }
    }

    private Image getImageByPath(String path) {
        if (mImages != null && mImages.size() > 0) {
            for (Image image : mImages) {
                if (image.path.equalsIgnoreCase(path)) {
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     *
     * @param images
     */
    public void setData(List<Image> images) {
        mSelectedImages.clear();

        if (images != null && images.size() > 0) {
            mImages = images;
        } else {
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 重置每个Column的Size
     *
     * @param columnWidth
     */
    public void setItemSize(int columnWidth) {
        if (mItemSize == columnWidth) {
            return;
        }
        mItemSize = columnWidth;
        mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera) {
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return showCamera ? mImages.size() + 1 : mImages.size();
    }

    @Override
    public Image getItem(int i) {
        if (showCamera) {
            if (i == 0) {
                return null;
            }
            return mImages.get(i - 1);
        } else {
            return mImages.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        if (type == TYPE_CAMERA) {
            view = mInflater.inflate(R.layout.image_choose_list_item_camera, null);
            view.setTag(null);
        } else if (type == TYPE_NORMAL) {
            ViewHolde holde;
            if (view == null) {
                view = mInflater.inflate(R.layout.image_choose_list_item_image, null);
                holde = new ViewHolde(view);
            } else {
                holde = (ViewHolde) view.getTag();
                if (holde == null) {
                    view = mInflater.inflate(R.layout.image_choose_list_item_image, null);
                    holde = new ViewHolde(view);
                }
            }
            if (holde != null) {
                holde.bindData(getItem(i));
            }
        }
        return view;
    }

    class ViewHolde {
        UISquaredImageView image;
        ImageView indicator;

        ViewHolde(View view) {
            image = (UISquaredImageView) view.findViewById(R.id.image);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            view.setTag(this);
        }

        void bindData(final Image data) {
            if (data == null) return;
            // 处理单选和多选状态
            if (showSelectIndicator) {
//                indicator.setVisibility(View.VISIBLE);
                if (mSelectedImages.contains(data)) {
                    // 设置选中状态
                    indicator.setVisibility(View.VISIBLE);
                } else {
                    // 未选择
                    indicator.setVisibility(View.GONE);
                }
            } else {
                indicator.setVisibility(View.GONE);
            }
            if (mItemSize > 0) {

                ImageLoader.getInstance(mContext, R.drawable.default_error).loadLocalImage(data.path, image);
            }
        }
    }

}
