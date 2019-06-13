package com.liu.dell.huarongdao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

public class SelectActivity extends AppCompatActivity {

    public static final String EXTRA_TURN =
            "com.liu.dell.huangraodao.extra.TURN";

    public double windowWidth;
    public double windowHeight;
    public HashMap turnHashMap = new HashMap();
    public int turnAmount = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Intent intent = getIntent();
        windowWidth = Double.valueOf(intent.getStringExtra(MainActivity.EXTRA_WIDTH));
        windowHeight = Double.valueOf(intent.getStringExtra(MainActivity.EXTRA_HEIGHT));
        getTurnHashMap();
    }

    public void getTurnHashMap() {
        for(int i = 1; i <= turnAmount; i++) {
            int turnId = this.getResources().getIdentifier("turn"+String.valueOf(i),
                    "id", this.getPackageName());
            turnHashMap.put(turnId, "turn"+String.valueOf(i));
        }
    }

    public void backToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startGame(View view) {
        String stringId = (String) turnHashMap.get(view.getId());
        if (stringId.substring(stringId.length()-2, stringId.length()-1).equals("n")) {
            stringId = stringId.substring(stringId.length() - 1, stringId.length());
        } else {
            stringId = stringId.substring(stringId.length() - 2, stringId.length());
        }
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MainActivity.EXTRA_WIDTH, String.valueOf(windowWidth));
        intent.putExtra(MainActivity.EXTRA_HEIGHT, String.valueOf(windowHeight));
        intent.putExtra(EXTRA_TURN, stringId);
        startActivity(intent);
    }
}
