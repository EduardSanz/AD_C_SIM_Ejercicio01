package modelos;

import java.io.Serializable;

public class AnimalDeCompanyia implements Serializable {

    private String especie;
    private String raza;
    private int edad;
    private String color;

    public AnimalDeCompanyia() {
    }

    public AnimalDeCompanyia(String especie, String raza, int edad, String color) {
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.color = color;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "AnimalDeCompanyia{" +
                "especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", color='" + color + '\'' +
                '}';
    }
}
