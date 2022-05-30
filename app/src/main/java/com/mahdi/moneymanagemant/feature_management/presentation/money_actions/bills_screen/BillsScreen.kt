package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.bills

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.mahdi.moneymanagemant.R
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.AccountIndicatorFake
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.BillRowFake
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.StatementBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.formatAmount

@Composable
fun BillsBody(
    bills: List<MoneyManagementDecrease>,
    onBillsClick: (Int) -> Unit = {}
) {
    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Bills" },
        items = bills,
        amounts = { bill -> bill.priceDecrease.toFloat() },
        colors = { bill -> Color(bill.colorDecrease) },
        amountsTotal = bills.map { bill -> bill.priceDecrease.toFloat() }.sum(),
        circleLabel = stringResource(R.string.due),
        rows = { bill ->
            BillRowFake(
                modifier = Modifier.clickable { onBillsClick(bill.idDecrease!!) },
                moneyManagementDecrease = bill
            )
        },
    )
}

@Composable
fun SingleBillsBody(bill: MoneyManagementDecrease) {
    StatementBody(
        items = listOf(bill),
        colors = { Color(bill.colorDecrease) },
        amounts = { bill.priceDecrease.toFloat() },
        amountsTotal = bill.priceDecrease.toFloat(),
        circleLabel = bill.nameDecrease,
    ) { row ->
        SingleBillRow(
            moneyManagementDecrease = row,
            modifier = Modifier
        )
    }
}

@Composable
fun SingleBillRow(
    modifier: Modifier = Modifier,
    moneyManagementDecrease: MoneyManagementDecrease,
) {
    SingleBaseRowBill(
        modifier = modifier,
        color = Color(moneyManagementDecrease.colorDecrease),
        title = moneyManagementDecrease.nameDecrease,
        subtitle = moneyManagementDecrease.cardNumberDecrease,
        amount = moneyManagementDecrease.priceDecrease.toFloat(),
        negative = false,
        date = moneyManagementDecrease.dateDecrease,
        description = moneyManagementDecrease.contentDecrease
    )
}

@Composable
private fun SingleBaseRowBill(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    subtitle: String,
    amount: Float,
    date: String,
    negative: Boolean,
    description: String,
) {
    val dollarSign = if (negative) "–$ " else "$ "
    val formattedAmount = formatAmount(amount)

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.animateContentSize(animationSpec = tween(200)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier
            .height(75.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(animationSpec = tween(100))) {
            Row(
                modifier = modifier
                    .height(70.dp)
                    .clearAndSetSemantics {
                        contentDescription =
                            "$title account ending in ${
                                subtitle.takeLast(5)
                            }, current balance $dollarSign$formattedAmount"
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val typography = MaterialTheme.typography
                AccountIndicatorFake(
                    color = color,
                    modifier = Modifier
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = title, style = typography.body1)

                }
                Spacer(Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dollarSign,
                            style = typography.h6,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text(
                            text = formattedAmount,
                            style = typography.h6,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(Modifier.width(16.dp))
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Icon(
                        imageVector = Icons.Filled.Description,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp)
                    )
                }
            }
        }
        AnimatedVisibility(visible = expanded) {
            Box(modifier = Modifier.height(75.dp)) {
                Row(
                    modifier = modifier
                        .height(70.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val typography = MaterialTheme.typography

                    Spacer(Modifier.width(12.dp))
                    Column(Modifier) {
                        Text(text = "Description", style = typography.body1)
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium
                        ) {
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = subtitle, style = typography.subtitle1)
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 12.dp, top = 10.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = date,
                                style = typography.h6
                            )
                        }
                    }
                }
            }
        }
    }
}