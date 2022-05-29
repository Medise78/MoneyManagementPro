package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionEvent
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionViewModel

@Composable
fun RadioButtonsIncrease(viewModel: AddMoneyActionViewModel = hiltViewModel()) {
    val radioOption = listOf("Entertainment", "Social & LifeStyle", "Beauty & Health", "Other")
    Box(
        modifier = Modifier
              .height(300.dp)
              .fillMaxWidth(0.85f)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            RoundedCornerShape(25.dp), elevation = 5.dp
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                          .fillMaxWidth(0.1f)
                          .fillMaxHeight()
                          .weight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MoneyManagement.colors.forEach { color ->
                        Card(
                            modifier = Modifier
                                  .height(25.dp)
                                  .width(8.dp), RoundedCornerShape(10.dp)
                        ) {
                            Divider(color = color)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                          .fillMaxWidth(0.9f)
                          .weight(3.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    radioOption.forEach { text ->
                        Row(
                            modifier = Modifier
                                  .fillMaxWidth()
                                  .height(74.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = text, modifier = Modifier)
                        }
                        Divider()
                    }
                }
                Column(
                    modifier = Modifier
                          .fillMaxWidth(0.1f)
                          .fillMaxHeight()
                          .weight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MoneyManagement.colors.forEach { color ->
                        val colorInt = color.toArgb()
                        Box(
                            modifier = Modifier
                                  .size(22.dp)
                                  .shadow(15.dp, CircleShape)
                                  .clip(
                                        CircleShape
                                  )
                                  .background(Color.White)
                                  .border(
                                        width = 5.dp,
                                        color = if (viewModel.changeColor.value == colorInt) color else Color.White,
                                        shape = CircleShape
                                  )
                                  .clickable {
                                        viewModel.onEvent(
                                              AddMoneyActionEvent.ChangeColor(
                                                    colorInt
                                              )
                                        )
                                  }
                        )
                    }
                }
            }
        }
    }
}