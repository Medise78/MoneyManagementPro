package com.mahdi.moneymanagemant.di

import android.app.Application
import androidx.room.Room
import com.mahdi.moneymanagemant.feature_management.data.data_resource.money_deacrese_db.MoneyManagementDecreaseDataBase
import com.mahdi.moneymanagemant.feature_management.data.repository.decrease_repository.MoneyManagementDecreaseRepositoryImpl
import com.mahdi.moneymanagemant.feature_management.domain.repository.decrease_repository.MoneyManagementDecreaseRepository
import com.mahdi.moneymanagemant.feature_management.domain.use_case.decrease_use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DecreaseAppModule {

     @Provides
     @Singleton
     fun providesMoneyManagementDecrease(app: Application): MoneyManagementDecreaseDataBase {
          return Room.databaseBuilder(
               app,
               MoneyManagementDecreaseDataBase::class.java,
               MoneyManagementDecreaseDataBase.DATA_BASE_NAME
          ).fallbackToDestructiveMigration().build()
     }

     @Provides
     @Singleton
     fun providesMoneyManagementDecreaseRepository(dataBase: MoneyManagementDecreaseDataBase): MoneyManagementDecreaseRepository {
          return MoneyManagementDecreaseRepositoryImpl(dataBase.moneyManagementDecreaseDao)
     }

     @Provides
     @Singleton
     fun providesMoneyManagementDecreaseUseCases(repository: MoneyManagementDecreaseRepository): MoneyActionDecreaseUseCases {
          return MoneyActionDecreaseUseCases(
               addMoneyActionDecreaseUseCase = AddMoneyActionDecreaseUseCase(repository),
               deleteMoneyActionDecreaseUseCase = DeleteMoneyActionDecreaseUseCase(repository),
               getMoneyActionDecreaseUseCase = GetMoneyActionDecreaseUseCase(repository),
               getMoneyActionsDecreaseUseCase = GetMoneyActionsDecreaseUseCase(repository)
          )
     }
}