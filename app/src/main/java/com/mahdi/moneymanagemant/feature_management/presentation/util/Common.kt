package com.mahdi.moneymanagemant.feature_management.presentation.util

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

val String.creditCardFormatted2:String
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