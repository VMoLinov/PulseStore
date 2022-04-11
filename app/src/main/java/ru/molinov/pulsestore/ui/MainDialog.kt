package ru.molinov.pulsestore.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import ru.molinov.pulsestore.R
import ru.molinov.pulsestore.databinding.DialogFabBinding
import ru.molinov.pulsestore.model.StoreDB

class MainDialog(private val callback: (StoreDB) -> Unit) : DialogFragment() {

    private lateinit var systolic: TextInputEditText
    private lateinit var dystolic: TextInputEditText
    private lateinit var pulse: TextInputEditText
    private lateinit var button: Button
    private var _binding: DialogFabBinding? = null
    private val binding get() = _binding!!

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            button.isEnabled = (
                    systolic.text?.isNotEmpty() == true
                            && dystolic.text?.isNotEmpty() == true
                            && pulse.text?.isNotEmpty() == true
                            && systolic.text.toString().toInt() in SYSTOLIC_MIN until SYSTOLIC_MAX
                            && dystolic.text.toString().toInt() in DYSTOLIC_MIN until DYSTOLIC_MAX
                            && pulse.text.toString().toInt() in PULSE_MIN until PULSE_MAX
                    )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFabBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                val data = StoreDB(
                    System.currentTimeMillis(),
                    binding.systolic.text.toString().toInt(),
                    binding.dystolic.text.toString().toInt(),
                    binding.pulse.text.toString().toInt()
                )
                callback.invoke(data)
            }
            .create()
    }

    override fun onStart() {
        super.onStart()
        systolic = binding.systolic
        dystolic = binding.dystolic
        pulse = binding.pulse
        button = (dialog as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE)
        button.isEnabled = false
        systolic.addTextChangedListener(textWatcher)
        dystolic.addTextChangedListener(textWatcher)
        pulse.addTextChangedListener(textWatcher)
        systolic.focusChange(SYSTOLIC_MIN, SYSTOLIC_MAX)
        dystolic.focusChange(DYSTOLIC_MIN, DYSTOLIC_MAX)
        pulse.focusChange(PULSE_MIN, PULSE_MAX)
    }

    private fun TextInputEditText.focusChange(MIN: Int, MAX: Int) {
        setOnFocusChangeListener { _, _ ->
            val value = text.toString()
            if (value.isNotEmpty() && (value.toInt() < MIN || value.toInt() > MAX)
            ) {
                error =
                    getString(R.string.dialog_range) + " $MIN " +
                            getString(R.string.dialog_until) + " $MAX "
            } else systolic.error = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val SYSTOLIC_MIN = 50
        const val SYSTOLIC_MAX = 150
        const val DYSTOLIC_MIN = 80
        const val DYSTOLIC_MAX = 200
        const val PULSE_MIN = 40
        const val PULSE_MAX = 200
    }
}
