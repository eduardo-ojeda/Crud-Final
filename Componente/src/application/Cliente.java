package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

    private IntegerProperty clienteId;
    private StringProperty nombre;
    private  StringProperty apellidos;
    private StringProperty direccion;

    public Cliente(
    		IntegerProperty clienteId,
    		StringProperty nombre,
    		StringProperty apellidos,
    		StringProperty direccion) {

        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }


    //Id
    public int getClienteId()
    {
    	return clienteId.get();
    }

    public IntegerProperty SetCliente()
    {
        return clienteId;
    }

    //Nombre
    public String getNombre() {
        return nombre.get();
    }

    public StringProperty setNombre()
    {
    	return nombre;
    }

    //Apellido
    public String getApellidos()
    {
        return apellidos.get();
    }

    public StringProperty setApellidos()
    {
        return apellidos;
    }

    //Dirección
    public String getDireccion()
    {
        return direccion.get();
    }

    public StringProperty setDireccion()
    {
        return direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "clienteId=" + clienteId +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}