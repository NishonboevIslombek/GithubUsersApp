package com.example.assignment.presentation.ui.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.presentation.ui.utils.BackIcon
import com.example.assignment.presentation.ui.utils.ErrorSnackBar
import com.example.assignment.presentation.utils.getTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreenPortrait(
    modifier: Modifier = Modifier,
    infoViewModel: InfoViewModel,
    login: String,
    onBackClicked: () -> Unit
) {

    LaunchedEffect(Unit) { infoViewModel.getUserByLogin(login) }
    val uiState by infoViewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = { TopBarInfoScreen(onBackClicked = onBackClicked) },
        modifier = modifier
    ) { paddingValues ->
        ContentInfoScreen(
            userItem = uiState.userItem,
            isLoading = uiState.isLoading,
            error = uiState.error,
            onDismissClicked = { infoViewModel.clearError() },
            onRetryClicked = { infoViewModel.getUserByLogin(login) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ContentInfoScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    error: String?,
    userItem: SingleUserItem?,
    onRetryClicked: () -> Unit,
    onDismissClicked: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {

        if (!isLoading && !error.isNullOrBlank())
            ErrorSnackBar(
                onRetryClicked = onRetryClicked,
                onDismissClicked = onDismissClicked,
                errorMassage = error,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )

        if (isLoading && userItem == null)
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        if (!isLoading && userItem != null)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                UserDetails(
                    imgUrl = userItem.avatarUrl,
                    login = userItem.login,
                    type = userItem.type
                )
                AchievementsRow(
                    followers = userItem.followers,
                    repos = userItem.repos,
                    gists = userItem.gists,
                    createdAt = userItem.createdAt,
                    modifier = Modifier
                )
            }
    }
}

@Composable
fun UserDetails(modifier: Modifier = Modifier, imgUrl: String, login: String, type: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SubcomposeAsyncImage(
            model = imgUrl,
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.padding(16.dp)
                )
            },
            modifier = modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Text(
            text = login,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = type,
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun AchievementsRow(
    modifier: Modifier = Modifier,
    followers: Int,
    repos: Int,
    gists: Int,
    createdAt: String,
) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(10.dp),
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        item { AchievementsElement(label = "Followers", value = followers.toString()) }
        item { AchievementsElement(label = "Public Repositories", value = repos.toString()) }
        item { AchievementsElement(label = "Gists", value = gists.toString()) }
        item { AchievementsElement(label = "Created", value = createdAt.getTime()) }
    }
}

@Composable
fun AchievementsElement(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    OutlinedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .height(100.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.secondary)
                )
                Text(text = label, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun TopBarInfoScreen(modifier: Modifier = Modifier, onBackClicked: () -> Unit) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        BackIcon(onClick = onBackClicked)
        Text(
            text = "Info",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}