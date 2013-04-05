package com.mybringback.theworks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Numbers extends Activity implements OnClickListener {
	EditText number;
	Button sendInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numbers);
		number = (EditText) findViewById(R.id.editText1);
		sendInfo = (Button) findViewById(R.id.button1);
		sendInfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String s = number.getText().toString();
		Intent i = getIntent();
		String msg = i.getStringExtra("numbers");
		if (msg.contentEquals("width")) {
			i.putExtra("widthInfo", s);
			setResult(RESULT_OK, i);
			finish();
		}
		if (msg.contentEquals("height")) {
			i.putExtra("heightInfo", s);
			setResult(RESULT_OK, i);
			finish();
		}
	}
}
