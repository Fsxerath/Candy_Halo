package com.example.candy_halo

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

open class Move (contex: Context?) : OnTouchListener{
    var detecter : GestureDetector
    override fun onTouch(v: View?, motion: MotionEvent): Boolean {
        return detecter.onTouchEvent(motion)
    }
    init {
        detecter = GestureDetector(contex,GestureListener())
    }
    private inner class GestureListener : SimpleOnGestureListener(){
        val Limit = 100
        val Speed_Limit = 100
        override fun  onDown(e: MotionEvent): Boolean{
            return true
        }
        override fun onFling(event1 : MotionEvent, event2 : MotionEvent, speed_X: Float, speed_Y: Float): Boolean{
            var flag = false
            val Diffy = event2.y - event1.y
            val Diffx = event2.x - event1.x
            if(Math.abs(Diffx) > Math.abs(Diffy)){
                if (Math.abs(Diffx) > Limit && Math.abs(speed_X) > Speed_Limit){
                    if (Diffx > 0){
                        onSwipeRigth()
                    }else{
                        onSwipeLeft()
                    }
                    flag = true
                }
            }
            else if(Math.abs(Diffy) > Limit && Math.abs(speed_Y) > Speed_Limit){
                if (Diffy > 0){
                    onSwipeDown()
                }else{
                    onSwipeTop()
                }
                flag = true
            }
            return flag
        }
    }

    open fun onSwipeLeft() {
    }
    open fun onSwipeRigth(){
    }
    open fun onSwipeTop(){
    }
    open fun onSwipeDown(){
    }


}