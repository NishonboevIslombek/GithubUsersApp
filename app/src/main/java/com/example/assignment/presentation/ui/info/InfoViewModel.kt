package com.example.assignment.presentation.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.networks.use_case.CheckInternetConnectionUseCase
import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.domain.users.use_case.GetUserByLoginUseCase
import com.example.assignment.presentation.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val getUserByLoginUseCase: GetUserByLoginUseCase,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InfoUiState())
    val uiState: StateFlow<InfoUiState>
        get() = _uiState


     fun getUserByLogin(login: String) {
        if (!checkInternetConnectionUseCase.hasInternetConnection())
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    error = "Not Internet Connection"
                )
            } else {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isLoading = true,
                    error = null
                )
            }

            viewModelScope.launch {
                when (val response = getUserByLoginUseCase(login)) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                userItem = response.data
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
                error = null
            )
        }
    }
}

data class InfoUiState(
    val isLoading: Boolean = false,
    val userItem: SingleUserItem? = null,
    val error: String? = null
)