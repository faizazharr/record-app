package com.example.pos.repositories

import androidx.annotation.WorkerThread
import com.example.pos.data.model.local.Record
import com.example.pos.db.RecordsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    @WorkerThread
    fun getRecords(from: Long, to: Long) = flow {
        val data = recordsDao.getListRecord(from, to)
//        val data = recordsDao.getAllListRecord()
        emit(data)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    suspend fun getRecordById(id: Int) = flow {
        val data = recordsDao.getRecordById(id)
        emit(data)
    }.onStart {
        emit(null)
    }.flowOn(Dispatchers.IO)

    suspend fun updateRecord(record: Record) {
        recordsDao.updateRecord(record)
    }

}