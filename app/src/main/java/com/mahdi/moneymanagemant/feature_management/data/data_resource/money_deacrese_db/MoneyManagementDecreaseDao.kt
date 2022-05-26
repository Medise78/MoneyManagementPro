package com.mahdi.moneymanagemant.feature_management.data.data_resource.money_deacrese_db

import androidx.room.*
import com.mahdi.moneymanagemant.feature_management.domain.model.money_decrease_model.MoneyManagementDecrease
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyManagementDecreaseDao {
     @Query("SELECT * FROM moneymanagementdecrease ORDER BY timeStampDecrease DESC")
     fun getMoneyAction(): Flow<List<MoneyManagementDecrease>>

     @Query("SELECT * FROM moneymanagementdecrease WHERE idDecrease = :id")
     suspend fun getMoneyActionById(id: Int): MoneyManagementDecrease?

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertMoneyAction(moneyManagementDecrease: MoneyManagementDecrease)

     @Delete
     suspend fun deleteMoneyAction(moneyManagementDecrease: MoneyManagementDecrease)
}