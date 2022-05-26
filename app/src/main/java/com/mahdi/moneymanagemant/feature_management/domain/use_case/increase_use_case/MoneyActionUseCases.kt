package com.mahdi.moneymanagemant.feature_management.domain.use_case.increase_use_case

data class MoneyActionUseCases(
     val addMoneyActionUseCase: AddMoneyActionUseCase,
     val deleteMoneyActionUseCase: DeleteMoneyActionUseCase,
     val getMoneyActionUseCase: GetMoneyActionUseCase,
     val getMoneyActionsUseCase: GetMoneyActionsUseCase
)
