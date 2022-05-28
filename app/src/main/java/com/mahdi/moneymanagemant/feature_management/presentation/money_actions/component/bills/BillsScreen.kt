package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.bills

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import com.mahdi.moneymanagemant.R
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.BillRowFake
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.StatementBody

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
          description = bills.map { bills ->bills.contentDecrease }.toString()
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
          description = bill.contentDecrease
     ) { row ->
          BillRowFake(
               moneyManagementDecrease = row,
               modifier = Modifier
          )
     }
}