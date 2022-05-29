package com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case

import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.InvalidException
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.InvalidExceptionDecrease
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.domain.repository.decrease_repository.MoneyManagementDecreaseRepository
import kotlin.jvm.Throws

class AddMoneyActionDecreaseUseCase(
     private val repository: MoneyManagementDecreaseRepository
) {
     @Throws(InvalidExceptionDecrease::class)
     suspend operator fun invoke(moneyManagementDecrease: MoneyManagementDecrease) {
          if (moneyManagementDecrease.nameDecrease.isBlank()) {
               throw InvalidException("Set Title")
          }
          if (moneyManagementDecrease.nameDecrease.length > 15){
               throw InvalidException("long Title")
          }
          if (moneyManagementDecrease.contentDecrease.isBlank()) {
               throw InvalidException("Set Content")
          }
          if (moneyManagementDecrease.cardNumberDecrease.isBlank()) {
               throw InvalidException("Set Card Number")
          }
          if (moneyManagementDecrease.cardNumberDecrease.length < 16 || moneyManagementDecrease.cardNumberDecrease.length >16){
               throw InvalidException("CardNumber Should be 16 character")
          }
          if (moneyManagementDecrease.priceDecrease.isBlank()) {
               throw InvalidException("Set Price")
          }
          repository.insertMoneyAction(moneyManagementDecrease)
     }
}