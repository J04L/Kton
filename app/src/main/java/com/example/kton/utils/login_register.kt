package com.example.kton.utils

import com.example.kton.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions // Importación necesaria

// --- Pantalla de Login ---

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

// --- Pantalla de Registro ---

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

// --- Previews para ver el diseño en Android Studio ---

// Necesitas un recurso drawable placeholder para que la preview funcione.
// Crea un archivo simple en res/drawable, por ejemplo, ic_placeholder_logo.xml
/*
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="100dp"
    android:height="100dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
  <path
      android:fillColor="#CCCCCC"
      android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2zM12,20c-4.41,0 -8,-3.59 -8,-8s3.59,-8 8,-8 8,3.59 8,8 -3.59,8 -8,8zM11,7h2v6h-2zM11,15h2v2h-2z"/>
</vector>
*/

// Placeholder para R (recursos). En un proyecto real, esto no es necesario.



@Preview(showBackground = true, name = "Login Screen Preview")
@Composable
fun LoginScreenPreview() {
    // Envuelve la preview en un tema si usas MaterialTheme en tu app
    MaterialTheme {
    LoginScreen(onLoginClick = { _, _ -> }, onNavigateToRegister = {})
    }
}

@Preview(showBackground = true, name = "Register Screen Preview")
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
    RegisterScreen(onRegisterClick = { _, _, _ -> }, onNavigateToLogin = {})
    }
}