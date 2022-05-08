package com.example.trainingdiary.support

import android.widget.EditText

fun EditText.removeDouble(amount: Double) {
    if (text.toString().isEmpty()) {
        setText("0.0")
    } else {
        if (text.toString().toDouble() >= amount) {
            setText(
                (text.toString().toDouble() - amount).toString()
            )
        } else {
            setText("0.0")
        }
    }
}

fun EditText.addDouble(amount: Double) {
    if (text.toString().isEmpty()) {
        setText("$amount")
    } else {
        setText(
            (text.toString().toDouble() + amount).toString()
        )
    }
}

fun EditText.removeInt(amount: Int) {
    if (text.toString().isEmpty()) {
        setText("0")
    } else {
        if (text.toString().toInt() >= amount) {
            setText(
                (text.toString().toInt() - amount).toString()
            )
        } else {
            setText("0")
        }
    }
}

fun EditText.addInt(amount: Int) {
    if (text.toString().isEmpty()) {
        setText("$amount")
    } else {
        setText(
            (text.toString().toInt() + amount).toString()
        )
    }
}

fun EditText.chekIntEmpty() {
    if (text.isBlank()) {
        setText("0")
    }
}

fun EditText.chekDoubleEmpty() {
    if (text.isBlank()) {
        setText("0.0")
    }
}
