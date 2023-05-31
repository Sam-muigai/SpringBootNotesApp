package com.sam.springbootnotesapp.feature_authentication.presentation.utils

import kotlin.jvm.Throws

@Throws(EmailValidationException::class)
fun verifyEmail(email: String): String {
    if (!email.contains("@gmail.com")) {
        throw EmailValidationException
    } else {
        return email
    }
}

@Throws(PasswordValidationException::class)
fun verifyPassword(password: String): String {
    if (password.count() < 8) {
        throw PasswordValidationException("Password is too short")
    } else if (!password.any { it.isDigit() }) {
        throw PasswordValidationException("Password must contain at least one digit.")
    } else if (!password.any { it.isLetter() }) {
        throw PasswordValidationException("Password must contain at least one alphabet.")
    } else {
        return password
    }
}

@Throws(ConfirmPasswordValidationException::class)
fun verifyConfirmPassword(password: String, confirmPassword: String): String {
    if (confirmPassword.isNotEmpty() && password.isNotEmpty() && password != confirmPassword) {
        throw ConfirmPasswordValidationException("Password and confirm password do not match")
    }
    return confirmPassword
}

class PasswordValidationException(message: String) : Exception(message)
object EmailValidationException : Exception("Email not valid")
class ConfirmPasswordValidationException(message: String) : Exception(message)
























