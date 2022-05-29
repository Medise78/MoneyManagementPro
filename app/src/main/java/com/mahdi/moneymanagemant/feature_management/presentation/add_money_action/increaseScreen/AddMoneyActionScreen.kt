@file:Suppress("UNUSED_EXPRESSION")

package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component.MyContent
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component.RadioButtonsIncrease
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component.SaveButtonIncrease
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component.TextFieldCustom
import kotlinx.coroutines.flow.collectLatest
import java.text.DecimalFormat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@Composable
fun AddMoneyActionScreen(
    viewModel: AddMoneyActionViewModel = hiltViewModel(),
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value
    val cardNumberState = viewModel.cardNumberState.value
    val priceState = viewModel.priceState.value

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                is AddMoneyActionViewModel.UiEvent.SnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is AddMoneyActionViewModel.UiEvent.SavedMoneyAction -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                 .fillMaxSize()
                 .background(Color.DarkGray)
                 .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                     .fillMaxWidth()
                     .fillMaxHeight(0.88f), contentAlignment = Alignment.Center
            ) {
                Column {
                    Box(
                        modifier = Modifier
                             .fillMaxSize()
                             .offset(x = 30.dp)
                    ) {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            Box(modifier = Modifier.padding(bottom = 15.dp)) {

                                Text(
                                    text = "Title :",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )


                            }
                            Card(
                                modifier = Modifier
                                     .fillMaxWidth(0.85f)
                                     .height(60.dp), RoundedCornerShape(15.dp), elevation = 5.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                         .fillMaxSize()
                                         .background(Color(0xFFF8F8F8)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    TextFieldCustom(
                                        labelText = "Enter Title",
                                        text = titleState.text,
                                        textChange = {
                                            viewModel.onEvent(
                                                AddMoneyActionEvent.Title(
                                                    it
                                                )
                                            )
                                        },
                                        keyboardType = KeyboardType.Text,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            Box(modifier = Modifier.padding(bottom = 15.dp)) {
                                Text(
                                    text = "Category :",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                            RadioButtonsIncrease()
                            Spacer(modifier = Modifier.height(40.dp))
                            Column {
                                Box(modifier = Modifier.padding(bottom = 15.dp)) {
                                    Text(
                                        text = "Amount :",
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                                Card(
                                    modifier = Modifier
                                         .fillMaxWidth(0.85f)
                                         .height(60.dp),
                                    RoundedCornerShape(15.dp),
                                    elevation = 5.dp
                                ) {
                                    Box(
                                        modifier = Modifier
                                             .fillMaxSize()
                                             .background(Color(0xFFF8F8F8)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        TextFieldCustom(
                                            labelText = "Enter Price...",
                                            text = priceState.text,
                                            textChange = {
                                                viewModel.onEvent(
                                                    AddMoneyActionEvent.Price(it)
                                                )
                                            },
                                            keyboardType = KeyboardType.Number,
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            MyContent()
                            Spacer(modifier = Modifier.height(40.dp))
                            Box(modifier = Modifier.padding(bottom = 15.dp)) {
                                Text(
                                    text = "Description :",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                            Card(
                                modifier = Modifier
                                     .fillMaxWidth(0.85f)
                                     .height(60.dp), RoundedCornerShape(15.dp), elevation = 5.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                         .fillMaxSize()
                                         .background(Color(0xFFF8F8F8)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    TextFieldCustom(
                                        labelText = "Enter Description...",
                                        text = contentState.text,
                                        textChange = {
                                            viewModel.onEvent(
                                                AddMoneyActionEvent.Content(
                                                    it
                                                )
                                            )
                                        },
                                        keyboardType = KeyboardType.Text,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            Box(modifier = Modifier.padding(bottom = 15.dp)) {
                                Text(
                                    text = "Card Number :",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                            Card(
                                modifier = Modifier
                                     .fillMaxWidth(0.85f)
                                     .height(60.dp), RoundedCornerShape(15.dp), elevation = 5.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                         .fillMaxSize()
                                         .background(Color(0xFFF8F8F8)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    TextFieldCustom(
                                        labelText = "Enter Card Number...",
                                        text = cardNumberState.text,
                                        textChange = {
                                            viewModel.onEvent(
                                                AddMoneyActionEvent.CardNumber(
                                                    it
                                                )
                                            )
                                        },
                                        keyboardType = KeyboardType.Number,
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                     .fillMaxWidth()
                     .fillMaxHeight(0.6f),
                contentAlignment = Alignment.BottomCenter
            ) {
                SaveButtonIncrease()
            }

        }
    }
}