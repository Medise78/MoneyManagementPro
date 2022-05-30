package com.mahdi.moneymanagemant.feature_management.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.decrease_screen.AddMoneyActionDecreaseScreen
import com.mahdi.moneymanagemant.feature_management.presentation.add_money_action.increaseScreen.AddMoneyActionScreen
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.RallyTabRow
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.accounts.AccountsBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.accounts.SingleAccountBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.bills.BillsBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.bills.SingleBillsBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.component.overview.OverviewBody
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.decrease_screen.MoneyActionsDecreaseViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.increase_screen.MoneyActionsViewModel
import com.mahdi.moneymanagemant.feature_management.presentation.money_actions.unit.RallyScreen
import com.mahdi.moneymanagemant.feature_management.presentation.splash_screen.SplashScreen
import com.mahdi.moneymanagemant.feature_management.presentation.util.Screen
import com.mahdi.moneymanagemant.ui.theme.RallyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContent {
               Surface(color = MaterialTheme.colors.background) {
                    RallyApp()
               }
          }
     }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RallyApp() {
     RallyTheme {
          val allScreens = RallyScreen.values().toList()
          val navController = rememberNavController()
          val scope = rememberCoroutineScope()
          var currentScreen by rememberSaveable {
               mutableStateOf(RallyScreen.Overview)
          }

               Scaffold(
                    topBar = {
                         AnimatedVisibility(visible = currentRoute(navController = navController) != Screen.SplashScreen.route , enter = expandVertically()) {
                              RallyTabRow(
                                   allScreens = allScreens,
                                   onTabSelected = { screen ->
                                        currentScreen = screen
                                        navController.navigate(screen.name){
                                             navController.popBackStack()
                                        }
                                   },
                                   currentScreen = currentScreen,
                              )
                         }

                    }
               ) { innerPadding ->
                    RallyNavHost(navController, modifier = Modifier.padding(innerPadding))
               }
     }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RallyNavHost(
     navController: NavHostController,
     modifier: Modifier = Modifier,
     viewModel: MoneyActionsViewModel = hiltViewModel(),
     viewModelDecrease: MoneyActionsDecreaseViewModel = hiltViewModel()
) {
     val state = viewModel.state.value
     val stateDecrease = viewModelDecrease.state.value
     NavHost(
          navController = navController,
          startDestination = Screen.SplashScreen.route,
          modifier = modifier
     ) {
          composable(Screen.SplashScreen.route){
               SplashScreen(navController = navController)
          }
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
               AddMoneyActionScreen(navController = navController)
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

@Composable
private fun currentRoute(navController : NavController):String?{
     val navBackStackEntry by navController.currentBackStackEntryAsState()
     return navBackStackEntry?.destination?.route
}