package com.example.candy_halo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import com.example.candy_halo.databinding.ActivityCandyHaloBinding
import kotlin.random.Random

class Candy_Halo : AppCompatActivity() {
    lateinit var binding: ActivityCandyHaloBinding
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
