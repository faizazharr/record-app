package com.example.pos.feature.uang_masuk

import androidx.lifecycle.LiveData
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
    private val startDate = MutableLiveData<String>()
    private val endDate = MutableLiveData<String>()

    init {
        startDate.value = ""
        endDate.value = ""
    }

    fun setStartDate(startDate: String) {
        this.startDate.value = startDate
    }

    fun setEndDate(endDate: String) {
        this.endDate.value = endDate
    }

    fun loadRecords(): LiveData<List<Record>> {
        val mutableRecord = MutableLiveData<List<Record>>()
        val startDate = startDate.value
        val endDate = endDate.value
        mutableRecord.value = emptyList()
        viewModelScope.launch {
            if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
                if (startDate.parseDate() < endDate.parseDate()) {
                    val records =
                        recordRepository.getRecords(
                            from = startDate.toLong(),
                            to = endDate.toLong()
                        )
                            .asLiveData(viewModelScope.coroutineContext)
                    mutableRecord.value = records.value
                }
            }
        }
        return mutableRecord
    }


}