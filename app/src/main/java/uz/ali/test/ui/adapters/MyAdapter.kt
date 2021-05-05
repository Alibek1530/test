package uz.ali.test.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.ali.test.R
import uz.ali.test.databese.DataModel
import uz.ali.test.models.characters.Result
import uz.ali.test.network.NetworkClient
import uz.ali.test.network.NetworkInterfase
import uz.ali.test.utils.FilterStatus
import java.lang.Exception


class MyAdapter(
   private var listt: ArrayList<DataModel>,
   private var listener: OnItemClickedListener
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
     var filterStatus:FilterStatus

    init {
        filterStatus= FilterStatus()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.imageView.setOnClickListener {
            listener.itemClicked(listt[position].id.toString(),listt[position].episode)
        }
        holder.bind(listt[position])



    }

    fun addList(list: List<DataModel>) {
        var lastCount = itemCount
        listt.addAll(list)
        notifyItemRangeInserted(lastCount, listt.size)
    }
    fun addListOffline(list: List<DataModel>) {
        listt.clear()
        listt.addAll(list)
        notifyItemRangeInserted(0, listt.size)
    }

    override fun getItemCount(): Int {
        return listt!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        var text1: TextView
        var status: CardView
        var text2: TextView

        var text21: TextView

        var text4: TextView
        var text6: TextView

        init {
            imageView = view.findViewById(R.id.item_image)
            text1 = view.findViewById(R.id.item_1)
            status = view.findViewById(R.id.item_online)
            text2 = view.findViewById(R.id.item_2)
            text21 = view.findViewById(R.id.item_21)
            text4 = view.findViewById(R.id.item_4)
            text6 = view.findViewById(R.id.item_6)
        }
        fun bind(result: DataModel){
            text1.text=result.name

            status.setCardBackgroundColor(Color.parseColor(filterStatus.getStatus(result.status)))
            text2.text=result.status
            text21.text="- "+result.species


            text4.text=result.locationname

            text6.text=result.created


            Picasso.get()
                .load(result.image)
                .into(imageView,object :Callback{
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        imageView.setImageResource(R.drawable.error)
                    }

                })
        }

    }

    interface OnItemClickedListener {
        fun itemClicked(position: String,episod0:String)
    }
}