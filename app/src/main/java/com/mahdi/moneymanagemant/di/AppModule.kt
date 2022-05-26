package com.mahdi.moneymanagemant.di

import android.app.Application
import androidx.room.Room
import com.mahdi.moneymanagemant.feature_management.data.data_resource.money_increase_db.MoneyManagementDataBase
import com.mahdi.moneymanagemant.feature_management.data.repository.increase_repository.MoneyManagementRepositoryImpl
import com.mahdi.moneymanagemant.feature_management.domain.repository.increase_repository.MoneyManagementRepository
import com.mahdi.moneymanagemant.feature_management.domain.use_case.increase_use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

     @Provides
     @Singleton
     fun providesMoneyManagementDatabase(app: Application): MoneyManagementDataBase {
          return Room.databaseBuilder(
               app,
               MoneyManagementDataBase::class.java,
               MoneyManagementDataBase.MONEY_MANAGEMENT_DATABASE
          ).fallbackToDestructiveMigration().build()
     }

     @Provides
     @Singleton
     fun providesMoneyManagementRepository(dataBase: MoneyManagementDataBase): MoneyManagementRepository {
          return MoneyManagementRepositoryImpl(dataBase.moneyManagementDao)
     }

     @Provides
     @Singleton
     fun providesMoneyManagementUseCase(repository: MoneyManagementRepository): MoneyActionUseCases {
          return MoneyActionUseCases(
               addMoneyActionUseCase = AddMoneyActionUseCase(repository),
               deleteMoneyActionUseCase = DeleteMoneyActionUseCase(repository),
               getMoneyActionsUseCase = GetMoneyActionsUseCase(repository),
               getMoneyActionUseCase = GetMoneyActionUseCase(repository)
          )
     }
}