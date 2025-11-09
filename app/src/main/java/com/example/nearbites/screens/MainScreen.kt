package com.example.nearbites.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nearbites.MainViewModel
import com.example.nearbites.api.NearbyRestroRepoImpl
import com.example.nearbites.api.RestroApiUseCase
import com.example.nearbites.common_widget.HeaderView
import com.example.nearbites.common_widget.RestaurantsItemView
import com.example.nearbites.database.AppDataBase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.nearbites.common_widget.Review
import com.example.nearbites.common_widget.ReviewsDialog
@Composable
fun MainScreen(modifier: Modifier) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var reviews by remember {
        mutableStateOf(
            listOf(
                Review("Alice", "Great food!"),
                Review("Bob", "Nice ambiance.")
            )
        )
    }

    val factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            MainViewModel(
                RestroApiUseCase(
                    NearbyRestroRepoImpl(AppDataBase.getInstance(context).restaurantDao())
                )
            )
        }
    }

    val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = factory
    )
    val restroData by viewModel.restroData.observeAsState()
    val listState = rememberLazyListState()
    val isLoading = remember { mutableStateOf(false) }

    // Trigger pagination when scrolling near bottom
    LaunchedEffect(listState.firstVisibleItemIndex, restroData) {
        if (restroData?.isSuccess == true) {
            val restaurants = restroData!!.getOrNull().orEmpty()
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            if (!isLoading.value && lastVisibleIndex >= restaurants.size - 3) {
                isLoading.value = true
                viewModel.getNearByRestroList() // fetch next page
                isLoading.value = false
            }
        }
    }

    when {
        restroData == null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        restroData!!.isSuccess -> {
            val restaurants = restroData!!.getOrNull().orEmpty()
            Column(modifier = modifier.background(Color.Gray)) {
                HeaderView()
                Box(contentAlignment = Alignment.Center) {
                    LazyColumn(state = listState) {
                        items(restaurants.size) { index ->
                            RestaurantsItemView(restaurants[index], index) {
                                showDialog = true
                            }
                            Spacer(Modifier.height(1.dp))
                        }

                        // Loading indicator at the bottom
                        if (isLoading.value) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }

                    if (showDialog) {
                        ReviewsDialog(
                            reviews = reviews,
                            onDismiss = { showDialog = false },
                            onSubmit = { newReview ->
                                reviews = reviews + Review("You", newReview)
                                showDialog = false
                            }
                        )
                    }
                }
            }
        }

        restroData!!.isFailure -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Failed to load restaurants")
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen(Modifier.fillMaxSize())
}