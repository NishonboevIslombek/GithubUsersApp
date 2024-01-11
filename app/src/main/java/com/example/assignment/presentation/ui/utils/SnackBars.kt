package com.example.assignment.presentation.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorSnackBar(
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit,
    onDismissClicked: () -> Unit,
    errorMassage: String?
) {
    Snackbar(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.primary,
        actionContentColor = MaterialTheme.colorScheme.primary,
        dismissActionContentColor = MaterialTheme.colorScheme.primary,
        action = {
            RetryIcon(onClick = onRetryClicked)
        },
        dismissAction = {
            ClearIcon(onClick = onDismissClicked)
        },
        modifier = modifier
    ) {
        Text(text = errorMassage ?: "")
    }
}