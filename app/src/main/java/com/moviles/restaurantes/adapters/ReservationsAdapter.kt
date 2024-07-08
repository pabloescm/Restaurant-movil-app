package com.moviles.restaurantes.adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviles.restaurantes.databinding.ReservaItemLayoutBinding
import com.moviles.restaurantes.models.Reservation

class ReservationsAdapter(
    private val reservations: ArrayList<Reservation>
) : RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = ReservaItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.bind(reservation)
    }

    fun updateData(newReservations: List<Reservation>?) {
        this.reservations.clear()
        newReservations?.let { this.reservations.addAll(it) }
        notifyDataSetChanged()
    }

    class ReservationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(reservation: Reservation) {
            ReservaItemLayoutBinding.bind(itemView).apply {
                txtReservationDate.text = reservation.date
                txtReservationTime.text = reservation.time
                txtReservationPeople.text = reservation.people.toString()
                txtReservationStatus.text = reservation.status
            }
        }
    }
}