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
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.Photo
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.utils.*
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
//TODO: Add creation date picker and sold/sale date toggle+date picker

class EditRealtyFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1

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

        districtLayoutView.validateAndUpdate(
                validationFn = { isNullOrBlank("Should not be blank") },
                updateFn = { editRealtyViewModel.editDistrict(it) }
        )

        priceLayoutView.validateAndUpdate(
                validationFn = { convertToDouble("Not a number") },
                updateFn = { editRealtyViewModel.editPrice(it) }
        )

        descriptionLayoutView.validateAndUpdate(
                validationFn = { isNullOrBlank("Should not be blank") },
                updateFn = { editRealtyViewModel.editDescription(it) }
        )

        surfaceLayoutView.validateAndUpdate(
                validationFn = { convertToDouble("Not a number") },
                updateFn = { editRealtyViewModel.editSurface(it) }
        )

        numberRoomsLayoutView.validateAndUpdate(
                validationFn = { convertToInt("Not a number") },
                updateFn = { editRealtyViewModel.editNbOfRooms(it) }
        )

        numberBathroomsLayoutView.validateAndUpdate(
                validationFn = { convertToInt("Not a number") },
                updateFn = { editRealtyViewModel.editNbOfBathrooms(it) }
        )

        numberBedroomsLayoutView.validateAndUpdate(
                validationFn = { convertToInt("Not a number") },
                updateFn = { editRealtyViewModel.editNbOfBedrooms(it) }
        )

        addressLayoutView.validateAndUpdate(
                validationFn = { isNullOrBlank("Should not be blank") },
                updateFn = { editRealtyViewModel.editAddress(it) }
        )

        subwaySwitch.setOnCheckedChangeListener { _, value -> editRealtyViewModel.editPoiSubway(value) }
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
        subwaySwitch.isChecked = realty.isCloseToSubway
        parkSwitch.isChecked = realty.isCloseToPark
        shopsSwitch.isChecked = realty.isCloseToShops
    }

    //TODO: Feedback on saved/not saved
    private fun saveRealty() {
        if (validateForm()) editRealtyViewModel.saveAndThen(
            isNetworkAvailable = Utils.isInternetAvailable(requireContext()),
            doNext = {
                Log.d(javaClass.canonicalName, "Saved")
                findNavController().navigateUp()
            },
            doOnError = {Log.d(javaClass.canonicalName, "Not saved")}
        )
    }

    //TODO: Validate
    private fun validateForm() = true

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
