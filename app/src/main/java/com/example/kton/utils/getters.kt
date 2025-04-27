package com.example.kton.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream

// --- Función auxiliar para obtener el archivo desde una Uri (Ejemplo) ---
fun getFileFromUri(context: Context, uri: Uri): File {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri)
    val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    // Crear un archivo temporal en la caché de la app
    val tempFile = File(context.cacheDir, "upload_temp.$fileExtension")
    try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return tempFile
    } catch (e: Exception) {
        throw Exception(e.message)
    }
}