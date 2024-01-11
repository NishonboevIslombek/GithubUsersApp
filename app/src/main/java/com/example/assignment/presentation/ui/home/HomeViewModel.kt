package com.example.assignment.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.networks.use_case.CheckInternetConnectionUseCase
import com.example.assignment.domain.users.model.UserItem
import com.example.assignment.domain.users.use_case.GetUsersByUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersByUsernameUseCase: GetUsersByUsernameUseCase,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState>
        get() = _uiState


    fun getUsersByUsername(username: String) {
        if (!checkInternetConnectionUseCase.hasInternetConnection()) {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    error = "Not Internet Connection",
                    isSuccess = false,
                    username = username
                )
            }
        } else {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isLoading = true,
                    isSuccess = false,
                    usersList = emptyList(),
                    error = null,
                    username = username
                )
            }

            viewModelScope.launch {
                when (val response = getUsersByUsernameUseCase(username)) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = true,
                                usersList = response.data
                            )
                        }
                    }

                    is Resource.Failed -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = response.message
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                error = null,
                usersList = emptyList()
            )
        }
    }

    fun clearUserList() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                usersList = emptyList(),
                isSuccess = false
            )
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val username: String = "",
    val usersList: List<UserItem> = emptyList(),
    val error: String? = null
)

