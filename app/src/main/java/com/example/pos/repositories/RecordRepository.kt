package com.example.pos.repositories

import com.example.pos.data.model.local.Record
import com.example.pos.db.RecordsDao
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordsDao: RecordsDao
) : Repository {

    suspend fun createRecord(record: Record) {
        recordsDao.insertRecords(record)
    }

    suspend fun deleteRecord(id: Int) {
        recordsDao.deleteRecordById(id)
    }

    suspend fun getRecords(from: Long, to: Long) = flow {
        val data = recordsDao.getListRecord(from, to)
        emit(data)
    }.onStart {
        emit(emptyList())
    }

    suspend fun getRecordById(id: Int) = flow {
        val data = recordsDao.getRecordById(id)
        emit(data)
    }.onStart {
        emit(null)
    }

    suspend fun updateRecord(record: Record) {
        recordsDao.updateRecord(record)
    }

}