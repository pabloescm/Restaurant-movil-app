package com.moviles.restaurantes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviles.restaurantes.databinding.HomeItemLayoutBinding
import com.moviles.restaurantes.models.Restaurant

class HomeRestaurantListAdapter(
    private val restaurant: ArrayList<Restaurant>
): RecyclerView.Adapter<HomeRestaurantListAdapter.HomeViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            HomeItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return restaurant.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val restaurant = restaurant[position]
        holder.bind(restaurant)
    }

    fun updateData(newRestaurantList: List<Restaurant>?) {
        this.restaurant.clear()
        newRestaurantList?.let { this.restaurant.addAll(it) }
        notifyDataSetChanged()


    }

    class HomeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: Restaurant) {
            HomeItemLayoutBinding.bind(itemView).apply {
                txtHomeRestaurantName.text = restaurant.name
                txtHomeRestaurantAddress.text = restaurant.address
                txtHomeRestaurantCity.text = restaurant.city
                txtHomeRestaurantDescription.text = restaurant.description
                Glide.with(itemView.context).load(restaurant.logo).into(imgHomeRestaurantLogo)
            }
        }

    }
}
