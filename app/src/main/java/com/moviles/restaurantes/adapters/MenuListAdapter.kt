package com.moviles.restaurantes.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.moviles.restaurantes.databinding.UsermenuItemLayoutBinding
import com.moviles.restaurantes.models.Menu
import com.moviles.restaurantes.ui.activities.AddPlateActivity

class MenuListAdapter(private val menus: ArrayList<Menu>) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = UsermenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menus[position]
        holder.bind(menu)
    }

    fun updateData(newMenuList: List<Menu>?) {
        this.menus.clear()
        newMenuList?.let { this.menus.addAll(it) }
        notifyDataSetChanged()
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(menu: Menu) {
            UsermenuItemLayoutBinding.bind(itemView).apply {
                txtUserNombreMenu.text = menu.name

                btnAddPlate.setOnClickListener{
                    val intent = Intent(itemView.context, AddPlateActivity::class.java)
                    intent.putExtra("menu_id", menu.id)
                    itemView.context.startActivity(intent)

                }

            }

        }
    }

}



