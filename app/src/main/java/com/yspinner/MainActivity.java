package com.yspinner;

import java.util.Arrays;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yspinner.adapter.DemoAdapter;
import com.yspinner.yspinner.R;
import com.yspinner.yspinner.databinding.DemoDataBinding;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] arr = new String[] { "one", "two", "three", "four", "five" };
		DemoDataBinding demoDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		DemoAdapter adapter = new DemoAdapter(this, R.layout.layout_spinner_item);
		adapter.setData(Arrays.asList(arr));
		demoDataBinding.spinner.setAdapter(adapter);
	}
}
