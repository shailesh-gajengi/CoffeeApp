package com.example.coffeeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffeeapp.navigation.Routes
import com.example.coffeeapp.ui.theme.LightBrown
import com.example.coffeeapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {

    val authViewModel: AuthViewModel = viewModel()
    val loginSuccess by authViewModel.loginSuccess.collectAsState()
    val error by authViewModel.error.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome Back",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    authViewModel.login(
                        email,
                        password
                    )



                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBrown
                )
            ) {

                Text("Login")

            }
            error?.let {

                Text(
                    text = it,
                    color = Color.Red
                )

            }
            LaunchedEffect(loginSuccess) {

                if (loginSuccess) {

                    navController.navigate(Routes.HomeScreen) {
                        popUpTo(Routes.LoginScreen) {
                            inclusive = true
                        }
                    }

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {

                    navController.navigate(
                        Routes.RegisterScreen
                    )

                }
            ) {

                Text("Don't have an account? Register")

            }

        }

    }

}