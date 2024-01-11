package com.example.assignment.presentation.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.assignment.R

@Composable
fun ClearIcon(modifier: Modifier = Modifier, onClick: () -> Unit, enabled: Boolean = true) {
    IconButton(onClick = onClick, enabled = enabled, modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = stringResource(id = R.string.action_clear)
        )
    }
}

@Composable
fun SearchIcon(modifier: Modifier = Modifier, onClick: () -> Unit, enabled: Boolean = true) {
    IconButton(onClick = onClick, enabled = enabled, modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = stringResource(id = R.string.action_search)
        )
    }
}

@Composable
fun RetryIcon(modifier: Modifier = Modifier, onClick: () -> Unit, enabled: Boolean = true) {
    IconButton(onClick = onClick, enabled = enabled, modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.Refresh,
            contentDescription = stringResource(id = R.string.action_retry)
        )
    }
}

@Composable
fun BackIcon(modifier: Modifier = Modifier, onClick: () -> Unit, enabled: Boolean = true) {
    IconButton(onClick = onClick, enabled = enabled, modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowLeft,
            contentDescription = stringResource(id = R.string.action_back)
        )
    }
}

