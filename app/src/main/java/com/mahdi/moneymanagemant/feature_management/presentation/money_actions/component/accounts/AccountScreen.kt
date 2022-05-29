package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.accounts

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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mahdi.moneymanagemant.R
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.*
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.AccountRowFake

@Composable
fun AccountsBody(
    accounts: List<MoneyManagement>,
    onAccountClick: (Int) -> Unit = {},
) {
    StatementBody(
        modifier = Modifier.semantics { contentDescription = "Accounts Screen" },
        items = accounts,
        amounts = { account -> account.price.toFloat() },
        colors = { account -> Color(account.color) },
        amountsTotal = accounts.map { account -> account.price.toFloat() }.sum(),
        circleLabel = stringResource(R.string.total),
        rows = { account ->
            AccountRowFake(
                modifier = Modifier.clickable {
                    onAccountClick(account.id!!)
                },
                moneyManagement = account
            )
        }
    )
}

@Composable
fun SingleAccountBody(account: MoneyManagement) {
    StatementBody(
        items = listOf(account),
        colors = { Color(account.color) },
        amounts = { account.price.toFloat() },
        amountsTotal = account.price.toFloat(),
        circleLabel = account.name,
    ) { row ->
        SingleAccountRow(
            moneyManagement = row
        )
    }
}

@Composable
fun SingleAccountRow(
    modifier: Modifier = Modifier,
    moneyManagement: MoneyManagement,
) {
    SingleBaseRowAccount(
        modifier = modifier,
        color = Color(moneyManagement.color),
        title = moneyManagement.name,
        subtitle = moneyManagement.content,
        amount = moneyManagement.price.toFloat(),
        negative = false,
        date = moneyManagement.date,
        description = moneyManagement.content
    )
}

@Composable
private fun SingleBaseRowAccount(
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