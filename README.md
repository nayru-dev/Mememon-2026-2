# Proyecto Semestral: Final Reality Tactics

## Descripción del Proyecto

Este repositorio contiene la plantilla base para el proyecto semestral del curso. El objetivo principal es desarrollar una versión simplificada de un juego de combate táctico, enfocado exclusivamente en la implementación de la lógica de negocio mediante el patrón arquitectónico **Modelo-Vista-Controlador (MVC)**. 

En particular, a lo largo del semestre trabajarán en la construcción del **Modelo** (las entidades del juego como personajes, armas, paneles y sus interacciones mediante acciones) y el **Controlador** (el motor lógico encargado de gestionar los turnos, flujo del juego y reglas, como `GameController`). No se implementará una Vista gráfica (frontend), por lo que todo se basará en código Scala puro.

## Referencia Visual

Aunque el proyecto se evaluará mediante pruebas unitarias y lógica de consola sin necesidad de conectarlo a una interfaz web, aquí tienen una imagen del "front-end" conceptual del juego. Esto les servirá para hacerse una idea de cómo deberían verse estructurados lógicamente el mapa (en base a paneles) y sus unidades a lo largo de las entregas:

![resultado.jpg](resultado.jpg)

## Enunciado del Proyecto

Las reglas completas del juego, las entidades requeridas y el detalle de cada entrega parcial y final pueden encontrarse en el enunciado oficial del proyecto.

[Link de ucursos del enunciado](https://www.u-cursos.cl/ingenieria/2026/2/CC3002/1/material_docente/detalle?id=10946097)

## ¿Qué se estará evaluando?

El trabajo a lo largo del semestre será evaluado principalmente en base a los siguientes tres pilares:

1. **Diseño (50%)**: Se evaluará la calidad de su código, exigiendo que este cumpla con los principios de diseño orientado a objetos enseñados en el curso. Se espera un código extensible y con responsabilidades bien definidas.
2. **Testing y Coverage (35%)**: Se evaluará que su código tenga pruebas automatizadas utilizando **MUnit** con una cobertura de al menos el 90% para obtener el puntaje completo. Las pruebas deben comprobar tanto los casos de uso esperados como los casos de borde (ej. fallos en restricciones de acciones).
3. **Documentación (15%)**: Cada clase, interfaz (trait) y método público debe estar debidamente documentado usando el formato Scaladoc.

## Cómo empezar

1. Lean detenidamente el enunciado principal del proyecto.
2. Exploren este código base. Encontrarán una clase inicial `GameController` en el paquete `controller` desde la cual podrán comenzar a articular su lógica.
3. Asegúrense de usar herramientas de control de versiones (Git) de manera constante, documentando adecuadamente sus avances mediante *commits*.

---

# Mi implementación

*Lo anterior corresponde a la plantilla del curso. Desde aquí se describe mi proyecto.*

## Organización del código

- `controller`: `GameController`, maneja los turnos y ejecuta las acciones.
- `mememon.units`: personajes y enemigos.
- `mememon.items`, `mememon.weapons`, `mememon.potions`: utilizables.
- `mememon.actions`, `mememon.actions.spells`: fuentes, objetivos, acciones y hechizos.
- `mememon.board`, `mememon.combat`, `mememon.players`, `mememon.exceptions`: paneles, turnos, jugadores y excepciones.

Cada jerarquía separa un `trait`, una `abstract class` y una `class` concreta.

## Decisiones de diseño

- **Personajes y enemigos son tipos distintos:** comparten `GameUnit`, pero el dueño de un arma es un `Character`, así el compilador impide que un enemigo tenga armas como pide el enunciado.
- **Double Dispatch:** se usa en las acciones y al equipar armas.
  Lo que pasa depende de quién actúa, qué hace y quién lo recibe, y así evito usar `isInstanceOf`.
- **Todo prohibido por defecto:** cada clase solo define lo que sí puede hacer.
  Por ejemplo, un enemigo no recibe Curación porque no define esa acción.
- **Template Method:** los hechizos siguen los mismos pasos, así que están una sola vez en `AbstractSpell`.
  Cada hechizo solo define su efecto.
- **Una acción fallida no cambia nada:** primero se revisan las reglas y después se aplica el efecto.
  `GameController` captura el error y mantiene el turno, así cumpliendo con lo que piden.

## Decisiones donde el enunciado da libertad

- Fortaleza aumenta la defensa de forma permanente.
- Una poción mágica consumida por un personaje no mágico lanza una excepción y no se pierde.
- Los hechizos ignoran la defensa, y Meteorito también daña a aliados y al mago si están en el panel.
- Curación no revive a una unidad derrotada.
- `reset` vacía la partida, sin restaurar las unidades.


