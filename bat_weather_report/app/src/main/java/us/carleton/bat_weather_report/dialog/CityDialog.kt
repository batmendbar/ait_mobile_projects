package us.carleton.bat_weather_report.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import us.carleton.bat_weather_report.data.City
import us.carleton.bat_weather_report.databinding.CityDialogBinding

class CityDialog : DialogFragment() {
    interface CityDialogHandler {
        public fun cityCreated(city: City)
    }

    lateinit var cityDialogHandler: CityDialogHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CityDialogHandler) {
            cityDialogHandler = context
        } else {
            throw java.lang.RuntimeException("Activity doesn't implement the CityDialogHandler")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Add New City")
        val dialogViewBinding = CityDialogBinding.inflate(requireActivity().layoutInflater)
        dialogBuilder.setView(dialogViewBinding.root)
        dialogBuilder.setPositiveButton("Add City") {
                dialog, which ->

            cityDialogHandler.cityCreated(
                City(
                    dialogViewBinding.etNewCityName.text.toString()
                )
            )
        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }
        return dialogBuilder.create()
    }
}