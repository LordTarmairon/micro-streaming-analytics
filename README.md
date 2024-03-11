## Instalación de la aplicación 

**Contenido del zip**
La aplicación vendrá comprimida en un zip, donde se encontrarán los siguientes elemntos:
 - [docker-compose.yml]: Este archivo contiene las instrucciones necesarias para ejecutarlo con docker y crear los contenedores necesarios para el funcionamiento de la aplicación. Estos son:

    - RabbitMQ: para la gestión de colas.
    - MondogDB: para la persistencia de los datos.

 - [micro-streaming-analytics-0.0.1.jar]: Este es el Jar que contiene el código de la aplicación Java. (No hay que hacer nada con el)

 - [Dockerfile]: Este archivo de docker se encarga de crear el contenedor para almacenar y ejecutar el micro-streaming-analytics-0.0.1.jar. 

 **Como instalar la aplicación**

Lo primero que debemos hacer es decomprimir el contenido del zip en un directorio donde nos resulte comodo trabajar con ello. 

Lo primero que tenemos que instalar es [docker-compose.yml], para disponer tanto del RabbitMQ como de MongoDB, si se instala el Dockerfile primero es posible que al autoejecutarse nos muestre un error de log que no puede conectarse a los servicios necesarios. 

Para instalar el [docker-compose.yml] podemos hacerlo de varias formas:

    - Si disponemos del Intellij IDEA abrimos el archivo con Intellij IDEA y con el plugin de Docker ejecutamos el archivo, este automaticamente nos creará los contenedores. 

    - Situandonos dentro del directorio donde estan nuestros archivos, ejecutamos mediante una shell el siguiente comando: 
   `docker-compose up -d`
    De este modo nos instalara los dos contenedores. 
    
        - RabbitMQ trabajara en localhost bajo los puertos 5672 (para las peticiones de la API) y 15672 (para conectarnos nosotros al panel de gestión) las credenciales de acceso serían: amplia/ampliatask123.

        - MongoDB trabajar  a en localhost bajo el puerto 27018. Utilizará la base de datos admin y se creara el usuario ampliadb/ampliadbtask123.

Para instalar el [Dockerfile] podemos hacerlo tambien de varias formas:
    
    - Si disponemos del Intellij IDEA abrimos el archivo con Intellij IDEA y con el plugin de Docker pulsamos al martillito que aparece donde el archivo, este creará el contenedor.

    -

Para ejecutar este contenedor es necesario lanzar el siguiente comando: 
`docker run -d -p 8080:8080 --env AR_KEY=8f991b285e80f354ed2f2665867e9e4d micro-streaming-analytics`

Desde el docker desktop podemos ver en que estado se encuentran nuestras maqinas. O si lanzamos desde una shell el comando `docker ps -a` este nos mostrara las máquinas que están ejecutandose en que estado estan y por que puertos.

## Estructura de los JSON
La aplicación gestiona varios tipos de JSON 
MessageDTO DataDTO y Statistics

[MessageDTO]: Contiene los datos que varias entidades nos envian a la aplicación (es una sismulación, no son datos reales), además de un identificador, una versión, una fecha de envío, un Id de entidad. Los datos que manegamos son Temperatura, humedad, velocidad del viento y presión. Estos datos vienen representados como DataDTO, y siguen la siguiente estructura JSON:

  {
    "id": INT,
    "version": String,
    "date": Date,
    "entityId": String,
    "data": {
      "temperature": Double,
      "humidity": Double,
      "pressure": Double,
      "windSpeed": Double
    }
  }

O en Array:
[
  {
    "id": INT,
    "version": String,
    "date": Date,
    "entityId": String,
    "data": {
      "temperature": Double,
      "humidity": Double,
      "pressure": Double,
      "windSpeed": Double
    }
  },
]

[Statistics]: Esta clase genera unos valores estadisticos recogidos por las colas de los MessageDTOs que obtenemos. Los valores que contempla son los siguientes: un identificador,  un nombre de entidad, la fecha de creación,  media, mediana, moda, desviación estándar, cuartiles, valor máximo  valor mínimo y el número de datos que han sido evaluados. Esta clase puede ser representada en varios formatos JSON: 

Estructura basica
  {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  }
Tipo de JSON utilizado para devolver todas las estadisticas almacenadas
{
  {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  },
  {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  },
  {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  },
  {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  }
}
Estructura Completa, donde se devuelve un JSON que incluye un array con el tipo de dato al que representa con sus estadisticas 
{
  "TEMPERATURE": {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  },
  "HUMIDITY": {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  },
  "PRESSURE": {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  },
  "WINDSPEED": {
    "id": Long,
    "entityName":String,
    "date":Date,
    "mean": Double,
    "median": Double,
    "mode": Double,
    "standardDeviation": Double,
    "quartiles": [
      Double,
      Double,
      Double
    ],
    "maxValue": Double,
    "minValue": Double,
    "evaluatedData": INT
  }
}

Se han decidido de esta forma para que sean más claros de visualizar para el usuario. 

##  Decisiones de Diseño 

Para esta aplicación hemos decidido utilizar un diseño basado en microservicios con Docker, para poder separar los diferentes contenedores que almacenan nuestras aplicaciones. Además de seguir un concepto de Endpoint o API REST para la realización de peticiones devolviendo en cada caso una ResponseEntity con el estado de la solicitud y el cuerpo. 
Para el código hemos seguido un patrón de diseño basado en interfaces y en MVC (modelo vista controlador) 
Para poder interactuar con la base de datos y poder persistir los datos y disponer de un CRUD hemos utilizado colecciones para MongoDB y Repositorios para las diferentes colecciones. 
Para temas de seguridad hemos decidido utilizar Jasypt, para encriptar todas las conexiones y contraseñas que necesitamos para conectarnos y operar entre las distintas aplicaciones, de esta manera, tampoco tenemos en los archivos de configuración la información en texto plano para poder acceder a RabbitMQ o MongoDB 
Se ha utilizado Log4j para la generación de logs en una carpeta que se crea llamada logs donde se iran almacenando en todo momento los estados por los que pasa nuestra aplicación, las consultas que se realizan y los errores que se puedan encontrar.

Toda la aplicación esta configurada mediante un fichero properties, por si en un futuro se necesitase modificar algun concepto, este properties es leido por la aplicación y generado variables que luego podremos utilizar en el código.

También se ha decidido generar una carpeta Utilities donde sen creado varias clases que son de de vital importancia en diferentes puntos de nuestra aplicación. 


## API REST FULL

La API REST FULL tiene varias secciones, siempre se accede por localhost y el puerto 8080 

La parte variable es la que sigue a continuación:

**MessageController** 

Bajo la URI `/amplia` (GET) seguida de los diferentes metodos a los que podemos acceder, principal funcionalidad crear y enviar a las colas MessageDTOs generados con datos aleatorios. Siendo estos los métodos disponibles: 
    
- `/sendMessage`:(POST) Genera un mensaje de tipo MessageDTO con valores y los manda a la cola para ser procesado.

- `/sendRandom`: (POST) Este metodo debe recibir un parametro de tipo INT (numerico), ya que genera tantos MessageDTOs como parametro indiquemos, tiene un limite de 100 MessageDTOs a procesar, si el numero indicado supera este limite, solo se generaran los 100 primeros. 

- `/version`: Nos responde con la versión actual de la aplicación. 

**MessageDTO** 

Bajo la URI `/api` seguida de los diferentes metodos a los que podemos acceder, principal funcionalidad obtener los MessageDTO. Teniendo los siguientes métodos: 
    
- `/getAllMessageDTOs`: (POST) Nos devuelve en formato JSON un array con todos los MessageDTOs generados hasta el momento. Estos anteriormente fueron almacenados en mongoDB a medida que se iban procesando en la cola. 


**Statistics** 

Bajo la URI `/api` seguida de los diferentes metodos a los que podemos acceder, principal funcionalidad obtener las estadisticas almacenadas en MongoDB para los diferentes datos almacenados de los MessageDTOs. Teniendo los siguientes métodos: 
    
- `/getAllStatistics`: (POST) Nos muestra todas las estadisticas almacenadas de todos los datos obtenidos. 

- `/getStatisticsUntilNow`: (POST) Nos devuelve en formato JSON en diferentes arrays identificados por el tipo de dato, todas las estadisticas relacionadas a ese tipo de dato (tipos de datos son Humedad, Temperatura, Presión...)

- `/getStatisticsByID`: (POST) Nos devuelve en formato JSON una estadistica identificada por el ID indicado. Para este Endpoint es necesario enviar y saber el ID de la estadistica.

- `/getStatisticsByEntityName`: (POST) Nos devuelve en formato JSON un array con todas las estadisticas relacionadas a esa entidad o tipo de dato (Humedad, temperatura, precisión...). Es necesario indicar el tipo de dato del que queremos obtener las estadisticas. 
