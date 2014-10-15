package com.sanrenx.funny;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingActivity extends Activity {

	private ImageView backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setUpViews();
	}

	public void setUpViews() {
		backBtn = (ImageView) findViewById(R.id.iv_back);
		backBtn.setOnClickListener(backClickListener);
	}

	OnClickListener backClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
}
