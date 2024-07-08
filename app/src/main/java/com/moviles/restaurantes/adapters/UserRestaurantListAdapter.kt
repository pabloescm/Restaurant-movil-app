package com.moviles.restaurantes.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moviles.restaurantes.databinding.UserItemLayoutBinding
import com.moviles.restaurantes.models.Restaurant
import com.moviles.restaurantes.repositories.RestaurantRepository
import com.moviles.restaurantes.repositories.SharedPrefManager
import com.moviles.restaurantes.ui.activities.AddMenuActivity
import com.moviles.restaurantes.ui.activities.AddRestaurantActivity
import com.moviles.restaurantes.ui.activities.MenuActivity
import com.moviles.restaurantes.ui.activities.ReservaActivity
import com.moviles.restaurantes.ui.activities.UploadLogoActivity
import com.moviles.viewmodels.UserViewModel
import kotlin.reflect.typeOf

class UserRestaurantListAdapter(
    private val restaurant: ArrayList<Restaurant>,
    private val model: UserViewModel
): RecyclerView.Adapter<UserRestaurantListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding.root,model)
    }

    override fun getItemCount(): Int {
        return restaurant.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val restaurant = restaurant[position]
        holder.bind(restaurant)
    }

    fun updateData(newRestaurantList: List<Restaurant>?) {
        this.restaurant.clear()
        newRestaurantList?.let { this.restaurant.addAll(it) }
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View,private val model: UserViewModel): RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: Restaurant) {
            UserItemLayoutBinding.bind(itemView).apply {
                txtUserRestaurantName.text = restaurant.name
                Glide.with(itemView.context).load(restaurant.logo).into(imageView)


                btnUserDelete.setOnClickListener {

                    val sharedPrefManager = SharedPrefManager(itemView.context)
                    val token = sharedPrefManager.getAccessToken()

                    if (token != null) {
                        RestaurantRepository.deleteRestaurant(
                            token,
                            restaurant.id!!,
                            success = {
                                Toast.makeText(itemView.context, "Restaurant deleted successfully", Toast.LENGTH_SHORT).show()

                                model.fetchUserRestaurants(itemView.context)
                            },
                            failure = { error ->
                                Toast.makeText(itemView.context, "Failed to delete restaurant: ${error.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(itemView.context, "Token is null", Toast.LENGTH_SHORT).show()
                    }
                }
                btnUserEdit.setOnClickListener {
                    val intent = Intent(itemView.context, AddRestaurantActivity::class.java)
                    intent.putExtra("restaurantId", restaurant.id)
                    intent.putExtra("isEditing", true)
                    intent.putExtra("restaurant", restaurant)
                    itemView.context.startActivity(intent)
                }
                btnUpload.setOnClickListener {
                    val intent = Intent(itemView.context, UploadLogoActivity::class.java)
                    intent.putExtra("restaurantId", restaurant.id)
                    itemView.context.startActivity(intent)
                }
                btnMenu.setOnClickListener {
                    //val restaurantId = restaurant.id
                    //Toast.makeText(itemView.context, "Restaurant ID in Adapter: $restaurantId", Toast.LENGTH_SHORT).show()
                   // val intent = Intent(itemView.context, AddMenuActivity::class.java)
                  //  intent.putExtra("restaurantId", restaurant.id)
                  //  itemView.context.startActivity(intent)
                    val intent = Intent(itemView.context, MenuActivity::class.java)
                    intent.putExtra("restaurantId", restaurant.id)
                    itemView.context.startActivity(intent)
                }
                btnReservation.setOnClickListener {
                    val intent = Intent(itemView.context, ReservaActivity::class.java)
                    intent.putExtra("restaurantId", restaurant.id)
                    itemView.context.startActivity(intent)
                }

            }
        }
    }
}