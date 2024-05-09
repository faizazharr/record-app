package com.example.pos.feature.input_uang_masuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pos.databinding.FragmentInputUangMasukBinding
import com.example.pos.util.currentDate

class InputUangMasukFragment : Fragment() {
    private val viewModel: InputUangMasukViewModel by activityViewModels()
    private lateinit var binding: FragmentInputUangMasukBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputUangMasukBinding.inflate(inflater, container, false)
        initUi()
        onClickEvent()
        return binding.root
    }

    private fun initUi() {
        viewModel.isValidated.observe(viewLifecycleOwner) { isvalidated ->
            if(!isvalidated){
//                show message error on fragment screen
            }
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
                    date = currentDate()
                )
            }
        }
    }
}