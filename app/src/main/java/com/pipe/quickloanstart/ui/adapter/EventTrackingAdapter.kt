package com.pipe.quickloanstart.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.quickloanstart.data.models.EventsStart
import com.pipe.quickloanstart.databinding.RecyclerEventsBinding

class EventTrackingAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindingEvent: RecyclerEventsBinding

    private var events: MutableList<EventsStart> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        bindingEvent = RecyclerEventsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(bindingEvent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(bindingEvent, events[position])
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(binding: RecyclerEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            binding: RecyclerEventsBinding,
            event: EventsStart
        ) {

            if (event.flag) {
                binding.imageTrue.visibility = View.VISIBLE
                binding.imageFalse.visibility = View.INVISIBLE
            } else {
                binding.imageTrue.visibility = View.INVISIBLE
                binding.imageFalse.visibility = View.VISIBLE
            }

            binding.textStatus.text = event.textEvent
        }
    }
}