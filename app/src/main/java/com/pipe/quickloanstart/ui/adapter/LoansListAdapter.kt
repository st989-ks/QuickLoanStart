package com.pipe.quickloanstart.ui.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.data.models.LoanEntity
import com.pipe.quickloanstart.databinding.RecyclerLoansBinding
import com.pipe.quickloanstart.extensions.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoansListAdapter @Inject constructor(
    private val application: Application,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private lateinit var bindingLoans: RecyclerLoansBinding

    private var local: Locale = Locale("Ru")

    var loans: List<LoanEntity> = listOf()
        set(value) {
            local = Locale(sharedPrefs.getLanguage())
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        bindingLoans = RecyclerLoansBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoansViewHolder(bindingLoans)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LoansViewHolder).bind(bindingLoans, loans[position], application, local)
    }

    override fun getItemCount(): Int = loans.size

    inner class LoansViewHolder(binding: RecyclerLoansBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("StringFormatMatches")
        fun bind(
            binding: RecyclerLoansBinding,
            loan: LoanEntity,
            application: Application,
            local: Locale
        ) {
            val statusLoan =
                application
                    .getLocaleStringResource(local, loan.state.getStatus()).format()
            val status =
                application
                    .getLocaleStringResource(local, R.string.loan_status_for_fragment)
                    .format(statusLoan)
            val info =
                application
                    .getLocaleStringResource(local, R.string.info_list_for_fragment)
                    .format(
                        loan.date.formatDate(),
                        loan.amount.toString(),
                        loan.percent.toString(),
                        loan.period.toString()
                    )
            Log.i(status,info)
            binding.loansStatusAll.apply {
                text = status
                textColorStatus(loan.state)
            }
            binding.listOfLoansStatusAll.text = info
            binding.cardLoanAll.setOnClickListener { onContainerClickListener?.let { it(loan) } }
        }
    }

    private var onContainerClickListener: ((LoanEntity) -> Unit)? = null
    fun setOnContainerClickListener(listener: (LoanEntity) -> Unit) {
        onContainerClickListener = listener
    }

}