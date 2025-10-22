# Errores Encontrados

## Empresa.agregarChofer

Solamente lanza ChoferRepetidoException cuando la instancia de chofer ya existe, no si existe una instancia distinta con el mismo DNI.

## Empresa.agregarCliente

No registra correctamente al cliente ya que luego al usar Empresa.agregarPedido lanza ClienteNoExisteException.

Para poder realizar las pruebas de Empresa.agregarPedido, se usa setClientes.

## Empresa.agregarPedido

No lanza ClienteConViajePendienteException cuando el cliente tiene un viaje iniciado.

## Empresa.calificacionDeChofer

No lanza SinViajesException cuando el chofer no tiene viajes realizados. En vez de eso, devuelve 0.0.
