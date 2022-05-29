package com.mahdi.moneymanagemant.feature_management.data.data_resource.money_increase_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement

@Database(
     entities = [MoneyManagement::class],
     version = 4,
     exportSchema = false
)
abstract class MoneyManagementDataBase : RoomDatabase() {
     abstract val moneyManagementDao: MoneyManagementDao

     companion object {
          const val MONEY_MANAGEMENT_DATABASE = "db_money_management"
     }
}