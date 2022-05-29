package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.decrease_screen.AddMoneyActionDecreaseEvent
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.decrease_screen.AddMoneyActionDecreaseViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionEvent
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionViewModel
import java.util.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyContentDecrease(
    addMoneyActionDecViewModel: AddMoneyActionDecreaseViewModel = hiltViewModel(),
) {

    var state = addMoneyActionDecViewModel.dateState.value

    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )
    Column {
        Box(modifier = Modifier.padding(bottom = 15.dp)) {
            Text(
                text = "Set Date & Time :",
                fontFamily = FontFamily.SansSerif,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Card(
            modifier = Modifier
                  .fillMaxWidth(0.85f)
                  .clickable(onClick = {
                        mDatePickerDialog.show()
                  })
                  .height(55.dp), RoundedCornerShape(15.dp), elevation = 5.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                          .fillMaxSize()
                          .weight(4f)
                          .background(Color(0xFF6B6969))
                ) {
                    Box(
                        modifier = Modifier.matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        state.text = mDate.value

                        TextFieldCustom(
                            labelText = "Select Date",
                            text = state.text,
                            textChange = {
                                addMoneyActionDecViewModel.onEvent(
                                    AddMoneyActionDecreaseEvent.Date(it)
                                )
                            },
                            keyboardType = KeyboardType.Ascii,
                            modifier = Modifier.clickable(onClick = {
                                mDatePickerDialog.datePicker.showContextMenu()
                            })
                        )
                    }
                }
                Box(
                    modifier = Modifier
                          .fillMaxHeight()
                          .weight(1f)
                          .background(Color(0xFF6B6969))
                ) {
                    Button(modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6B6969)),
                        onClick = { mDatePickerDialog.show() }) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "")
                    }
                }
            }
        }
    }
}