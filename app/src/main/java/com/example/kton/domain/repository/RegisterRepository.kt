package com.example.kton.domain.repository

import android.net.Uri
import com.example.kton.domain.model.UsuarioDomain
import java.io.File

interface RegisterRepository {
    suspend fun register(
        username:String,
        email: String,
        pass: String,
        foto: Uri?,
        defaultFotoId: Int?
    ) : Result<UsuarioDomain>
}