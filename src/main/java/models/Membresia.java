package models;

import java.time.LocalDate;

public class Membresia {

    private final int id;
    private final String nombrePersona;
    private final String tipoPlan;
    private final int costoMensual;
    private final LocalDate fechaRegistro;
    private final LocalDate fechaFin;
    private final String foto;

    public Membresia(int id, String nombrePersona, String tipoPlan, int costoMensual, LocalDate fechaRegistro, LocalDate fechaFin, String foto) {
        this.id = id;
        this.nombrePersona = nombrePersona;
        this.tipoPlan = tipoPlan;
        this.costoMensual = costoMensual;
        this.fechaRegistro = fechaRegistro;
        this.fechaFin = fechaFin;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public int getCostoMensual() {
        return costoMensual;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getFoto() {
        return foto;
    }
}
