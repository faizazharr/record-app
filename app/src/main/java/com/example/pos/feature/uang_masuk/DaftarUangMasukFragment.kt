package com.example.pos.feature.uang_masuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pos.R
import com.example.pos.data.model.local.Record
import com.example.pos.databinding.FragmentDaftarUangMasukBinding
import com.example.pos.util.NavGraph

class DaftarUangMasukFragment : Fragment() {
    private val viewModel: DaftarUangMasukViewModel by activityViewModels()
    private lateinit var binding: FragmentDaftarUangMasukBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaftarUangMasukBinding.inflate(inflater, container, false)
        setupRecordModel()
        return binding.root
    }

    private fun setupRecordModel() {
        viewModel.records.observe(viewLifecycleOwner) { records ->
            initUi(records)
        }
    }

    private fun initUi(records: List<Record>?) {
        binding.apply {
            if (!records.isNullOrEmpty()) {
                tvEmptyRecord.visibility = View.GONE
            } else {
                tvEmptyRecord.visibility = View.VISIBLE
            }
        }
    }

    // kalau mau navigate buat input uang panggil ini aja
    private fun navigateInputUang() {
        NavGraph.navigateFragment(
            binding.root,
            R.id.action_daftarUangMasukFragment_to_inputUangMasukFragment
        )
    }

}