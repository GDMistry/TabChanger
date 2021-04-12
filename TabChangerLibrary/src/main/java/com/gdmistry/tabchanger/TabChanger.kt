package com.gdmistry.tabchanger

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.gdmistry.tabchanger.databinding.ItemTabBinding

class TabChanger : LinearLayoutCompat {
    companion object {
        private const val TAG = "TabChanger"
    }

    var tabCount = 0
    var setImageAsResource = false
    var scaleType = ImageView.ScaleType.CENTER_INSIDE
    var firstTabToSelectIndex = 0 //Start from 0. When setting from implementation area, start from 1.
    private var textSize = 12f
    private var tabItemNameList: ArrayList<String>? = null
    private var tabItemImageList: ArrayList<Int>? = null
    private var tabChangedListener: TabChangedListener? = null
    private val tabItemViewList = ArrayList<ViewBinding>()
    private var selectedItemNameTextColor: Int = R.color.white
    private var deselectedItemNameTextColor: Int = R.color.black
    private var selectedTabColor: Int = R.color.black
    private var deselectedTabColor: Int = R.color.white
    private var selectedItemImageTint: Int? = null
    private var deselectedItemImageTint: Int? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    interface TabChangedListener {
        fun onTabChanged(position: Int)
    }

    fun addOnTabChangedListener(tabChangedListener: TabChangedListener) {
        this.tabChangedListener = tabChangedListener
    }

    fun setTabItemNames(tabItemNameList: ArrayList<String>) {
        this.tabItemNameList = tabItemNameList
    }

    fun setTabItemImages(tabItemImageList: ArrayList<Int>) {
        this.tabItemImageList = tabItemImageList
    }

    fun setSelectedTabTextColor(colorResId: Int) {
        selectedItemNameTextColor = colorResId
    }

    fun setDeselectedTabTextColor(colorResId: Int) {
        deselectedItemNameTextColor = colorResId
    }

    fun setSelectedTabColor(colorResId: Int) {
        selectedTabColor = colorResId
    }

    fun setDeselectedTabColor(colorResId: Int) {
        deselectedTabColor = colorResId
    }

    fun setSelectedTabImageTint(colorResId: Int) {
        selectedItemImageTint = colorResId
    }

    fun setDeselectedTabImageTint(colorResId: Int) {
        deselectedItemImageTint = colorResId
    }

    fun setTextSize(sizeInPxs: Float) {
        textSize = sizeInPxs
    }

    init {


    }

    /**
     * Call this function for initial setup with
     * tabCount or itemNameList or itemImageList
     */
    fun setup() {
        if (!tabItemNameList.isNullOrEmpty()) {
            tabCount = tabItemNameList!!.size
        } else if (!tabItemImageList.isNullOrEmpty()) {
            tabCount = tabItemImageList!!.size
        }

        if (tabCount == 0) {
            Log.e(TAG, "setup: Did you forget to initialize tabs")
            return
        }

        for (index in 0 until tabCount) {
            val binding = ItemTabBinding.inflate(LayoutInflater.from(context), this, true)
            val view = binding.root
            view.layoutParams.width = 0
            (view.layoutParams as LayoutParams).weight = 1f
            view.setOnClickListener {
                selectTab(index, true)
            }

            if (!tabItemImageList.isNullOrEmpty()) {
                if (setImageAsResource) {
                    binding.ivIcon.scaleType = scaleType
                    binding.ivIcon.setImageResource(tabItemImageList!![index])
                } else {
                    binding.ivIcon.background =
                        ResourcesCompat.getDrawable(resources, tabItemImageList!![index], null)
                }
            } else {
                binding.ivIcon.visibility = View.GONE
            }

            if (!tabItemNameList.isNullOrEmpty()) {
                binding.tvItemName.text = tabItemNameList!![index]
                binding.tvItemName.textSize = textSize
                binding.tvItemName.setTextColor(
                    ContextCompat.getColor(
                        context,
                        deselectedItemNameTextColor
                    )
                )
            } else {
                binding.tvItemName.visibility = View.GONE
            }

            tabItemViewList.add(binding)
        }

        //Select the first tab by default
        if (firstTabToSelectIndex > tabCount) {
            Log.e(TAG, "setup: First tab to select index cannot be greater than tab count")
            return
        }

        selectTab(firstTabToSelectIndex - 1, false)
    }

    fun selectTab(position: Int, shouldCallListener: Boolean) {
        for (index in tabItemViewList.indices) {
            manageTabChangeAction(
                index,
                deselectedTabColor,
                deselectedItemNameTextColor,
                deselectedItemImageTint
            )

            if (index == position) {
                manageTabChangeAction(
                    position,
                    selectedTabColor,
                    selectedItemNameTextColor,
                    selectedItemImageTint
                )
            }
        }

        if (shouldCallListener) {
            tabChangedListener?.onTabChanged(position)
            Log.i(TAG, "selectTab: TabChangedListener called")
        }
    }

    private fun manageTabChangeAction(
        position: Int,
        itemColor: Int,
        itemNameColor: Int,
        itemImageTint: Int?
    ) {
        val rootView = tabItemViewList[position] as ItemTabBinding
        if (rootView.tvItemName.visibility == View.VISIBLE) {
            rootView.tvItemName.setTextColor(
                ContextCompat.getColor(
                    context,
                    itemNameColor
                )
            )
        }

        if (itemImageTint != null) {
            rootView.ivIcon.backgroundTintList =
                ResourcesCompat.getColorStateList(resources, itemImageTint, null)
        } else {
            rootView.ivIcon.backgroundTintList = null
        }
        rootView.root.setBackgroundColor(itemColor)
    }
}