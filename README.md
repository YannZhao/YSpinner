# 效果图如下

<img src="/performance.gif" width="50%" height="50%">

## 在xml中配置

```xml
<com.yspinner.widget.YSpinner
            android:id="@+id/spinner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textColor="@color/spinner_text_color" />
```

## 自定义adapter继承YSpinnerBaseAdapter
```java
//第一个泛型可传入任意自定义Object，此处用String测试
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
```

## 初始化数据进行测试
```java
String[] arr = new String[] { "one", "two", "three", "four", "five" };
		DemoDataBinding demoDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		DemoAdapter adapter = new DemoAdapter(this, R.layout.layout_spinner_item);
		adapter.setData(Arrays.asList(arr));
		demoDataBinding.spinner.setAdapter(adapter);
```


