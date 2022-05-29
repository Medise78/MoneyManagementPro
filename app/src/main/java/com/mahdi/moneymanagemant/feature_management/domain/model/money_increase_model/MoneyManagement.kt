package com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdi.moneymanagemant.ui.theme.*

@Entity
data class MoneyManagement(
     val name: String,
     val content: String,
     val color: Int,
     val price: String,
     val date:String,
     val cardNumber: String,
     val timeStamp: Long,
     @PrimaryKey val id: Int? = null
) {
     companion object {
          val colors = listOf(RedOrange, LightGreen, Violet, BabyBlue)
     }
}
class InvalidException(message: String) : Exception(message)