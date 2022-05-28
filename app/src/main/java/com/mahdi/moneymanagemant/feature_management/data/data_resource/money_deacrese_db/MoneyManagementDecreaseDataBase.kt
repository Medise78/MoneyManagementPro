package com.mahdi.moneymanagemant.feature_management.data.data_resource.money_deacrese_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease

@Database(
     entities = [MoneyManagementDecrease::class],
     version = 5,
     exportSchema = false
)
abstract class MoneyManagementDecreaseDataBase:RoomDatabase() {
     abstract val moneyManagementDecreaseDao:MoneyManagementDecreaseDao
     companion object{
          const val DATA_BASE_NAME = "db_money_management_decrease"
     }
}