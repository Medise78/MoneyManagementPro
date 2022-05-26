package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.accounts

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.mahdi.moneymanagemant.R
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.StatementBody
import com.mahdi.moneymanagemant.feature_management.data.radly_data.Account
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.AccountRow
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.AccountRowFake

/**
 * The Accounts screen.
 */
@Composable
fun AccountsBody(
     accounts: List<MoneyManagement>,
     onAccountClick: (Int) -> Unit = {},
) {
     StatementBody(
          modifier = Modifier.semantics { contentDescription = "Accounts Screen" },
          items = accounts,
          amounts = { account -> account.price.toFloat() },
          colors = { account -> Color(account.color)},
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

/**
 * Detail screen for a single account.
 */
@Composable
fun SingleAccountBody(account: MoneyManagement) {
     StatementBody(
          items = listOf(account),
          colors = { Color(account.color) },
          amounts = { account.price.toFloat() },
          amountsTotal = account.price.toFloat(),
          circleLabel = account.name,
     ) { row ->
          AccountRowFake(
               moneyManagement = row
          )
     }
}