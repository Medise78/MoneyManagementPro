package com.mahdi.moneymanagemant.feature_management.domain.use_case.increase_use_case

import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.domain.repository.increase_repository.MoneyManagementRepository
import com.mahdi.moneymanagemant.feature_management.domain.util.MoneyManagementOrder
import com.mahdi.moneymanagemant.feature_management.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMoneyActionsUseCase(
     private val repository: MoneyManagementRepository
) {
     operator fun invoke(
          moneyManagementOrder: MoneyManagementOrder = MoneyManagementOrder.Date(OrderType.Ascending)
     ): Flow<List<MoneyManagement>> {
          return repository.getMoneyAction().map { moneyAction ->
               when (moneyManagementOrder.orderType) {
                    is OrderType.Ascending -> {
                         when (moneyManagementOrder) {
                              is MoneyManagementOrder.Date -> moneyAction.sortedBy { it.timeStamp }
                         }
                    }
               }
          }
     }
}