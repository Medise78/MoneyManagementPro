package com.mahdi.moneymanagemant.feature_management.data.repository.increase_repository

import com.mahdi.moneymanagemant.feature_management.data.data_resource.money_increase_db.MoneyManagementDao
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.domain.repository.increase_repository.MoneyManagementRepository
import kotlinx.coroutines.flow.Flow

class MoneyManagementRepositoryImpl(
     private val moneyManagementDao: MoneyManagementDao
) : MoneyManagementRepository {
     override fun getMoneyAction(): Flow<List<MoneyManagement>> {
          return moneyManagementDao.getMoneyAction()
     }

     override suspend fun getMoneyActionById(id: Int): MoneyManagement? {
          return moneyManagementDao.getMoneyActionById(id)
     }

     override suspend fun insertMoneyAction(moneyManagement: MoneyManagement) {
          return moneyManagementDao.insertMoneyAction(moneyManagement)
     }

     override suspend fun deleteMoneyAction(moneyManagement: MoneyManagement) {
          return moneyManagementDao.deleteMoneyAction(moneyManagement)
     }
}