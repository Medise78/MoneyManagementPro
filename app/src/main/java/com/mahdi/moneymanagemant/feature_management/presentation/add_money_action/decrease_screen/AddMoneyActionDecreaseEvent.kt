package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.decrease_screen

import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionEvent

sealed class AddMoneyActionDecreaseEvent {
     data class TitleDecrease(val text: String) : AddMoneyActionDecreaseEvent()
     data class ContentDecrease(val text: String) : AddMoneyActionDecreaseEvent()
     data class CardNumberDecrease(val number: String) : AddMoneyActionDecreaseEvent()
     data class PriceDecrease(val price: String) : AddMoneyActionDecreaseEvent()
     data class Date(val date : String) : AddMoneyActionDecreaseEvent()
     data class ChangeColorDecrease(val color: Int) : AddMoneyActionDecreaseEvent()
     object SavedActionDecrease : AddMoneyActionDecreaseEvent()
}