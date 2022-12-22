package us.carleton.bat_shopping_list

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import us.carleton.bat_shopping_list.adapter.ShoppingAdapter
import us.carleton.bat_shopping_list.data.AppDatabase
import us.carleton.bat_shopping_list.data.ShoppingItem
import us.carleton.bat_shopping_list.databinding.ActivityScrollingBinding
import us.carleton.bat_shopping_list.dialog.ShoppingItemDialog
import us.carleton.bat_shopping_list.viewmodel.ShoppingViewModel
import kotlin.concurrent.thread

class ScrollingActivity : AppCompatActivity(), ShoppingItemDialog.ShoppingItemDialogHandler {

    companion object {
        const val KEY_ITEM_EDIT = "KEY_ITEM_EDIT"
        const val TAG_ITEM_EDIT = "TAG_ITEM_EDIT"
    }

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var adapter: ShoppingAdapter
    private lateinit var shoppingViewModel: ShoppingViewModel
    private var isShowingUnchecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shoppingViewModel = ViewModelProvider(this)[ShoppingViewModel::class.java]

        initRecyclerView()

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        val expand = AnimationUtils.loadAnimation(this, R.anim.expand)

        binding.fabAddItem.setOnClickListener { view ->
            binding.fabAddItem.startAnimation(expand)
            val shoppingItemDialog = ShoppingItemDialog()
            shoppingItemDialog.show(supportFragmentManager, getString(R.string.dialogTag))
        }

        binding.fabNotChecked.setOnClickListener { view ->
            binding.fabNotChecked.startAnimation(expand)
            if (isShowingUnchecked == false) {
                AppDatabase.getInstance(this@ScrollingActivity).shoppingItemDao().findUnchecked().observe(
                    this@ScrollingActivity, Observer { items ->
                        adapter.submitList(items)
                    })
                isShowingUnchecked = true
                Snackbar.make(
                    binding.root,
                    getString(R.string.message_not_purchased),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                AppDatabase.getInstance(this@ScrollingActivity).shoppingItemDao().getAllItems().observe(
                    this@ScrollingActivity, Observer { items ->
                        adapter.submitList(items)
                    })
                Snackbar.make(
                    binding.root,
                    getString(R.string.all_items),
                    Snackbar.LENGTH_LONG
                ).show()
                isShowingUnchecked = false
            }


        }

        binding.fabDeleteAllItems.setOnClickListener { view ->
            binding.fabDeleteAllItems.startAnimation(expand)
            shoppingViewModel.deleteAllItems()
        }
    }

    fun initRecyclerView() {
        adapter = ShoppingAdapter(this, shoppingViewModel)
        binding.recyclerShoppingList.adapter = adapter

        shoppingViewModel.allItems.observe(this) {items ->
            adapter.submitList(items)
        }
    }

    fun showEditDialog(itemToEdit: ShoppingItem) {
        val dialog = ShoppingItemDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_ITEM_EDIT, itemToEdit)
        dialog.arguments = bundle

        dialog.show(supportFragmentManager, TAG_ITEM_EDIT)
    }

    override fun shoppingItemCreated(shoppingItem: ShoppingItem) {
        shoppingViewModel.insertItem(shoppingItem)
        Snackbar.make(
            binding.root,
            "Shopping Item Added",
            Snackbar.LENGTH_LONG
        ).setAction("UNDO") {
            adapter.removeLastItem()
        }.show()
    }

    override fun shoppingItemUpdated(shoppingItem: ShoppingItem) {
        shoppingViewModel.updateItem(shoppingItem)
    }
}