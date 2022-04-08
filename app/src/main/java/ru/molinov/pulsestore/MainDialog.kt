package ru.molinov.pulsestore

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
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
            button.isEnabled = (systolic.text.toString().isNotEmpty()
                    && dystolic.text.toString().isNotEmpty()
                    && pulse.text.toString().isNotEmpty())
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFabBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle("Title")
            .setPositiveButton("Ok") { dialog, _ ->
                val d = dialog as Dialog
                d.window?.let {
                    val data = StoreDB(
                        System.currentTimeMillis(),
                        it.findViewById<TextInputEditText>(R.id.systolic).text.toString().toInt(),
                        it.findViewById<TextInputEditText>(R.id.dystolic).text.toString().toInt(),
                        it.findViewById<TextInputEditText>(R.id.pulse).text.toString().toInt()
                    )
                    callback.invoke(data)
                }
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
