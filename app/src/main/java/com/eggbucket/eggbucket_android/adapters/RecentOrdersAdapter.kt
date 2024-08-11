import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eggbucket.eggbucket_android.R
import com.eggbucket.eggbucket_android.model.AllOrders

class RecentOrdersAdapter(private val orders: List<AllOrders>) : RecyclerView.Adapter<RecentOrdersAdapter.RecentOrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentOrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_outletorder, parent, false)
        return RecentOrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentOrdersViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class RecentOrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numTraysTextView: TextView = itemView.findViewById(R.id.order_trays)
        private val locationTextView: TextView = itemView.findViewById(R.id.order_location)
        private val statusTextView: TextView = itemView.findViewById(R.id.order_status)

        fun bind(order: AllOrders) {
            numTraysTextView.text = "${order.numTrays}"
            locationTextView.text = "${order.amount}" // Replace with actual location if available
            statusTextView.text = "${order.status}"
            when (order.status) {
                "pending" -> {statusTextView.setBackgroundResource(R.drawable.pending_back)}
                "completed" -> { statusTextView.setBackgroundResource(R.drawable.completed_back)}
                "intransit" -> { statusTextView.setBackgroundResource(R.drawable.intransit_bg)}
                "cancelled" -> { statusTextView.setBackgroundResource(R.drawable.cancelled_bg) }
            }
        }
    }
}
