package com.eos.hyfem;

import com.eos.hyfem.slidingmenu.SlidingMenuActivity;

import android.os.Bundle;

public class MainActivity extends SlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.initSildeMenu();
		this.testMenu();
	}
}