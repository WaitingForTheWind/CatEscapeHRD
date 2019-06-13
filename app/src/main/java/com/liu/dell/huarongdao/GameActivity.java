package com.liu.dell.huarongdao;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private static Context context;
    public double windowWidth;
    public double windowHeight;
    public double boardSize;
    public ImageButton blocks[] = new ImageButton[10];
    public ImageButton exits[] = new ImageButton[2];
    public ConstraintLayout constraintLayout;
    public boolean boardAvailable[][] = new boolean[5][4];
    public int dogAmount = 0;
    public int lionAmount = 0;
    public int stoneAmount = 0;
    float oldX, oldY, newX, newY, originX, originY;
    String directionOptions[] = {"right", "left", "up", "down"};
    String direction = "";
    int blockIndex;
    public Stack<MoveItem> moveHistory = new Stack<>();
    public BlockPosition[] currentBlockPosition = new BlockPosition[10];
    public HashMap idHashMap = new HashMap();
    public int step = 0;
    public TextView stepView;
    public int turnNumber = 0;
    public Timer mTimer;
    public TimerTask mTimerTask;
    public int times = 0;
    public TextView timeView;
    public TextView turnView;

    // 存储关卡
    public static BlockInfo blockInfo[][] = {
            {
                    new BlockInfo(1, 1, 1, 2), new BlockInfo(1, 2, 1, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 1, 1, 2),
                    new BlockInfo(3, 2, 1, 2), new BlockInfo(3, 3, 2, 2),
                    new BlockInfo(5, 1, 1, 1), new BlockInfo(5, 2, 1, 1),
                    new BlockInfo(5, 3, 1, 1), new BlockInfo(5, 4, 1, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 1), new BlockInfo(1, 2, 2, 1),
                    new BlockInfo(1, 4, 1, 1), new BlockInfo(2, 1, 1, 2),
                    new BlockInfo(2, 2, 2, 2), new BlockInfo(2, 4, 1, 2),
                    new BlockInfo(4, 1, 1, 2), new BlockInfo(4, 2, 1, 1),
                    new BlockInfo(4, 3, 1, 1), new BlockInfo(4, 4, 1, 2)
            },
            {
                    new BlockInfo(1, 1, 2, 2), new BlockInfo(1, 3, 2, 1),
                    new BlockInfo(2, 3, 2, 1), new BlockInfo(3, 3, 1, 1),
                    new BlockInfo(3, 4, 1, 1), new BlockInfo(4, 1, 1, 2),
                    new BlockInfo(4, 2, 1, 2), new BlockInfo(4, 3, 1, 1),
                    new BlockInfo(4, 4, 1, 2), new BlockInfo(5, 3, 1, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 2), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 1, 1, 2),
                    new BlockInfo(3, 2, 2, 1), new BlockInfo(4, 2, 1, 1),
                    new BlockInfo(4, 3, 1, 1), new BlockInfo(3, 4, 1, 2),
                    new BlockInfo(5, 1, 1, 1), new BlockInfo(5, 4, 1, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 2), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 1, 1, 1),
                    new BlockInfo(3, 2, 2, 1), new BlockInfo(3, 4, 1, 1),
                    new BlockInfo(4, 1, 1, 2), new BlockInfo(4, 2, 1, 1),
                    new BlockInfo(4, 3, 1, 1), new BlockInfo(4, 4, 1, 2)
            },
            {
                    new BlockInfo(1, 1, 1, 2), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 1, 1, 1),
                    new BlockInfo(3, 2, 1, 1), new BlockInfo(3, 3, 1, 1),
                    new BlockInfo(3, 4, 1, 1), new BlockInfo(4, 1, 1, 2),
                    new BlockInfo(4, 2, 2, 1), new BlockInfo(4, 4, 1, 2)
            },
            {
                    new BlockInfo(1, 1, 1, 1), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 1), new BlockInfo(2, 1, 1, 2),
                    new BlockInfo(3, 2, 2, 1), new BlockInfo(2, 4, 1, 2),
                    new BlockInfo(4, 1, 1, 2), new BlockInfo(4, 2, 1, 1),
                    new BlockInfo(4, 3, 1, 1), new BlockInfo(4, 4, 1, 2)
            },
            {
                    new BlockInfo(1, 1, 2, 2), new BlockInfo(1, 3, 1, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 1, 2, 1),
                    new BlockInfo(3, 3, 1, 1), new BlockInfo(3, 4, 1, 1),
                    new BlockInfo(4, 1, 1, 2), new BlockInfo(4, 2, 1, 2),
                    new BlockInfo(4, 3, 1, 1), new BlockInfo(4, 4, 1, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 1), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 1), new BlockInfo(2, 1, 1, 1),
                    new BlockInfo(2, 4, 1, 1), new BlockInfo(3, 1, 2, 1),
                    new BlockInfo(3, 3, 2, 1), new BlockInfo(4, 1, 2, 1),
                    new BlockInfo(4, 3, 2, 1), new BlockInfo(5, 3, 2, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 2), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 2, 1, 2),
                    new BlockInfo(3, 3, 1, 2), new BlockInfo(4, 1, 1, 1),
                    new BlockInfo(4, 4, 1, 1), new BlockInfo(5, 1, 1, 1),
                    new BlockInfo(5, 2, 2, 1), new BlockInfo(5, 4, 1, 1)
            },
            {
                    new BlockInfo(1, 1, 2, 2), new BlockInfo(1, 3, 2, 1),
                    new BlockInfo(2, 3, 1, 2), new BlockInfo(2, 4, 1, 1),
                    new BlockInfo(3, 1, 1, 2), new BlockInfo(3, 2, 1, 2),
                    new BlockInfo(3, 4, 1, 1), new BlockInfo(4, 3, 1, 1),
                    new BlockInfo(4, 4, 1, 1), new BlockInfo(5, 1, 2, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 2), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 1, 1,1),
                    new BlockInfo(3, 2, 1, 2), new BlockInfo(3, 4, 1, 1),
                    new BlockInfo(4, 1, 1, 1), new BlockInfo(4, 4, 1, 1),
                    new BlockInfo(5, 1, 2, 1), new BlockInfo(5, 3, 2, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 1), new BlockInfo(1, 2, 1, 2),
                    new BlockInfo(1, 3, 1, 2), new BlockInfo(1, 4, 1, 2),
                    new BlockInfo(2, 1, 1, 1), new BlockInfo(3, 1, 2, 1),
                    new BlockInfo(3, 3, 1, 1), new BlockInfo(3, 4, 1, 1),
                    new BlockInfo(4, 1, 2, 1), new BlockInfo(4, 3, 2, 2)
            },
            {
                    new BlockInfo(1, 1, 1, 1), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 1), new BlockInfo(2, 1, 1, 1),
                    new BlockInfo(2, 4, 1, 1), new BlockInfo(3, 1, 1, 2),
                    new BlockInfo(3, 2, 1, 2), new BlockInfo(3, 3, 2, 1),
                    new BlockInfo(4, 3, 2, 1), new BlockInfo(5, 1, 2, 1)
            },
            {
                    new BlockInfo(1, 1, 1, 2), new BlockInfo(1, 2, 2, 2),
                    new BlockInfo(1, 4, 1, 2), new BlockInfo(3, 1, 2, 1),
                    new BlockInfo(3, 3, 2, 1), new BlockInfo(4, 1, 1, 1),
                    new BlockInfo(4, 2, 2, 1), new BlockInfo(4, 4, 1, 1),
                    new BlockInfo(5, 1, 1, 1), new BlockInfo(5, 4, 1, 1)
            }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 更新计时器
                    times++;
                    timeView.setText("时间："+String.valueOf(times)+"秒");
                    break;
                default:
                    break;
            }
        }
    };

    // 移动历史项
    public class MoveItem {
        public int index;   // 移动block的index值
        public String direction; // 移动方向

        public MoveItem(int index, String direction) {
            this.index = index;
            this.direction = direction;
        }
    }

    // 滑块位置
    public class BlockPosition {
        public int row;
        public int column;

        public BlockPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    // 关卡信息
    public static class BlockInfo {
        public int row;
        public int column;
        public int width;
        public int height;

        public BlockInfo (int row, int column, int width, int height) {
            this.row = row;
            this.column = column;
            this.width = width;
            this.height = height;
        }
    }

    // 监听
    public class TouchBlock implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // 按下
                originX = oldX = event.getX() + v.getX();
                originY = oldY = event.getY() + v.getY();
                blockIndex = getBlockIndex(v);
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // 拖动
                onMove(v, event);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // 抬起
                newX = event.getX() + v.getX();
                newY = event.getY() + v.getY();
                if (direction.equals("")) {
                    // 未选定方向
                    if (!getDirection()) {
                        // 未成功判定方向，本次滑动失效
                        return true;
                    }
                }
                // 具有滑动方向，根据移动方向移动一格
                // 存储移动历史
                moveHistory.push(new MoveItem(blockIndex, direction));
                // 更新棋盘状态
                updateBoard(direction);
                // 移动滑块并更新滑块位置信息
                setEndBlockPosition(blockIndex, direction);
                step++;
                stepView.setText("步数："+String.valueOf(step)+"步");
                direction = "";
                if (checkSuccess()) {
                    new MyDialog(context, windowWidth, step, times, new MyDialog.LeaveMyDialogListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.button_next:
                                    startNextGame(view);
                                    break;
                            }
                        }
                    }).show();
                    // 停止计时器
                    mTimer.cancel();
                    mTimerTask.cancel();
                }
            }
            return true;
        }
    }

    // 判断移动方向，返回是否成功判断
    public boolean getDirection () {
        int row = currentBlockPosition[blockIndex].row;
        int column = currentBlockPosition[blockIndex].column;
        int width = blockInfo[turnNumber][blockIndex].width;
        int height = blockInfo[turnNumber][blockIndex].height;
        if (Math.abs(newX - oldX) > Math.abs(newY - oldY)) {
            // 横向移动
            if (Math.abs(newX - oldX) < 50) {
                return false;
            }
            if (newX > oldX) {
                // 向右
                for (int i = row; i < row + height; i++) {
                    if (column+width-1 >= 4 ||
                            !boardAvailable[i-1][column+width-1]) {
                        return false;
                    }
                }
                direction = directionOptions[0];
            } else {
                // 向左
                for (int i = row; i < row + height; i++) {
                    if (column-2 < 0 ||
                            !boardAvailable[i-1][column-2]) {
                        return false;
                    }
                }
                direction = directionOptions[1];
            }
        } else {
            // 纵向移动
            if (Math.abs(newY - oldY) < 50) {
                return false;
            }
            if (newY > oldY) {
                // 向下
                for (int i = column; i < column + width; i++) {
                    if (row+height-1 >= 5 ||
                            !boardAvailable[row+height-1][i-1]) {
                        return false;
                    }
                }
                direction = directionOptions[3];
            } else {
                // 向上
                for (int i = column; i < column + width; i++) {
                    if (row-2 < 0 ||
                            !boardAvailable[row-2][i-1]) {
                        return false;
                    }
                }
                direction = directionOptions[2];
            }
        }
        return true;
    }

    // 返回是否处理移动
    public boolean onMove(View v, MotionEvent event) {
        newX = event.getX() + v.getX();
        newY = event.getY() + v.getY();
        // 移动超出一格则不处理移动
        if (Math.abs(newX - originX) > boardSize ||
                Math.abs(newY - originY) > boardSize) {
            return false;
        }
        if (direction.equals("")) {
            if (!getDirection()) {
                // 未成功获取方向，不处理
                return false;
            }
            // 成功获取方向，移动block
            if (!moveBlock(v)) {
                // 未成功移动，原因为超出一格
                return false;
            }
        } else {
            if (!moveBlock(v)) {
                // 未成功移动，原因为超出一格
                return false;
            }
        }
        // 成功移动后，更新坐标值
        oldX = newX;
        oldY= newY;
        return true;
    }

    // 返回是否进行移动
    public boolean moveBlock(View v) {
        if (direction.equals(directionOptions[0]) ||
                direction.equals(directionOptions[1])) {
            // 横向移动
            if (Math.abs(newX - originX) > boardSize) {
                // 超出一格
                return false;
            }
            setMoveBlockPosition(v, newX-oldX, 0);
        } else {
            // 纵向移动
            if (Math.abs(newY - originY) > boardSize) {
                // 超出一格
                return false;
            }
            setMoveBlockPosition(v, 0, newY-oldY);
        }
        return true;
    }

    // 获取当前block的index值
    public int getBlockIndex(View v) {
        String stringId = (String) idHashMap.get(v.getId());
        stringId = stringId.substring(stringId.length() - 1, stringId.length());
        int intId = Integer.parseInt(stringId)-1;
        return intId == -1 ? 9 : intId;
    }

    // 设置block的位置
    private void setMoveBlockPosition (View v, float horizontal, float vertical) {
        v.setX(v.getX()+horizontal);
        v.setY(v.getY()+vertical);
    }

    // 设置block的位置
    private void setEndBlockPosition (int index, String direction) {
        if (direction.equals(directionOptions[0])) {
            // 向右
            currentBlockPosition[index].column++;
        } else if (direction.equals(directionOptions[1])) {
            // 向左
            currentBlockPosition[index].column--;
        } else if (direction.equals(directionOptions[2])) {
            // 向上
            currentBlockPosition[index].row--;
        } else {
            // 向下
            currentBlockPosition[index].row++;
        }
        blocks[index].setX((float)((currentBlockPosition[index].column - 1) * boardSize));
        blocks[index].setY((float)((currentBlockPosition[index].row - 1) * boardSize));
    }

    // 更新棋盘状态
    private void updateBoard(String direction) {
        if (direction.equals(directionOptions[0]) || direction.equals(directionOptions[2])) {
            topRightUpdateBoard(blockIndex, direction);
        } else {
            downLeftUpdateBoard(blockIndex, direction);
        }
    }

    // 向上或向右滑动后更新棋牌状态
    private void topRightUpdateBoard(int index, String direction) {
        int row = currentBlockPosition[index].row;
        int column = currentBlockPosition[index].column;
        int width = blockInfo[turnNumber][index].width;
        int height = blockInfo[turnNumber][index].height;
        for (int i = row; i < row + height; i++) {
            for (int j = column + width - 1; j >= column; j--) {
                boardAvailable[i-1][j-1] = true;
                if (direction.equals(directionOptions[0])) {
                    // 向右
                    boardAvailable[i-1][j] = false;
                } else if (direction.equals(directionOptions[2])) {
                    // 向上
                    boardAvailable[i-2][j-1] = false;
                }
            }
        }
    }

    // 向下或向左滑动后更新棋牌状态
    private void downLeftUpdateBoard(int index, String direction) {
        int row = currentBlockPosition[index].row;
        int column = currentBlockPosition[index].column;
        int width = blockInfo[turnNumber][index].width;
        int height = blockInfo[turnNumber][index].height;
        for (int i = row + height - 1; i>= row; i--) {
            for (int j = column; j < column + width; j++) {
                boardAvailable[i-1][j-1] = true;
                if (direction.equals(directionOptions[1])) {
                    // 向左
                    boardAvailable[i-1][j-2] = false;
                } else if (direction.equals(directionOptions[3])) {
                    // 向下
                    boardAvailable[i][j-1] = false;
                }
            }
        }
    }

    // 检查是否成功
    public boolean checkSuccess() {
        if (blockInfo[turnNumber][blockIndex].width == 2 &&
                blockInfo[turnNumber][blockIndex].height == 2) {
            if (currentBlockPosition[blockIndex].row == 4 &&
                    currentBlockPosition[blockIndex].column == 2) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 获取block对象
    private void getBlocks() {
        for(int i = 1; i <= 10; i++) {
            int blockId = this.getResources().getIdentifier("image"+String.valueOf(i),
                    "id", this.getPackageName());
            idHashMap.put(blockId, "image"+String.valueOf(i));
            blocks[i-1] = (ImageButton) findViewById(blockId);
        }
    }

    // 初始化棋盘状态
    private void initBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                boardAvailable[i][j] = true;
            }
        }
        int row;
        int column;
        int width;
        int height;
        for (int i = 0; i < 10; i++) {
            row = blockInfo[turnNumber][i].row;
            column = blockInfo[turnNumber][i].column;
            width = blockInfo[turnNumber][i].width;
            height = blockInfo[turnNumber][i].height;
            for (int j = row; j < row+height; j++) {
                for (int k = column; k < column+width; k++) {
                    boardAvailable[j-1][k-1] = false;
                }
            }
        }
    }

    // 初始化第i个块
    void initBlock (int index) {
        currentBlockPosition[index] = new BlockPosition(blockInfo[turnNumber][index].row, blockInfo[turnNumber][index].column);
        blocks[index].setX((float)((blockInfo[turnNumber][index].column - 1) * boardSize));
        blocks[index].setY((float)((blockInfo[turnNumber][index].row - 1) * boardSize));
        blocks[index].getLayoutParams().width = (int)(blockInfo[turnNumber][index].width * boardSize);
        blocks[index].getLayoutParams().height = (int)(blockInfo[turnNumber][index].height * boardSize);
        if (blockInfo[turnNumber][index].width == 1 && blockInfo[turnNumber][index].height == 1) {
            int imageId = this.getResources().getIdentifier(
                    "stone"+String.valueOf(++stoneAmount),
                    "drawable", this.getPackageName());
            blocks[index].setImageResource(imageId);
        } else if (blockInfo[turnNumber][index].width  == 1 && blockInfo[turnNumber][index].height == 2) {
            int imageId = this.getResources().getIdentifier(
                    "dog"+String.valueOf(++dogAmount),
                    "drawable", this.getPackageName());
            blocks[index].setImageResource(imageId);
        } else if (blockInfo[turnNumber][index].width  == 2 && blockInfo[turnNumber][index].height == 1) {
            int imageId = this.getResources().getIdentifier(
                    "lion"+String.valueOf(++lionAmount),
                    "drawable", this.getPackageName());
            blocks[index].setImageResource(imageId);
        } else if (blockInfo[turnNumber][index].width == 2 && blockInfo[turnNumber][index].height == 2) {
            int imageId = this.getResources().getIdentifier("cat", "drawable",
                    this.getPackageName());
            blocks[index].setImageResource(imageId);
        }
        blocks[index].setOnTouchListener(new TouchBlock());
    }

    public void initBlocks() {
        dogAmount = 0;
        lionAmount = 0;
        stoneAmount = 0;
        // 初始化块的状态
        for (int i = 0; i < 10; i++) {
            initBlock(i);
        }
    }

    public void initExit() {
        // 初始化LinearLayout
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.exit);
        LayoutParams params = linearLayout.getLayoutParams();
        params.width = (int)(0.8 * windowWidth);
        params.height = (int)(boardSize / 2);
        linearLayout.setX((float)(0.1 * windowWidth));
        linearLayout.setY((float)((windowHeight+windowWidth-boardSize/2)*0.5));
        // 初始化exit
        exits[0] = (ImageButton) findViewById(R.id.exitleft);
        exits[1] = (ImageButton) findViewById(R.id.exitright);
        exits[0].setX((float)boardSize);
        exits[0].setY(0);
        exits[1].setX((float)boardSize);
        exits[1].setY(0);
        exits[0].getLayoutParams().width = (int)(boardSize);
        exits[0].getLayoutParams().height = (int)(boardSize / 2);
        exits[1].getLayoutParams().width = (int)(boardSize);
        exits[1].getLayoutParams().height = (int)(boardSize / 2);
    }

    // 初始化操作栏
    public void initOperationButtons() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.operation_layout);
        LayoutParams params = linearLayout.getLayoutParams();
        params.height = (int)((windowHeight-windowWidth-boardSize/2)*0.5*0.8);
        linearLayout.setY((float)(windowHeight-params.height/8*9));
    }

    // 初始化步数时间显示区域
    public void initDisplayArea() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.display_layout);
        LayoutParams params = linearLayout.getLayoutParams();
        linearLayout.setY((float)((windowHeight-windowWidth-boardSize/2)*0.5*0.1));
        params.height = (int)((windowHeight-windowWidth-boardSize/2)*0.5*0.8);
        stepView = (TextView) findViewById(R.id.step_text);
        timeView = (TextView) findViewById(R.id.time_text);
        turnView = (TextView) findViewById(R.id.turn_text);
        turnView.setText("第"+String.valueOf(turnNumber+1)+"关");
        // 设置计时器
        mTimer = new Timer(true);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        };
        // 每1000ms执行 延迟0s
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    // 重置游戏
    public void restartGame(View v) {
        // 重置棋盘状态
        initBoard();
        // 重置blocks及位置
        initBlocks();
        // 重置历史记录
        while (!moveHistory.empty()) {
            moveHistory.pop();
        }
        // 重置步数
        step = 0;
        stepView.setText("步数："+String.valueOf(step)+"步");
        // 重置时间
        // 停止计时器
        mTimer.cancel();
        mTimerTask.cancel();
        times = 0;
        // 设置计时器
        mTimer = new Timer(true);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        };
        // 每1000ms执行 延迟0s
        mTimer.schedule(mTimerTask, 0, 1000);
        timeView.setText("时间：0秒");
    }

    // 上一关
    public void startLastGame(View view) {
        if (turnNumber == 0) {
            return;
        }
        turnNumber--;
        turnView.setText("第"+String.valueOf(turnNumber+1)+"关");
        restartGame(view);
    }

    // 下一关
    public void startNextGame(View view) {
        if (turnNumber == 14) {
            return;
        }
        turnNumber++;
        turnView.setText("第"+String.valueOf(turnNumber+1)+"关");
        restartGame(view);
    }

    // 回退
    public void revertOneStep(View v) {
        // 弹出上一移动状态
        if (moveHistory.empty()) {
            return;
        }
        MoveItem lastMove = moveHistory.pop();
        // 回退棋盘状态
        String direction;
        if (lastMove.direction.equals(directionOptions[0])) {
            // 向左
            direction = directionOptions[1];
            downLeftUpdateBoard(lastMove.index, direction);
        } else if (lastMove.direction.equals(directionOptions[1])) {
            // 向右
            direction = directionOptions[0];
            topRightUpdateBoard(lastMove.index, direction);
        } else if (lastMove.direction.equals(directionOptions[2])) {
            // 向下
            direction = directionOptions[3];
            downLeftUpdateBoard(lastMove.index, direction);
        } else {
            // 向上
            direction = directionOptions[2];
            topRightUpdateBoard(lastMove.index, direction);
        }
        // 回退步数
        step--;
        stepView.setText("步数："+String.valueOf(step)+"步");
        // 回退block位置及状态
        setEndBlockPosition(lastMove.index, direction);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context=this;
        Intent intent = getIntent();
        windowWidth = Double.valueOf(intent.getStringExtra(MainActivity.EXTRA_WIDTH));
        windowHeight = Double.valueOf(intent.getStringExtra(MainActivity.EXTRA_HEIGHT));
        turnNumber = Integer.valueOf(intent.getStringExtra(SelectActivity.EXTRA_TURN)) - 1;
        // 初始化棋盘
        constraintLayout = (ConstraintLayout) findViewById(R.id.board);
        LayoutParams params = constraintLayout.getLayoutParams();
        params.width = (int)(0.8 * windowWidth);
        params.height = (int)(windowWidth);
        boardSize = 0.8 * windowWidth / 4;
        constraintLayout.setX((float)(0.1 * windowWidth));
        constraintLayout.setY((float)((windowHeight-windowWidth-boardSize/2)*0.5));
        initBoard();
        // 初始化block
        getBlocks();
        initBlocks();
        initExit();
        initOperationButtons();
        initDisplayArea();
    }
}
