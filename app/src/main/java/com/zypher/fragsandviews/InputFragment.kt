package com.zypher.fragsandviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class TextInputFragment : Fragment() {

    private var listener: InputListener? = null

    interface InputListener {
        fun onInputSent(input: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InputListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement InputListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)
        val inputEditText = view.findViewById<EditText>(R.id.inputEditText)
        view.findViewById<Button>(R.id.submitBtn).setOnClickListener {
            listener?.onInputSent(inputEditText.text.toString())
        }
        return view
    }

    companion object {
        fun newInstance(): InputFragment {
            return InputFragment()
        }
    }
}
