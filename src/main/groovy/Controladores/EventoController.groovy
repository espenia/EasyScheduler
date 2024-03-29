package Controladores

import Modelos.Evento
import Modelos.Tarea
import Servicios.EventoService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

import javax.servlet.http.HttpServletResponse
import javax.validation.Payload
import java.time.LocalDate

@RestController
class EventoController extends ApiControllerBase {
    @Autowired
    private EventoService service

    @PostMapping("/evento/{nombre}&{fecha}&{equipo}&{usuario}")
    @ResponseStatus(HttpStatus.CREATED)
    Evento crearEvento(@PathVariable String nombre, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha, @PathVariable String equipo, @PathVariable String usuario) {
        if (nombre.isAllWhitespace() || equipo.isAllWhitespace() || usuario.isAllWhitespace()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        }
        return service.crearEvento(new Evento(nombre, fecha, equipo, ObjectId.get()), usuario)
    }

    @PutMapping("/evento/{usuario}")
    @ResponseStatus(HttpStatus.OK)
    Evento editarEvento(@RequestBody Evento evento, @PathVariable String usuario) {
        if (usuario.isAllWhitespace()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        }
        return service.editarEvento(evento, usuario)
    }

    @DeleteMapping("/evento/{nombreFecha}&{usuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void borrarEvento(@PathVariable String nombreFecha, @PathVariable String usuario) {
        if (!nombreFecha || nombreFecha.isAllWhitespace() || usuario.isAllWhitespace()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        }
        service.borrarEvento(nombreFecha, usuario)
    }

    @GetMapping("/evento/btfechas/{desde}&{hasta}")
    @ResponseStatus(HttpStatus.OK)
    Evento[] getEventosByFechas(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate desde, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hasta) {
        return service.getEventosByFechas(desde, hasta)
    }

    @GetMapping("/evento/btfechasyNombre/{desde}&{hasta}&{nombre}")
    @ResponseStatus(HttpStatus.OK)
    Evento[] getEventosByFechasAndNombre(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate desde, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hasta,@PathVariable String nombre) {
        if (nombre.isAllWhitespace())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        return service.getEventosByFechasAndNombre(desde, hasta, nombre)
    }

    @GetMapping("/evento/btfechasyEquipo/{desde}&{hasta}&{equipo}")
    @ResponseStatus(HttpStatus.OK)
    Evento[] getEventosByFechasAndEquipo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate desde, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hasta,@PathVariable String equipo) {
        if (equipo.isAllWhitespace())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        return service.getEventosByFechasAndEquipo(desde, hasta, equipo)
    }

    @GetMapping("/evento/equipo/{equipo}")
    @ResponseStatus(HttpStatus.OK)
    Evento[] getEventosByEquipo(@PathVariable String equipo) {
        if (equipo.isAllWhitespace())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        return service.getEventosByEquipo(equipo)
    }

    @GetMapping("/evento/like/{nombre}")
    @ResponseStatus(HttpStatus.OK)
    Evento[] getEventosByNombre(@PathVariable String nombre) {
        if (nombre.isAllWhitespace())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        return service.getEventosByNombre(nombre)
    }

    @GetMapping("/evento/{nombreFecha}")
    @ResponseStatus(HttpStatus.OK)
    Evento getEvento(@PathVariable String nombreFecha) {
        if (nombreFecha.isAllWhitespace())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "")
        return service.getEvento(nombreFecha)
    }
}
