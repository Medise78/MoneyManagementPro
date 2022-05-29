package com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case

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
               throw InvalidExceptionDecrease("Set Title")
          }
          if (moneyManagementDecrease.nameDecrease.length > 15){
               throw InvalidExceptionDecrease("long Title")
          }
          if (moneyManagementDecrease.contentDecrease.isBlank()) {
               throw InvalidExceptionDecrease("Set Content")
          }
          if (moneyManagementDecrease.priceDecrease.isBlank()) {
               throw InvalidExceptionDecrease("Set Price")
          }
          repository.insertMoneyAction(moneyManagementDecrease)
     }
}