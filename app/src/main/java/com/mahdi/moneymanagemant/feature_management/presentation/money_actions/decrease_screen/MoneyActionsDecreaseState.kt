package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.decrease_screen

import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease

data class MoneyActionsDecreaseState(
     val moneyActions: List<MoneyManagementDecrease> = emptyList(),
     val changeAccountMoney: Float? = null,
     val changeUsedPrice: Int? = null,
)