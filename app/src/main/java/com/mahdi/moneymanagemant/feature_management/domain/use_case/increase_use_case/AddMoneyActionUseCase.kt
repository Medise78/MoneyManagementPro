package com.mahdi.moneymanagemant.feature_management.domain.use_case.increase_use_case

import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.InvalidException
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.domain.repository.increase_repository.MoneyManagementRepository
import kotlin.jvm.Throws

class AddMoneyActionUseCase(
     private val repository: MoneyManagementRepository
) {
     @Throws(InvalidException::class)
     suspend operator fun invoke(moneyManagement: MoneyManagement) {
          if (moneyManagement.name.isBlank()) {
               throw InvalidException("Set Title")
          }
          if (moneyManagement.name.length > 15){
               throw InvalidException("long Title")
          }
          if (moneyManagement.content.isBlank()) {
               throw InvalidException("Set Content")
          }
          if (moneyManagement.cardNumber.isBlank()) {
               throw InvalidException("Set Card Number")
          }
          if (moneyManagement.cardNumber.length < 16 || moneyManagement.cardNumber.length >16){
               throw InvalidException("CardNumber Should be 16 character")
          }
          if (moneyManagement.price.isBlank()) {
               throw InvalidException("Set Price")
          }

          repository.insertMoneyAction(moneyManagement)
     }
}