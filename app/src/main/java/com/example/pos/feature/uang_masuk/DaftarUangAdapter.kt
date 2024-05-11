package com.example.pos.feature.uang_masuk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.data.model.local.Record
import com.example.pos.databinding.ItemRvUangMasukBinding
import com.example.pos.util.formatDate

class DaftarUangAdapter(private var records: List<Record>?):
    RecyclerView.Adapter<DaftarUangAdapter.DaftarUangVH>() {
    class  DaftarUangVH(private val binding: ItemRvUangMasukBinding): RecyclerView.ViewHolder(binding.root) {
        private val FORMAT_DATE = "dd MMMM yyyy\nHH:mm"
        fun bind(record: Record) {
            binding.apply {
                tvInfo.text = itemView.context.getString(R.string.text_from_to, record.from, record.to)
                tvNotes.text = record.note
                tvDate.text = record.date?.formatDate(FORMAT_DATE)
                tvAmount.text = record.total
                btnEdit.setOnClickListener {

                }
                btnDelete.setOnClickListener {

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarUangVH =
        DaftarUangVH(
            ItemRvUangMasukBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = if(records.isNullOrEmpty()) 0 else records!!.size

    override fun onBindViewHolder(holder: DaftarUangVH, position: Int) =
        holder.bind(records!![position])

    fun setRecords(newRecords: List<Record>?){
        records = newRecords
        notifyDataSetChanged()
    }
}