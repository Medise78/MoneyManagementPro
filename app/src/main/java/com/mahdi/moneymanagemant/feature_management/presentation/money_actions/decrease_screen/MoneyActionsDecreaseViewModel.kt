package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.decrease_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case.MoneyActionDecreaseUseCases
import com.mahdi.moneymanagemant.feature_management.domain.util.MoneyManagementOrder
import com.mahdi.moneymanagemant.feature_management.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoneyActionsDecreaseViewModel @Inject constructor(
     private val useCases: MoneyActionDecreaseUseCases
) :ViewModel(){
     private val _state = mutableStateOf(MoneyActionsDecreaseState())
     val state: State<MoneyActionsDecreaseState> = _state

     private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
     val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

     private var getMoneyManagementJob: Job? = null
     private var recentlyMoneyActionDelete: MoneyManagementDecrease? = null


     init {
          getMoneyAction(MoneyManagementOrder.Date(OrderType.Ascending))
     }

     fun onEvent(event: MoneyActionsDecreaseEvent) {
          when (event) {
               is MoneyActionsDecreaseEvent.DeleteMoneyAction -> {
                    viewModelScope.launch {
                         useCases.deleteMoneyActionDecreaseUseCase(event.moneyManagementDecrease)
                         recentlyMoneyActionDelete = event.moneyManagementDecrease
                    }
               }
               is MoneyActionsDecreaseEvent.RestoreMoneyAction -> {
                    viewModelScope.launch {
                         useCases.addMoneyActionDecreaseUseCase(
                              recentlyMoneyActionDelete ?: return@launch
                         )
                         recentlyMoneyActionDelete = null
                    }
               }
          }
     }

     private fun getMoneyAction(moneyManagementOrder: MoneyManagementOrder) {
          getMoneyManagementJob?.cancel()
          getMoneyManagementJob = useCases.getMoneyActionsDecreaseUseCase(moneyManagementOrder)
               .onEach { moneyActions ->
                    _state.value = state.value.copy(
                         moneyActions = moneyActions
                    )
               }.launchIn(viewModelScope)
     }

     fun onItemExpanded(cardId: Int) {
          if (_revealedCardIdsList.value.contains(cardId)) return
          _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
               list.add(cardId)
          }
     }

     fun onItemCollapsed(cardId: Int) {
          if (!_revealedCardIdsList.value.contains(cardId)) return
          _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
               list.remove(cardId)
          }
     }
}