package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.overview_screen.component.DraggableCardDecrease

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.BillRowFake
import com.mahdi.moneymanagemant.ui.theme.DarkBlue900
import com.mahdi.moneymanagemant.ui.theme.Green
import kotlin.math.roundToInt

const val ANIMATION_DURATION = 500
const val MIN_DRAG_AMOUNT = 6

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCardDecrease(
    moneyManagement: MoneyManagementDecrease,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onBillClick: (Int) -> Unit
) {

    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val cardBgColor by transition.animateColor(
        label = "cardBgColorTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (isRevealed) Green else DarkBlue900
        }
    )
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset else 0f },

        )
    val cardElevation by transition.animateDp(
        label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) 40.dp else 2.dp }
    )

    Card(
        modifier = Modifier
             .fillMaxWidth()
             .fillMaxHeight()
             .padding(2.dp)
             .padding(vertical = 5.dp)
             .offset { IntOffset(offsetTransition.roundToInt(), 0) }
             .pointerInput(Unit) {
                  detectHorizontalDragGestures { _, dragAmount ->
                       when {
                            dragAmount >= MIN_DRAG_AMOUNT -> onExpand()
                            dragAmount < -MIN_DRAG_AMOUNT -> onCollapse()
                       }
                  }
             },
        backgroundColor = cardBgColor,
        shape = RoundedCornerShape(0.dp),
        elevation = cardElevation,
    ) {
        BillRowFake(moneyManagementDecrease = moneyManagement, modifier = Modifier.clickable {
            onBillClick(moneyManagement.idDecrease!!)
            onCollapse()
        })
    }
}