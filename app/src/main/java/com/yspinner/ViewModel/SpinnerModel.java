package com.yspinner.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.yspinner.yspinner.BR;

/**
 * Created by Yann on 20/02/2017.
 */

public class SpinnerModel extends BaseObservable {

	private String itemText;

	@Bindable
	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
		notifyPropertyChanged(BR.itemText);
	}
}
