package com.mahdi.moneymanagemant.feature_management.domain.repository.increase_repository

import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import kotlinx.coroutines.flow.Flow

interface MoneyManagementRepository {
     fun getMoneyAction(): Flow<List<MoneyManagement>>
     suspend fun getMoneyActionById(id: Int): MoneyManagement?
     suspend fun insertMoneyAction(moneyManagement: MoneyManagement)
     suspend fun deleteMoneyAction(moneyManagement: MoneyManagement)
}