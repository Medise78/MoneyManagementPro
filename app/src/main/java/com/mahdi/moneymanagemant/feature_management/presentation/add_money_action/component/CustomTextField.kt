package com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*



@Composable
fun MyContent() {

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
            { _: DatePicker , mYear: Int , mMonth: Int , mDayOfMonth: Int ->
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
                        .clickable { mDatePickerDialog.show() }
                        .height(55.dp) , RoundedCornerShape(15.dp) , elevation = 5.dp
            ) {
                  Box(
                        modifier = Modifier
                              .fillMaxSize()
                              .background(Color(0xFF6B6969))
                  ) {
                        Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                              Text(
                                    text = if (mDate.value == ""  ) "Open Date Picker" else "${mDate.value}" ,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = if (mDate.value == "")Color.White.copy(0.5f) else Color.White,
                              )
                        }
                  }
            }
      }
}