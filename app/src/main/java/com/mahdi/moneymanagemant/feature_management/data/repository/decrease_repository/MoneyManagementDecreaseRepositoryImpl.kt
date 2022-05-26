package com.mahdi.moneymanagemant.feature_management.data.repository.decrease_repository

import com.mahdi.moneymanagemant.feature_management.data.data_resource.money_deacrese_db.MoneyManagementDecreaseDao
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.domain.repository.decrease_repository.MoneyManagementDecreaseRepository
import kotlinx.coroutines.flow.Flow

class MoneyManagementDecreaseRepositoryImpl(
     private val moneyManagementDecreaseDao: MoneyManagementDecreaseDao
) : MoneyManagementDecreaseRepository {
     override fun getMoneyAction(): Flow<List<MoneyManagementDecrease>> {
          return moneyManagementDecreaseDao.getMoneyAction()
     }

     override suspend fun getMoneyActionById(id: Int): MoneyManagementDecrease? {
          return moneyManagementDecreaseDao.getMoneyActionById(id)
     }

     override suspend fun insertMoneyAction(moneyManagementDecrease: MoneyManagementDecrease) {
          return moneyManagementDecreaseDao.insertMoneyAction(moneyManagementDecrease)
     }

     override suspend fun deleteMoneyAction(moneyManagementDecrease: MoneyManagementDecrease) {
          return moneyManagementDecreaseDao.deleteMoneyAction(moneyManagementDecrease)
     }
}