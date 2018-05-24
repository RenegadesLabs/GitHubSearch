package com.labs.renegades.githubsearch.util

import java.text.SimpleDateFormat
import java.util.*

class DateDelegate {

    private val isoFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val displayFormat = "dd MMM, yy"
    private val isoFormatter = SimpleDateFormat(isoFormat, Locale.getDefault())
    private val formatter = SimpleDateFormat(displayFormat, Locale.getDefault())

    fun formatDate(date: String): String {
        return formatter.format(isoFormatter.parse(date))
    }
}