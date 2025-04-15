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
fun LoginScreen(
    onLoginClick: (String, String) -> Unit, // Callback para el botón de login
    onNavigateToRegister: () -> Unit, // Callback para navegar a registro
    // Añade un parámetro para el logo si lo cargas dinámicamente
    // logoPainter: Painter = painterResource(id = R.drawable.ic_company_logo) // Ejemplo
) {
    // Estados para guardar el email y la contraseña introducidos por el usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Contenedor principal que centra los elementos vertical y horizontalmente
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible
            .padding(32.dp), // Añade padding alrededor
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Center // Centra verticalmente
    ) {
        // --- Logo de la Empresa ---
        // Asegúrate de tener un recurso drawable llamado 'ic_company_logo'
        // o reemplázalo con tu logo real.
        Image(
            // painter = logoPainter, // Usa el parámetro si lo pasas
            painter = painterResource(id = R.drawable.kton_logo), // Usa un placeholder
            contentDescription = "Logo de la Empresa",
            modifier = Modifier
                .size(120.dp) // Tamaño del logo
                .padding(bottom = 48.dp), // Espacio debajo del logo
            contentScale = ContentScale.Fit // Escala la imagen para que quepa
        )

        // --- Campo de Texto para Email ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email o Usuario") },
            singleLine = true, // El campo de texto ocupa una sola línea
            modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho disponible
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio vertical

        // --- Campo de Texto para Contraseña ---
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(), // Oculta la contraseña
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Teclado de contraseña
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio vertical

        // --- Botón de Login ---
        Button(
            onClick = { onLoginClick(email, password) }, // Llama al callback al hacer clic
            modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio vertical

        // --- Texto/Botón para ir a Registro ---
        TextButton(onClick = onNavigateToRegister) { // Llama al callback al hacer clic
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}