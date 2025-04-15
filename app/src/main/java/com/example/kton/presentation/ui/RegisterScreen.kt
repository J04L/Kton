package com.example.kton.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kton.R

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit, // Callback para el botón de registro
    onNavigateToLogin: () -> Unit, // Callback para navegar a login
    // logoPainter: Painter = painterResource(id = R.drawable.ic_company_logo) // Ejemplo
) {
    // Estados para los campos del formulario de registro
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Logo de la Empresa ---
        Image(
            // painter = logoPainter,
            painter = painterResource(id = R.drawable.kton_logo), // Placeholder
            contentDescription = "Logo de la Empresa",
            modifier = Modifier
                .size(100.dp) // Un poco más pequeño quizás en registro
                .padding(bottom = 32.dp),
            contentScale = ContentScale.Fit
        )

        // --- Campo de Texto para Nombre de Usuario ---
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo de Texto para Email ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), // Teclado de email
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo de Texto para Contraseña ---
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo de Texto para Confirmar Contraseña ---
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            // Podrías añadir validación aquí para que coincida con la contraseña
            isError = password != confirmPassword && confirmPassword.isNotEmpty()
        )
        // Mensaje de error simple si las contraseñas no coinciden
        if (password != confirmPassword && confirmPassword.isNotEmpty()) {
            Text(
                text = "Las contraseñas no coinciden",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }


        Spacer(modifier = Modifier.height(32.dp))

        // --- Botón de Registro ---
        Button(
            onClick = {
                // Solo llama al callback si las contraseñas coinciden y no están vacías
                if (password == confirmPassword && password.isNotEmpty()) {
                    onRegisterClick(username, email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            // Deshabilita el botón si las contraseñas no coinciden o están vacías
            enabled = password == confirmPassword && password.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty()
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Texto/Botón para ir a Login ---
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia Sesión")
        }
    }
}