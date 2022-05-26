package com.mahdi.moneymanagemant.feature_management.domain.repository.decrease_repository

import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import kotlinx.coroutines.flow.Flow

interface MoneyManagementDecreaseRepository {
     fun getMoneyAction(): Flow<List<MoneyManagementDecrease>>
     suspend fun getMoneyActionById(id: Int): MoneyManagementDecrease?
     suspend fun insertMoneyAction(moneyManagementDecrease: MoneyManagementDecrease)
     suspend fun deleteMoneyAction(moneyManagementDecrease: MoneyManagementDecrease)
}