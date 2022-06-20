package com.pipe.quickloanstart.ui.fragments
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import com.pipe.quickloanstart.databinding.DialogLanguageBinding
import com.pipe.quickloanstart.extensions.setLocale


class ChangeLanguageDialog(context: Context, val language: String) : Dialog(context) {

    private lateinit var binding: DialogLanguageBinding

    lateinit var onSave: (String) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window? = window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        if (language == "Ru") {
            binding.radioButtonAr.isChecked = true
        } else {
            binding.radioButtonEn.isChecked = true
        }

        binding.btnSave.setOnClickListener {
            if (binding.radioButtonAr.isChecked) {
                context.setLocale("Ru")
                onSave("Ru")
            } else {
                context.setLocale("En")
                onSave("En")
            }

        }
    }
}