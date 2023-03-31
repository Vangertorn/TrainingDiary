package com.yankin.trainingdiary.support.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.yankin.trainingdiary.R

fun TextInputEditText.onImeActionDone(clickAction: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            clickAction.invoke()
        }
        false
    }
}

fun TextInputEditText.clearDrawable() {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
}

fun TextInputEditText.setDrawableRight() {
    setCompoundDrawablesWithIntrinsicBounds(
        null,
        null,
        ContextCompat.getDrawable(context, R.drawable.ic_right),
        null
    )
}

fun EditText.setTextNotDash() {
    setText(
        if (text.toString() == "-") {
            ""
        } else {
            text.toString()
        }
    )
}

fun TextInputEditText.textOrEmpty(): String {
    return if (this.text.toString().isBlank()) "-"
    else this.text.toString()
}
