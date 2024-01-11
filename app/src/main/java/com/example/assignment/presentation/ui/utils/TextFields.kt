package com.example.assignment.presentation.ui.utils

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.assignment.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isSuccess: Boolean,
    username: String,
    onClearClicked: () -> Unit,
    onTextChanged: (username: String) -> Unit
) {
    val onTextChangedState = remember { mutableStateOf(false) }
    val usernameState = rememberSaveable { mutableStateOf(username) }

    LaunchedEffect(usernameState.value) {
        if (usernameState.value.length >= 2 && onTextChangedState.value) {
            delay(1000)
            onTextChanged(usernameState.value)
        }
    }

    OutlinedTextField(
        value = usernameState.value,
        onValueChange = {
            usernameState.value = it
            onTextChangedState.value = true
            if (it.isBlank()) onClearClicked()
        },
        placeholder = { Text(text = stringResource(id = R.string.placeholder_search_bar)) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            placeholderColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.onPrimary,
                backgroundColor = MaterialTheme.colorScheme.secondary
            )
        ),
        trailingIcon = {
            if (isLoading && !isSuccess) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 1.dp,
                    modifier = Modifier.size(16.dp)
                )

            } else if (!isLoading && isSuccess && !onTextChangedState.value) {
                ClearIcon(onClick = {
                    onTextChangedState.value = true
                    usernameState.value = ""
                    onClearClicked()
                }, enabled = usernameState.value.isNotBlank())
            } else {
                SearchIcon(
                    onClick = {},
                    enabled = false
                )
            }
        },
        modifier = modifier
    )
}