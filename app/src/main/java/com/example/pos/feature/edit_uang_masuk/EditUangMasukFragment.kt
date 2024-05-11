package com.example.pos.feature.edit_uang_masuk

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pos.data.model.local.Record
import com.example.pos.databinding.FragmentEditUangMasukBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditUangMasukFragment : Fragment() {
    private val viewModel: EditUangMasukViewModel by activityViewModels()
    private lateinit var binding: FragmentEditUangMasukBinding
    private val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES,
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
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


    private fun checkPermissionGallery() {
        when {
            hasPermissions(requireContext(), permission) -> {
            }

            showPermissionRationale(permission)
            -> {
                Snackbar.make(binding.root, "Permission Denied", Snackbar.LENGTH_SHORT)
            }

            else -> {
                requestMultiplePermissions.launch(
                    permission
                )
            }
        }
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
//            imageChooser()
        } else {
            Snackbar.make(binding.root, "Permission Denied", Snackbar.LENGTH_SHORT)
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun showPermissionRationale(permissions: Array<String>): Boolean =
        permissions.all {
            shouldShowRequestPermissionRationale(it)
        }
}