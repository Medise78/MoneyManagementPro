/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.overview.getCard
import com.mahdi.moneymanagemant.feature_management.presentation.util.creditCardFormatted
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


@Composable
fun AccountRowFake(
    modifier: Modifier = Modifier,
    moneyManagement: MoneyManagement,
) {
    BaseRowAccount(
        modifier = modifier ,
        color = Color(moneyManagement.color) ,
        title = moneyManagement.name ,
        subtitle = getCard(moneyManagement.cardNumber)  ,
        amount = moneyManagement.price.toFloat() ,
        negative = false ,
        date = moneyManagement.date ,
        description = moneyManagement.content
    )
}

@Composable
private fun BaseRowAccount(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    subtitle: String,
    amount: Float,
    date: String,
    negative: Boolean,
    description: String
) {
    val dollarSign = if (negative) "–$ " else "$ "
    val formattedAmount = formatAmount(amount)

            Column(horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.height(75.dp)){
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
                Column(Modifier) {
                    Text(text = title, style = typography.body1)
                    Spacer(modifier = Modifier.padding(5.dp))
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(text = subtitle.creditCardFormatted, style = typography.subtitle1 , maxLines = 1 , overflow = TextOverflow.Ellipsis)
                    }
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
                    Text(
                        text = date, style = MaterialTheme.typography.body1, fontSize = 11.sp,
                        modifier = Modifier
                            .alpha(0.7f)
                            .align(Alignment.End)
                    )
                }

                Spacer(Modifier.width(16.dp))

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp)
                    )
                }
            }
            }
    }
    RallyDivider()
}

@Composable
private fun BaseRowBills(
       modifier: Modifier = Modifier,
       color: Color,
       title: String,
       subtitle: String,
       amount: Float,
       date: String,
       negative: Boolean,
       description: String
) {
    val dollarSign = if (negative) "–$ " else "$ "
    val formattedAmount = formatAmount(amount)

    Column(horizontalAlignment = Alignment.CenterHorizontally ,
           verticalArrangement = Arrangement.SpaceBetween) {
        Box(modifier = Modifier.height(75.dp)){
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
                Column(Modifier) {
                    Text(text = title, style = typography.body1)
                    Spacer(modifier = Modifier.padding(5.dp))
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(text = if (subtitle.length > 16) "${subtitle.take(16)} ..." else subtitle, style = typography.subtitle1 , maxLines = 1 , overflow = TextOverflow.Ellipsis)
                    }
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
                    Text(
                        text = date, style = MaterialTheme.typography.body1, fontSize = 11.sp,
                        modifier = Modifier
                            .alpha(0.7f)
                            .align(Alignment.End)
                    )
                }

                Spacer(Modifier.width(16.dp))

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp)
                    )
                }
            }
        }
    }
    RallyDivider()
}

@Composable
fun AccountIndicatorFake(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(4.dp , 36.dp)
            .background(color = color)
    )
}

@Composable
fun BillRowFake(
    modifier: Modifier = Modifier,
    moneyManagementDecrease: MoneyManagementDecrease,
) {
    BaseRowBills(
        modifier = modifier,
        color = Color(moneyManagementDecrease.colorDecrease),
        title = moneyManagementDecrease.nameDecrease,
        subtitle = moneyManagementDecrease.cardNumberDecrease ,
        amount = moneyManagementDecrease.priceDecrease.toFloat(),
        negative = true,
        date = moneyManagementDecrease.dateDecrease,
        description = moneyManagementDecrease.contentDecrease
    )
}
@Composable
private fun AccountIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(4.dp , 36.dp)
            .background(color = color)
    )
}

@Composable
fun RallyDivider(modifier: Modifier = Modifier) {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp, modifier = modifier)
}

fun formatAmount(amount: Float): String {
    return AmountDecimalFormat.format(amount)
}

private val AmountDecimalFormat = DecimalFormat("#,###.##")

fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

