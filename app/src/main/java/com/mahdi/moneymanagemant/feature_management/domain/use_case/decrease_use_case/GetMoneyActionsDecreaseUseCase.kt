package com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case

import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.domain.repository.decrease_repository.MoneyManagementDecreaseRepository
import com.mahdi.moneymanagemant.feature_management.domain.util.MoneyManagementOrder
import com.mahdi.moneymanagemant.feature_management.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMoneyActionsDecreaseUseCase(
     private val repository: MoneyManagementDecreaseRepository
) {
     operator fun invoke(
          moneyManagementOrder: MoneyManagementOrder = MoneyManagementOrder.Date(OrderType.Ascending)
     ): Flow<List<MoneyManagementDecrease>> {
          return repository.getMoneyAction().map { moneyAction ->
               when (moneyManagementOrder.orderType) {
                    is OrderType.Ascending -> {
                         when (moneyManagementOrder) {
                              is MoneyManagementOrder.Date -> moneyAction.sortedBy { it.timeStampDecrease }
                         }
                    }
               }
          }
     }
}