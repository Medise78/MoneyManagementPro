package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.decrease_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.InvalidExceptionDecrease
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case.MoneyActionDecreaseUseCases
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMoneyActionDecreaseViewModel @Inject constructor(
    private val useCase: MoneyActionDecreaseUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _titleState = mutableStateOf(AddMoneyActionDecreaseState())
    val titleState: State<AddMoneyActionDecreaseState> = _titleState
    private val _contentState = mutableStateOf(AddMoneyActionDecreaseState())
    val contentState: State<AddMoneyActionDecreaseState> = _contentState
    private val _cardNumberState = mutableStateOf(AddMoneyActionDecreaseState())
    val cardNumberState: State<AddMoneyActionDecreaseState> = _cardNumberState
    private val _priceState = mutableStateOf(AddMoneyActionDecreaseState())
    val priceState: State<AddMoneyActionDecreaseState> = _priceState
    private val _dateState = mutableStateOf(AddMoneyActionState())
    val dateState: State<AddMoneyActionState> = _dateState
    private val _changeColor =
        mutableStateOf(MoneyManagementDecrease.colorsDecrease.random().toArgb())
    val changeColor: State<Int> = _changeColor
    private val _sharedFlow = MutableSharedFlow<UiEventDecrease>()
    val sharedFlow = _sharedFlow.asSharedFlow()
    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("moneyManagementDecreaseId")?.let { moneyManagementDecreaseId ->
            viewModelScope.launch {
                if (moneyManagementDecreaseId != -1) {
                    useCase.getMoneyActionDecreaseUseCase(moneyManagementDecreaseId)
                        ?.also { moneyManagement ->
                            currentId = moneyManagement.idDecrease

                            _titleState.value = titleState.value.copy(
                                text = moneyManagement.nameDecrease
                            )
                            _contentState.value = contentState.value.copy(
                                text = moneyManagement.contentDecrease
                            )
                            _cardNumberState.value = cardNumberState.value.copy(
                                text = moneyManagement.cardNumberDecrease
                            )
                            _priceState.value = priceState.value.copy(
                                text = moneyManagement.priceDecrease
                            )
                            _dateState.value = dateState.value.copy(
                                text = moneyManagement.dateDecrease
                            )
                            _changeColor.value = moneyManagement.colorDecrease
                        }
                }
            }
        }
    }

    fun onEvent(event: AddMoneyActionDecreaseEvent) {
        when (event) {
            is AddMoneyActionDecreaseEvent.TitleDecrease -> {
                _titleState.value = titleState.value.copy(
                    text = event.text
                )
            }
            is AddMoneyActionDecreaseEvent.ContentDecrease -> {
                _contentState.value = contentState.value.copy(
                    text = event.text
                )
            }
            is AddMoneyActionDecreaseEvent.CardNumberDecrease -> {
                _cardNumberState.value = cardNumberState.value.copy(
                    text = event.number
                )
            }
            is AddMoneyActionDecreaseEvent.PriceDecrease -> {
                _priceState.value = priceState.value.copy(
                    text = event.price
                )
            }
            is AddMoneyActionDecreaseEvent.Date -> {
                _dateState.value = dateState.value.copy(
                    text = event.date)
            }
            is AddMoneyActionDecreaseEvent.ChangeColorDecrease -> {
                _changeColor.value = event.color
            }
            is AddMoneyActionDecreaseEvent.SavedActionDecrease -> {
                viewModelScope.launch {
                    try {
                        useCase.addMoneyActionDecreaseUseCase(
                            MoneyManagementDecrease(
                                colorDecrease = changeColor.value,
                                timeStampDecrease = System.currentTimeMillis(),
                                contentDecrease = contentState.value.text,
                                idDecrease = currentId,
                                nameDecrease = titleState.value.text,
                                cardNumberDecrease = cardNumberState.value.text,
                                priceDecrease = priceState.value.text,
                                dateDecrease = dateState.value.text
                            )
                        )
                        _sharedFlow.emit(UiEventDecrease.SavedMoneyAction)
                    } catch (e: InvalidExceptionDecrease) {
                        _sharedFlow.emit(UiEventDecrease.SnackBar(e.message ?: "Error"))
                    }
                }
            }
        }
    }

    sealed class UiEventDecrease {
        class SnackBar(val message: String) : UiEventDecrease()
        object SavedMoneyAction : UiEventDecrease()
    }
}