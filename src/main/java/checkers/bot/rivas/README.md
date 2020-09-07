# Explicación del bot CHECKERS

Para este proyecto se uso una mejora del algoritmo minmax que es la **poda Alpha-beta** que básicamente es una técnica de búsqueda que reduce el numero de nodos evaluados en el árbol de juego por el algoritmo minmax.

Practicamente se siguió el pseudocodigo dado en las diapositivas para la implementación de la poda MIN-MAX, por tal razón se crearon los métodos: MINUTILITY y MAXUTILITY para que el método no sea tan largo.
**ALPHA** se definió como Integer.MIN_VALUE y **BETA** como Integer.MAX_VALUE.

Se crearon 3 clases: **RIVASBOT, MYPLAYERGAME, CHECKERSPABLO**.
RIVAS BOT implementa play de CheckersPlayer, define la constante del nivel de profundidad al que se llegará y una variable boolean que define si el jugador es rojo o negro.
La clase CHECKERS PABLO hereda de CheckersBot algunos métodos como **CLONE O GET CURRENT PLAYER.**
y finalmente MYPLAYERGAME es objeto que fue creado con la finalidad de no pasar demasiados atributos a los métodos, si no simplemente el objeto haciendo más limpio el código.

### Métodos Implementados

**GET BEST MOVE, GET BESTMOVEOPTION, GENERATEPOSSIBLEMOVES AND CAPTURES, GETUTILITYMAP, MINUTILITY, MAXUTILITY Y la HEURISTICA.**

#### getBestMoveOption

Este método ve la mejor opción para mi movimiento, viendo la utilidad de getUtility llamo al objeto CHECKERSMOVE y después de una iteración le doy la mejor opción posible.
generatePossibleMovesAndCaptures genera los posibles movimientos si no hay una captura, en caso de existir una captura esta la devuelve.

#### getUtility

En este método se define Alpha y beta, se verifica la altura y devolver la heurística y si la lista esta vacía, aparte de verificar si es mi jugador para ver maxUtilityForMyPlayer o minUtilityForOtherBot para mi enemigo

#### maxUtilityForMyPlayer

Simplemente ve la mejor opción para mi y verifica si Alpha es mayor a beta para hacer un break y así no perder el tiempo explorando algo innecesario.

#### minUtilityForOtherBot

Simplemente ve la peor opción para mi enemigo y verifica si Alpha es mayor a beta, está beta se compara con minutility y es el nuevo beta002E

#### getHeuristic

La Heuristica definida es realmente es muy sencilla, está se definió con sumar 2 si la pieza no está coronada y suma 5 la pieza es REINA, esto se hace por cada pieza mía y resta la misma cantidad si la pieza no es mía.

Adicionalmente cree un objeto llamado **MYPLAYERGAME** con los atributos, board, level, startingplayer y myplayer.
Todo esto con el fin de no dar muchos parámetros en **getutility y getUtilityMap.**

**_Cabe recalcar que con solo leer el nombre de los métodos y las constantes se puede saber que hace cada uno. En si se trató de limpiar el código en lo más posible reduciendo las funciones largas a menos de 20 lineas y haciendo que se fácil de leer._**
