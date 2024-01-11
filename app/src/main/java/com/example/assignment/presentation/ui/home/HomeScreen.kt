package com.example.assignment.presentation.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.assignment.domain.users.model.UserItem
import com.example.assignment.presentation.ui.components.EmptyListMessage
import com.example.assignment.presentation.ui.utils.ErrorSnackBar
import com.example.assignment.presentation.ui.utils.SearchTextField

@Composable
fun HomeScreenPortrait(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    onUserClicked: (login: String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()
    Surface(
        color = MaterialTheme.colorScheme.primary, modifier = modifier.fillMaxSize()
    ) {
        ContentHomeScreen(
            uiState = uiState,
            onTextChanged = {
                homeViewModel.getUsersByUsername(it)
            },
            onClearError = { homeViewModel.clearError() },
            onUserClicked = { onUserClicked(it) },
            onClearClicked = { homeViewModel.clearUserList() },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ContentHomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onTextChanged: (username: String) -> Unit,
    onClearError: () -> Unit,
    onUserClicked: (login: String) -> Unit,
    onClearClicked: () -> Unit
) {

    Box(modifier = modifier) {
        if (!uiState.error.isNullOrBlank() && !uiState.isLoading) ErrorSnackBar(
            onRetryClicked = { onTextChanged(uiState.username) },
            onDismissClicked = onClearError,
            errorMassage = uiState.error,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
                .padding(bottom = 16.dp)
        )
        else Column {
            SearchTextField(
                isSuccess = uiState.isSuccess,
                isLoading = uiState.isLoading,
                username = uiState.username,
                onClearClicked = onClearClicked,
                onTextChanged = { onTextChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
            if (uiState.usersList.isEmpty() && uiState.isSuccess && !uiState.isLoading && uiState.username.isNotBlank()) EmptyListMessage()
            else UsersColumn(list = uiState.usersList) {
                onUserClicked(it)
            }
        }
    }
}

@Composable
fun UsersColumn(
    modifier: Modifier = Modifier, list: List<UserItem>, onUserClicked: (login: String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        modifier = modifier
    ) {
        items(list) {
            UserElement(imageUrl = it.avatarUrl,
                login = it.login,
                type = it.type,
                onUserClicked = { onUserClicked(it.login) })
        }
    }
}


@Composable
fun UserElement(
    modifier: Modifier = Modifier,
    imageUrl: String,
    login: String,
    type: String,
    onUserClicked: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .clickable { onUserClicked() }
            .padding(8.dp)
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.padding(16.dp)
                )
            },
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = login, style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = type, style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


