package com.example.kton.domain.model.interfaces

interface Receta {
    val id: String?
    val titulo: String
    val descripcion: String
    val ingredientes: List<Ingrediente>
    val informacionNutricional: InformacionNutricional
    val herramientas: List<String>
    val tiempo: Int
    val resultados: List<Resultado>
    val etiquetas : List<String>
    val restriccionesAlimentarias : List<String>
}
interface Ingrediente {
    val nombre: String
    val cantidad: String
    val peso: Int?
    val porcentajeEnergetico: PorcentajeEnergetico?
}
interface PorcentajeEnergetico {
    val energia: Int
    val proteinas: Int
    val carbohidratos: Int
    val grasas: Int
}
interface InformacionNutricional {
    val energia: Int
    val proteinas: Int
    val carbohidratos: Int
    val grasas: Int
}
interface Resultado{
    val nombre: String
    val pasos: List<Paso>
}
interface Paso {
    val verbo: String
    val ingredientes: List<IngredientePaso>
    val herramienta: String?
    val frase: String
    val tiempo: Int?
    val numero: Int
}
interface IngredientePaso {
    val nombre: String
    val cantidad: String?
    val peso: Int?
}


