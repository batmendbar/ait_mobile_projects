package us.carleton.bat_shopping_list.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import us.carleton.bat_shopping_list.R
import us.carleton.bat_shopping_list.ScrollingActivity
import us.carleton.bat_shopping_list.data.AppDatabase
import us.carleton.bat_shopping_list.data.ShoppingItem
import us.carleton.bat_shopping_list.databinding.ShoppingItemRowBinding
import us.carleton.bat_shopping_list.viewmodel.ShoppingViewModel

class ShoppingAdapter(
    private val context: Context,
    private val shoppingViewModel: ShoppingViewModel
) : ListAdapter<ShoppingItem, ShoppingAdapter.ViewHolder> (ShoppingDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val shoppingItemRowBinding = ShoppingItemRowBinding.inflate(
            LayoutInflater.from(context),
            parent, false)
        return ViewHolder(shoppingItemRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentShoppingItem = getItem(holder.adapterPosition)
        holder.bind(currentShoppingItem)
    }

    fun removeLastItem () {
        val lastItem = getItem(currentList.lastIndex)
        shoppingViewModel.deleteItem(lastItem)
    }

    inner class ViewHolder(private var shoppingItemRowBinding : ShoppingItemRowBinding) :
        RecyclerView.ViewHolder(shoppingItemRowBinding.root)
    {
        fun bind(shoppingItem: ShoppingItem) {
            shoppingItemRowBinding.tvItemName.text = shoppingItem.name
            shoppingItemRowBinding.cbIsBought.isChecked = shoppingItem.isBought
            shoppingItemRowBinding.tvDescription.text = shoppingItem.description
            shoppingItemRowBinding.tvPrice.text = shoppingItem.price.toString()
            var categoryImage = R.drawable.add_shopping_cart
            when (shoppingItem.category) {
                context.getString(R.string.grocery) -> categoryImage = R.drawable.cat_grocery
                "Eat Out" -> categoryImage = R.drawable.cat_eat_out
                "Transportation" -> categoryImage = R.drawable.cat_transportation
            }
            shoppingItemRowBinding.ivActivityIcon.setImageResource(categoryImage)

            shoppingItemRowBinding.btnDelete.setOnClickListener {
                shoppingViewModel.deleteItem(shoppingItem)
            }

            shoppingItemRowBinding.btnEdit.setOnClickListener {
                (context as ScrollingActivity).showEditDialog(shoppingItem)
            }

            shoppingItemRowBinding.cbIsBought.setOnClickListener {
                shoppingItem.isBought = shoppingItemRowBinding.cbIsBought.isChecked
                shoppingViewModel.updateItem((shoppingItem))
            }
        }
    }
}

class ShoppingDiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {
    override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem._shoppingItemId == newItem._shoppingItemId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
        return oldItem == newItem
    }
}