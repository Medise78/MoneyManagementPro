package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.overview

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mahdi.moneymanagemant.R
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.increase_screen.MoneyActionsViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.*
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.overview_screen.component.ActionsRow
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.overview_screen.component.DraggableCardDecrease.DraggableCardDecrease
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.overview_screen.component.DraggableCardIncrease.DraggableCard
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.decrease_screen.MoneyActionsDecreaseEvent
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.decrease_screen.MoneyActionsDecreaseViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.increase_screen.MoneyActionsEvent
import com.mahdi.moneymanagemant.feature_management.presentation.util.Screen
import java.text.NumberFormat
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OverviewBody(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (Int) -> Unit = {},
    onBillClick: (Int) -> Unit = {},
    onClickAddAccounts: () -> Unit,
    onClickAddBills: () -> Unit = {},
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .semantics { contentDescription = "Overview Screen" }
        ) {
            AlertCard()
            Spacer(Modifier.height(RallyDefaultPadding))
            AccountsCard(
                navController,
                onClickSeeAllAccounts,
                onAccountClick = onAccountClick,
                onClickAdd = onClickAddAccounts
            )
            Spacer(Modifier.height(RallyDefaultPadding))
            BillsCard(
                onClickSeeAllBills,
                onClickAddBills,
                onBillClick,
                navController = navController
            )
        }
    }
}

@Composable
private fun AlertCard(
    increaseViewModel: MoneyActionsViewModel = hiltViewModel(),
    decreaseViewModel: MoneyActionsDecreaseViewModel = hiltViewModel(),
) {
    val increaseState = increaseViewModel.state.value
    val decreaseState = decreaseViewModel.state.value
    val priceDec = decreaseState.moneyActions.filter { it.nameDecrease.isNotBlank() }
        .sumOf { it.priceDecrease.toDouble() }
    val priceInc =
        increaseState.moneyActions.filter { it.name.isNotBlank() }.sumOf { it.price.toDouble() }
    val result = priceDec
    val def = NumberFormat.getPercentInstance().format(result)
    var showDialog by remember { mutableStateOf(false) }
    val alertMessage =
        "Heads up, you've saved ${result}  of your Shopping budget for this month."

    if (showDialog) {
        RallyAlertDialog(
            onDismiss = {
                showDialog = false
            },
            bodyText = alertMessage,
            buttonText = "Dismiss".uppercase(Locale.getDefault())
        )
    }
    Card {
        Column {
            AlertHeader {
                showDialog = true
            }
            RallyDivider(
                modifier = Modifier.padding(
                    start = RallyDefaultPadding,
                    end = RallyDefaultPadding
                )
            )
            AlertItem(alertMessage)
        }
    }
}

@Composable
private fun AlertHeader(onClickSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Alerts",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = onClickSeeAll,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "SEE ALL",
                style = MaterialTheme.typography.button,
            )
        }
    }
}

@Composable
private fun AlertItem(message: String) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = MaterialTheme.typography.body2,
            modifier = Modifier.weight(1f),
            text = message
        )
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.Top)
                .clearAndSetSemantics {}
        ) {
            Icon(Icons.Filled.Sort, contentDescription = null)
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun <T> OverviewScreenCardAccounts(
    navController: NavController,
    viewModel: MoneyActionsViewModel = hiltViewModel(),
    viewModelBills: MoneyActionsDecreaseViewModel = hiltViewModel(),
    title: String,
    date: String,
    amount: Double,
    onClickSeeAll: () -> Unit,
    onClickAdd: () -> Unit,
    onAccountClick: (Int) -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit,
) {

    val state = viewModel.state.value
    val revealedCardIds = viewModel.revealedCardIdsList.collectAsState()
    val billState = viewModelBills.state.value

    Card {
        Column {
            Column(Modifier.padding(RallyDefaultPadding)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(text = title, style = MaterialTheme.typography.subtitle2)
                        val amountText = "$" + formatAmount(
                            amount.toFloat()
                        )
                        Text(
                            text = amountText,
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.padding(top = 15.dp)
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(onClick = {
                            for (i in state.moneyActions) {
                                val id = i.id
                                viewModel.onItemCollapsed(id!!)
                            }
                            for (i in billState.moneyActions) {
                                val id = i.idDecrease
                                viewModelBills.onItemCollapsed(id!!)
                            }
                            onClickAdd()
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

            }
            if (amount.toFloat() == 0f) {
                Divider(color = Color.White)
            } else {
                OverViewDivider(data, values, colors)
            }

            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                if (amount.toFloat() == 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp), contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Add Action ➕", color = Color.White.copy(0.6f))
                    }
                } else {
                    LazyColumn(
                        Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    ) {
                        itemsIndexed(
                            state.moneyActions,
                            key = { _, item -> item.id!! }) { _, moneyManagement ->
                            Box(modifier = Modifier.fillMaxWidth()) {
                                ActionsRow(
                                    actionIconSize = 56.dp,
                                    onDelete = {
                                        viewModel.onEvent(
                                            MoneyActionsEvent.DeleteMoneyAction(
                                                moneyManagement
                                            )
                                        )
                                        moneyManagement.id?.let {
                                            viewModel.onItemCollapsed(it)
                                        }
                                    },
                                    onEdit = {
                                        navController.navigate(
                                            Screen.AddMoneyActionIncreaseScreen.route +
                                                    "?moneyManagementId=${moneyManagement.id}"
                                        )
                                        for (i in state.moneyActions) {
                                            val id = i.id
                                            viewModel.onItemCollapsed(id!!)
                                        }
                                        for (i in billState.moneyActions) {
                                            val id = i.idDecrease
                                            viewModelBills.onItemCollapsed(id!!)
                                        }
                                    },
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                DraggableCard(
                                    moneyManagement = moneyManagement,
                                    isRevealed = revealedCardIds.value.contains(
                                        moneyManagement.id!!
                                    ),
                                    cardOffset = 450f,
                                    onExpand = { viewModel.onItemExpanded(moneyManagement.id) },
                                    onCollapse = {
                                        viewModel.onItemCollapsed(
                                            moneyManagement.id
                                        )
                                    },
                                    onAccountClick = onAccountClick
                                )
                            }
                        }
                    }
                }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
private fun <T> OverviewScreenCardBills(
    viewModel: MoneyActionsDecreaseViewModel = hiltViewModel(),
    viewModelAccount: MoneyActionsViewModel = hiltViewModel(),
    navController: NavController,
    title: String,
    amount: Double,
    onClickSeeAll: () -> Unit,
    onClickAdd: () -> Unit,
    onBillsClick: (Int) -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit,
) {
    val state = viewModel.state.value
    val revealedCardIds = viewModel.revealedCardIdsList.collectAsState()
    val stateAcc = viewModelAccount.state.value

    Card {
        Column {
            Column(Modifier.padding(RallyDefaultPadding)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(text = title, style = MaterialTheme.typography.subtitle2)
                        val amountText = "$" + formatAmount(
                            amount.toFloat()
                        )
                        Text(
                            text = amountText,
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(onClick = {
                            onClickAdd()
                            for (i in state.moneyActions) {
                                val id = i.idDecrease
                                id?.let { it1 -> viewModel.onItemCollapsed(it1) }
                            }
                            for (i in stateAcc.moneyActions) {
                                val id = i.id
                                viewModelAccount.onItemCollapsed(id!!)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
            if (amount.toFloat() == 0f) {
                Divider(color = Color.White)
            } else {
                OverViewDivider(data, values, colors)
            }
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {

                if (amount.toFloat() == 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp), contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Add Action ➕", color = Color.White.copy(0.6f))
                    }
                } else {
                    LazyColumn(
                        Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    ) {
                        itemsIndexed(
                            state.moneyActions,
                            key = { _, item -> item.idDecrease!! }) { _, moneyManagement ->
                            Box(modifier = Modifier.fillMaxWidth()) {
                                ActionsRow(
                                    actionIconSize = 56.dp,
                                    onDelete = {
                                        viewModel.onEvent(
                                            MoneyActionsDecreaseEvent.DeleteMoneyAction(
                                                moneyManagement
                                            )
                                        )
                                    },
                                    onEdit = {
                                        navController.navigate(
                                            Screen.AddMoneyActionDecreaseScreen.route +
                                                    "?moneyManagementDecreaseId=${moneyManagement.idDecrease}"
                                        )
                                        for (i in state.moneyActions) {
                                            val id = i.idDecrease
                                            viewModel.onItemCollapsed(id!!)
                                        }
                                        for (i in stateAcc.moneyActions) {
                                            val id = i.id
                                            viewModelAccount.onItemCollapsed(id!!)
                                        }
                                    },
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                DraggableCardDecrease(
                                    moneyManagement = moneyManagement,
                                    isRevealed = revealedCardIds.value.contains(
                                        moneyManagement.idDecrease!!
                                    ),
                                    cardOffset = 450f,
                                    onExpand = { viewModel.onItemExpanded(moneyManagement.idDecrease) },
                                    onCollapse = {
                                        viewModel.onItemCollapsed(
                                            moneyManagement.idDecrease
                                        )
                                    },
                                    onBillClick = onBillsClick
                                )
                            }
                        }
                    }
                }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}


@Composable
private fun <T> OverViewDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color,
) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun AccountsCard(
    navController: NavController,
    onClickSeeAll: () -> Unit,
    onClickAdd: () -> Unit,
    onAccountClick: (Int) -> Unit,
    viewModel: MoneyActionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    OverviewScreenCardAccounts(
        navController = navController,
        title = stringResource(R.string.accounts),
        amount = state.moneyActions.filter { it.name.isNotBlank() }.sumOf { it.price.toDouble() },
        onClickSeeAll = onClickSeeAll,
        data = state.moneyActions,
        colors = { Color(it.color) },
        values = { it.price.toFloat() },
        onClickAdd = onClickAdd,
        onAccountClick = onAccountClick,
        date = state.moneyActions.find { true }?.date.toString()
    ) { account ->
        AccountRowFake(
            modifier = Modifier.clickable { onAccountClick(account.id!!) },
            moneyManagement = account
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BillsCard(
    onClickSeeAll: () -> Unit,
    onClickAdd: () -> Unit,
    onBillClick: (Int) -> Unit,
    viewModel: MoneyActionsDecreaseViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state.value

    OverviewScreenCardBills(
        navController = navController,
        title = stringResource(R.string.bills),
        amount = state.moneyActions.filter { it.nameDecrease.isNotBlank() }
            .sumOf { it.priceDecrease.toDouble() },
        onClickSeeAll = onClickSeeAll,
        data = state.moneyActions,
        colors = { Color(it.colorDecrease) },
        values = { it.priceDecrease.toFloat() },
        onClickAdd = onClickAdd,
        onBillsClick = onBillClick
    ) { bill ->
        BillRowFake(
            moneyManagementDecrease = bill
        )
    }
}

@Composable
private fun SeeAllButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.see_all))
    }
}

private val RallyDefaultPadding = 12.dp