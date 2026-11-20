# Colección de Artefactos: API REST con Jakarta EE y JPA

Aplicación web para **gestionar el catálogo de una colección de objetos** (por ejemplo, los fondos de un pequeño museo o de un coleccionista). Se pueden dar de alta, listar, editar y eliminar artefactos desde una página web que se comunica con una **API REST en Java** que guarda los datos en **MariaDB** mediante **JPA/Hibernate**.

> Práctica guiada de la asignatura de Programación (1.º DAM).

## Funcionalidades

| Acción | Petición HTTP | Respuesta |
|---|---|---|
| Listar artefactos | `GET /artefactos` | `200` con un array JSON |
| Crear artefacto | `POST /artefactos` (JSON en el cuerpo) | `201` con el artefacto creado y su id |
| Editar artefacto | `PUT /artefactos` (JSON con `id`) | `200`, o `404` si no existe |
| Eliminar artefacto | `DELETE /artefactos?id=5` | `200`, o `404` si no existe |

- Errores devueltos como JSON con su código HTTP (`400` si la petición es incorrecta, por ejemplo un `DELETE` sin id; `500` si falla la base de datos).
- Transacciones con `rollback` si una operación falla.
- Interfaz web (`index.html`) con formulario de alta/edición, tabla de artefactos y confirmación antes de borrar.

Ejemplo de artefacto:

```json
{ "nombre": "Vasija", "descripcion": "Vasija de barro", "anioCreacion": 1850, "procedencia": "Mijas" }
```

## Arquitectura

```
index.html  --fetch/AJAX (JSON)-->  ArtefactoServlet  -->  ArtefactoDAO  -->  JPA / Hibernate  -->  MariaDB
(webapp)                            (controller)           (dao)              (util/JPAUtil)
```

- `model/Artefacto.java`: entidad JPA (`@Entity`, clave primaria autoincremental, `nombre` obligatorio, `descripcion` como texto largo).
- `dao/ArtefactoDAO.java`: operaciones CRUD con `EntityManager` y transacciones.
- `controller/ArtefactoServlet.java`: endpoints REST y conversión JSON con Gson.
- `util/JPAUtil.java`: crea la conexión leyendo las credenciales de **variables de entorno**.

## Tecnologías

Java · Jakarta EE 9.1 (Servlets) · JPA con Hibernate 6.2 · MariaDB · Gson · Maven · HTML, Tailwind CSS y jQuery

## Cómo ejecutarlo

Requisitos: JDK 17 o superior, Maven, MariaDB y **Tomcat 10** (o GlassFish 6+; Tomcat 9 no sirve porque usa `javax`).

1. Crea la base de datos (las tablas las crea Hibernate al arrancar):

   ```sql
   CREATE DATABASE coleccion_artefactos_db;
   ```

2. Define las variables de entorno con tus datos de conexión (no se guardan en el código):

   | Variable | Ejemplo | Por defecto |
   |---|---|---|
   | `DB_URL` | `jdbc:mariadb://localhost:3306/coleccion_artefactos_db` | ese mismo valor |
   | `DB_USER` | `root` | `root` |
   | `DB_PASSWORD` | `tu_contraseña` | vacía |

3. Compila y despliega:

   ```bash
   mvn package
   ```

   Copia `target/ColeccionArtefactosApp-1.0-SNAPSHOT.war` a la carpeta `webapps` de Tomcat (o ejecútalo desde NetBeans) y abre `http://localhost:8080/ColeccionArtefactosApp/`.

## Estado y mejoras pendientes

- Comprobado: el proyecto compila con Maven y las operaciones del DAO (crear, listar, editar y borrar) funcionan contra MariaDB.
- Pendiente de probar: el despliegue completo en Tomcat 10 con la interfaz web.
- Mejoras previstas:
  - Validar los datos en el servidor (ahora solo se valida en el formulario).
  - Añadir pruebas automáticas con JUnit.
