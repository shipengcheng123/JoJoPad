package com.jojo.pad.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jojo.pad.R;
import com.jojo.pad.dialog.DatePickDialog;
import com.jojo.pad.listener.ViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/6 0006.
 */

public class EditTextItem extends LinearLayout {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_input)
    EditText editInput;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_input)
    LinearLayout llInput;
    @BindView(R.id.tv_checkname)
    TextView tvCheckname;
    @BindView(R.id.switch_state)
    SwitchCompat switchState;
    @BindView(R.id.fl_check)
    LinearLayout flCheck;
    @BindView(R.id.tv_date)
    ImageView tvDate;
    private Context context;


    private OnClickListener moreListener;
    private DatePickDialog datePickDialog;
    private ViewClickListener listener;

    public void setMoreListener(OnClickListener moreListener) {
        this.moreListener = moreListener;
    }

    public EditTextItem(Context context) {
        this(context, null);
    }

    public EditTextItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextItem, defStyleAttr, 0);

            String title = a.getString(R.styleable.EditTextItem_edittitle);
            String checkcontent = a.getString(R.styleable.EditTextItem_checkcontent);
            String morecontent = a.getString(R.styleable.EditTextItem_morecontent);
            int showcheck = a.getInt(R.styleable.EditTextItem_showcheck, GONE);
            int showomore = a.getInt(R.styleable.EditTextItem_showmore, GONE);
            int showodate = a.getInt(R.styleable.EditTextItem_editshowdate, GONE);
            boolean editable = a.getBoolean(R.styleable.EditTextItem_editable,true);

            if (!editable) {
                editInput.setCursorVisible(false);
                editInput.setFocusable(false);
                editInput.setFocusableInTouchMode(false);
            }

            tvTitle.setText(title);
            tvMore.setText(morecontent);
            tvCheckname.setText(checkcontent);
            if (showcheck == VISIBLE) {
                flCheck.setVisibility(VISIBLE);
                llInput.setVisibility(GONE);
            } else {
                flCheck.setVisibility(GONE);
                llInput.setVisibility(VISIBLE);
            }

            tvMore.setVisibility(showomore);
            tvDate.setVisibility(showodate);
        }
    }

    public String getEditTextValue() {
        return editInput.getText().toString();
    }
    public void setEditTextValue(String value) {
        editInput.setText(value);
    }

    public boolean getCheckBoxValue() {
        return switchState.isChecked();
    }


    private void initView() {
        View.inflate(context, R.layout.edittext_item_layout, this);
        ButterKnife.bind(this);

        if (moreListener != null) {
            tvMore.setOnClickListener(moreListener);
        }
        tvDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickDialog = new DatePickDialog.Builder(context,false).setListener(listener).setTitle("设置生日").create();
                datePickDialog.setCanceledOnTouchOutside(true);
                datePickDialog.show();
            }
        });

        listener = new ViewClickListener() {
            @Override
            public void clickListener(String msg, int type) {
                editInput.setText(msg);
            }
        };
    }
}
