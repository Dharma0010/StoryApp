package com.example.storyapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.storyapp.R
import com.google.android.material.textfield.TextInputLayout

class MyTextInputLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputLayout(context, attrs) {



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (editText?.text?.length!! <= 6) {
            boxStrokeColor = ContextCompat.getColor(context, R.color.md_theme_error)
        }
    }
}