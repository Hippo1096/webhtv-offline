package com.fongmi.android.tv.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fongmi.android.tv.R;

import java.util.List;

/**
 * 剧集图片适配器 - 显示本集所有剧照
 */
public class EpisodePhotoAdapter extends RecyclerView.Adapter<EpisodePhotoAdapter.ViewHolder> {

    private final List<String> mPhotos;
    private final int photoWidth = 220;  // dp
    private final int photoHeight = 124; // dp (16:9)

    public EpisodePhotoAdapter(List<String> photos) {
        this.mPhotos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
            com.fongmi.android.tv.utils.ResUtil.dp2px(photoWidth),
            com.fongmi.android.tv.utils.ResUtil.dp2px(photoHeight)
        ));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setFocusable(true);
        imageView.setFocusableInTouchMode(true);
        imageView.setBackgroundResource(R.drawable.selector_item);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String photoUrl = mPhotos.get(position);

        // 使用原图
        String originalUrl = photoUrl.replace("/w300/", "/original/");

        Glide.with(holder.imageView.getContext())
            .load(originalUrl)
            .placeholder(R.color.black)
            .error(R.color.black)
            .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }
}
