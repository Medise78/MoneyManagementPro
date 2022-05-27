package com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdi.moneymanagemant.ui.theme.*

@Entity
data class MoneyManagementDecrease(
     val nameDecrease: String,
     val contentDecrease: String,
     val colorDecrease: Int,
     val priceDecrease: String,
     val cardNumberDecrease: String,
     val timeStampDecrease: Long,
     @PrimaryKey val idDecrease: Int? = null
) {
     //har ja estefade beshe chandta element ba khodesh dare
     companion object {
          val colorsDecrease = listOf(RedOrange, LightGreen, Violet, BabyBlue)
     }
}

class InvalidExceptionDecrease(message: String) : Exception(message)
