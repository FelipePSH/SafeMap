package com.felipe.santos.safemap.utils

fun generateInviteCode(length: Int = 6): String {
    val allowedChars = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}