package com.example.assignment.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.assignment.domain.common.Resource
import com.example.assignment.domain.networks.use_case.CheckInternetConnectionUseCase
import com.example.assignment.domain.users.model.UserItem
import com.example.assignment.domain.users.use_case.GetUsersByUsernameUseCase
import com.example.assignment.presentation.ui.home.HomeViewModel
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
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel
    private lateinit var getUsersByUsernameUseCase: GetUsersByUsernameUseCase
    private lateinit var checkInternetConnectionUseCase: CheckInternetConnectionUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
        getUsersByUsernameUseCase = mockk()
        checkInternetConnectionUseCase = mockk()
        viewModel = HomeViewModel(getUsersByUsernameUseCase, checkInternetConnectionUseCase)
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
    fun `getUsersByUsername with no internet connection`() = runTest{
        // Arrange
        val username = "testUser"
        every { checkInternetConnectionUseCase.hasInternetConnection() } returns false

        // Act
        viewModel.getUsersByUsername(username)

        // Assert
        assert(viewModel.uiState.value.error == "Not Internet Connection")
        assert(!viewModel.uiState.value.isSuccess)
        assert(viewModel.uiState.value.username == username)
    }

    @Test
    fun `getUsersByUsername with success`() = runTest{
        // Arrange
        val username = "testUser"
        val userItems = listOf(
            UserItem(1, "user1", "avatar", "test"),
            UserItem(2, "user2", "avatar", "test")
        )
        val successResource = Resource.Success(userItems)
        every { checkInternetConnectionUseCase.hasInternetConnection() } returns true
        coEvery { getUsersByUsernameUseCase.invoke(username) } returns successResource

        // Act
        viewModel.getUsersByUsername(username)

        advanceTimeBy(3000)

        // Assert
        assert(!viewModel.uiState.value.isLoading)
        assert(viewModel.uiState.value.isSuccess)
        assert(viewModel.uiState.value.usersList == userItems)
        assert(viewModel.uiState.value.error == null)
    }

    @Test
    fun test_getUsersByUsername_failedResourceResponse() = runTest {
        // Arrange
        val username = "testUser"
        val errorMessage = "Failed to get users"
        val failedResource = Resource.Failed<List<UserItem>>(errorMessage)
        every { checkInternetConnectionUseCase.hasInternetConnection() } returns true
        coEvery { getUsersByUsernameUseCase.invoke(username) } returns failedResource

        // Act
        viewModel.getUsersByUsername(username)

        advanceTimeBy(3000)

        // Assert
        assert(!viewModel.uiState.value.isLoading)
        assert(!viewModel.uiState.value.isSuccess)
        assert(viewModel.uiState.value.usersList.isEmpty())
        assert(viewModel.uiState.value.error == errorMessage)
    }
}
