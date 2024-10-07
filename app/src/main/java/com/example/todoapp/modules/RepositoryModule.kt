package com.example.todoapp.modules

import com.example.todoapp.model.CaseRepository
import com.example.todoapp.model.CaseRepositoryImpl
import com.example.todoapp.model.NotificationRepository
import com.example.todoapp.model.NotificationRepositoryImpl
import com.example.todoapp.model.CitasRepository
import com.example.todoapp.model.CitasRepositoryImpl
import com.example.todoapp.model.UserRepository
import com.example.todoapp.model.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindCaseRepository(
        caseRepositoryImpl: CaseRepositoryImpl
    ): CaseRepository

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    abstract fun bindCitasRepository(
        citasRepositoryImpl: CitasRepositoryImpl
    ): CitasRepository

}
