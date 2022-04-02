package com.niksaen.pcsim.games.snake

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.niksaen.pcsim.R

class SnakeCoreKT(val activity: Activity, val field: FrameLayout) {
    enum class Direction{UP,RIGHT,BOTTOM,LEFT}
    private var currentDirection:Direction = Direction.BOTTOM;

    var delay:Long = 250;
    var difference:Int = 50;
    var size:Int = 50;
    var cellsCount:Int = 15;

    private var head:View;
    private var eat:ImageView;
    private var headTexture:Drawable?=activity.getDrawable(android.R.color.holo_blue_light);
    private var tailTexture:Drawable?=activity.getDrawable(android.R.color.holo_blue_dark);
    private var eatTexture:Drawable?=activity.getDrawable(android.R.color.holo_green_light);

    fun setHeadTexture(newHeadTexture:Drawable){
        this.head.background = newHeadTexture;
        this.headTexture = newHeadTexture;
    }
    fun setEatTexture(newEatTexture:Drawable){
        eat.setImageDrawable(newEatTexture);
        this.eatTexture = newEatTexture;
    }
    fun setTailTexture(newTailTexture:Drawable){
        this.tailTexture = newTailTexture;
    }

    var isPlaying:Boolean = true;
    var isLose:Boolean = false;
    val partOfTails = mutableListOf<PartOfTail>();
    private var thread: Thread;

    init {
        head = View(activity);
        head.background = headTexture;
        head.layoutParams = FrameLayout.LayoutParams(size,size);

        eat = ImageView(activity);
        eat.setImageDrawable(eatTexture);
        eat.layoutParams = FrameLayout.LayoutParams(size,size);

        field.addView(head);
        field.layoutParams.height = cellsCount*size;
        field.layoutParams.width = cellsCount*size;

        generateNewEat();

        thread = Thread{
            while (true){
                Thread.sleep(delay);
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
                            Direction.UP->layoutParams.topMargin -= difference;
                            Direction.LEFT->layoutParams.leftMargin -= difference;
                            Direction.RIGHT->layoutParams.leftMargin += difference;
                            Direction.BOTTOM->layoutParams.topMargin += difference;
                        }
                        checkIfSnakeEat();
                        field.removeView(head);
                        field.addView(head);
                    }
                }
            }
        }
    }
    fun start(){thread.start()}
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
        if(head.top<0 || head.left<0 || head.top>= size*cellsCount || head.left >= size*cellsCount){
            return true;
        }
        return false;
    }

    private var c = 1;
    private fun generateEatCoordinate():Coordinate{
        val coordinate = Coordinate(0,0);
        coordinate.top = (0 until cellsCount).random()*size;
        coordinate.left = (0 until cellsCount).random()*size;
        for(partTail in partOfTails){
            if(partTail.coordinate.left == coordinate.left && partTail.coordinate.left == coordinate.left){
                if(c == 9){
                    coordinate.top = (1 until cellsCount-1).random()*size;
                    coordinate.left = (1 until cellsCount-1).random()*size;
                    return coordinate
                }
                c++;
                return generateEatCoordinate();
            }
        }
        c = 1;
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

    private fun addPartOfTail(top:Int, left:Int){
        val tailPart = drawPartOfTail(top,left);
        partOfTails.add(PartOfTail(Coordinate(top, left),tailPart));
    }
    private fun drawPartOfTail(top:Int, left:Int): View {
        val tailImage = View(activity);
        tailImage.background = tailTexture;
        tailImage.layoutParams = FrameLayout.LayoutParams(size,size);
        (tailImage.layoutParams as FrameLayout.LayoutParams).topMargin = top;
        (tailImage.layoutParams as FrameLayout.LayoutParams).leftMargin = left;
        field.addView(tailImage);
        return tailImage;
    }
    private fun makeTailMove(headTop:Int, headLeft:Int){
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
        head.layoutParams = FrameLayout.LayoutParams(size,size);

        eat = ImageView(activity);
        eat.background =  eatTexture;
        eat.layoutParams = FrameLayout.LayoutParams(size,size);
        generateNewEat();

        field.addView(head);
        currentDirection = Direction.BOTTOM;
        isLose = false;
        isPlaying = true;
    }
}