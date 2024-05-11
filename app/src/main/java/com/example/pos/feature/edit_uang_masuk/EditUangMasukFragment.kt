package com.example.pos.feature.edit_uang_masuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pos.data.model.local.Record
import com.example.pos.databinding.FragmentEditUangMasukBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditUangMasukFragment : Fragment() {
    private val viewModel: EditUangMasukViewModel by activityViewModels()
    private lateinit var binding: FragmentEditUangMasukBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditUangMasukBinding.inflate(inflater, container, false)
        initUi()
        onClickEvent()
        return binding.root
    }


    private fun initUi() {
        val id = arguments?.getInt("id")
        viewModel.isValidated.observe(viewLifecycleOwner) { isvalidated ->
            if(!isvalidated){
//                show message error on fragment screen
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            id?.let {
                viewModel.getRecord(it).observe(viewLifecycleOwner) { records ->
                    viewModel.setupRecord(records?.get(0))
                    initRecords(records?.get(0))
                }
            }
        }
    }

    private fun initRecords(records: Record?) {
        binding.apply {
            tietTo.setText(records?.to)
            tietFrom.setText(records?.from)
            tietTotal.setText(records?.total)
            tietNote.setText(records?.note)
            tietType.setText(records?.type)
        }
    }

    private fun onClickEvent() {
        binding.apply {
            btnSave.setOnClickListener {
                viewModel.insert(
                    to = tietTo.text.toString(),
                    from = tietFrom.text.toString(),
                    total = tietTotal.text.toString(),
                    note = tietNote.text.toString(),
                    type = tietType.text.toString(),
                )
            }
        }
    }
}