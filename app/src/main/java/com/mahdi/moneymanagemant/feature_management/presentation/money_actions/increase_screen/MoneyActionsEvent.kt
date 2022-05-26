package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.increase_screen

import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement

sealed class MoneyActionsEvent {
     data class DeleteMoneyAction(val moneyManagement: MoneyManagement): MoneyActionsEvent()
     object RestoreMoneyAction: MoneyActionsEvent()
}