package com.openclassrooms.realestatemanager.feature.editrealty


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.Photo
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.utils.onSelected
import com.openclassrooms.realestatemanager.utils.setText
import com.openclassrooms.realestatemanager.utils.toEditable
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.fragment_edit_realty.*
import kotlinx.android.synthetic.main.fragment_edit_realty.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

//TODO: Add way to remove photo
//TODO: Add way to add/change name of photo
//TODO: Add confirmation: message if everything went fine at save
//TODO: Handle dollar/euro switch

class EditRealtyFragment : Fragment() {

    val REQUEST_IMAGE_CAPTURE = 1

    private val editRealtyViewModel: EditRealtyViewModel by viewModel()

    private val adapter = EditRealtyPhotoAdapter(emptyList()) {
        Log.d("TAG", it.uri)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater
                .inflate(R.layout.fragment_edit_realty, container, false)
                .also { view ->
                    ArrayAdapter.createFromResource(
                            view.context,
                            R.array.types_array,
                            android.R.layout.simple_spinner_item).also { adapter ->
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                view.typeSpinner.adapter = adapter
                            }

                    view.takePhotoButton.setOnClickListener { dispatchTakePictureIntent() }

                    with(view.galleryRecyclerView) {
                        layoutManager = LinearLayoutManager(this@EditRealtyFragment.context, HORIZONTAL, false)
                        adapter = this@EditRealtyFragment.adapter
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visible = false
        saveButton.visible = true

        wireUi()

        render(editRealtyViewModel.currentRealty.value?: Realty.default())

        editRealtyViewModel.currentRealty.observe(viewLifecycleOwner) { realty ->
            realty?.let {
                adapter.updateData(it.photos)
            }
        }
    }

    private fun wireUi() {
        typeSpinner.onSelected { editRealtyViewModel.editType(it?: "") }
        districtEditText.doOnTextChanged { text, _, _, _ -> editRealtyViewModel.editDistrict(text?.toString()?: "")}

        priceEditText.doOnTextChanged { text, _, _, _ ->
            text.coerceToDoubleAndUpdate(priceLayoutView) {
                editRealtyViewModel.editPrice(it)
            }
        }

        descriptionEditText.doOnTextChanged { text, _, _, _ -> editRealtyViewModel.editDescription(text?.toString()?: "")}

        surfaceEditText.doOnTextChanged { text, _, _, _ ->
            text.coerceToDoubleAndUpdate(surfaceLayoutView) {
                editRealtyViewModel.editSurface(it)
            }
        }

        numberRoomsEditText.doOnTextChanged { text, _, _, _ ->
            text.coerceToIntAndUpdate(numberRoomsLayoutView) {
                editRealtyViewModel.editNbOfRooms(it)
            }
        }

        numberBathroomsEditText.doOnTextChanged { text, _, _, _ ->
            text.coerceToIntAndUpdate(numberBathroomsLayoutView) {
                editRealtyViewModel.editNbOfBathrooms(it)
            }
        }

        numberBedroomsEditText.doOnTextChanged { text, _, _, _ ->
            text.coerceToIntAndUpdate(numberBedroomsLayoutView) {
                editRealtyViewModel.editNbOfBedrooms(it)
            }
        }

        addressEditText.doOnTextChanged { text, _, _, _ -> editRealtyViewModel.editAddress(text?.toString()?: "")}

        metroSwitch.setOnCheckedChangeListener { _, value -> editRealtyViewModel.editPoiMetro(value) }
        parkSwitch.setOnCheckedChangeListener {_, value -> editRealtyViewModel.editPoiPark(value)}
        shopsSwitch.setOnCheckedChangeListener { _, value -> editRealtyViewModel.editPoiShops(value) }

        saveButton.setOnClickListener { saveRealty() }
    }

    private fun render(realty: Realty) {
        typeSpinner.setText(realty.type)
        districtEditText.text = realty.district.toEditable()
        priceEditText.text = realty.priceInDollars.toString().toEditable()
        descriptionEditText.text = realty.description.toEditable()
        surfaceEditText.text = realty.surface.toString().toEditable()
        numberRoomsEditText.text = realty.numberOfRooms.toString().toEditable()
        numberBathroomsEditText.text = realty.numberOfBathrooms.toString().toEditable()
        numberBedroomsEditText.text = realty.numberOfBedrooms.toString().toEditable()
        addressEditText.text = realty.address.toEditable()
        metroSwitch.isChecked = realty.pointsOfInterest.closeToMetro
        parkSwitch.isChecked = realty.pointsOfInterest.closeToPark
        shopsSwitch.isChecked = realty.pointsOfInterest.closeToShops
    }

    //TODO: Feedback on saved/not saved
    private fun saveRealty() {
        editRealtyViewModel.saveAndThen(doNext = {
            Log.d(javaClass.canonicalName, "Saved")
            findNavController().navigateUp()
        }, doOnError = {Log.d(javaClass.canonicalName, "Not saved")})
    }

    private fun CharSequence?.coerceToIntAndUpdate(view: TextInputLayout, saveFn: (Int) -> Unit) {
        val number = toString().trim().toIntOrNull()
        if (number == null) view.error = "Not a number"
        number?.let {
            view.error = null
            saveFn(it)
        }
    }

    private fun CharSequence?.coerceToDoubleAndUpdate(view: TextInputLayout, saveFn: (Double) -> Unit) {
        val number = toString().trim().toDoubleOrNull()
        if (number == null) view.error = "Not a number"
        number?.let {
            view.error = null
            saveFn(it)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            activity?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        null
                    }
                    photoFile?.let { file ->
                        val photoURI: Uri = FileProvider.getUriForFile(
                                requireContext(),
                                "com.openclassrooms.realestatemanage.fileprovider",
                                file
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           editRealtyViewModel.addPhoto(Photo(currentPhotoPath, "photoName"))
        }
    }

}
