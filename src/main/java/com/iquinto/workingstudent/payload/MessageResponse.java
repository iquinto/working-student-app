package com.iquinto.workingstudent.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponse {
    public final static String REGISTER_CORRECTLY = "Se ha registrado correctamente el usuario. ";
    public final static String SAVE_CORRECTLY = "Se ha guardado correctamente. ";
    public final static String UPDATE_CORRECTLY = "Se ha actualizado correctamente. ";
    public final static String DELETE_CORRECTLY = "Se ha eliminado correctamente correctamente. ";

    public final static String REGISTER_FAILED  = "No se  ha registrado correctamente el usuario. ";
    public final static String SAVE_FAILED = "No se ha guardado correctamente. ";
    public final static String UPDATE_FAILED = "No se ha actualizado correctamente. ";
    public final static String DELETE_FAILED = "No se ha eliminado correctamente. ";

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
