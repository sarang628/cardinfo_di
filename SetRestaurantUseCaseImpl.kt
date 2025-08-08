package com.sarang.torang.di.cardinfo_di

import com.sarang.torang.di.repository.repository.impl.FindRepositoryImpl
import com.sarang.torang.usecase.cardinfo.SetRestaurantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class SetRestaurantUseCaseImpl {
    @Provides
    fun provideSetRestaurantUseCase(findRepository: FindRepositoryImpl): SetRestaurantUseCase {
        return object : SetRestaurantUseCase {
            override suspend fun invoke(restaurantId: Int) {
                findRepository.selectRestaurant(restaurantId)
            }
        }
    }
}