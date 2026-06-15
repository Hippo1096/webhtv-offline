package com.fongmi.android.tv.ui.presenter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.fongmi.android.tv.bean.TmdbItem;
import com.fongmi.android.tv.databinding.AdapterTmdbRecommendationBinding;

public class TmdbRecommendationPresenter extends Presenter {

    private final OnClickListener mListener;

    public TmdbRecommendationPresenter(OnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnClickListener {
        void onItemClick(TmdbItem item);
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(AdapterTmdbRecommendationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        TmdbItem tmdbItem = (TmdbItem) item;
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.binding.title.setText(tmdbItem.getTitle());
        // rating字段在布局中visibility为gone，所以暂时不显示
        if (!tmdbItem.getPosterUrl().isEmpty()) {
            Glide.with(holder.binding.poster.getContext()).load(tmdbItem.getPosterUrl()).into(holder.binding.poster);
        }
        setOnClickListener(holder, view -> {
            if (mListener != null) mListener.onItemClick(tmdbItem);
        });
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private final AdapterTmdbRecommendationBinding binding;

        public ViewHolder(@NonNull AdapterTmdbRecommendationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
