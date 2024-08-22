package com.eggbucket.eggbucket_android

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.eggbucket.eggbucket_android.adapters.OrdersAdapter
import com.eggbucket.eggbucket_android.model.allcustomer.CustomerDetailsItem
import com.eggbucket.eggbucket_android.model.allorders.GetAllOrdersItem
import com.eggbucket.eggbucket_android.model.data.OrderDetailsResponse
import com.eggbucket.eggbucket_android.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailsOutletActivity : AppCompatActivity() {
    private var orderId: String? = null
    private var trays: String? = null
    private var amount: String? = null
    private var name: String? = null
    private var createdAt: String? = null
    private var id: String? = null
    private var customerName:String?=null
    private var deliveryName:String?=null
    /*private var outletImage: String? = null
    private var customerImage: String? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details_outlet)
       val outletImage= findViewById<ImageView>(R.id.im_outlet_image)

        val customerImage= findViewById<ImageView>(R.id.im_customer_image)
        // Retrieve the order ID from intent or use a default one
        orderId = intent.getStringExtra("ORDER_ID")
        trays = intent.getStringExtra("TRAYS")
        amount = intent.getStringExtra("AMOUNT")
        name = intent.getStringExtra("NAME")
        createdAt = intent.getStringExtra("CREATED_AT")
        id = intent.getStringExtra("id")
        deliveryName=intent.getStringExtra("DELIVERY_NAME")
        customerName=intent.getStringExtra("CUSTOMER_NAME")


        //try {


        val customer = RetrofitInstance.api.getCustomerImageByID(id)
        customer.enqueue(object : Callback<ArrayList<CustomerDetailsItem>> {
            override fun onResponse(
                call: Call<ArrayList<CustomerDetailsItem>>,
                response: Response<ArrayList<CustomerDetailsItem>>
            ) {
                if (response.isSuccessful) {
                    val partner = response.body()
                    if (partner != null) {
                       // Picasso.get().load(partner.img).into(profileImageView)
                        for(list in partner){
                            if (id==list.customerId){
                                Picasso.get().load(list.img).into(customerImage)
                                Picasso.get().load(list.outlet.img).into(outletImage)
                            }
                        }

                        Log.d("Customer Image", "Success to load image")
                        //outletImage= partner[0].img
                       // customerImage=partner[0].outlet.img

                        /*for (list in partner){
                            if (id ==list.customerId){
                                outletImage=list.outlet.img
                                customerImage=list.img
                            }
                        }*/
                    }

                }
            }

            override fun onFailure(call: Call<ArrayList<CustomerDetailsItem>>, t: Throwable) {
                Log.d("Customer Image", "Failed to load image ${t.message}")
            }

        })


        /* }catch (e:Exception){
             //Toast.makeText(this@OrderDetailsOutletActivity,"api error ${e.message}",Toast.LENGTH_SHORT).show()
                Log.d("ApiCall error","problem ${e.message}")
             }    }*/


//        intent.putExtra("order_ID",orderId);
        populateOrderDetails()
    }

    fun populateOrderDetails() {
        // Update the UI elements with data
        findViewById<TextView>(R.id.orderIdTextView1).text = orderId
        findViewById<TextView>(R.id.numTraysTextView1).text = trays
        findViewById<TextView>(R.id.amountTextView1).text = "₹ $amount"
        findViewById<TextView>(R.id.vendorNameTextView1).text = name
        findViewById<TextView>(R.id.txt_order_created).text = "createdAt $createdAt"
        findViewById<TextView>(R.id.outlet_customer_name).text=customerName
        findViewById<TextView>(R.id.outlet_delivery_name).text=deliveryName



    }

}