package com.example.assignment.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.networks.use_case.CheckInternetConnectionUseCase
import com.example.assignment.domain.users.model.SingleUserItem
import com.example.assignment.domain.users.use_case.GetUserByLoginUseCase
import com.example.assignment.presentation.ui.info.InfoViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InfoViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: InfoViewModel
    private lateinit var getUserByLoginUseCase: GetUserByLoginUseCase
    private lateinit var checkInternetConnectionUseCase: CheckInternetConnectionUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
        getUserByLoginUseCase = mockk()
        checkInternetConnectionUseCase = mockk()
        viewModel = InfoViewModel(getUserByLoginUseCase, checkInternetConnectionUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `hasInternetConnection returns true`() {
        // Arrange
        every { checkInternetConnectionUseCase.hasInternetConnection() } returns true

        // Act
        val result = checkInternetConnectionUseCase.hasInternetConnection()

        // Assert
        assert(result)
    }

    @Test
    fun `hasInternetConnection returns false`() {
        // Arrange
        every { checkInternetConnectionUseCase.hasInternetConnection() } returns false

        // Act
        val result = checkInternetConnectionUseCase.hasInternetConnection()

        // Assert
        assert(!result)
    }

    @Test
    fun `getUsersByUsername with no internet connection`() = runTest {
        // Arrange
        val username = "testUser"
        every { checkInternetConnectionUseCase.hasInternetConnection() } returns false

        // Act
        viewModel.getUserByLogin(username)

        // Assert
        assert(!viewModel.uiState.value.isLoading)
        assert(viewModel.uiState.value.error == "Not Internet Connection")
        assert(viewModel.uiState.value.userItem == null)
    }

    @Test
    fun `getUsersByUsername with success`() = runTest {
        // Arrange
        val username = "testUser"
        val singleUserItem = SingleUserItem(
            id = 1,
            login = "john_doe",
            avatarUrl = "https://example.com/avatar.jpg",
            type = "user",
            followers = 100,
            gists = 5,
            repos = 10,
            createdAt = "2021-01-01"
        )

        val successResource = Resource.Success(singleUserItem)
        every { checkInternetConnectionUseCase.hasInternetConnection() } returns true
        coEvery { getUserByLoginUseCase.invoke(username) } returns successResource

        // Act
        viewModel.getUserByLogin(username)

        advanceTimeBy(3000)

        // Assert
        assert(!viewModel.uiState.value.isLoading)
        assert(viewModel.uiState.value.userItem == singleUserItem)
        assert(viewModel.uiState.value.error == null)
    }
}
