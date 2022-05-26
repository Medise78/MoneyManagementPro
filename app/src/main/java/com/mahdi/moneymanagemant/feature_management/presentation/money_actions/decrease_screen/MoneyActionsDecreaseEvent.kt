package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.decrease_screen

import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease

sealed class MoneyActionsDecreaseEvent {
     data class DeleteMoneyAction(val moneyManagementDecrease: MoneyManagementDecrease): MoneyActionsDecreaseEvent()
     object RestoreMoneyAction: MoneyActionsDecreaseEvent()
}