import java.time.LocalDate

class DuplicarEvento {
    static Evento DuplicarEventos(Evento evento, Usuario usuario, Equipo equipo, fecha = LocalDate.now()) {
        def duplicado = new Evento(evento.Nombre, fecha, equipo, usuario)
        evento.tareas.eachWithIndex {t, index -> duplicado.addTarea(index, t.Nombre, t.HoraInicio, t.HoraFin, usuario, usuario, t.Peso)}
        return duplicado
    }
}
