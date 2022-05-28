package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.InvalidException
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.domain.use_case.increase_use_case.MoneyActionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMoneyActionViewModel @Inject constructor(
    private val moneyActionUseCases: MoneyActionUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _titleState = mutableStateOf(AddMoneyActionState())
    val titleState: State<AddMoneyActionState> = _titleState
    private val _contentState = mutableStateOf(AddMoneyActionState())
    val contentState: State<AddMoneyActionState> = _contentState
    private val _cardNumberState = mutableStateOf(AddMoneyActionState())
    val cardNumberState: State<AddMoneyActionState> = _cardNumberState
    private val _priceState = mutableStateOf(AddMoneyActionState())
    val priceState: State<AddMoneyActionState> = _priceState
    private val _dateState = mutableStateOf(AddMoneyActionState())
    val dateState: State<AddMoneyActionState> = _dateState
    private val _changeColor = mutableStateOf(MoneyManagement.colors.random().toArgb())
    val changeColor: State<Int> = _changeColor
    private val _sharedFlow = MutableSharedFlow<UiEvent>()
    val sharedFlow = _sharedFlow.asSharedFlow()
    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("moneyManagementId")?.let { moneyManagementId ->
            viewModelScope.launch {
                if (moneyManagementId != -1) {

                    moneyActionUseCases.getMoneyActionUseCase(moneyManagementId)
                        ?.also { moneyManagement ->
                            currentId = moneyManagement.id

                            _titleState.value = titleState.value.copy(
                                text = moneyManagement.name
                            )
                            _contentState.value = contentState.value.copy(
                                text = moneyManagement.content
                            )
                            _cardNumberState.value = cardNumberState.value.copy(
                                text = moneyManagement.cardNumber
                            )
                            _priceState.value = priceState.value.copy(
                                text = moneyManagement.price
                            )
                            _dateState.value = dateState.value.copy(
                                text = moneyManagement.date
                            )
                            _changeColor.value = moneyManagement.color
                        }
                }
            }
        }
    }

    fun onEvent(event: AddMoneyActionEvent) {
        when (event) {
            is AddMoneyActionEvent.Title -> {
                _titleState.value = titleState.value.copy(
                    text = event.text
                )
            }


            is AddMoneyActionEvent.Content -> {
                _contentState.value = contentState.value.copy(
                    text = event.text
                )
            }


            is AddMoneyActionEvent.CardNumber -> {
                _cardNumberState.value = cardNumberState.value.copy(
                    text = event.number
                )
            }


            is AddMoneyActionEvent.Price -> {
                _priceState.value = priceState.value.copy(
                    text = event.price
                )
            }


            is AddMoneyActionEvent.ChangeColor -> {
                _changeColor.value = event.color
            }


            is AddMoneyActionEvent.Date -> {
                _dateState.value = dateState.value.copy(
                    text = event.date
                )
            }


            is AddMoneyActionEvent.SavedNote -> {
                viewModelScope.launch {
                    try {
                        moneyActionUseCases.addMoneyActionUseCase(
                            MoneyManagement(
                                color = changeColor.value,
                                timeStamp = System.currentTimeMillis(),
                                content = contentState.value.text,
                                id = currentId,
                                name = titleState.value.text,
                                cardNumber = cardNumberState.value.text,
                                price = priceState.value.text,
                                date = dateState.value.text
                            )
                        )
                        _sharedFlow.emit(UiEvent.SavedMoneyAction)
                    } catch (e: InvalidException) {
                        _sharedFlow.emit(UiEvent.SnackBar(e.message ?: "Error"))
                    }
                }
            }
        }
    }

    sealed class UiEvent {

        class SnackBar(val message: String) : UiEvent()
        object SavedMoneyAction : UiEvent()
    }
}