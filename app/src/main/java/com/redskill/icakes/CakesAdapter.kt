package com.redskill.icakes

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.redskill.icakes.databinding.ItemCakeBinding
import com.redskill.icakes.model.Cake
import com.squareup.picasso.Picasso

class CakesAdapter : ListAdapter<Cake, CakesAdapter.ViewHolder>(DiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCakeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.bind(getItem(position), context)
    }

    class ViewHolder(private val itemCakeBinding: ItemCakeBinding) :
        RecyclerView.ViewHolder(itemCakeBinding.root) {

        init {
            val fadeAnim = ObjectAnimator.ofFloat(itemCakeBinding.root,"alpha",0f,1f).apply { duration = 2500 }
            fadeAnim.start()
            //TODO Perhaps use an AnimatorSet instead to prevent the "laggy" effect of the list being empty and reappearing at startup
        }

        fun bind(cake: Cake, context: Context) {
            itemCakeBinding.cakeTitle.text = cake.title
            itemCakeBinding.root.setOnClickListener {
                createDialog(context, cake)
            }
            Picasso.get().load(cake.image).into(itemCakeBinding.cakeImage)
        }

        private fun createDialog(context: Context, cake: Cake) {
            val builder = AlertDialog.Builder(context)
            builder.apply {
                setTitle(cake.title)
                setMessage(cake.desc)
                setNegativeButton(context.getString(R.string.Cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
                setPositiveButton(context.getString(R.string.OK)) { dialogInterface, _ -> dialogInterface.dismiss() }
                show()
            }
        }
    }
}

class DiffUtils : DiffUtil.ItemCallback<Cake>() {
    override fun areItemsTheSame(oldItem: Cake, newItem: Cake): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Cake, newItem: Cake): Boolean {
        return oldItem == newItem
    }
}