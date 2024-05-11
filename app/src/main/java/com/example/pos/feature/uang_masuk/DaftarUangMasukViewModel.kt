package com.example.pos.feature.uang_masuk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pos.data.model.local.Record
import com.example.pos.repositories.RecordRepository
import com.example.pos.util.currentDate
import com.example.pos.util.parseDate
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DaftarUangMasukViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    val startDate = MutableLiveData<String>()
    val endDate = MutableLiveData<String>()

    init {
        startDate.value = currentDate()
        endDate.value = currentDate()
    }

    fun setStartDate(startDate: String) {
        this.startDate.value = startDate
    }

    fun setEndDate(endDate: String) {
        Timber.tag("vm").d("endDate: %s", endDate)
        this.endDate.value = endDate
    }

    fun loadRecords(): LiveData<List<Record>?>? {
        val startDate = startDate.value
        val endDate = endDate.value

        if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
            return recordRepository.getRecords(startDate.parseDate(), endDate.parseDate())
                .asLiveData(viewModelScope.coroutineContext)
        }

        return null
    }
}