package com.openclassrooms.realestatemanager.utils

import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

sealed class ValidationResult<T> {
    data class Success<T>(val value: T): ValidationResult<T>()
    data class Failure<T>(val reason: String): ValidationResult<T>()
}

inline fun <T> ValidationResult<T>.onSuccess(action: (value: T) -> Unit): ValidationResult<T> {
    if (this is ValidationResult.Success<T>) action(value)
    return this
}

inline fun <T> ValidationResult<T>.onFailure(action: (reason: String) -> Unit): ValidationResult<T> {
    if (this is ValidationResult.Failure) action(reason)
    return this
}

fun <T> TextInputLayout.validateAndUpdate(validationFn: String?.() -> ValidationResult<T>, updateFn: (T) -> Unit) {
    check(editText != null) {"No EditText in Layout"}
    editText?.doOnTextChanged { text, _, _, _ ->
        text.toString().trim().validationFn()
                .onSuccess {
                    error = null
                    updateFn(it)
                }
                .onFailure { error = it }
    }
}

fun String?.identity(): ValidationResult<String?> {
    if (this == "") {
        return ValidationResult.Success(null)
    }
    return ValidationResult.Success(this)
}

fun String?.isNullOrBlank(error: String): ValidationResult<String> {
    return if (isNullOrBlank()) {
        ValidationResult.Failure(error)
    } else ValidationResult.Success(this!!)
}

fun String?.convertToInt(error: String): ValidationResult<Int> {
    val result = toString().trim().toIntOrNull()
    result?.let {
        return ValidationResult.Success(it)
    } ?: return ValidationResult.Failure(error)
}

fun String?.convertToDouble(error: String): ValidationResult<Double> {
    val result = toString().trim().toDoubleOrNull()
    result?.let {
        return ValidationResult.Success(it)
    } ?: return ValidationResult.Failure(error)
}

fun String?.convertToIntWithBlankEquals(error: String, default: Int?): ValidationResult<Int?> {
    val string = toString().trim()
    if (string == "") {
        return ValidationResult.Success(default)
    } else {
        val result = toString().trim().toIntOrNull()
        result?.let {
            return ValidationResult.Success(it)
        } ?: return ValidationResult.Failure(error)
    }
}

fun String?.convertToDoubleWithBlankEquals(error: String, default: Double?): ValidationResult<Double?> {
    val string = toString().trim()
    if (string == "") {
        return ValidationResult.Success(default)
    } else {
        val result = toString().trim().toDoubleOrNull()
        result?.let {
            return ValidationResult.Success(it)
        } ?: return ValidationResult.Failure(error)
    }
}