package com.mahdi.moneymanagemant.feature_management.data.data_resource.money_increase_db

import androidx.room.*
import com.mahdi.moneymanagemant.feature_management.domain.model.money_increase_model.MoneyManagement
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyManagementDao {
     @Query("SELECT * FROM moneymanagement")
     fun getMoneyAction(): Flow<List<MoneyManagement>>

     @Query("SELECT * FROM moneymanagement WHERE id = :id")
     suspend fun getMoneyActionById(id: Int): MoneyManagement?

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertMoneyAction(moneyManagement: MoneyManagement)

     @Delete
     suspend fun deleteMoneyAction(moneyManagement: MoneyManagement)
}