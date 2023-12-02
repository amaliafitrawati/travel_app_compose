package com.dicoding.jetpacksubmission.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.ViewModelFactory
import com.dicoding.jetpacksubmission.di.Injection
import com.dicoding.jetpacksubmission.model.Place
import com.dicoding.jetpacksubmission.ui.common.UiState
import com.dicoding.jetpacksubmission.ui.screen.home.PlacesListItem

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoritePlace()
            }
            is UiState.Success -> {
                FavoriteContent(
                    listPlace = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    listPlace: List<Place>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favorite",
                        fontWeight = FontWeight.Bold)
                },
            )
        }
    ) {innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            val listState = rememberLazyListState()

            LazyColumn(state = listState) {
                if (listPlace.isNotEmpty()) {
                    items(listPlace, key = { it.id }) { place ->
                        PlacesListItem(
                            id = place.id,
                            name = place.name,
                            photo = place.photoUrl,
                            location = place.price,
                            navigateToDetail = navigateToDetail,
                            modifier = modifier
                                .fillMaxWidth()
                                .clickable { navigateToDetail(place.id) })
                    }
                } else {
                    item {
                        Text(
                            text = stringResource(R.string.empty_alert),
                            modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                        .testTag("emptyData")
                        )
                    }
                }
            }
        }
    }
}