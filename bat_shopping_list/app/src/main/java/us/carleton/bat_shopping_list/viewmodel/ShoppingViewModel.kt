package us.carleton.bat_shopping_list.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import us.carleton.bat_shopping_list.data.AppDatabase
import us.carleton.bat_shopping_list.data.ShoppingItem
import us.carleton.bat_shopping_list.data.ShoppingItemDAO


class ShoppingViewModel(application: Application) :
    AndroidViewModel(application) {

    val allItems: LiveData<List<ShoppingItem>>

    private var shoppingItemDAO: ShoppingItemDAO

    init {
        shoppingItemDAO = AppDatabase.getInstance(application).
        shoppingItemDao()
        allItems = shoppingItemDAO.getAllItems()
    }

    fun insertItem(shoppingItem: ShoppingItem)  {
        Thread {
            shoppingItemDAO.addItem(shoppingItem)
        }.start()
    }

    fun deleteItem(shoppingItem: ShoppingItem) {
        Thread {
            shoppingItemDAO.deleteItem(shoppingItem)
        }.start()
    }

    fun deleteAllItems() {
        Thread {
            shoppingItemDAO.deleteAllItems()
        }.start()
    }

    fun updateItem(shoppingItem: ShoppingItem) {
        Thread {
            shoppingItemDAO.updateItem(shoppingItem)
        }.start()
    }
}