/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integrador.library_management_system.util;

/**
 *
 * @author exe
 */
import java.util.HashMap;
import java.util.Map;

public class GestorDatos {

    private static final Map<String, Object> datos = new HashMap<>();

    public static void guardarDato(String clave, Object valor) {
        datos.put(clave, valor);
    }

    public static Object obtenerDato(String clave) {
        return datos.get(clave);
    }

    public static void eliminarDato(String clave) {
        datos.remove(clave);
    }
}
