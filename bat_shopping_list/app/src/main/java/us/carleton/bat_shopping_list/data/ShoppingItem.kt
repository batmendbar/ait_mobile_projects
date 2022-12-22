package us.carleton.bat_shopping_list.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingItem")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) var _shoppingItemId: Long?,
    @ColumnInfo(name = "itemName") var name: String,
    @ColumnInfo(name = "isBought") var isBought : Boolean,
    @ColumnInfo(name = "description") var description : String,
    @ColumnInfo(name = "price") var price : Float,
    @ColumnInfo(name = "category") var category : String
    ) : java.io.Serializable {

}