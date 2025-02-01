# PoliGame

Un proyecto de [libGDX](https://libgdx.com/) generado con [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

Este proyecto fue generado con una plantilla en Kotlin que incluye lanzadores de aplicaciones en Kotlin y utilidades de [KTX](https://libktx.github.io/).

## Plataformas

- `core`: Módulo principal con la lógica de la aplicación compartida entre todas las plataformas.
- `android`: Plataforma móvil para Android. Requiere el SDK de Android.
- `ios`: Plataforma móvil para iOS utilizando RoboVM.

## Gradle

Este proyecto utiliza [Gradle](https://gradle.org/) para gestionar dependencias.
El wrapper de Gradle está incluido, por lo que puedes ejecutar tareas de Gradle utilizando los comandos `gradlew.bat` o `./gradlew`.
Tareas y opciones útiles de Gradle:

- `--continue`: al usar esta opción, los errores no detendrán la ejecución de las tareas.
- `--daemon`: con esta opción, se usará el demonio de Gradle para ejecutar las tareas seleccionadas.
- `--offline`: al usar esta opción, se utilizarán los archivos de dependencias almacenados en caché.
- `--refresh-dependencies`: esta opción fuerza la validación de todas las dependencias. Útil para versiones snapshot.
- `android:lint`: realiza la validación del proyecto Android.
- `build`: compila los archivos fuente y genera los archivos necesarios de cada proyecto.
- `cleanEclipse`: elimina los datos del proyecto Eclipse.
- `cleanIdea`: elimina los datos del proyecto IntelliJ.
- `clean`: elimina las carpetas `build`, que almacenan las clases compiladas y los archivos generados.
- `eclipse`: genera los datos del proyecto Eclipse.
- `idea`: genera los datos del proyecto IntelliJ.
- `test`: ejecuta pruebas unitarias (si existen).

Ten en cuenta que la mayoría de las tareas que no son específicas de un solo proyecto pueden ejecutarse con el prefijo `name:`, donde `name` debe ser reemplazado por el ID de un proyecto específico.
Por ejemplo, `core:clean` elimina la carpeta `build` solo del proyecto `core`.

---

## 🆕 Notas de la Versión

### 🎮 ¡Primera Versión Disponible – El Poliperro entra en acción! 🐶🍖

En esta versión, presentamos la lógica del **Poliperro**, el protagonista de esta aventura, junto con el escenario donde se desenvuelve. 🏞️✨

🔹 **Novedades:**
- Implementación de la lógica del Poliperro.
- Creación del escenario interactivo.
- Objetivo del juego: ¡No dejes caer las croquetas y cómelas todas! 🦴🎯

📥 **Descárgala ahora y ayúdanos a mejorar con tu feedback!** 🚀🐕

---

## 🎥 Vista Previa del Juego

[![PoliGame Video](https://raw.githubusercontent.com/Davidvela-306/poliGame/refs/heads/main/assets/example.png)](https://drive.google.com/file/d/1VWIHdGoEdnkxq2jAf8KIVjzzfdaqAmW0/view)

