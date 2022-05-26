package com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.overview.component

import androidx.compose.animation.*
import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun ActionsRow(
     actionIconSize: Dp,
     onDelete: () -> Unit,
     onEdit: () -> Unit,
) {
     Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
          AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
               IconButton(
                    modifier = Modifier
                         .size(actionIconSize)
                         .animateEnterExit(
                              slideInVertically(),
                              slideOutVertically(),
                         ),
                    onClick = {
                         onDelete()
                    },
                    content = {
                         Icon(
                              imageVector = Icons.Default.Delete,
                              tint = Color.Gray,
                              contentDescription = "delete action",
                         )
                    }
               )
          }

          IconButton(
               modifier = Modifier.size(actionIconSize),
               onClick = onEdit,
               content = {
                    Icon(
                         imageVector = Icons.Default.Edit,
                         tint = Color.Gray,
                         contentDescription = "edit action",
                    )
               }
          )
     }
}