package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component

import java.text.SimpleDateFormat
import java.util.*

fun getInvoiceDate(invoiceDate : String) : String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault())
        val calendar : Calendar = Calendar.getInstance()
        calendar.time = originalFormat.parse(invoiceDate)
        val newFormat = SimpleDateFormat("dd MMM yyyy" , Locale.getDefault())
        return newFormat.format(calendar.time)
    }
    catch (e : Exception) {
        ""
    }
}
fun getInvoiceDateForDbFormat(invoiceDate : String) : String {
    return try {
        val originalFormat = SimpleDateFormat("dd MMM yyyy" , Locale.getDefault())
        val calendar : Calendar = Calendar.getInstance()
        calendar.time = originalFormat.parse(invoiceDate)
        val newFormat = SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault())
        return newFormat.format(calendar.time)
    }
    catch (e : Exception) {
        ""
    }
}