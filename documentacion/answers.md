
---
# Respuestas a la Prueba Técnica – Blossom

Este documento contiene las respuestas a las preguntas planteadas en la prueba técnica para la posición de Java Developer en Blossom, aplicadas al contexto del proyecto **weather-proxy**.

---

## 1. Patrón de diseño: Strategy

**¿Cómo has utilizado el patrón Strategy en un proyecto real en Java?**

En `weather-proxy`, el patrón Strategy se usó para encapsular distintas formas de consumo de servicios de clima. Por ejemplo, mediante una interfaz `WeatherServiceStrategy` y varias implementaciones (como `OpenWeatherService`), se facilita el cambio de proveedor sin alterar el código del consumidor, cumpliendo el principio de abierto/cerrado.

---

## 2. Java Streams

**¿Cómo funcionan los Java Streams? ¿Puedes dar un ejemplo de transformar una lista de objetos en un mapa resumen?**

Los Java Streams permiten transformar colecciones de forma declarativa. En este proyecto, podríamos agrupar los datos climáticos por ciudad y obtener un promedio de temperatura:

```
Map<String, Double> avgTemps = weatherDataList.stream()
    .collect(Collectors.groupingBy(
        WeatherData::getCity,
        Collectors.averagingDouble(WeatherData::getTemperature)
    ));
```

## 3. Manejo de nulls y Optional
¿Cómo usas Optional de manera efectiva? ¿Cuándo es apropiado?

Se usa Optional para consultas condicionales, como recuperar clima en caché:
```
Optional<WeatherData> cached = weatherRepository.findByCity("Bogotá");
cached.ifPresent(data -> log.info("Clima desde caché: {}", data));
```
Esto evita errores por null y hace explícita la posibilidad de ausencia de datos. Es ideal en métodos de retorno, pero no como atributos de entidades o parámetros.

## 4. Manejo de errores en integraciones REST
¿Cómo manejarías errores de una integración con una API REST en Java (por ejemplo, 400, 401, 500)?
En weather-proxy, se captura y maneja errores con bloques try-catch y excepciones personalizadas:
```
try {
var response = restTemplate.getForEntity(url, WeatherResponse.class);
return response.getBody();
} catch (HttpClientErrorException | HttpServerErrorException ex) {
log.error("Error externo: {}", ex.getStatusCode());
throw new ExternalWeatherServiceException("Error en proveedor de clima", ex);
}
```

También se usa @ControllerAdvice para un manejo global de excepciones y respuestas coherentes al cliente.
