package com.ditdah.features.auth.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.component.AppScaffold
import com.ditdah.core.designsystem.component.AppSnackbarHost
import com.ditdah.core.designsystem.component.AppSnackbarVisuals
import com.ditdah.core.designsystem.component.AppTextButton
import com.ditdah.core.designsystem.component.AppTextField
import com.ditdah.core.designsystem.component.PrimaryButton
import com.ditdah.core.designsystem.component.SnackbarMessageType
import com.ditdah.core.user.model.LoginInput
import com.ditdah.core.user.model.RegisterInput
import com.ditdah.features.auth.viewmodel.AuthEffect
import com.ditdah.features.auth.viewmodel.AuthEvent
import com.ditdah.features.auth.viewmodel.AuthVIewModel

@Composable
fun AuthScreen(
    viewModel: AuthVIewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            when (it) {
                is AuthEffect.Error -> {
                    snackbarHostState.showSnackbar(AppSnackbarVisuals(
                        type = SnackbarMessageType.ERROR,
                        message = it.message
                    ))
                }
            }
        }
    }

    when (state.isLogin) {
        true -> LoginContent(
            input = state.loginInput,
            onUsernameInput = {viewModel.onEvent(AuthEvent.LoginInput.InputUsername(it))},
            onPasswordInput = {viewModel.onEvent(AuthEvent.LoginInput.InputPassword(it))},
            onSumbit = {viewModel.onEvent(AuthEvent.LoginInput.Submit)},
            onChange = {viewModel.onEvent(AuthEvent.ChangeMode)},
            snackbarHost = snackbarHostState
        )
        false -> {
            RegisterContent(
                input = state.registerInput,
                onUsernameInput = {viewModel.onEvent(AuthEvent.RegisterInput.InputUsername(it))},
                onPasswordInput = {viewModel.onEvent(AuthEvent.RegisterInput.InputPassword(it))},
                onSumbit = {viewModel.onEvent(AuthEvent.RegisterInput.Submit)},
                onChange = {viewModel.onEvent(AuthEvent.ChangeMode)},
                snackbarHost = snackbarHostState
            )
        }
    }
}

@Composable
private fun LoginContent(
    input: LoginInput,
    onUsernameInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onSumbit: () -> Unit,
    onChange: () -> Unit,
    snackbarHost: SnackbarHostState
) {

    AppScaffold(modifier = Modifier.fillMaxSize(), snackbarHost = { AppSnackbarHost(snackbarHost) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AppTextField(
                        value = input.username,
                        label = "Имя пользователя",
                        onValueChange = onUsernameInput,
                    )
                    AppTextField(
                        value = input.password,
                        label = "Пароль",
                        onValueChange = onPasswordInput,
                    )
                    PrimaryButton(
                        text = "Войти",
                        onClick = onSumbit
                    )
                    AppTextButton(
                        text = "Нет аккаунта? Зарегистрироваться",
                        onClick = onChange
                    )
                }
            }
        }
    }
}

@Composable
private fun RegisterContent(
    input: RegisterInput,
    onUsernameInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onSumbit: () -> Unit,
    onChange: () -> Unit,
    snackbarHost: SnackbarHostState
) {

    AppScaffold(modifier = Modifier.fillMaxSize(), snackbarHost = { AppSnackbarHost(snackbarHost) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AppTextField(
                        value = input.username,
                        label = "Имя пользователя",
                        onValueChange = onUsernameInput,
                    )
                    AppTextField(
                        value = input.password,
                        label = "Пароль",
                        onValueChange = onPasswordInput,
                    )
                    PrimaryButton(
                        text = "Зарегистрироваться",
                        onClick = onSumbit
                    )
                    AppTextButton(
                        text = "Уже есть аккаунт? Войти",
                        onClick = onChange
                    )
                }
            }
        }
    }
}