# KTON

## Estructura del Proyecto

### app
Contiene el punto de entrada principal, como la actividad principal (`MainActivity`), y componentes de nivel de aplicación.  
_Ejemplos_: configuraciones globales, clases de aplicación personalizadas.

### data
Gestiona todas las operaciones relacionadas con los datos.  
- **network**: Interfaces de servicio API y clases de red.  
- **db**: Interacciones con bases de datos, incluyendo entidades Room, DAOs y la clase de base de datos.  
- **repository**: Implementa el patrón de repositorio para el acceso a datos.  
- **pref**: Maneja el almacenamiento de preferencias, como SharedPreferences.

### domain
Contiene la lógica de negocio y las entidades específicas del dominio.  
- **model**: Modelos de datos del dominio.  
- **repository**: Interfaces de repositorio para el acceso a datos.

### di
Gestiona la inyección de dependencias, típicamente usando Dagger o Hilt.  
- **modules**: Módulos para definir las dependencias.  
- **scopes**: Componentes y alcances personalizados.

### presentation
Encargada de la interfaz de usuario, organizada por pantallas o características.  
- **ui**: Subdirectorios por pantalla, que incluyen funciones composables, view models y componentes reutilizables.  
- **common**: Componentes y view models compartidos entre pantallas.  
- **theme**: Configuraciones de temas Compose (colores, formas y tipografía).  
- **navigation**: Lógica de navegación, como NavHost y utilidades relacionadas.  
- **notification**: Clases para crear y gestionar notificaciones.  
- **workers**: Clases Worker para tareas específicas o trabajos en segundo plano.

### utils
Funciones y clases de utilidad para lógica compartida en todo el proyecto.

## Configuración y Uso
Instrucciones para configurar y ejecutar el proyecto:
1. Clona el repositorio.
2. Abre el proyecto en tu IDE preferido.
3. Configura las variables de entorno y dependencias.
4. Compila y ejecuta la aplicación.

## Contribución
Si deseas contribuir, sigue estos pasos:
1. Realiza un fork del repositorio.
2. Crea una nueva rama:  
   ```bash
   git checkout -b feature/nueva-caracteristica
