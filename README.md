# Weather Proxy API - Hexagonal Architecture

Una API REST que actúa como proxy para servicios externos del clima (weatherstack, OpenWeather, etc.), aplicando lógica personalizada y registrando cada solicitud.

## Arquitectura

Este proyecto implementa una arquitectura hexagonal (Ports and Adapters), siguiendo estos principios:

```
weather-proxy-hexa/
│
├── domain/                   # Capa de dominio (sin dependencias externas)
│   ├── model/                # Entidades y objetos de valor
│   ├── ports/                # Interfaces que definen contratos
│   │   ├── in/               # Puertos primarios (casos de uso)
│   │   └── out/              # Puertos secundarios (persistencia, servicios externos)
│   └── services/             # Implementación de los casos de uso
│
├── shared/                   # DTOs y excepciones compartidas entre capas
│   ├── dto/                  # Objetos de transferencia de datos
│   └── exception/            # Excepciones personalizadas
│
├── application/              # Orquestación de la aplicación (depende de domain y shared)
│   ├── service/              # Implementación de servicios de aplicación
│   └── config/               # Configuración específica de la aplicación
│
├── adapters/                 # Adaptadores (dependen de application, domain y shared)
│   ├── in/                   # Adaptadores de entrada
│   │   └── rest/             # Controladores REST
│   └── out/                  # Adaptadores de salida
│       ├── persistence/      # Implementación de persistencia
│       └── api/              # Implementación de integración con APIs externas
│
└── bootstrap/                # Arranque e inicialización (depende de todas las capas)
    └── config/               # Configuración global
```

## Características implementadas

- **Arquitectura Hexagonal**: Separa claramente el dominio de las implementaciones técnicas.
- **Java 17**: Uso de características avanzadas como:
  - Records para objetos inmutables (DTOs, entidades y objetos de valor)
  - Pattern matching para instanceof
  - Bloques de texto multilínea (""")
  - Var para inferencia de tipos
  - Métodos de factoría para colecciones
- **Spring Boot 3.x**: Soporta Jakarta EE en lugar de Java EE.
- **API REST**: Punto de conexión único con comportamiento configurable.
- **Múltiples fuentes de clima**: Sistema extensible para conectar con diferentes proveedores.
- **Logging**: Registro de todas las solicitudes y respuestas.
- **Persistencia**: Almacenamiento de registros en base de datos H2.
- **Documentación API**: Integración con Swagger/OpenAPI.
- **Manejo de errores**: Tratamiento de excepciones robusto.

## Requisitos

- Java 17+
- Maven 3.8+

## Cómo ejecutar

```bash
# Clonar repositorio
git clone https://github.com/tu-usuario/weather-proxy-hexa.git
cd weather-proxy-hexa

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080

Acceso a:
- API: http://localhost:8080/api/weather/{ciudad}
- Swagger UI: http://localhost:8080/swagger-ui.html
- Consola H2: http://localhost:8080/h2-console

## Uso de la API

### Obtener información del clima para una ciudad
```
GET /api/weather/{ciudad}?source={fuente}&config={configuración}
```

Ejemplo:
```
GET /api/weather/Madrid?source=mock
```

Con configuración personalizada:
```
GET /api/weather/Madrid?source=openweather&config={"apiKey":"tu-api-key","baseUrl":"https://api.openweathermap.org/data/2.5"}
```

## Respuesta normalizada

```json
{
  "city": "Madrid",
  "temperature": {
    "value": 25.5,
    "unit": "°C"
  },
  "condition": "Sunny",
  "wind": {
    "speed": 12.3,
    "unit": "km/h"
  }
}
```

## Proveedores de clima implementados

1. **Mock** (simulado): Provee datos aleatorios para pruebas.
2. **OpenWeather**: Integración con la API de OpenWeatherMap.

## Próximas mejoras

- Añadir más proveedores de clima (WeatherStack, AccuWeather, etc.)
- Implementar caché con tiempo de expiración
- Añadir autenticación y autorización
- Implementar pruebas unitarias y de integración
- Dockerización del servicio
