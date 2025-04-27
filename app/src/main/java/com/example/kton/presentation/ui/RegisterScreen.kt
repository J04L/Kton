package com.example.registrousuario // Reemplaza con tu nombre de paquete

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.kton.R

// Define IDs for default avatars
val defaultAvatars = listOf(
    R.drawable.cheff_avatar,
    R.drawable.shusi_avatar,
    R.drawable.tarta_avatar,
    R.drawable.pollo_bailando_avatar,
    R.drawable.helado_pirata_avatar,
    R.drawable.unset_avatar
)

@Composable
fun RegisterScreen(
    onRegisterClick: (
        nombre: String, email: String, pass: String, fotoUri: Uri?, fotoDefaultResId: Int?
            ) -> Unit = { _, _, _, _, _ -> }, // Callback para el regis
    onNavigateToLogin: () -> Unit
) {
    // State for input fields
    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    // State for profile picture
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var selectedDefaultAvatarResId by rememberSaveable { mutableStateOf<Int?>(null) }
    var showDefaultAvatarSelection by rememberSaveable { mutableStateOf(false) }

    // Activity Result Launcher for picking an image from the gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        selectedDefaultAvatarResId = null // Deselect default avatar if user uploads
        showDefaultAvatarSelection = false // Hide selection row
    }

    // Main layout column, scrollable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Make the column scrollable
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Registro", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        // --- Profile Picture Section ---
        Box(contentAlignment = Alignment.BottomEnd) {
            // Display selected image or default avatar or placeholder
            val painter = when {
                selectedImageUri != null -> rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(selectedImageUri)
                        .crossfade(true)
                        .build()
                )
                selectedDefaultAvatarResId != null -> painterResource(id = selectedDefaultAvatarResId!!)
                else -> painterResource(id = android.R.drawable.ic_menu_camera) // Placeholder icon
            }

            Image(
                painter = painter,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable {
                        // Toggle default avatar selection visibility
                        showDefaultAvatarSelection = !showDefaultAvatarSelection
                    },
                contentScale = ContentScale.Crop // Crop the image to fit the circle
            )

            // Small icon button to indicate editability
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Editar foto",
                modifier = Modifier
                    .size(30.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    .padding(4.dp)
                    .align(Alignment.BottomEnd),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // --- Default Avatar Selection (conditionally visible) ---
        if (showDefaultAvatarSelection) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Elige un avatar o sube tu foto", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                // Button to upload photo
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(0.8f) // Limit width
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Subir foto")
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Horizontal list of default avatars (if any are defined)
                if (defaultAvatars.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(defaultAvatars) { avatarResId ->
                            Image(
                                painter = painterResource(id = avatarResId),
                                contentDescription = "Avatar por defecto",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        if (selectedDefaultAvatarResId == avatarResId) MaterialTheme.colorScheme.primary else Color.Gray,
                                        CircleShape
                                    )
                                    .clickable {
                                        selectedDefaultAvatarResId = avatarResId
                                        selectedImageUri = null // Deselect uploaded image
                                        showDefaultAvatarSelection = false // Hide selection after choosing
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    Text("No hay avatares por defecto definidos.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp)) // Space after avatar selection
            }
        }

        // --- Input Fields ---
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Icono Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Icono Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Icono Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Info
                else Icons.Filled.Close
                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        // --- Registration Button ---
        Button(
            onClick = {
                // Basic validation (can be expanded)
                if (nombre.isNotBlank() && email.isNotBlank() && password.isNotBlank() && (selectedImageUri != null || selectedDefaultAvatarResId != null)) {
                    onRegisterClick(nombre, email, password, selectedImageUri, selectedDefaultAvatarResId)
                } else {
                    // Handle validation error (e.g., show a Snackbar)
                    println("Error: Completa todos los campos y selecciona una foto.") // Replace with user feedback
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}
