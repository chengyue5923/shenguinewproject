package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;

import java.util.ArrayList;
import java.util.HashMap;


import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageSendViewNew extends LinearLayout implements EmojiViewPageAdapter.EmojiSelectedDelegate {


    @Bind(R.id.audioBtn)
    ImageView audioBtn;
    @Bind(R.id.messageEditText)
    EditText messageEditText;
    @Bind(R.id.speakBtn)
    TextView speakBtn;
    @Bind(R.id.emojiBtn)
    ImageView emojiBtn;
    @Bind(R.id.photoBtn)
    ImageView photoBtn;
    @Bind(R.id.carmearBtn)
    ImageView carmearBtn;
    @Bind(R.id.moreLayout)
    LinearLayout moreLayout;
    @Bind(R.id.emojiPager)
    ViewPager emojiPager;
    @Bind(R.id.emojiPageControl)
    LinearLayout emojiPageControl;
    @Bind(R.id.emojiLayout)
    LinearLayout emojiLayout;
    public MessageSendViewDelegate mDelegate;
    @Bind(R.id.sendTextView)
    TextView sendTextView;
    @Bind(R.id.addBtn)
    ImageView addBtn;
    @Bind(R.id.mEmojiAndMoreContainer)
    RelativeLayout mEmojiAndMoreContainer;
    protected boolean mIsRecording = false;


    private Context con;
    private InputMethodManager methodManager;

    public MessageSendViewNew(Context context) {
        this(context, null);
        con=context;
    }

    public MessageSendViewNew(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
        con=context;
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_send_message, this);
        ButterKnife.bind(this, view);
        initMessageSendEmojiView(context);
        initEvent();
        con=context;
    }

    protected void showContainerSubView(boolean isEmoji) {

        if (isEmoji) {
//            messageEditText.requestFocus();
            emojiLayout.setVisibility(View.VISIBLE);
            moreLayout.setVisibility(View.INVISIBLE);
        } else {
            messageEditText.clearFocus();
            emojiLayout.setVisibility(View.INVISIBLE);
            moreLayout.setVisibility(View.VISIBLE);
        }
    }

    private void switchToolbar() {

        hideKeyboard();
        if (mEmojiAndMoreContainer.getVisibility() == View.VISIBLE) {
            mEmojiAndMoreContainer.setVisibility(View.GONE);
        }
        if (speakBtn.getVisibility() != VISIBLE) {
            messageEditText.setText("");
            speakBtn.setVisibility(VISIBLE);
            messageEditText.setVisibility(GONE);
            audioBtn.setImageResource(R.drawable.chat_change_keybord);
        } else {
            speakBtn.setVisibility(GONE);
            messageEditText.setVisibility(VISIBLE);
            audioBtn.setImageResource(R.drawable.chat_view_audio_view);
        }


        if (mDelegate != null) {
            mDelegate.touchSwitchButton();
        }
    }

    private void initEvent() {

        photoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDelegate != null)
                    mDelegate.touchLibraryPhotoButton();
            }
        });
        carmearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDelegate != null)
                    mDelegate.touchPickCameraPhotoButton();
            }
        });
        audioBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToolbar();
            }
        });
        messageEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && messageEditText.getText().length() > 0) {
                    switchMoreTextMessageSendButton(true);
                }
            }
        });
        sendTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMessage();
            }
        });
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, final int start, int before, final int count) {
                if (s.toString().length() > 0) {
                    switchMoreTextMessageSendButton(true);

                } else {
                    switchMoreTextMessageSendButton(false);
                }
                ;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        messageEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                messageEditText.requestFocus();

                moreLayout.setVisibility(View.GONE);
                emojiLayout.setVisibility(View.GONE);
                return false;
            }
        });

        messageEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    sendTextMessage();
                    return true;
                }
                return false;
            }
        });
        speakBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                try{


                    Logger.e("ecet-eeeeeeeeeeeeeeee---");
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mIsRecording = true;
                        speakBtn.setText(R.string.media_recording);
                        if (mDelegate != null) {
                            mDelegate.touchDownAudioRecordButton(event);
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (mDelegate != null) {
                            mDelegate.touchMoveAudioRecordButton(event);
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        speakBtn.setEnabled(false);
                        speakBtn.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                speakBtn.setText(R.string.hold_to_talk);
                                speakBtn.setBackgroundResource(R.drawable.messagesendview_avinput_bg_normal);
                                if (mDelegate != null) {
                                    mDelegate.touchUpAudioRecordButton(event);
                                }
                                speakBtn.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        mIsRecording = false;
                                        speakBtn.setEnabled(true);
                                    }

                                }, 200);

                            }
                        }, 500);
                    }
                }catch (Exception e){
                    Logger.e("ecet"+e.getMessage());
                }

                return true;
            }
        });
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                messageEditText.setVisibility(View.VISIBLE);
                speakBtn.setVisibility(View.GONE);
                mEmojiAndMoreContainer.setVisibility(View.VISIBLE);
                moreLayout.setVisibility(View.VISIBLE);
                emojiLayout.setVisibility(View.INVISIBLE);
                hideInput();
            }
        });
        emojiBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (mEmojiAndMoreContainer.getVisibility() == View.VISIBLE) {
                    if (emojiLayout.getVisibility() == View.INVISIBLE || emojiLayout.getVisibility() == View.GONE) {
                        showContainerSubView(true);
                    }
                } else {
                    hideKeyboardAndShowContainer(true);
                }
            }
        });
    }

    private void hideInput() {
        methodManager = ((InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow(((Activity) con).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void clickPerEmoji(String emoji, boolean isDeleteButton) {
        if (emoji.equals("-1")) {
            return;
        }

        String str = null;
        if (!isDeleteButton) {
            str = messageEditText.getText().toString() + EmojiParser.getInstance(getContext()).convertUnicode(emoji);
            messageEditText.setText(EmojiParser.getInstance(getContext()).convertToHtml(str, getContext()));
            messageEditText.setSelection(messageEditText.getText().toString().length());
            messageEditText.requestFocus();
        } else if (messageEditText.getText().length() > 0) {
            messageEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            messageEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
        }
    }

    protected void hideKeyboardAndShowContainer(final boolean isEmoji) {
        hideKeyboard();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmojiAndMoreContainer.setVisibility(View.VISIBLE);
                showContainerSubView(isEmoji);
            }
        }, 200);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(messageEditText.getWindowToken(), 0);
        mEmojiAndMoreContainer.setVisibility(View.GONE);
        messageEditText.clearFocus();
    }

    protected void switchMoreTextMessageSendButton(boolean hideMoreButton) {
        if (hideMoreButton) {
            addBtn.setVisibility(View.GONE);
            sendTextView.setVisibility(View.VISIBLE);
        } else {
            sendTextView.setVisibility(View.GONE);
            addBtn.setVisibility(View.VISIBLE);
        }
    }

    private void sendTextMessage() {
        String text = messageEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(text) && mDelegate != null) {
            mDelegate.touchSendTextMessageButton(text);
        }
        moreLayout.setVisibility(View.GONE);
        emojiLayout.setVisibility(View.GONE);
        messageEditText.setText("");
    }

    private void initMessageSendEmojiView(Context context) {
        HashMap<String, ArrayList<String>> emoMap = EmojiParser.getInstance(context).getEmoMap();
        ArrayList<String> emojiList = new ArrayList<>();
        getEmojiList(emojiList, emoMap.get("people"));
        getEmojiList(emojiList, emoMap.get("objects"));
        getEmojiList(emojiList, emoMap.get("nature"));
        getEmojiList(emojiList, emoMap.get("places"));
        getEmojiList(emojiList, emoMap.get("symbols"));

        EmojiViewPageAdapter adpter = new EmojiViewPageAdapter(context, emojiList, 7);
        emojiPager.setAdapter(adpter);
        adpter.setEmojiSelectDelegate(this);

        final ImageView[] pageControlImageViews = new ImageView[adpter.getPageViewSize()];

        for (int i = 0; i < adpter.getPageViewSize(); i++) {
            ImageView img = new ImageView(context);
            pageControlImageViews[i] = img;
            if (i == 0) {
                img.setBackgroundResource(R.drawable.starting_page_control_focused);
            } else {
                img.setBackgroundResource(R.drawable.starting_page_control_normal);
            }
            emojiPageControl.addView(img);

            LayoutParams imgLayoutParams = (LayoutParams) img.getLayoutParams();
            imgLayoutParams.leftMargin = 6;
            imgLayoutParams.rightMargin = 6;
            img.setLayoutParams(imgLayoutParams);
        }

        emojiPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < pageControlImageViews.length; i++) {
                    pageControlImageViews[arg0]
                            .setBackgroundResource(R.drawable.starting_page_control_focused);
                    if (arg0 != i) {
                        pageControlImageViews[i]
                                .setBackgroundResource(R.drawable.starting_page_control_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void getEmojiList(ArrayList<String> target, ArrayList<String> source) {
        if (target == null || source == null || source.size() == 0) {
            return;
        }

        for (String string : source) {
            target.add(string);
        }
    }

    public interface MessageSendViewDelegate {
        void touchSendTextMessageButton(String text);

        void touchSwitchButton();

        void touchPickCameraPhotoButton();

        void touchLibraryPhotoButton();

        void touchDownAudioRecordButton(MotionEvent event);

        void touchMoveAudioRecordButton(MotionEvent event);

        void touchUpAudioRecordButton(MotionEvent event);

    }

    public void setDelegate(MessageSendViewDelegate delegate) {
        mDelegate = delegate;
    }

}
