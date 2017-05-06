package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GroupGridView extends GridView {
	public GroupGridView(Context context) {
		super(context); 
	} 
	public GroupGridView(Context context, AttributeSet attrs) {
		super(context, attrs); 
	} 

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec); 
	} 
}
