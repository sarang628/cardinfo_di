package com.sarang.torang.di.cardinfo_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.compose.cardinfo.RestaurantCardUIState
import com.sarang.torang.di.repository.repository.impl.FindRepositoryImpl
import com.sarang.torang.usecase.cardinfo.GetCurrentRestaurantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@InstallIn(SingletonComponent::class)
@Module
class GetCurrentRestaurantUseCaseImpl {
    @Provides
    fun provideGetCurrentRestaurantUseCase(findRepository: FindRepositoryImpl): GetCurrentRestaurantUseCase {
        return object : GetCurrentRestaurantUseCase {
            override fun invoke(coroutineScope: CoroutineScope): StateFlow<RestaurantCardUIState> {
                return findRepository.selectedRestaurant.map {
                    RestaurantCardUIState(
                        restaurantId = it.restaurant.restaurantId,
                        restaurantName = it.restaurant.restaurantName,
                        rating = it.restaurant.rating,
                        foodType = it.restaurant.restaurantType,
                        restaurantImage = BuildConfig.RESTAURANT_IMAGE_SERVER_URL + it.restaurant.imgUrl1,
                        price = it.restaurant.prices,
                        distance = ""
                    )
                }.stateIn(scope = coroutineScope, started = SharingStarted.Companion.Eagerly, initialValue = RestaurantCardUIState())
            }
        }
    }
}