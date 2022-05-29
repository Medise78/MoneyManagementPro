package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.decrease_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component.*
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@Composable
fun AddMoneyActionDecreaseScreen(
    navController: NavController,
    viewModel: AddMoneyActionDecreaseViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value
    val priceState = viewModel.priceState.value
    val cardNumberState = viewModel.cardNumberState.value
    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                is AddMoneyActionDecreaseViewModel.UiEventDecrease.SnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is AddMoneyActionDecreaseViewModel.UiEventDecrease.SavedMoneyAction -> {
                    navController.navigateUp()

                }
            }
        }
    }
    Scaffold(floatingActionButton = {}, scaffoldState = scaffoldState) {
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
                                                AddMoneyActionDecreaseEvent.TitleDecrease(
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
                            RadioButtons()
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
                                                    AddMoneyActionDecreaseEvent.PriceDecrease(it)
                                                )
                                            },
                                            keyboardType = KeyboardType.Number,
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.height(40.dp))
                            MyContentDecrease()
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
                                                AddMoneyActionDecreaseEvent.ContentDecrease(
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
                                        labelText = "Description...",
                                        text = cardNumberState.text,
                                        textChange = {
                                            viewModel.onEvent(
                                                AddMoneyActionDecreaseEvent.CardNumberDecrease(
                                                    it
                                                )
                                            )
                                        },
                                        keyboardType = KeyboardType.Text,
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
                SaveButton()
            }
        }
    }
}

