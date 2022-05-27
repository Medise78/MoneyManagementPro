package com.mahdi.moneymanagemant.feature_management.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mahdi.moneymanagemant.feature_management.data.radly_data.UserData
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.decrease_screen.AddMoneyActionDecreaseScreen
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionScreen
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.RallyTabRow
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.accounts.AccountsBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.accounts.SingleAccountBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.bills.BillsBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.bills.SingleBillsBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.overview.OverviewBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.decrease_screen.MoneyActionsDecreaseViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.increase_screen.MoneyActionsViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.unit.RallyScreen
import com.mahdi.moneymanagemant.feature_management.presentation.util.Screen
import com.mahdi.moneymanagemant.ui.theme.RallyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContent {
               // A surface container using the 'background' color from the theme
               Surface(color = MaterialTheme.colors.background) {
                    RallyApp()
               }
          }
     }
}

@Composable
fun RallyApp() {
     RallyTheme {
          val allScreens = RallyScreen.values().toList()
          val navController = rememberNavController()
          var currentScreen by rememberSaveable {
               mutableStateOf(RallyScreen.Overview)
          }
          val bottomBarState = rememberSaveable { (mutableStateOf(true)) }


          Scaffold(
               topBar = {
                    RallyTabRow(
                         allScreens = allScreens,
                         onTabSelected = { screen ->
                              currentScreen = screen
                              navController.navigate(screen.name)
                         },
                         currentScreen = currentScreen
                    )
               }
          ) { innerPadding ->
               RallyNavHost(navController, modifier = Modifier.padding(innerPadding), toggleBottomBar = {bottomBarState.value = it})
          }
     }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RallyNavHost(
     navController: NavHostController,
     toggleBottomBar : (value : Boolean) -> Unit,
     modifier: Modifier = Modifier,
     viewModel: MoneyActionsViewModel = hiltViewModel(),
     viewModelDecrease: MoneyActionsDecreaseViewModel = hiltViewModel()
) {
     val state = viewModel.state.value
     val stateDecrease = viewModelDecrease.state.value
     NavHost(
          navController = navController,
          startDestination = RallyScreen.Overview.name,
          modifier = modifier
     ) {
          composable(RallyScreen.Overview.name) {
               OverviewBody(
                    onClickSeeAllAccounts = { navController.navigate(RallyScreen.Accounts.name) },
                    onClickSeeAllBills = { navController.navigate(RallyScreen.Bills.name) },
                    onAccountClick = { id ->
                         navigateToSingleAccount(navController, id)
                    },
                    onBillClick = { id ->
                         navigateToSingleBills(navController, id)
                    },
                    onClickAddBills = { navController.navigate(Screen.AddMoneyActionDecreaseScreen.route) },
                    onClickAddAccounts = { navController.navigate(Screen.AddMoneyActionIncreaseScreen.route) },
                    navController = navController,
               )
          }
          composable(RallyScreen.Accounts.name) {
               AccountsBody(accounts = state.moneyActions) { id ->
                    navigateToSingleAccount(navController = navController, accountId = id)
               }
          }
          composable(RallyScreen.Bills.name) {
               BillsBody(bills = stateDecrease.moneyActions) { id ->
                    navigateToSingleBills(navController, accountId = id)
               }
          }
          val billsName = RallyScreen.Bills.name
          composable(
               route = "$billsName/{id}",
               arguments = listOf(
                    navArgument("id") {
                         type = NavType.IntType
                    }
               ),
               deepLinks = listOf(
                    navDeepLink {
                         uriPattern = "rally://$billsName/{id}"
                    }
               ),
          ) { entry ->
               val billsName = entry.arguments?.getInt("id")
               val bill = stateDecrease.moneyActions.filter { it.idDecrease!!.equals(billsName) }
               bill.forEach { moneyManagement ->
                    SingleBillsBody(bill = moneyManagement)
               }
          }


          val accountsName = RallyScreen.Accounts.name
          composable(
               route = "$accountsName/{id}",
               arguments = listOf(
                    navArgument("id") {
                         type = NavType.IntType
                    }
               ),
               deepLinks = listOf(
                    navDeepLink {
                         uriPattern = "rally://$accountsName/{id}"
                    }
               ),
          ) { entry ->
               val accountName = entry.arguments?.getInt("id")
               val account = state.moneyActions.filter { it.id!!.equals(accountName) }
               account.forEach { moneyManagement ->
                    SingleAccountBody(account = moneyManagement)
               }
          }
          composable(
               Screen.AddMoneyActionIncreaseScreen.route + "?moneyManagementId={moneyManagementId}",
               arguments = listOf(
                    navArgument("moneyManagementId") {
                         type = NavType.IntType
                         defaultValue = -1
                    }
               )
          ) {
               val addMoneyActionViewModel :AddMoneyActionViewModel = hiltViewModel()
               val invoice :MoneyManagement = addMoneyActionViewModel.invoice
               AddMoneyActionScreen(navController = navController , invoice = invoice , toggleBottomBar = toggleBottomBar )
          }
          composable(
               Screen.AddMoneyActionDecreaseScreen.route + "?moneyManagementDecreaseId={moneyManagementDecreaseId}",
               arguments = listOf(
                    navArgument("moneyManagementDecreaseId") {
                         type = NavType.IntType
                         defaultValue = -1
                    }
               )
          ) {
               AddMoneyActionDecreaseScreen(navController)
          }
     }
}

private fun navigateToSingleAccount(navController: NavHostController, accountId: Int) {
     navController.navigate("${RallyScreen.Accounts.name}/$accountId")
}

private fun navigateToSingleBills(navController: NavHostController, accountId: Int) {
     navController.navigate("${RallyScreen.Bills.name}/$accountId")
}