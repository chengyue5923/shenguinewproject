package com.shengui.app.android.shengui.android.ui.utilsview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;


public class EditTextMultiLine extends LinearLayout {

    TextView contentCountTv;
   final EditText contentEditText;
    int maxLength = 140;


    public EditTextMultiLine(Context context) {
        this(context, null);
    }

    public EditTextMultiLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextMultiLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.edit_text_multi, this);

        contentCountTv = (TextView) findViewById(R.id.edit_text_max_length_tv);
        contentEditText = (EditText) findViewById(R.id.edit_text_content);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.EditTextMultiLine);


        
     	/*if(labelTextSize>18){
            labelTextSize = 18;
    	}*/


        String hint = attributes.getString(R.styleable.EditTextMultiLine_android_hint);
        if(!TextUtils.isEmpty(hint)){
        	contentEditText.setHint(hint);
        }
        contentEditText.setMinLines(attributes.getInt(R.styleable.EditTextMultiLine_android_minLines,4));
        float textSize = attributes.getDimension(R.styleable.EditTextMultiLine_android_textSize, getResources().getDimension(R.dimen.fiveteensp));
    	/*if(textSize>18){
    		textSize = 18;
    	}*/
        contentEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

//    	int mcolor = attributes.getColor(R.styleable.EditTextMultiLine_android_textColor, R.color.text);
//    	contentEditText.setTextColor(mcolor);

        int inputType = attributes.getInt(R.styleable.EditTextMultiLine_android_inputType, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        contentEditText.setInputType(inputType);

        maxLength = attributes.getInt(R.styleable.EditTextMultiLine_android_maxLength, -1);
        if (maxLength > 0) {
            contentCountTv.setVisibility(View.VISIBLE);
//            contentCountTv.setText("0/" + maxLength+"字");
            contentCountTv.setText("最多"+maxLength+"字");
            contentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            showContentCountLimit(maxLength);
        }
        attributes.recycle();
    }

    public void setText(CharSequence value) {
        if (TextUtils.isEmpty(value)) {
            if(value!=null){
                contentEditText.setText("");
                if (contentCountTv.getVisibility() == View.VISIBLE) {
//                    contentCountTv.setText("0/" + maxLength+"字");
                    contentCountTv.setText("最多"+maxLength+"字");
                }
            }
            return;
        }
        contentEditText.setText(value);
        if (contentCountTv.getVisibility() == View.VISIBLE) {
//            contentCountTv.setText(value.length() + "/" + maxLength+"字");
            contentCountTv.setText(value.length() +"字");
        }
        contentEditText.setSelection(value.length());
    }

    public Editable getText() {
        return contentEditText.getText();
    }

    public void clear() {
        contentEditText.setText("");
    }

    public EditText getContentEditText() {
        return contentEditText;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        contentEditText.addTextChangedListener(textWatcher);
    }


    public void showContentCountLimit(final int maxLength) {
        this.maxLength = maxLength;
        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
//                contentCountTv.setText(value.length() + "/" + maxLength+"字");
                contentCountTv.setText(value.length() +"字");
            }
        });
        contentCountTv.setVisibility(View.VISIBLE);
    }

}
