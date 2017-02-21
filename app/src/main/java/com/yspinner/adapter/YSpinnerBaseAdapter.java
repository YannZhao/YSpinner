package com.yspinner.adapter;

import java.util.List;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Yann on 20/02/2017.
 */

public abstract class YSpinnerBaseAdapter<T, D extends ViewDataBinding> extends BaseAdapter {

	protected Context mContext;
	protected int mSelectedIndex;
	protected List<T> data;
	protected D dataBinding;
	private int layoutId;

	public YSpinnerBaseAdapter(Context context, int layoutId) {
		this.mContext = context;
		this.layoutId = layoutId;
	}

	public void setData(List<T> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size() - 1;
		} else {
			return 0;
		}
	}

	/*
	 * 用于外部取item数据
	 */
	public T getItemData(int position) {
		return data.get(position);
	}

	/*
	 * 用于渲染item，不作为外部取item数据
	 */
	@Override
	public T getItem(int position) {
		if (position >= mSelectedIndex) {
			return data.get(position + 1);
		} else {
			return data.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void notifyItemSelected(int index) {
		mSelectedIndex = index;
	}

	public abstract String getItemContent(int position);

	public abstract void setData(T info, D dataBinding);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
		} else {
			dataBinding = DataBindingUtil.getBinding(convertView);
		}
		setData(getItem(position), dataBinding);
		dataBinding.executePendingBindings();
		return dataBinding.getRoot();
	}
}
