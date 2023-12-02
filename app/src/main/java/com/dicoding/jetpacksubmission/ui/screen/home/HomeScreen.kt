package com.dicoding.jetpacksubmission.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetpacksubmission.ViewModelFactory
import com.dicoding.jetpacksubmission.data.PlaceRepository
import com.dicoding.jetpacksubmission.model.Place
import com.dicoding.jetpacksubmission.ui.common.UiState
import com.dicoding.jetpacksubmission.ui.component.Search

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    viewModel : HomeViewModel = viewModel(factory = ViewModelFactory(PlaceRepository())),
    navigateToDetail: (String) -> Unit,){

    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listPlace = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun PlacesListItem(
    id : String,
    name: String,
    photo: Int,
    location : String,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(4.dp)
            .clickable {navigateToDetail(id)}
    ) {
        Image(
            painter = painterResource(photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .size(150.dp)
        )
        Column(modifier = Modifier.padding(8.dp)){
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Rp. $location/pax",
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listPlace: List<Place>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = modifier.testTag("listPlace")){

            item {
                Search(
                    query = query,
                    onQueryChange = onQueryChange,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                )
            }

            items(listPlace, key = { it.id }) { place ->
                PlacesListItem(
                    id = place.id,
                    name = place.name,
                    photo = place.photoUrl,
                    location = place.price,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier.fillMaxWidth()
                        .clickable { navigateToDetail(place.id) })
            }
        }
    }
}