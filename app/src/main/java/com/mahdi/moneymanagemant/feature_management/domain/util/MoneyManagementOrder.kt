package com.mahdi.moneymanagemant.feature_management.domain.util

sealed class MoneyManagementOrder(val orderType: OrderType) {
     class Date(orderType: OrderType) : MoneyManagementOrder(orderType)

     fun copy(orderType: OrderType): MoneyManagementOrder {
          return when (this) {
               is Date -> Date(orderType)
          }
     }
}
