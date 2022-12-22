package us.carleton.bat_shopping_list.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingItemDAO {
    @Query("SELECT * FROM shoppingitem")
    fun getAllItems() : LiveData<List<ShoppingItem>>

    @Insert
    fun addItem(shoppingItem: ShoppingItem)

    @Delete
    fun deleteItem(shoppingItem: ShoppingItem)

    @Update
    fun updateItem(shoppingItem: ShoppingItem)

    @Query("DELETE FROM shoppingitem")
    fun deleteAllItems()

    @Query("SELECT * FROM shoppingitem WHERE isBought = 0")
    fun findUnchecked() : LiveData<List<ShoppingItem>>
}