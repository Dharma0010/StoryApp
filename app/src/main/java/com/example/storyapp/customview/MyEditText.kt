package com.example.storyapp.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyapp.R
import com.google.android.material.textfield.TextInputEditText

class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        if (s.toString().length < 8) {
            setError(context.getString(R.string.password_error_message), null)
        } else {
            setError(null, null)
        }
    }
}