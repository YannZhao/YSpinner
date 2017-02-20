package com.yspinner.adapter;

import android.content.Context;

import com.yspinner.ViewModel.SpinnerModel;
import com.yspinner.yspinner.databinding.SpinnerItemDataBinding;

/**
 * Created by Yann on 20/02/2017.
 */

public class DemoAdapter extends YSpinnerBaseAdapter<String, SpinnerItemDataBinding> {

	public DemoAdapter(Context context, int layoutId) {
		super(context, layoutId);
	}

	@Override
	public String getItemContent(int position) {
		return data.get(position);
	}

	@Override
	public void setData(String info, SpinnerItemDataBinding dataBinding) {
		if (dataBinding != null) {
			if (dataBinding.getModel() == null) {
				dataBinding.setModel(new SpinnerModel());
			}
			dataBinding.getModel().setItemText(info);
		}
	}

}
