package com.dicoding.jetpacksubmission.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.ViewModelFactory
import com.dicoding.jetpacksubmission.di.Injection
import com.dicoding.jetpacksubmission.ui.common.UiState

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel
    = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPlaceById(id)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailItem(
                    id = data.id,
                    name = data.name,
                    photo = data.photoUrl,
                    location = data.location,
                    price = data.price,
                    region = data.region,
                    description = data.description,
                    navigateBack = navigateBack,
                    isFavorite = data.isFavorite,
                    onFavoriteButtonClicked = {
                        viewModel.updateFavorite(it)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailItem(
    id : String,
    name: String,
    photo: Int,
    location : String,
    price : String,
    region : String,
    description : String,
    navigateBack: () -> Unit,
    isFavorite: Boolean,
    onFavoriteButtonClicked: (id: String) -> Unit,
    modifier: Modifier = Modifier){
    var isClicked by remember { mutableStateOf(isFavorite)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail",
                        fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("backBtn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            isClicked = !isClicked
                            onFavoriteButtonClicked(id)
                        },
                        modifier = Modifier
                            .padding(end = 16.dp, top = 16.dp)
                            .size(40.dp)
                            .background(Color.White)
                            .testTag("favoriteBtn")
                    ) {
                        Icon(
                            imageVector = if (isClicked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isClicked) stringResource(R.string.unfavorite) else stringResource(
                                R.string.favorited
                            ),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
                Text(
                    text = "$location, $region",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )

                Text(
                    text = description,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Rp. $price/pax",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}