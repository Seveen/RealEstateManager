package com.openclassrooms.realestatemanager.feature.editrealty


import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.EstateAgent
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

class EditRealtyFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_INSERT = 2

    private val editRealtyViewModel: EditRealtyViewModel by viewModel()

    private val adapter = EditRealtyPhotoAdapter(emptyList(),
         onDeletePhoto = {
             addPhotoToDeletion(it)
            editRealtyViewModel.removePhoto(it)
         }, onRenamePhoto = {
            renamePhoto(it)
    })

    private val deletedPhotos: MutableList<Photo> = mutableListOf()

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

                    ArrayAdapter<EstateAgent>(view.context, android.R.layout.simple_spinner_item).also { adapter ->
                        editRealtyViewModel.allAgents.observe(viewLifecycleOwner) {
                            adapter.addAll(it)
                        }
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        view.agentSpinner.adapter = adapter
                    }

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

        typeSpinner.onSelected { editRealtyViewModel.editType(it ?: "") }

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

        entryDateButton.setOnClickListener {
            showMarketEntryDatePicker()
        }

        soldSwitch.setOnCheckedChangeListener { _, value ->
            editRealtyViewModel.editSold(value)
            if (value) editRealtyViewModel.editSaleDate(Date())
            else editRealtyViewModel.editSaleDate(null)
            soldDateButton.gone = value.not()
            editRealtyViewModel.currentRealty.value?.saleDate?.let {
                soldDateButton.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(it)
            }
        }

        soldDateButton.setOnClickListener { showSaleDatePicker() }

        takePhotoButton.setOnClickListener { dispatchTakePictureIntent() }
        insertPhotoButton.setOnClickListener { dispatchInsertPictureIntent() }

        saveButton.setOnClickListener { saveRealty() }
    }

    private fun render(realty: Realty) {
        typeSpinner.setText(realty.type)
        districtEditText.text = realty.district.toEditable()
        priceEditText.text = String.format("%.2f", realty.priceInDollars).toEditable()
        descriptionEditText.text = realty.description.toEditable()
        surfaceEditText.text = realty.surface.toString().toEditable()
        numberRoomsEditText.text = realty.numberOfRooms.toString().toEditable()
        numberBathroomsEditText.text = realty.numberOfBathrooms.toString().toEditable()
        numberBedroomsEditText.text = realty.numberOfBedrooms.toString().toEditable()
        addressEditText.text = realty.address.toEditable()
        subwaySwitch.isChecked = realty.isCloseToSubway
        parkSwitch.isChecked = realty.isCloseToPark
        shopsSwitch.isChecked = realty.isCloseToShops
        soldSwitch.isChecked = realty.isSold
        soldDateButton.gone = realty.isSold.not()
        realty.saleDate?.let {
            soldDateButton.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(it)
        }
        entryDateButton.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(realty.marketEntryDate)
        editRealtyViewModel.allAgents.observe(viewLifecycleOwner) { list ->
            agentSpinner.setAgent(list.firstOrNull { it.id == realty.assignedEstateAgentId })
            agentSpinner.onAgentSelected { editRealtyViewModel.editAgent(it ?: EstateAgent.default()) }
        }
    }

    private fun saveRealty() {
        if (validateForm()) {
            editRealtyViewModel.saveAndThen(
                    isNetworkAvailable = Utils.isInternetAvailable(requireContext()),
                    doNext = {
                        Toast.makeText(context, "Realty saved.", Toast.LENGTH_LONG).show()
                        deletedPhotos.forEach { deletePhoto(it) }
                        findNavController().navigateUp()
                    },
                    doOnError = { Toast.makeText(context, "You need at least one picture.", Toast.LENGTH_LONG).show() }
            )
        } else {
            Toast.makeText(context, "You can't save with errors.", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateForm() = validated(
            districtLayoutView,
            priceLayoutView,
            descriptionLayoutView,
            surfaceLayoutView,
            numberRoomsLayoutView,
            numberBathroomsLayoutView,
            numberBedroomsLayoutView,
            addressLayoutView
    )

    private fun showMarketEntryDatePicker() {
        val currentDate = editRealtyViewModel.currentRealty.value?.marketEntryDate ?: Date()
        val calendar = Calendar.getInstance().apply { time = currentDate }

        DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val resultCalendar = Calendar.getInstance()
                            .apply {
                                set(year, month, day)
                            }
                    editRealtyViewModel.editMarketEntryDate(resultCalendar.time)
                    entryDateButton.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(resultCalendar.time)
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show()
    }

    private fun showSaleDatePicker() {
        val currentDate = editRealtyViewModel.currentRealty.value?.saleDate ?: Date()
        val calendar = Calendar.getInstance().apply { time = currentDate }

        DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val resultCalendar = Calendar.getInstance()
                            .apply {
                                set(year, month, day)
                            }
                    editRealtyViewModel.editSaleDate(resultCalendar.time)
                    soldDateButton.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(resultCalendar.time)
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show()
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

    private fun dispatchInsertPictureIntent() {
        startActivityForResult(Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }, REQUEST_IMAGE_INSERT)
    }

    private lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
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
            EditPhotoNameDialogFragment {
                editRealtyViewModel.addPhoto(Photo(currentPhotoPath, it))
            }.apply { show(this@EditRealtyFragment.requireActivity().supportFragmentManager, "editPhotoDialog") }
        } else if (requestCode == REQUEST_IMAGE_INSERT && resultCode == RESULT_OK) {
            EditPhotoNameDialogFragment {
                editRealtyViewModel.addPhoto(Photo(data?.data.toString(), it))
            }.apply { show(this@EditRealtyFragment.requireActivity().supportFragmentManager, "editPhotoDialog") }
        }
    }

    private fun deletePhoto(photo: Photo) {
        File(photo.uri).delete()
    }

    private fun addPhotoToDeletion(photo: Photo) {
        deletedPhotos.add(photo)
    }

    private fun renamePhoto(photo: Photo) {
        EditPhotoNameDialogFragment(photo.name) {
            editRealtyViewModel.renamePhoto(photo, it)
        }.apply { show(this@EditRealtyFragment.requireActivity().supportFragmentManager, "editPhotoDialog") }
    }

}
