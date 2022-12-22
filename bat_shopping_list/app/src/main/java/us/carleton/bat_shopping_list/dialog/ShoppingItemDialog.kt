package us.carleton.bat_shopping_list.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import us.carleton.bat_shopping_list.R
import us.carleton.bat_shopping_list.ScrollingActivity
import us.carleton.bat_shopping_list.data.ShoppingItem
import us.carleton.bat_shopping_list.databinding.ShoppingItemDialogBinding

class ShoppingItemDialog : DialogFragment() {
    interface ShoppingItemDialogHandler {
        public fun shoppingItemCreated(shoppingItem: ShoppingItem)
        public fun shoppingItemUpdated(shoppingItem: ShoppingItem)
    }

    lateinit var shoppingItemDialogHandler: ShoppingItemDialogHandler
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ShoppingItemDialogHandler) {
            shoppingItemDialogHandler = context
        } else {
            throw java.lang.RuntimeException(getString(R.string.dialog_error))
        }
    }

    private var isEditMode = false
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        if (arguments != null && requireArguments().containsKey(
                ScrollingActivity.KEY_ITEM_EDIT)) {
            isEditMode = true
            dialogBuilder.setTitle(getString(R.string.edit_item))
        } else {
            isEditMode = false
            dialogBuilder.setTitle(getString(R.string.new_item))
        }

        val dialogViewBinding = ShoppingItemDialogBinding.inflate(
            requireActivity().layoutInflater)
        dialogBuilder.setView(dialogViewBinding.root)

        if (isEditMode) {
            val itemToEdit =
                requireArguments().getSerializable(
                    ScrollingActivity.KEY_ITEM_EDIT) as ShoppingItem

            dialogViewBinding.etShoppingItemName.setText(itemToEdit.name)
            dialogViewBinding.etDescription.setText(itemToEdit.description)
            dialogViewBinding.etPrice.setText(itemToEdit.price.toString())
            dialogViewBinding.cbIsBought.isChecked = itemToEdit.isBought
        }

        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->

            if (isEditMode) {
                val itemToEdit =
                    (requireArguments().getSerializable(ScrollingActivity.KEY_ITEM_EDIT) as ShoppingItem).copy(
                        name = dialogViewBinding.etShoppingItemName.text.toString(),
                        description = dialogViewBinding.etDescription.text.toString(),
                        price = dialogViewBinding.etPrice.text.toString().toFloat(),
                        isBought = dialogViewBinding.cbIsBought.isChecked,
                        category = dialogViewBinding.sCategory.selectedItem.toString()
                    )
                shoppingItemDialogHandler.shoppingItemUpdated(itemToEdit)
            } else {
                shoppingItemDialogHandler.shoppingItemCreated(
                    ShoppingItem(
                        null,
                        dialogViewBinding.etShoppingItemName.text.toString(),
                        dialogViewBinding.cbIsBought.isChecked,
                        dialogViewBinding.etDescription.text.toString(),
                        dialogViewBinding.etPrice.text.toString().toFloat(),
                        dialogViewBinding.sCategory.selectedItem.toString()
                    )
                )
            }
        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }

        return dialogBuilder.create()
    }
}