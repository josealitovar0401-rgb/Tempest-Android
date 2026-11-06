# Tempest: El nacimiento — prototipo Android 0.1

Proyecto Android nativo, offline, en orientación horizontal, con una escena 2D dibujada en Canvas (no es una página web ni pixel art). Es una primera rebanada jugable con controles táctiles, mapa desplazable, aldea, slimes, ataques, experiencia/niveles, recolección y guardado local.

## Compilar en la nube (GitHub Actions)

1. Crea un repositorio en GitHub y sube **el contenido de esta carpeta** (de modo que `settings.gradle` quede en la raíz del repositorio).
2. Abre la pestaña **Actions** y ejecuta **Build Android APK** con **Run workflow** (también corre al subir a `main`).
3. Cuando el job termine correctamente, descarga el artefacto **Tempest-Android-APK**.
4. Descomprime el artefacto. El archivo `app-debug.apk` es un APK de depuración firmado automáticamente con una clave de depuración; permite instalar y probar en un teléfono Android. Habilita la instalación desde esa fuente si Android lo solicita.

El workflow compila con Java 17, Android SDK y Gradle en los runners de GitHub. No se incluye una clave privada de publicación. Para distribuir en Google Play se necesita crear una clave de firma propia y generar un build release.

## Compilar localmente

Requiere Android Studio/SDK, JDK 17 y Gradle compatible con Android Gradle Plugin 8.7.3. Desde la carpeta del proyecto:

```sh
gradle assembleDebug
```

APK de depuración: `app/build/outputs/apk/debug/app-debug.apk`.

## Controles

- Mantén y arrastra el joystick izquierdo para moverte.
- Pulsa el botón azul **MAGIA** para atacar a un slime cercano.
- Pulsa el botón **+** para recolectar madera y piedra.
- El progreso básico se conserva localmente en el teléfono.

## Alcance honesto

Es un prototipo 2D estilizado, no el RPG 3D completo de la descripción. Aún no incluye campaña, personajes aliados, jefes, edificios construibles, música ni mapa de mundo. El APK no ha sido compilado ni comprobado en este entorno; la acción de GitHub es la ruta preparada para producirlo y debe finalizar correctamente antes de instalarlo.
