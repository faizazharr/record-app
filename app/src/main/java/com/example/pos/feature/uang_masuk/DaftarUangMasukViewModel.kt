package com.example.pos.feature.uang_masuk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pos.data.model.local.Record
import com.example.pos.repositories.RecordRepository
import com.example.pos.util.parseDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaftarUangMasukViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    private val mutableRecord = MutableLiveData<List<Record>>()
    private val startDate = MutableLiveData<String>()
    private val endDate = MutableLiveData<String>()

    val records: MutableLiveData<List<Record>> = mutableRecord

    fun setStartDate(startDate: String) {
        this.startDate.value = startDate
    }

    fun setEndDate(endDate: String) {
        this.endDate.value = endDate
    }

    fun loadRecords() {
        val startDate = this.startDate.value
        val endDate = this.endDate.value
        viewModelScope.launch {
            if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
                if (startDate.parseDate() < endDate.parseDate()) {
                    val records =
                        recordRepository.getRecords(
                            from = startDate.toLong(),
                            to = endDate.toLong()
                        )
                            .asLiveData()
                    mutableRecord.value = records.value
                }
            }
        }
    }


}