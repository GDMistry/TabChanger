package com.gdmistry.tabchanger

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.gdmistry.tabchanger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabChanger.setTabItemNames(ArrayList<String>().apply {
            add("Cold")
            add("Hot")
            add("Dry")
        })
        binding.tabChanger.setTabItemImages(ArrayList<Int>().apply {
            add(R.drawable.ic_arrow_back_black)
            add(R.drawable.ic_arrow_back_black)
            add(R.drawable.ic_arrow_back_black)
        })
        binding.tabChanger.setImageAsResource = true
        binding.tabChanger.scaleType = ImageView.ScaleType.CENTER_INSIDE
        binding.tabChanger.firstTabToSelectIndex = 3
        binding.tabChanger.setup()
    }
}