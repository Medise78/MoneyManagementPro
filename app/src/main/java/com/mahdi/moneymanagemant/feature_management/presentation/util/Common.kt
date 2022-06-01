package com.mahdi.moneymanagemant.feature_management.presentation.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

val String.creditCardFormatted:String
get()
{
      val preparedString = replace(" " , "").trim()
      val result = StringBuilder()
      for (i in preparedString.indices){
            if (i % 4 == 0 && i != 0){
                  result.append("-")
            }
            result.append(preparedString[i])
      }
      return result.toString()
}

fun formatOtherCardNumbers(text:AnnotatedString):TransformedText{
      val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
      var out = ""

      for (i in trimmed.indices){
            out += trimmed[i]
            if (i % 4 == 3 && i != 15) out += "-"
      }
      val creditCardOffsetTranslator = object :OffsetMapping{
            override fun originalToTransformed(offset : Int) : Int
            {
                  if (offset <= 3) return offset
                  if (offset <= 7) return offset + 1
                  if (offset <= 11) return offset + 2
                  if (offset <= 16) return offset + 3
                  return 19
            }

            override fun transformedToOriginal(offset : Int) : Int
            {
                  if (offset <= 4) return offset
                  if (offset <= 9) return offset - 1
                  if (offset <= 14) return offset - 2
                  if (offset <= 19) return offset - 3
                  return 16
            }
      }
      return TransformedText(AnnotatedString(out) , creditCardOffsetTranslator)
}

fun identifyCardScheme(cardNumber: String): CardScheme {
      val jcbRegex = Regex("^(?:2131|1800|35)[0-9]{0,}$")
      val ameRegex = Regex("^3[47][0-9]{0,}\$")
      val dinersRegex = Regex("^3(?:0[0-59]{1}|[689])[0-9]{0,}\$")
      val visaRegex = Regex("^4[0-9]{0,}\$")
      val masterCardRegex = Regex("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]{0,}\$")
      val maestroRegex = Regex("^(5[06789]|6)[0-9]{0,}\$")
      val discoverRegex =
             Regex("^(6011|65|64[4-9]|62212[6-9]|6221[3-9]|622[2-8]|6229[01]|62292[0-5])[0-9]{0,}\$")

      val trimmedCardNumber = cardNumber.replace(" ", "")

      return when {
            trimmedCardNumber.matches(jcbRegex) -> CardScheme.JCB
            trimmedCardNumber.matches(ameRegex) -> CardScheme.AMEX
            trimmedCardNumber.matches(dinersRegex) -> CardScheme.DINERS_CLUB
            trimmedCardNumber.matches(visaRegex) -> CardScheme.VISA
            trimmedCardNumber.matches(masterCardRegex) -> CardScheme.MASTERCARD
            trimmedCardNumber.matches(discoverRegex) -> CardScheme.DISCOVER
            trimmedCardNumber.matches(maestroRegex) -> if (cardNumber[0] == '5') CardScheme.MASTERCARD else CardScheme.MAESTRO
            else -> CardScheme.UNKNOWN
      }
}

enum class CardScheme {
      JCB, AMEX, DINERS_CLUB, VISA, MASTERCARD, DISCOVER, MAESTRO, UNKNOWN
}

