package com.hr.ui.view.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;

public class ValidDialog extends Dialog {
    private Context context;
    private ImageView  ivAutoCode;
    private TextView tvReflesh;
    private EditText etAutoCode;
    private RelativeLayout rlConfirm;
    private OnRefleshClickListener onRefleshClickListener;
    private OnConfirmListener onConfirmListener;

    public void setOnRefleshClickListener(OnRefleshClickListener onRefleshClickListener) {
        this.onRefleshClickListener = onRefleshClickListener;
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnRefleshClickListener{
        public void doReflesh();
    }
    public interface OnConfirmListener{
        public void onConfirm(String autoCode);
    }
    public  ValidDialog(Context context){
        super(context, R.style.MyDialog);
        this.context=context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    public void setImageBitMap(Bitmap bitMap){
        if(ivAutoCode!=null) {
            ivAutoCode.setImageBitmap(bitMap);
        }
    }
    public void setText(String text){
        if(etAutoCode!=null){
            etAutoCode.setText(text);
        }
    }
    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view=inflater.inflate(R.layout.layout_autocode,null);
        setContentView(view);
        ivAutoCode = view.findViewById(R.id.vc_image);
        tvReflesh = view.findViewById(R.id.vc_refresh);
        etAutoCode = view.findViewById(R.id.vc_code);
        rlConfirm = view.findViewById(R.id.rl__item_autocode_confirm);
        if(onConfirmListener!=null){
            rlConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String autoCodeText = etAutoCode.getText().toString();
                    if (autoCodeText != null && !"".equals(autoCodeText)) {
                        etAutoCode.setText("");
                        onConfirmListener.onConfirm(autoCodeText);
                    } else {
                        ToastUitl.show("请填写图形验证码", Toast.LENGTH_SHORT);
                    }
                }
            });
        }
        if(onRefleshClickListener!=null){
            tvReflesh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefleshClickListener.doReflesh();
                }
            });
        }
        setCanceledOnTouchOutside(true);
        Utils.setIM2(etAutoCode,context);
    }
}
