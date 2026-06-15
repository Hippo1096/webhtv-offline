package com.fongmi.android.tv.ui.presenter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.fongmi.android.tv.bean.TmdbPerson;
import com.fongmi.android.tv.databinding.AdapterTmdbCastBinding;

public class TmdbCastPresenter extends Presenter {

    private final OnClickListener mListener;

    public TmdbCastPresenter(OnClickListener listener) {
        this.mListener = listener;
    }

    public interface OnClickListener {
        void onItemClick(TmdbPerson item);
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(AdapterTmdbCastBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        TmdbPerson person = (TmdbPerson) item;
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.binding.name.setText(person.getName());
        holder.binding.role.setText(person.getSubtitle());
        if (!person.getProfileUrl().isEmpty()) {
            Glide.with(holder.binding.getRoot().getContext()).load(person.getProfileUrl()).into(holder.binding.profile);
        }
        setOnClickListener(holder, view -> {
            if (mListener != null) mListener.onItemClick(person);
        });
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private final AdapterTmdbCastBinding binding;

        public ViewHolder(@NonNull AdapterTmdbCastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
