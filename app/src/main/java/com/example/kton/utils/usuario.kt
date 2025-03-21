package com.example.kton.utils

val nivelesDeActividad : Map<String, Int> = mapOf(
    "sedentario" to 1,
    "ligero" to 2,
    "moderado" to 3,
    "activo" to 4,
    "muyActivo" to 5
)
sealed class NivelesActividad(val name: String, val level: Int) {
    object Sedentario : NivelesActividad("sedentario", 1)
    object Ligero : NivelesActividad("ligero", 2)
    object Moderado : NivelesActividad("moderado", 3)
    object Activo : NivelesActividad("activo", 4)
    object MuyActivo : NivelesActividad("muyActivo", 5)
}

val restriccionesAlimentarias = listOf(
    "Vegana",
    "Vegetariana",
    "AlergiaALosCacahuetes",
    "AlergiaALaLactosa",
    "SinGluten",
    "AlergiaAlMarisco",
    "DietaKeto"
)
val historialRecetas = listOf("Kotlin", "Jetpack Compose", "Android")
val recetasFavoritas : List<String> = listOf("Legumbres", "Pollo Con Arroz", "Calabaza", "Martillo")
data class Receta(
    val nombre: String,
    val tiempo: Int, // Tiempo en minutos
    val url: String
)
val recetasGuardadas = listOf(
    Receta(
        nombre = "TortillaDePatatas",
        tiempo = 40,
        url = "https://imgs.search.brave.com/dQ-5aGd6xcIF9mxMWnJM7R74Pvn8FmtQWT38KKa7VOg/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9pbWFn/LmJvbnZpdmV1ci5j/b20vdG9ydGlsbGEt/ZGUtcGF0YXRhcy1v/LWVzcGFub2xhLmpw/Zw"
    ),
    Receta(
        nombre = "EnsaladaCesar",
        tiempo = 15,
        url = "https://imgs.search.brave.com/JOshPfCvKCtKlJzy1Wpo1GztyDG9B0ukrOmRYqahqhA/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9pbWFn/LmJvbnZpdmV1ci5j/b20vZW5zYWxhZGEt/Y2VzYXItdGVybWlu/YWRhLmpwZw"
    ),
    Receta(
        nombre = "PastaCarbonara",
        tiempo = 25,
        url = "https://imgs.search.brave.com/Q55v0IDyWa7FPdFGEgxiIq-Wl2VPxI4otkdgi-FjqS4/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTE0/MjM5MTQ2My9waG90/by9wYXN0YS1jYXJi/b25hcmEuanBnP3M9/NjEyeDYxMiZ3PTAm/az0yMCZjPTdnTzlt/UmVORnpZMTBxc211/X1g0X0xaNDUtVWNW/UHR6cEhGLURPRnA2/Q2M9"
    )
)