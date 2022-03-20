package com.niksaen.pcsim.games.snake

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.niksaen.pcsim.R
import com.niksaen.pcsim.classes.Others

class SnakeCoreKT(val activity: Activity, val field: FrameLayout) {
    enum class Direction{UP,RIGHT,BOTTOM,LEFT;}
    var currentDirection:Direction = Direction.BOTTOM;

    val DIFFERENCE = 50;
    val SIZE:Int = 50;

    val head:View;
    val eat:View;

    var isPlaying:Boolean = true;
    init {
        head = View(activity);
        head.background = activity.getDrawable(R.drawable.snake_circle);
        head.layoutParams = FrameLayout.LayoutParams(SIZE,SIZE);

        eat = View(activity);
        eat.background =  activity.getDrawable(R.drawable.snake_eat);
        eat.layoutParams = FrameLayout.LayoutParams(SIZE,SIZE);

        field.addView(head);
        generateNewEat();
        start();
    }
    fun start(){
        Thread{
            while (true){
                Thread.sleep(250);
                activity.runOnUiThread {
                    if(isPlaying){
                        val layoutParams:FrameLayout.LayoutParams = head.layoutParams as FrameLayout.LayoutParams;
                        makeTailMove(layoutParams.topMargin,layoutParams.leftMargin);
                        when(currentDirection){
                            Direction.UP->layoutParams.topMargin -= DIFFERENCE;
                            Direction.LEFT->layoutParams.leftMargin -= DIFFERENCE;
                            Direction.RIGHT->layoutParams.leftMargin += DIFFERENCE;
                            Direction.BOTTOM->layoutParams.topMargin += DIFFERENCE;
                        }
                        checkIfSnakeEat();
                        field.removeView(head);
                        field.addView(head);
                    }
                }
            }
        }.start();
    }
    fun move(direction: Direction){
       currentDirection = direction;
    }
    fun checkIfSnakeEat(){
        if (head.left == eat.left && head.top == eat.top){
            addPartOfTail(eat.top,eat.left);
            generateNewEat();
        }
    }

    private fun generateNewEat() {
        val layoutParams:FrameLayout.LayoutParams = eat.layoutParams as FrameLayout.LayoutParams;
        layoutParams.topMargin = Others.random(0,10)*SIZE;
        layoutParams.leftMargin = Others.random(0,10)*SIZE;
        field.removeView(eat);
        field.addView(eat);
    }

    val partOfTails = mutableListOf<PartOfTail>()
    fun addPartOfTail(top:Int,left:Int){
        val tailPart = drawPartOfTail(top,left);
        partOfTails.add(PartOfTail(top,left,tailPart));
    }
    fun drawPartOfTail(top:Int,left:Int): View {
        val tailImage = View(activity);
        tailImage.background = activity.getDrawable(R.drawable.snake_tail);
        tailImage.layoutParams = FrameLayout.LayoutParams(SIZE,SIZE);
        (tailImage.layoutParams as FrameLayout.LayoutParams).topMargin = top;
        (tailImage.layoutParams as FrameLayout.LayoutParams).leftMargin = left;
        field.addView(tailImage);
        return tailImage;
    }
    fun makeTailMove(headTop:Int,headLeft:Int){
        var tempPart:PartOfTail? = null;
        for (index in 0 until partOfTails.size){
            val tailPart = partOfTails[index];
            field.removeView(tailPart.view);
            if(index == 0){
                tempPart = tailPart;
                partOfTails[index] = PartOfTail(headTop,headLeft,drawPartOfTail(headTop,headLeft));
            }else{
                val anotherTempPart:PartOfTail = partOfTails[index];
                tempPart?.let {
                    partOfTails[index] = PartOfTail(it.top,it.left,drawPartOfTail(it.top,it.left));
                }
                tempPart = anotherTempPart;
            }
        }
    }
}