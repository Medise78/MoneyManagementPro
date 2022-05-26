package com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case

import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.domain.repository.decrease_repository.MoneyManagementDecreaseRepository

class DeleteMoneyActionDecreaseUseCase(
     private val repository: MoneyManagementDecreaseRepository
) {
     suspend operator fun invoke(moneyManagementDecrease: MoneyManagementDecrease) {
          repository.deleteMoneyAction(moneyManagementDecrease)
     }
}