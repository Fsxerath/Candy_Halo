package com.example.candy_halo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import com.example.candy_halo.databinding.ActivityCandyHaloBinding
import kotlin.random.Random

class Candy_Halo : AppCompatActivity() {
    private lateinit var binding: ActivityCandyHaloBinding
    private lateinit var mHand: Handler
    private val time: Int = 100
    private var points: Int = 0
    private val icons_img = listOf(
        R.drawable.arbitrer_icon,
        R.drawable.cortana_icon,
        R.drawable.emile_icon,
        R.drawable.legendary_icon,
        R.drawable.oracle_icon,
        R.drawable.masterchief_icon
    )
    private var widthofBlock: Int = 0
    private var noOfBlocks: Int = 8
    private var widthofScreen: Int = 0
    private var heightofScreen: Int = 0
    private val Icons_Halo = mutableListOf<ImageView>()
    private var icon_Move: Int = 0
    private var icon_Replace: Int = 0
    private var noIcon: Int = R.drawable.transparent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandyHaloBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val display = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)

        widthofScreen = display.widthPixels
        heightofScreen = display.heightPixels
        widthofBlock = widthofScreen / noOfBlocks
        board()
        AddListener()
        mHand = Handler()
        starCheck()
    }



    fun NewIcons(){
        val rows = listOf(0,1,2,3,4,5,6,7)
        for (i in 55 downTo 0){
            if(Icons_Halo[i+noOfBlocks].tag as Int == noIcon){

                Icons_Halo.get(i+noOfBlocks).setImageResource(Icons_Halo.get(i).getTag() as Int)
                Icons_Halo[i + noOfBlocks].tag = Icons_Halo[i].tag as Int
                Icons_Halo.get(i).setImageResource(noIcon)
                Icons_Halo.get(i).setTag(noIcon)

                if(rows.contains(i) && Icons_Halo.get(i).getTag() as Int == noIcon){
                    val ran: Int = Random.nextInt(0,icons_img.size)
                    Icons_Halo.get(i).setImageResource(icons_img.get(ran))
                    Icons_Halo.get(i).tag = icons_img.get(ran)
                }
            }
        }
        for(i in 0 until 8){
            if(Icons_Halo.get(i).getTag() == noIcon){
                val ran: Int = Random.nextInt(0,icons_img.size)
                Icons_Halo.get(i).setImageResource(icons_img.get(ran))
                Icons_Halo.get(i).tag = icons_img.get(ran)
            }
        }
    }

    fun columnThree(){
        for (i in 0..47){
            var icon_chose = Icons_Halo.get(i).getTag()
            var empty = Icons_Halo.get(i).getTag() == noIcon
            var x = i
            if (Icons_Halo.get(x).tag as Int == icon_chose && !empty
                && Icons_Halo.get(x+noOfBlocks).tag as Int == icon_chose &&
                Icons_Halo.get(x+2*noOfBlocks).getTag()==icon_chose)
            {
                points+=3
                binding.points.text = points.toString()
                Icons_Halo.get(x).setImageResource(noIcon)
                Icons_Halo.get(x).setTag(noIcon)
                x += noOfBlocks
                Icons_Halo.get(x).setImageResource(noIcon)
                Icons_Halo.get(x).setTag(noIcon)
                x += noOfBlocks
                Icons_Halo.get(x).setImageResource(noIcon)
                Icons_Halo.get(x).setTag(noIcon)
            }
        }
        NewIcons()
    }

    fun rowThree (){
        for (i in 0..62){
            var icon_chose = Icons_Halo.get(i).getTag()
            var empty = Icons_Halo.get(i).getTag() == noIcon
            val invalid= listOf(6,7,14,15,22,23,30,31,38,39,46,47,54,55)
            if(!invalid.contains(i)){
                var x = i
                if (Icons_Halo.get(x++).tag as Int == icon_chose && !empty
                    && Icons_Halo.get(x++).tag as Int == icon_chose &&
                    Icons_Halo.get(x).getTag()==icon_chose)
                {
                    points+=3
                    binding.points.text = points.toString()
                    Icons_Halo.get(x).setImageResource(noIcon)
                    Icons_Halo.get(x).setTag(noIcon)
                    x--
                    Icons_Halo.get(x).setImageResource(noIcon)
                    Icons_Halo.get(x).setTag(noIcon)
                    x--
                    Icons_Halo.get(x).setImageResource(noIcon)
                    Icons_Halo.get(x).setTag(noIcon)
                }
            }
        }
        NewIcons()
    }

    private var repeat : Runnable = Runnable {
        run {
            try {
                rowThree()
                columnThree()
                NewIcons()
            }finally {
                mHand.postDelayed(repeat, time.toLong())
            }
        }

    }
    fun starCheck(){
        repeat.run()
    }

    fun AddListener(){
        for (icon in Icons_Halo){
            icon.setOnTouchListener(object: Move(this){
               override fun onSwipeLeft(){
                  super.onSwipeLeft()
                   icon_Move = icon.id
                   icon_Replace = icon_Move - 1
                   icon_exchange()
              }

                override fun onSwipeRigth() {
                    super.onSwipeRigth()
                    icon_Move = icon.id
                    icon_Replace = icon_Move + 1
                    icon_exchange()
                }

                override fun onSwipeTop() {
                    super.onSwipeTop()
                    icon_Move = icon.id
                    icon_Replace = icon_Move - noOfBlocks
                    icon_exchange()
                }

                override fun onSwipeDown() {
                    super.onSwipeDown()
                    icon_Move = icon.id
                    icon_Replace = icon_Move + noOfBlocks
                    icon_exchange()
                }
            })
        }
    }

    fun icon_exchange(){
        var groud = Icons_Halo.get(icon_Replace).tag as Int
        var groud2= Icons_Halo.get(icon_Move).tag as Int

        Icons_Halo.get(icon_Move).setImageResource(groud)
        Icons_Halo.get(icon_Replace).setImageResource(groud2)
        Icons_Halo.get(icon_Move).tag = groud
        Icons_Halo.get(icon_Replace).tag = groud2

    }

    fun board (){
        binding.board.rowCount = noOfBlocks
        binding.board.columnCount = noOfBlocks
        binding.board.layoutParams.width = widthofScreen
        binding.board.layoutParams.height = widthofScreen
        for (i in 0..noOfBlocks*noOfBlocks){
            val icon = ImageView(this)
            icon.id = i
            icon.layoutParams = LayoutParams(widthofBlock, widthofBlock)
            icon.maxHeight = widthofBlock
            icon.maxWidth = widthofBlock
            var img = Random.nextInt(0 , icons_img.size)
            icon.setImageResource(icons_img[img])
            icon.setTag(icons_img[img])
            binding.board.addView(icon)
            Icons_Halo.add(icon)
        }
    }

}
