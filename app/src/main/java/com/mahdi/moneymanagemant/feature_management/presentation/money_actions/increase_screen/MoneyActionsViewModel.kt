package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.increase_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.domain.use_case.increase_use_case.MoneyActionUseCases
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
class MoneyActionsViewModel @Inject constructor(
     private val moneyActionUseCases: MoneyActionUseCases
) : ViewModel() {
     private val _state = mutableStateOf(MoneyActionsState())
     val state: State<MoneyActionsState> = _state

     private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
     val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

     private var getMoneyManagementJob: Job? = null
     private var recentlyMoneyActionDelete: MoneyManagement? = null

     init {
          getMoneyAction(MoneyManagementOrder.Date(OrderType.Ascending))
     }

     fun onEvent(event: MoneyActionsEvent) {
          when (event) {
               is MoneyActionsEvent.DeleteMoneyAction -> {
                    viewModelScope.launch {
                         moneyActionUseCases.deleteMoneyActionUseCase(event.moneyManagement)
                         recentlyMoneyActionDelete = event.moneyManagement
                    }
               }
               is MoneyActionsEvent.RestoreMoneyAction -> {
                    viewModelScope.launch {
                         moneyActionUseCases.addMoneyActionUseCase(
                              recentlyMoneyActionDelete ?: return@launch
                         )
                         recentlyMoneyActionDelete = null
                    }
               }
          }
     }

     fun getMoneyAction(moneyManagementOrder: MoneyManagementOrder) {
          getMoneyManagementJob?.cancel()
          getMoneyManagementJob = moneyActionUseCases.getMoneyActionsUseCase(moneyManagementOrder)
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
          navOptions {
               launchSingleTop = true
               this.restoreState = true
               restoreState = true
          }
     }

     fun onItemCollapsed(cardId: Int) {
          if (!_revealedCardIdsList.value.contains(cardId)) return
          _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
               list.remove(cardId)
          }
          navOptions {
               launchSingleTop = true
               this.restoreState = true
               restoreState = true

          }
     }
}