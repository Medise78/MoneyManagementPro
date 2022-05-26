package com.mahdi.moneymanagemant.feature_management.domain.use_case.increase_use_case

import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.domain.repository.increase_repository.MoneyManagementRepository

class GetMoneyActionUseCase(
     private val repository: MoneyManagementRepository
) {
     suspend operator fun invoke(id: Int): MoneyManagement? {
          return repository.getMoneyActionById(id)
     }
}