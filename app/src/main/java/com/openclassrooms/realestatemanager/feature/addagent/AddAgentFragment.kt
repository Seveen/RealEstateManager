package com.openclassrooms.realestatemanager.feature.addagent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.*
import kotlinx.android.synthetic.main.fragment_add_agent.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddAgentFragment : Fragment() {

    private val addAgentViewModel: AddAgentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_agent, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visible = false
        saveButton.visible = true

        wireUi()

        render()
    }

    private fun render() {
        agentSurnameLayoutView.editText?.text = "".toEditable()
        agentNameLayoutView.editText?.text = "".toEditable()
    }

    private fun wireUi() {
        agentSurnameLayoutView.validateAndUpdate(
                validationFn = { isNullOrBlank("Can't be empty") },
                updateFn = { addAgentViewModel.updateSurname(it) }
        )

        agentNameLayoutView.validateAndUpdate(
                validationFn = { isNullOrBlank("Can't be empty")},
                updateFn = { addAgentViewModel.updateName(it) }
        )

        saveButton.setOnClickListener { saveAgent() }
    }

    private fun saveAgent() {
        if (validated(agentNameLayoutView, agentSurnameLayoutView)) {
            saveButton.visible = false
            progressBar.visible = true
            addAgentViewModel.saveAndThen {
                Toast.makeText(context, "Agent saved.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        } else {
            Toast.makeText(context, "You can't save with errors.", Toast.LENGTH_SHORT).show()
        }
    }
}
