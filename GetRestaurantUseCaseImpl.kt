package com.sarang.torang.di.cardinfo_di

import com.sarang.torang.BuildConfig
import com.sarang.torang.compose.cardinfo.RestaurantCardUIState
import com.sarang.torang.di.repository.repository.impl.FindRepositoryImpl
import com.sarang.torang.usecase.cardinfo.GetRestaurantUseCase
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
class GetRestaurantUseCaseImpl {
    @Provides
    fun provideGetRestaurantUseCase(findRepository: FindRepositoryImpl): GetRestaurantUseCase {
        return object : GetRestaurantUseCase {
            override fun invoke(coroutineScope: CoroutineScope): StateFlow<List<RestaurantCardUIState>> {
                return findRepository.restaurants.map {
                    it.map { restaurant ->
                        RestaurantCardUIState(
                            restaurantId = restaurant.restaurantId ?: -1,
                            restaurantName = restaurant.restaurantName ?: "null",
                            rating = restaurant.rating ?: 0f,
                            foodType = restaurant.restaurantType ?: "null",
                            restaurantImage = BuildConfig.RESTAURANT_IMAGE_SERVER_URL + restaurant.imgUrl1,
                            price = restaurant.prices ?: "null",
                            distance = ""
                        )
                    }
                }.stateIn(scope = coroutineScope, started = SharingStarted.Companion.Eagerly, initialValue = emptyList())

            }
        }
    }
}