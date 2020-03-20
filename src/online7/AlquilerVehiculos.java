/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online7;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import static online7.ES.*;
import static online7.Utilidades.*;
import static online7.Cliente.*;
import static online7.Enumerados.*;
import static online7.Mercancias.*;
import static online7.Alquiler.*;

/**
 *
 * @author juans
 */
public class AlquilerVehiculos {

    private static final int MAX_VEHICULOS = 50;
    private static final int MAX_CLIENTES = 50;
    private static final int MAX_ALQUILERES = 50;
    private static Vehiculo[] vehiculos = new Vehiculo[MAX_VEHICULOS];
    private static Cliente[] clientes = new Cliente[MAX_CLIENTES];
    private static Alquiler[] alquileres = new Alquiler[MAX_ALQUILERES];
    private static boolean esCorrecto;
    private static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public AlquilerVehiculos() {
    }

    public static Cliente getCliente(String dni) {

        Cliente c = null;

        if ((Utilidades.comprobarDni(dni))) {
            for (int i = 0; i < MAX_CLIENTES; i++) {
                //si la posicion array no es nula y coincide el dni existente lo devuelve
                if (clientes[i] != null && clientes[i].getDni().equalsIgnoreCase(dni)) {
                    c = clientes[i];
                    break;
                }
            }
        }

        return c;

    }

    /*Crea un método anadirCliente que añada un cliente al array de clientes si cabe
y si no existe ningún otro con el mismo DNI o muestre un mensaje con el error que se ha producido.*/
    public static void anadirCliente(Cliente c) {

        int pos = -1;
        boolean encontrado = false;

        //Primero comprobamos si existen espacios libres
        for (int i = 0; i < clientes.length; i++) {

            if (clientes[i] == null) {
                pos = i;
                break;
            }
        }
        //Encontrado un espacio comparamos los dni        
        if (pos != -1) {
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i] != null && clientes[i].getDni().equalsIgnoreCase(c.getDni())) {
                    encontrado = true;
                    break;
                }
            }
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("No hay espacio para nuevos clientes");
            escribirLn("------------------------------------------------\n");
        }

        if (encontrado) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Dni/Nie ya registrado. Cliente no añadido.");
            escribirLn("------------------------------------------------\n");
        } else {
            clientes[pos] = c;
            escribirLn(clientes[pos].toString());
            escribirLn("\nCliente añadido correctamente.");
            escribirLn("------------------------------------------------\n");
        }
    }

    /*Crea un método borrarCliente que elimine un cliente, dado su DNI.*/
    public static void borrarClientes(String dni) {
        boolean eliminado = false;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getDni().equalsIgnoreCase(dni)) {
                clientes[i] = null;
                eliminado = true;
                escribirLn("\nCliente borrado.");
                escribirLn("------------------------------------------------\n");
                break;
            }
        }
        if (!eliminado) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Dni/Nie no registrado.");
            escribirLn("------------------------------------------------\n");
        }
    }

    //Devuelve la posición del array de un DNI determinado.
    public static int buscarCliente(String dni) {
        int pos = -1;

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null && dni.equalsIgnoreCase(clientes[i].getDni())) {
                pos = i;
            }
        }
        return pos;
    }

    /*Crea un método getVehiculo que se le pase la matrícula de un turismo y nos
lo devuelva si este existe o null en caso contrario.*/
    public static Vehiculo getVehiculo(String matricula) {
        Vehiculo v = null;

        if ((Utilidades.comprobarMatricula(matricula))) {
            for (int i = 0; i < MAX_VEHICULOS; i++) {
                //si la posicion array no es nula y coincide el dni existente lo devuelve
                if (vehiculos[i] != null && vehiculos[i].getMatricula().equalsIgnoreCase(matricula)) {
                    v = vehiculos[i];
                    break;
                }
            }
        }
        return v;
    }

    /*Crea un método anadirVehiculo que añada un coche al array de vehiculos si
cabe y no existe ningún otro con la misma matrícula o muestre un mensaje con el
error que se ha producido.*/
    public static void anadirVehiculo() {
        //pos para guardar primera posición nula(vacia)
        int pos = -1;
        boolean encontrado = false;

        //Comprobamos si existen espacios libres
        for (int i = 0; i < vehiculos.length; i++) {

            if (vehiculos[i] == null) {
                pos = i;
                break;
            }
        }
        //existe espacio en el array, ha dado toda la vuelta y pos no ha cambiado. Ahora comprobamos que no este ya guarda la matrícula
        //Si 0 esta vacia, pos = 0 por ser null, pero 0 aún no tine vehículo para poder comparar matrícula.(pos!=0)
        if (pos != -1) {

            //Existe espacio vacío. Comenzamos a pedir información al usuario.
            String matricula = (leerCadena("\nIntroduce matrícula del vehículo: ")).toUpperCase();

            if (comprobarMatricula(matricula)) {

                for (int i = 0; i < vehiculos.length; i++) {

                    if (vehiculos[i] != null && vehiculos[i].getMatricula().equalsIgnoreCase(matricula)) {
                        // escribirLn(vehiculos[i].getMatricula());
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado) {
                    escribirLn("\n********************ATENCION********************");
                    escribirLn("Matrícula ya registrada. Vehiculo no añadido.");
                    escribirLn("------------------------------------------------\n");
                } else {

                    //Si la matricula no está registrada, comenzamos a pedir los datos del vehiculo.
                    String marca = (leerCadena("\nIntroduce marca del vehículo: ")).toUpperCase();
                    String modelo = (leerCadena("\nIntroduce modelo del vehículo: ")).toUpperCase();
                    int cilindrada = leerEntero("\nIntroduce cilindrada del vehículo: ");
                    int seleccion = leerEntero(1, 2, "\nSelecciona tipo de vehículo.\n1.Mercancias.\n2.Turismo.");

                    if (seleccion == 1) {

                        int pma = leerEntero("\nVas a registrar una furgoneta.\nIntroduce pma: ");
                        int volumen = leerEntero("\nIntroduce volumen: ");
                        boolean refrigerado = leerBoolean("\nVehículo refrigerado S/N");

                        int posicion = leerEntero(1, 3, "\nSelecciona un tamaño:\n1.Grande\n2.Mediano\n3.Pequeño");

                        Tamanio tamanio = Tamanio.values()[posicion - 1];

                        Furgoneta furgoneta = new Furgoneta(matricula, marca, modelo, cilindrada, pma, volumen, refrigerado, tamanio);

                        vehiculos[pos] = furgoneta;

                        escribirLn("\nVehiculo añadido correctamente.");
                        escribirLn("------------------------------------------------\n");

                    } else {

                        int numPuertas = leerEntero(3, 5, "\nVas a añadir un turismo.\nIntroduce número de puertas entre 3 y 5:");

                        int posicion = leerEntero(1, 4, "\nSelecciona tipo de combustible:\n1.Gasolina.\n2.Diesel.\n3.Híbrido.\n4.Eléctrico.");

                        Combustible combustible = Combustible.values()[posicion - 1];

                        seleccion = leerEntero(1, 2, "\nEscoja tipo de turismo:\n1.Familiar.\n2.Deportivo");

                        if (seleccion == 1) {
                            escribirLn("\nHas escogido añadir un familiar.");

                            int numPlazas = leerEntero(4, 7, "\nElija el número de plazas entre 4 y 7.");

                            boolean sillaBebe = leerBoolean("\n¿Tiene silla de bebe? S/N");

                            Familiar familiar = new Familiar(matricula, marca, modelo, cilindrada, numPuertas, combustible, numPlazas, sillaBebe);

                            vehiculos[pos] = familiar;

                            escribirLn("\nVehículo familiar añadido correctamente.");
                            escribirLn("------------------------------------------------\n");

                        } else {
                            escribirLn("\nHas escogido añadir un deportivo");

                            boolean descapotable = leerBoolean("\n¿Deportivo descapotable? S/N");

                            int opcion = leerEntero(1, 2, "\nSelecciona tipo de caja de cambios: \n1.Automático.\n2.Manual.");

                            CajaCambios cambio = CajaCambios.values()[opcion - 1];

                            Deportivo deportivo = new Deportivo(matricula, marca, modelo, cilindrada, numPuertas, combustible, cambio, descapotable);

                            vehiculos[pos] = deportivo;

                            escribirLn("\nVehículo deportivo añadido correctamente.");
                            escribirLn("------------------------------------------------\n");

                        }

                    }

                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Formato de matrícula incorrecto.");
                escribirLn("------------------------------------------------\n");

            }
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("No hay espacio para nuevos vehiculos.");
            escribirLn("------------------------------------------------\n");
        }

    }

    /* Crea un método borrarVehiculo que borre un vehiculo, dada su matrícula, del
    array de vehiculos. */
    public static void borrarVehiculo(String m) {
        int pos = -1;
        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null && vehiculos[i].getMatricula().equalsIgnoreCase(m)) {
                vehiculos[i] = null;
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Matricula no registrada.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\nVehiculo borrado correctamente.");
            escribirLn("------------------------------------------------\n");
        }
    }

    //Devuelve la posición del array de una matrícula determinada.
    public static int buscarVehiculo(String matricula) {
        int pos = -1;

        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null && matricula.equalsIgnoreCase(vehiculos[i].getMatricula())) {
                pos = i;
            }
        }
        return pos;
    }

    /*Crea un método nuevoAlquiler que dado un cliente y un vehiculo cree un nuevo
alquiler y lo añada al array de alquileres. Para ello se debe comprobar que el vehiculo esté disponible.*/
    public static void nuevoAlquiler(Cliente cliente, Vehiculo vehiculo) {
        int pos = -1;
        if (vehiculo.getDisponible()) {

            for (int i = 0; i < MAX_ALQUILERES; i++) {
                if (alquileres[i] == null) {
                    pos = i;
                    alquileres[i] = new Alquiler(cliente, vehiculo);
                    break;
                }
            }
            if (pos == -1) {
                escribirLn("\n********************ATENCION********************");
                escribirLn("No existe espacio para nuevos alquileres.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("\nVehiculo no disponible.");
            escribirLn("------------------------------------------------\n");
        }

    }

    /*Crea un método cerrarAlquiler que cierre el alquiler dado un cliente y un vehiculo.*/
    public static void cerrarAlquiler(Cliente cliente, Vehiculo vehiculo) {
        int pos = -1;

        for (int i = 0; i < MAX_ALQUILERES; i++) {
            if (alquileres[i].getCliente().equals(cliente) && alquileres[i].getVehiculo().equals(vehiculo)) {
                alquileres[i].cerrar(vehiculos);
                escribirLn("\nAlquiler finalizado con exito.");
            }
        }
        if (pos == -1) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("No existe alquiler con ese cliente y vehículo");
            escribirLn("------------------------------------------------\n");
        }

    }

    //---------------------------------------MAIN------------------------------------------------------//  
    public static void main(String[] args) {

        leerDatos();

        int opcion;
        Scanner sc = new Scanner(System.in);
        escribirLn("-------------------------------------------------------");
        escribirLn("Bienvenido al programa de gestión de Alquileres Xuanin.");
        escribirLn("-------------------------------------------------------");

        do {
            esCorrecto = false;

            escribirLn("Opciones: ");
            escribirLn("1. Añadir cliente.\n2. Borrar cliente.\n3. Listar clientes.\n"
                    + "4. Añadir vehiculo.\n5. Borrar vehiculo.\n6. Listar vehiculos.\n"
                    + "7. Nuevo alquiler.\n8. Cerrar alquiler.\n9. Listar alquileres.\n"
                    + "10. Guardar datos.\n11. Salir.");

            opcion = leerEntero("\nIntroduce opción: ");

            switch (opcion) {
                case 1:
                    caseAnadirCliente();
                    break;
                case 2:
                    caseBorrarCliente();
                    break;
                case 3:
                    caseListarClientes();
                    break;
                case 4:
                    anadirVehiculo();
                    break;
                case 5:
                    caseBorrarVehiculo();
                    break;
                case 6:
                    caseListarVehiculos();
                    break;
                case 7:
                    caseNuevoAlquiler();
                    break;
                case 8:
                    caseCerrarAlquiler();
                    break;
                case 9:
                    caseListarAlquileres();
                    break;
                case 10:
                    guardarDatos();
                    break;
                case 11:
                    confirmarGuardarDatos(); 
                    escribirLn("\nFin de programa");
                    escribirLn("------------------------------------------------\n");
                    escribirLn("------------------------------------------------\n");
                    break;

            }
        } while (opcion != 11);
    }

//---------------------------------------METODOS OPCIONES MENU-------------------------------------//
    public static void caseAnadirCliente() {

        String nie;
        String dni = leerCadena("\nIntroduce Dni/Nie de cliente: ").toUpperCase();
        boolean value = false;

        if (comprobarDni(dni)) {
            nie = dni;
            if (nie.substring(0, 1).equalsIgnoreCase("X")
                    || nie.substring(0, 1).equalsIgnoreCase("Y")
                    || nie.substring(0, 1).equalsIgnoreCase("Z")) {
                nie = pasarNieADni(nie);
            }
            //Comparamos la letra del dni/nie con la letra calculada con el método calcular letra
            if (nie.substring(8, 9).equalsIgnoreCase(calcularLetraDni(nie.substring(0, 8)))) {
                //escribirLn("DNI correcto");
                String nombre = leerCadena("\nIntroduce nombre de cliente: ").toUpperCase();
                String direccion = leerCadena("\nIntroduce dirección de cliente: ").toUpperCase();
                String localidad = leerCadena("\nIntroduce localidad de cliente: ").toUpperCase();

                //utilizar un while para volver a pedir el cp si fuera erroneo
                while (!value) {
                    String cod_postal = leerCadena("\nIntroduce el código postal de cliente: ");
                    if (comprobarCodigoPostal(cod_postal)) {
                        value = true;
                        anadirCliente(new Cliente(dni, nombre, direccion, localidad, cod_postal));

                    } else {
                        escribirLn("\nCódigo postal incorrecto.");
                        escribirLn("------------------------------------------------\n");
                    }
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Letra de Dni/Nie incorrecta.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de Dni/Nie incorrecto.");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void caseBorrarCliente() {

        String dni = leerCadena("\nIntroduce Dni/Nie de cliente a borrar: ").toUpperCase();
        if (comprobarDni(dni)) {
            if (dni.substring(0, 1).equalsIgnoreCase("X")
                    || dni.substring(0, 1).equalsIgnoreCase("Y")
                    || dni.substring(0, 1).equalsIgnoreCase("Z")) {
                dni = pasarNieADni(dni);
            }
            //Comparamos la letra del dni con la letra calculada con el método calcular letra
            if (dni.substring(8, 9).equalsIgnoreCase(calcularLetraDni(dni.substring(0, 8)))) {
                borrarClientes(dni);
                esCorrecto = true;
            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Letra de Dni/Nie incorrecta.");
                escribirLn("------------------------------------------------\n");
            }
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de Dni/Nie incorrecto");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseListarClientes() {

        boolean vacio = true;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null) {
                escribirLn(clientes[i].toString());
                vacio = false;

            }
        }
        if (vacio) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("No existen clientes.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseBorrarVehiculo() {

        String matricula = (leerCadena("\nIntroduce matrícula del vehiculo a borrar: ")).toUpperCase();
        comprobarMatricula(matricula);
        if (comprobarMatricula(matricula)) {
            borrarVehiculo(matricula);

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de matrícula incorrecto.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseListarVehiculos() {
        boolean vacio = true;

        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null) {
                escribirLn(vehiculos[i].toString());
                vacio = false;
            }

            if (vacio) {

                escribirLn("\n********************ATENCION********************");
                escribirLn("No existen vehículos.");
                escribirLn("------------------------------------------------\n");
            }
        }

    }

    public static void caseNuevoAlquiler() {
        String dni;
        String dniAux;
        String matricula;
        int posCliente;
        int posVehiculo;
        boolean value = false;

        dni = (leerCadena("\nIntroduce Dni/Nie del cliente: ")).toUpperCase();
        dniAux = dni;

        if (comprobarDni(dniAux)) {

            //Comprobamos si es un NIE, y en caso de serlo lo convertimos a DNI para posteriormente
            //comprobar la letra final.
            if (dniAux.substring(0, 1).equalsIgnoreCase("X")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Y")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Z")) {
                dniAux = pasarNieADni(dniAux);
            }

            //Comprobamos la letra final del dni para validarlo
            if (dniAux.substring(8, 9).equalsIgnoreCase(calcularLetraDni(dniAux.substring(0, 8)))) {

                posCliente = buscarCliente(dni);

                while (!value) {
                    if (posCliente != -1) {

                        matricula = leerCadena("\nIntroduce matricula del vehículo: ").toUpperCase();

                        if (comprobarMatricula(matricula)) {

                            posVehiculo = buscarVehiculo(matricula);

                            if (posVehiculo != -1) {

                                if (vehiculos[posVehiculo].getDisponible() == true) {

                                    for (int i = 0; i < alquileres.length && !value; i++) {
                                        if (alquileres[i] == null) {
                                            vehiculos[posVehiculo].setDisponible(false);
                                            alquileres[i] = new Alquiler(clientes[posCliente], vehiculos[posVehiculo]);
                                            System.out.println(alquileres[i]);
                                            value = true;
                                        }
                                    }

                                    if (!value) {
                                        escribirLn("\n********************ATENCION********************");
                                        escribirLn("No hay espacio en la memoria para nuevos alquileres.");
                                        escribirLn("------------------------------------------------\n");
                                    } else {
                                        escribirLn("Alquiler registrado correctamente");
                                        escribirLn("------------------------------------------------\n");
                                    }//FIN

                                } else {
                                    escribirLn("\n********************ATENCION********************");
                                    escribirLn("El vehiculo no está disponible en este momento.");
                                    escribirLn("------------------------------------------------\n");
                                }

                            } else {
                                escribirLn("\n********************ATENCION********************");
                                System.out.println("El vehículo no está registrado.");
                                escribirLn("------------------------------------------------\n");
                            }

                        } else {
                            escribirLn("\n********************ATENCION********************");
                            System.out.println("Formato de matrícula incorrecto.");
                            escribirLn("------------------------------------------------\n");
                        }

                    } else {
                        escribirLn("\n********************ATENCION********************");
                        escribirLn("No hay ningún cliente registro con el Dni/Nie proporciado");
                        escribirLn("------------------------------------------------\n");
                    }
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Letra del Dni/Nie incorrecto.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de Dni/Nie incorrecto");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void caseCerrarAlquiler() {
        String dni;
        String dniAux;
        String matricula;
        int posCliente;
        int posVehiculo;
        boolean value = false;
        Cliente cliente;
        Vehiculo vehiculo;

        dni = (leerCadena("\nIntroduce Dni/Nie del cliente: ")).toUpperCase();
        dniAux = dni;

        if (comprobarDni(dniAux)) {

            //Comprobamos si es un NIE, y en caso de serlo lo convertimos a DNI para posteriormente
            //comprobar la letra final.
            if (dniAux.substring(0, 1).equalsIgnoreCase("X")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Y")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Z")) {
                dniAux = pasarNieADni(dniAux);
            }

            //Comprobamos la letra final del dni para validarlo
            if (dniAux.substring(8, 9).equalsIgnoreCase(calcularLetraDni(dniAux.substring(0, 8)))) {

                posCliente = buscarCliente(dni);

                if (posCliente != -1) {

                    cliente = clientes[posCliente];

                    matricula = leerCadena("\nIntroduce matricula del vehículo: ").toUpperCase();

                    if (comprobarMatricula(matricula)) {

                        posVehiculo = buscarVehiculo(matricula);

                        if (posVehiculo != -1) {

                            vehiculo = vehiculos[posVehiculo];

                            if (!vehiculo.getDisponible()) {

                                for (int i = 0; i < alquileres.length && !value; i++) {
                                    if (alquileres[i] != null) {

                                        if (alquileres[i].getCliente().equals(cliente) && alquileres[i].getVehiculo().equals(vehiculo)) {

                                            alquileres[i].cerrar(vehiculos);
                                            value = true;

                                        }
                                    }
                                }

                                if (!value) {
                                    escribirLn("\n********************ATENCION********************");
                                    escribirLn("No hay alquileres que contengan el cliente y el vehiculo indicado.");
                                    escribirLn("------------------------------------------------\n");
                                } else {
                                    escribirLn("\nAlquiler cerrado correctamente");
                                    escribirLn("------------------------------------------------\n");
                                }//FIN

                            } else {
                                escribirLn("\n********************ATENCION********************");
                                escribirLn("El vehiculo no está en alquiler.");
                                escribirLn("------------------------------------------------\n");
                            }

                        } else {
                            escribirLn("\n********************ATENCION********************");
                            System.out.println("El vehículo no está registrado.");
                            escribirLn("------------------------------------------------\n");
                        }

                    } else {
                        escribirLn("\n********************ATENCION********************");
                        System.out.println("Formato de matrícula incorrecto.");
                        escribirLn("------------------------------------------------\n");
                    }

                } else {
                    escribirLn("\n********************ATENCION********************");
                    escribirLn("No hay ningún cliente registro con el Dni/Nie proporciado");
                    escribirLn("------------------------------------------------\n");
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Letra del Dni/Nie incorrecto.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de Dni/Nie incorrecto");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void caseListarAlquileres() {
        boolean vacio = true;

        for (int i = 0; i < alquileres.length; i++) {
            if (alquileres[i] != null) {
                escribirLn(alquileres[i].toString());
                vacio = false;
            }

        }
        if (vacio) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("No existen alquileres.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void guardarDatos() {

        //Archivo para array clientes.
        String ruta = "clientes.txt";

        String datosCliente = "";

        //escribirArchivo(String ruta, String datos, boolean sobreescribir)
        for (int i = 0; i < clientes.length; i++) {

            if (clientes[i] != null) {
                datosCliente += clientes[i].getDni() + "#" + clientes[i].getNombre()
                        + "#" + clientes[i].getDireccion() + "#" + clientes[i].getLocalidad() + "#" + clientes[i].getCodigoPostal() + "\n";
            }

        }

        if (escribirArchivo(ruta, datosCliente, true)) {
            escribirLn("\nDatos de clientes guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

        //Archivo para array vehículos
        ruta = "vehiculos.txt";

        String datosVehiculos = "";

        for (int i = 0; i < vehiculos.length; i++) {

            //int numPuertas, Enumerados.Combustible combustible, CajaCambios cambio,boolean descapotable
            if (vehiculos[i] != null) {

                if (vehiculos[i] instanceof Deportivo) {

                    Deportivo aux = (Deportivo) vehiculos[i];

                    datosVehiculos += "Deportivo#" + aux.getMatricula() + "#" + aux.getMarca() + "#"
                            + aux.getModelo() + "#" + aux.getCilindrada() + "#" + aux.getDisponible() + "#"
                            + +aux.getNumeroPuertas() + "#" + aux.getCombustible() + "#" + aux.getCambio() + "#" + aux.getDescapotable() + "\n";

                }

                if (vehiculos[i] instanceof Familiar) {

                    Familiar aux = (Familiar) vehiculos[i];

                    datosVehiculos += "Familiar#" + aux.getMatricula() + "#" + aux.getMarca() + "#"
                            + aux.getModelo() + "#" + aux.getCilindrada() + "#" + aux.getDisponible() + "#"
                            + +aux.getNumeroPuertas() + "#" + aux.getCombustible() + "#" + aux.getNumPlazas() + "#" + aux.getSillaBebe() + "\n";

                }

                if (vehiculos[i] instanceof Furgoneta) {

                    Furgoneta aux = (Furgoneta) vehiculos[i];

                    datosVehiculos += "Furgoneta#" + aux.getMatricula() + "#" + aux.getMarca() + "#"
                            + aux.getModelo() + "#" + aux.getCilindrada() + "#" + aux.getDisponible() + "#"
                            + +aux.getPma() + "#" + aux.getVolumen() + "#" + aux.getRefrigerado() + "#" + aux.getTamanio() + "\n";

                }

            }

        }

        if (escribirArchivo(ruta, datosVehiculos, true)) {
            escribirLn("\nDatos de vehículos guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

        //Archivo para array alquileres
        ruta = "alquileres.txt";
        String datosAlquileres = "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (int i = 0; i < alquileres.length; i++) {

            if (alquileres[i] != null) {

                datosAlquileres += alquileres[i].getCliente().getDni() + "#"
                        + alquileres[i].getVehiculo().getMatricula() + "#"
                        + sdf.format(alquileres[i].getFecha().getTime())
                        + "#" + alquileres[i].getDias() + "\n";

            }

        }
        if (escribirArchivo(ruta, datosAlquileres, true)) {
            escribirLn("\nDatos de alquileres guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void leerDatos() {

        int tipoVehiculo = 0; //Esta variable la utilizaremos para posteriormente saber qué tipo de vehiculo almacenamos en el array de alquileres

        //CLIENTES
        String clientesTxt = leerArchivo("clientes.txt");

        if (!clientesTxt.isEmpty()) {

            String[] datosClientes = clientesTxt.split("\n");

            for (int i = 0; i < datosClientes.length; i++) {

                String[] datos = datosClientes[i].split("#");

                Cliente nuevoCliente = new Cliente(datos[0], datos[1], datos[2], datos[3], datos[4]);

                clientes[i] = nuevoCliente;

            }

        }

        //VEHICULOS
        String vehiculosTxt = leerArchivo("vehiculos.txt");

        if (!vehiculosTxt.isEmpty()) {

            String[] datosVehiculos = vehiculosTxt.split("\n");

            for (int i = 0; i < datosVehiculos.length; i++) {

                String[] datos = datosVehiculos[i].split("#");

                //Vehiculo vehiculo = null;
                String matricula = datos[1];
                String marca = datos[2];
                String modelo = datos[3];
                int cilindrada = Integer.parseInt(datos[4]);
                boolean disponible = (datos[5].equalsIgnoreCase("true")) ? true : false;

                switch (datos[0]) {

                    case "Furgoneta":
                        tipoVehiculo = 1;
                        //int pma, int volumen, boolean refrigerado, Tamanio tamanio
                        int pma = Integer.parseInt(datos[6]);
                        int volumen = Integer.parseInt(datos[7]);
                        boolean refrigerado = (datos[8].equalsIgnoreCase("true")) ? true : false;
                        Tamanio tamanio = null;

                        if (datos[9].equalsIgnoreCase("GRANDE")) {
                            tamanio = Enumerados.Tamanio.GRANDE;
                        } else if (datos[9].equalsIgnoreCase("MEDIANO")) {
                            tamanio = Enumerados.Tamanio.MEDIANO;
                        } else {
                            tamanio = Enumerados.Tamanio.PEQUENIO;
                        }

                        vehiculos[i] = new Furgoneta(matricula, marca, modelo, cilindrada,
                                pma, volumen, refrigerado, tamanio);
                        vehiculos[i].setDisponible(disponible);
                        break;

                    case "Deportivo":

                        tipoVehiculo = 2;

                        //protected int numPuertas;
                        // protected Combustible combustible;
                        // private CajaCambios cambio;
                        //  private boolean descapotable;
                        int numPuertas = Integer.parseInt(datos[6]);
                        Combustible combustible = null;

                        if (datos[7].equalsIgnoreCase("GASOLINA")) {
                            combustible = Enumerados.Combustible.GASOLINA;
                        } else if (datos[7].equalsIgnoreCase("DIESEL")) {
                            combustible = Enumerados.Combustible.DIESEL;
                        } else if (datos[7].equalsIgnoreCase("HIBRIDO")) {
                            combustible = Enumerados.Combustible.HIBRIDO;
                        } else {
                            combustible = Enumerados.Combustible.ELECTRICO;
                        }

                        CajaCambios cambio = null;

                        if (datos[8].equalsIgnoreCase("AUTOMATICO")) {
                            cambio = Enumerados.CajaCambios.AUTOMATICO;
                        } else {
                            cambio = Enumerados.CajaCambios.MANUAL;
                        }

                        boolean descapotable = (datos[9].equalsIgnoreCase("true")) ? true : false;

                        vehiculos[i] = new Deportivo(matricula, marca, modelo, cilindrada, numPuertas,
                                combustible, cambio, descapotable);
                        vehiculos[i].setDisponible(disponible);
                        break;

                    case "Familiar":

                        tipoVehiculo = 3;

                        int numPuertasFami = Integer.parseInt(datos[6]);
                        Combustible combustibleFami = null;

                        if (datos[7].equalsIgnoreCase("GASOLINA")) {
                            combustibleFami = Enumerados.Combustible.GASOLINA;
                        } else if (datos[7].equalsIgnoreCase("DIESEL")) {
                            combustibleFami = Enumerados.Combustible.DIESEL;
                        } else if (datos[7].equalsIgnoreCase("HIBRIDO")) {
                            combustibleFami = Enumerados.Combustible.HIBRIDO;
                        } else {
                            combustibleFami = Enumerados.Combustible.ELECTRICO;
                        }

                        //int numPlazas, boolean sillaBebe
                        int numPlazas = Integer.parseInt(datos[8]);
                        boolean sillaBebe = (datos[9].equalsIgnoreCase("true")) ? true : false;

                        vehiculos[i] = new Familiar(matricula, marca, modelo, cilindrada, numPuertasFami,
                                combustibleFami, numPlazas, sillaBebe);
                        vehiculos[i].setDisponible(disponible);
                        break;
                }

            }

        }
        //ALQUILERES

        String alquileresTxt = leerArchivo("alquileres.txt");

        if (!alquileresTxt.isEmpty()) {

            String[] datosAlquileres = alquileresTxt.split("\n");

            for (int i = 0; i < datosAlquileres.length; i++) {

                String[] datos = datosAlquileres[i].split("#");

                String dni = datos[0];
                String matricula = datos[1];
                String fecha = datos[2];
                int dias = Integer.parseInt(datos[3]);

                Cliente nuevoCliente = clientes[buscarCliente(dni)];

                Vehiculo nuevoVehiculo = vehiculos[buscarVehiculo(matricula)];

                String[] datosFecha = fecha.split("[#/ :]+");

                int day = Integer.parseInt(datosFecha[0]);
                int month = Integer.parseInt(datosFecha[1]);
                int year = Integer.parseInt(datosFecha[2]);
                int hour = Integer.parseInt(datosFecha[3]);
                int minute = Integer.parseInt(datosFecha[4]);

                Calendar fechaAlquiler = new GregorianCalendar(day, month + 1, year, hour, minute);

                Alquiler nuevoAlquiler = new Alquiler(nuevoCliente, nuevoVehiculo);
                nuevoAlquiler.setFecha(fechaAlquiler);
                nuevoAlquiler.setDias(dias);

                alquileres[i] = nuevoAlquiler;

                //¿fecha y días/disponible??    
                //Vehiculo nuevoVehiculo = new Vehiculo(vehiculos[buscarVehiculo(matricula)]);
                //Alquiler nuevoAlquiler = new Alquiler(nuevoCliente, nuevoVehiculo);
                //alquileres[i] = nuevoAlquiler;
            }

        }
        System.out.println("\nDatos cargados desde los archivos correctamente.");
    }

    public static void confirmarGuardarDatos() {

        if (leerBoolean("¿Desea guardar cambios? S/N.")) {
            guardarDatos();
        }

    }

}