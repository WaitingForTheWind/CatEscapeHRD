package com.liu.dell.huarongdao;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialog extends Dialog implements android.view.View.OnClickListener {

    public double windowWidth;
    public int step;
    public int times;
    public TextView content1;
    public TextView content2;
    public Context context;
    private LeaveMyDialogListener listener;

    public interface LeaveMyDialogListener{
        public void onClick(View view);
    }

    public MyDialog(Context context, double width, int step, int times, LeaveMyDialogListener listener) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.windowWidth = width;
        this.step = step;
        this.times = times;
        this.listener = listener;
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = (int)(windowWidth * 0.8);
        layoutParams.height = layoutParams.width;
        dialogWindow.setAttributes(layoutParams);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_animation, null);
        ImageView passPicture = (ImageView) mView.findViewById(R.id.pass_picture);
        passPicture.setX((float)(windowWidth * 0.8 * 0.3));
        passPicture.setY(20);
        passPicture.getLayoutParams().width = (int)(windowWidth * 0.8 * 0.4);
        passPicture.getLayoutParams().height = (int)(windowWidth * 0.8 * 0.4);
        Button cancelButton = (Button) mView.findViewById(R.id.button_cancel);
        Button nextButton = (Button) mView.findViewById(R.id.button_next);
        cancelButton.getLayoutParams().width = (int)(0.2 * windowWidth);
        cancelButton.getLayoutParams().height = (int)(0.1 * windowWidth);
        nextButton.getLayoutParams().width = (int)(0.2 * windowWidth);
        nextButton.getLayoutParams().height = (int)(0.1 * windowWidth);
        cancelButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        this.setCanceledOnTouchOutside(false);
        // 初始化内容
        content1 = (TextView) mView.findViewById(R.id.pass_content1);
        content2 = (TextView) mView.findViewById(R.id.pass_content2);
        content1.setText("用时："+String.valueOf(times)+"秒");
        content2.setText("步数："+String.valueOf(step)+"步");
        super.setContentView(mView);
    }

    @Override
    public void onClick(View v) {
        MyDialog.this.dismiss();
        listener.onClick(v);
    }
}
