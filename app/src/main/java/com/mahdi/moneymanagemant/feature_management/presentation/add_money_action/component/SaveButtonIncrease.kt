package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionEvent
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionViewModel

@Composable
fun SaveButtonIncrease(viewModel: AddMoneyActionViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
              .fillMaxHeight()
              .fillMaxWidth(0.85f)
              .offset(y = 20.dp), contentAlignment = Alignment.TopCenter
    ) {
        Card(modifier = Modifier.fillMaxSize(), RoundedCornerShape(35.dp)) {
            Box(modifier = Modifier
                  .fillMaxWidth()
                  .clickable {
                        viewModel.onEvent(AddMoneyActionEvent.SavedNote)
                  }, contentAlignment = Alignment.Center) {
                Text(
                    text = "Save",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

        }
    }
}