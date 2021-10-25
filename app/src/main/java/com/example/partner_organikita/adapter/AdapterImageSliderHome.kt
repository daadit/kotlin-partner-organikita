package com.example.partner_organikita.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.partner_organikita.R
import com.example.partner_organikita.model.ImageSliderHomeModel
import com.makeramen.roundedimageview.RoundedImageView

class AdapterImageSliderHome internal constructor(
    private val sliderItems: List<ImageSliderHomeModel>,
    sliderItem: MutableList<ImageSliderHomeModel>,
    viewPager: ViewPager2
): RecyclerView.Adapter<AdapterImageSliderHome.SliderViewHolder>(){

    private val viewPager2: ViewPager2

    init {
        this.viewPager2 = viewPager
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageView: RoundedImageView = itemView.findViewById(R.id.imageSlide)

        fun image(sliderItem: ImageSliderHomeModel){
            imageView.setImageResource(sliderItem.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slider_item_home,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.image(sliderItems[position])
        if(position == sliderItems.size - 2){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    private val runnable = Runnable {
        sliderItem.addAll(sliderItems)
        notifyDataSetChanged()
    }
}