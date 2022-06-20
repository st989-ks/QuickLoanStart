package com.pipe.quickloanstart.ui.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.quickloanstart.databinding.RecyclerManualBinding
import com.pipe.quickloanstart.extensions.ListManual
import com.pipe.quickloanstart.extensions.SharedPrefs
import com.pipe.quickloanstart.extensions.getLocaleDrawableResource
import com.pipe.quickloanstart.extensions.getLocaleStringResource
import java.util.*
import javax.inject.Inject

class ManualAdapter @Inject constructor(
    private val application: Application,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private lateinit var bindingListManual: RecyclerManualBinding

    private var local: Locale = Locale("Ru")

    var listManual: List<ListManual> = listOf()
        set(value) {
            local = Locale(sharedPrefs.getLanguage())
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        bindingListManual = RecyclerManualBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ManualViewHolder(bindingListManual)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ManualViewHolder).bind(bindingListManual, listManual[position], application,local)
    }

    override fun getItemCount(): Int = listManual.size

    class ManualViewHolder(binding: RecyclerManualBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            binding: RecyclerManualBinding,
            listManual: ListManual,
            application: Application,
            local: Locale
        ) {
            binding.onboardingImage.setImageDrawable(application.getLocaleDrawableResource(local,listManual.image))
            binding.onboardingTitle.text = application.getLocaleStringResource(local, listManual.title)
            binding.onboardingMessage.text = application.getLocaleStringResource(local, listManual.text)
        }
    }
}