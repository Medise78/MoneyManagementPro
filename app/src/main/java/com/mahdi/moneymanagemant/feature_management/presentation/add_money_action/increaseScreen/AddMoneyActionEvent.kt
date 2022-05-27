package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen

sealed class AddMoneyActionEvent
{

      data class Title(val text : String) : AddMoneyActionEvent()
      data class Content(val text : String) : AddMoneyActionEvent()
      data class CardNumber(val number : String) : AddMoneyActionEvent()
      data class Price(val price : String) : AddMoneyActionEvent()
      data class Date(val date : String) : AddMoneyActionEvent()
      data class ChangeColor(val color : Int) : AddMoneyActionEvent()
      object SavedNote : AddMoneyActionEvent()
}