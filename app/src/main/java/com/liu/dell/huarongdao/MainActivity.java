package com.liu.dell.huarongdao;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_WIDTH =
            "com.liu.dell.huangraodao.extra.WIDTH";
    public static final String EXTRA_HEIGHT =
            "com.liu.dell.huangraodao.extra.HEIGHT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSelect(View view) {
         Rect rect = new Rect();
         getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
         Intent intent = new Intent(this, SelectActivity.class);
         intent.putExtra(EXTRA_WIDTH, String.valueOf(rect.right-rect.left));
         intent.putExtra(EXTRA_HEIGHT, String.valueOf(rect.bottom-rect.top));
         startActivity(intent);
    }

    public void toDescription(View view) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        startActivity(intent);
    }
}
