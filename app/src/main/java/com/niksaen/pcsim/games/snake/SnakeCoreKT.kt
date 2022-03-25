package com.niksaen.pcsim.games.snake

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.FrameLayout
import com.niksaen.pcsim.R

class SnakeCoreKT(val activity: Activity, val field: FrameLayout) {
    enum class Direction{UP,RIGHT,BOTTOM,LEFT;}
    private var currentDirection:Direction = Direction.BOTTOM;

    var Delay:Long = 250;
    var Difference:Int = 50;
    var Size:Int = 50;
    var CellsCount:Int = 15;

    private var head:View;
    private var eat:View;
    var headTexture:Drawable?=activity.getDrawable(android.R.color.holo_blue_light);
    var tailTexture:Drawable?=activity.getDrawable(android.R.color.holo_blue_dark);
    var eatTexture:Drawable?=activity.getDrawable(android.R.color.holo_green_light);

    var isPlaying:Boolean = true;
    var isLose:Boolean = false;
    val partOfTails = mutableListOf<PartOfTail>();
    private var thread: Thread;

    init {
        head = View(activity);
        head.background = headTexture;
        head.layoutParams = FrameLayout.LayoutParams(Size,Size);

        eat = View(activity);
        eat.background =  eatTexture;
        eat.layoutParams = FrameLayout.LayoutParams(Size,Size);

        field.addView(head);
        field.layoutParams.height = CellsCount*Size;
        field.layoutParams.width = CellsCount*Size;

        generateNewEat();

        thread = Thread{
            while (true){
                Thread.sleep(Delay);
                activity.runOnUiThread {
                    if(checkIfSnakeSmash(head)){
                        isLose = true;
                        isPlaying = false;
                        return@runOnUiThread;
                    }
                    if(isPlaying){
                        val layoutParams:FrameLayout.LayoutParams = head.layoutParams as FrameLayout.LayoutParams;
                        makeTailMove(layoutParams.topMargin,layoutParams.leftMargin);
                        when(currentDirection){
                            Direction.UP->layoutParams.topMargin -= Difference;
                            Direction.LEFT->layoutParams.leftMargin -= Difference;
                            Direction.RIGHT->layoutParams.leftMargin += Difference;
                            Direction.BOTTOM->layoutParams.topMargin += Difference;
                        }
                        checkIfSnakeEat();
                        field.removeView(head);
                        field.addView(head);
                    }
                }
            }
        }
        thread.start();
    }
    fun move(direction: Direction){
        if(partOfTails.size > 0) {
            if (direction == Direction.UP && currentDirection != Direction.BOTTOM ||
                direction == Direction.BOTTOM && currentDirection != Direction.UP ||
                direction == Direction.LEFT && currentDirection != Direction.RIGHT ||
                direction == Direction.RIGHT && currentDirection != Direction.LEFT) {
                currentDirection = direction
            }
        }else{
            currentDirection = direction;
        }
    }
    fun checkIfSnakeEat(){
        if (head.left == eat.left && head.top == eat.top){
            addPartOfTail(eat.top,eat.left);
            generateNewEat();
        }
    }
    private fun checkIfSnakeSmash(head:View): Boolean{
        for(part in partOfTails){
            if(part.coordinate.left == head.left && part.coordinate.top == head.top){
                return true;
            }
        }
        if(head.top<0 || head.left<0 || head.top>= Size*CellsCount || head.left >= Size*CellsCount){
            return true;
        }
        return false;
    }

    private fun generateEatCoordinate():Coordinate{
        val coordinate = Coordinate(0,0);
        coordinate.top = (0 until CellsCount).random()*Size;
        coordinate.left = (0 until CellsCount).random()*Size;
        for(partTail in partOfTails){
            if(partTail.coordinate.left == coordinate.left && partTail.coordinate.left == coordinate.left){
                return generateEatCoordinate();
            }
        }
        return coordinate;
    }
    private fun generateNewEat() {
        val coordinate = generateEatCoordinate();
        val layoutParams:FrameLayout.LayoutParams = eat.layoutParams as FrameLayout.LayoutParams;
        layoutParams.topMargin = coordinate.top;
        layoutParams.leftMargin = coordinate.left;
        field.removeView(eat);
        field.addView(eat);
    }

    fun addPartOfTail(top:Int,left:Int){
        val tailPart = drawPartOfTail(top,left);
        partOfTails.add(PartOfTail(Coordinate(top, left),tailPart));
    }
    fun drawPartOfTail(top:Int,left:Int): View {
        val tailImage = View(activity);
        tailImage.background = tailTexture;
        tailImage.layoutParams = FrameLayout.LayoutParams(Size,Size);
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
                partOfTails[index] = PartOfTail(Coordinate(headTop,headLeft),drawPartOfTail(headTop,headLeft));
            }else{
                val anotherTempPart:PartOfTail = partOfTails[index];
                tempPart?.let {
                    partOfTails[index] = PartOfTail(Coordinate(it.coordinate.top,it.coordinate.left),drawPartOfTail(it.coordinate.top,it.coordinate.left));
                }
                tempPart = anotherTempPart;
            }
        }
    }
    fun restart(){
        partOfTails.clear();
        field.removeAllViews();
        head = View(activity);
        head.background = headTexture;
        head.layoutParams = FrameLayout.LayoutParams(Size,Size);

        eat = View(activity);
        eat.background =  eatTexture;
        eat.layoutParams = FrameLayout.LayoutParams(Size,Size);
        generateNewEat();

        field.addView(head);
        currentDirection = Direction.BOTTOM;
        isLose = false;
        isPlaying = true;
    }
}