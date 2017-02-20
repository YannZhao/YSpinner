package com.yspinner.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yspinner.adapter.YSpinnerBaseAdapter;
import com.yspinner.yspinner.R;

/**
 * Created by Yann on 20/02/2017.
 */

public class YSpinner extends TextView {

	private int selectedIndex;
	private boolean hideArrow;
	private Drawable arrow;
	private PopupWindow popupWindow;
	private ListView listView;
	private YSpinnerBaseAdapter adapter;
	private YSpinnerEventListener listener;

	public YSpinner(Context context) {
		super(context);
		init(context, null);
	}

	public YSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public YSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YSpinner);
		setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		setClickable(true);
		hideArrow = typedArray.getBoolean(R.styleable.YSpinner_hideArrow, false);
		if (!hideArrow) {
			arrow = typedArray.getDrawable(R.styleable.YSpinner_arrow);
			if (arrow == null) {
				arrow = ContextCompat.getDrawable(context, R.drawable.default_spinner_arrow_drawable);
			}
			setCompoundDrawablesWithIntrinsicBounds(null, null, arrow, null);
		}
		typedArray.recycle();
		listView = new ListView(context);
		listView.setItemsCanFocus(true);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position >= selectedIndex && position < adapter.getCount()) {
					position++;
				}
				selectedIndex = position;
				if (listener != null) {
					listener.onItemClick(position);
					listener.onItemSelected(position);
				}
				adapter.notifyItemSelected(position);
				setText(adapter.getItemContent(position));
				dismissPopupList();
			}
		});
		popupWindow = new PopupWindow(context);
		popupWindow.setContentView(listView);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

			@Override
			public void onDismiss() {
				if (!hideArrow) {
					arrowAnimation(false);
				}
			}
		});
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		popupWindow.setWidth(MeasureSpec.getSize(widthMeasureSpec));
		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setSelectedIndex(int position) {
		if (adapter != null) {
			if (position >= 0 && position <= adapter.getCount()) {
				adapter.notifyItemSelected(position);
				selectedIndex = position;
				setText(adapter.getItemContent(position));
			} else {
				throw new IllegalArgumentException("position out of array size!");
			}
		}
	}

	public void setAdapter(@NonNull YSpinnerBaseAdapter adapter) {
		selectedIndex = 0;
		this.adapter = adapter;
		listView.setAdapter(adapter);
		setText(adapter.getItemContent(selectedIndex).toString());
		adapter.notifyItemSelected(selectedIndex);
	}

	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			showPopupList(this);
		}
		return super.onTouchEvent(event);
	}

	// 可设置anchor的view
	public void showPopupList(View view) {
		if (!hideArrow) {
			arrowAnimation(true);
		}
		popupWindow.showAsDropDown(view);
	}

	public void dismissPopupList() {
		popupWindow.dismiss();
	}

	private void arrowAnimation(boolean open) {
		int start = open ? 0 : 10000;
		int end = open ? 10000 : 0;
		ObjectAnimator animator = ObjectAnimator.ofInt(arrow, "level", start, end);
		animator.setInterpolator(new LinearOutSlowInInterpolator());
		animator.start();
	}

	public void setListener(YSpinnerEventListener listener) {
		this.listener = listener;
	}

	public interface YSpinnerEventListener {
		void onItemClick(int position);

		void onItemSelected(int position);
	}
}
