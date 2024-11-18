import java.util.ArrayList;
import java.util.Scanner;

class CompraVentaMotos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Moto> motos = new ArrayList<>();
        ArrayList<Moto> ventas = new ArrayList<>();
        int opcion;

        do {
            System.out.println("\n--- Menú Compra-Venta de Motos ---");
            System.out.println("1. Nuevo ingreso");
            System.out.println("2. Vender");
            System.out.println("3. Información de ventas");
            System.out.println("4. Información de motos disponibles");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Nuevo Ingreso ---");
                    System.out.print("Placa: ");
                    String placa = scanner.nextLine();
                    System.out.print("Marca: ");
                    String marca = scanner.nextLine();
                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine();
                    System.out.print("Nombre del dueño: ");
                    String nombreDueño = scanner.nextLine();
                    System.out.print("Precio de compra: ");
                    double precioCompra = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Fecha de ingreso: ");
                    String fechaIngreso = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();

                    motos.add(new Moto(placa, marca, modelo, nombreDueño, precioCompra, fechaIngreso, telefono));
                    System.out.println("Moto ingresada exitosamente");
                    break;

                case 2:
                    if (motos.isEmpty()) {
                        System.out.println("No hay motos disponibles para vender");
                        break;
                    }

                    System.out.println("\n--- Vender Moto ---");
                    System.out.println("Motos disponibles:");
                    for (int i = 0; i < motos.size(); i++) {
                        if (!motos.get(i).vendida) {
                            System.out.println((i + 1) + ". " + motos.get(i).modelo + " (Placa: " + motos.get(i).placa + ")");
                        }
                    }

                    System.out.print("Seleccione una moto (número): ");
                    int seleccion = scanner.nextInt();
                    scanner.nextLine();

                    if (seleccion < 1 || seleccion > motos.size() || motos.get(seleccion - 1).vendida) {
                        System.out.println("Opción inválida");
                        break;
                    }

                    Moto motoSeleccionada = motos.get(seleccion - 1);
                    System.out.print("Nombre del nuevo dueño: ");
                    String nuevoDueño = scanner.nextLine();
                    System.out.print("Teléfono del nuevo dueño: ");
                    String nuevoTelefono = scanner.nextLine();
                    System.out.print("Fecha de salida: ");
                    String fechaSalida = scanner.nextLine();
                    System.out.print("Precio de venta: ");
                    double precioVenta = scanner.nextDouble();
                    scanner.nextLine();

                    motoSeleccionada.vender(nuevoDueño, nuevoTelefono, fechaSalida, precioVenta);
                    ventas.add(motoSeleccionada);
                    System.out.println("Moto vendida exitosamente");
                    break;

                case 3:
                    if (ventas.isEmpty()) {
                        System.out.println("No hay ventas registradas");
                        break;
                    }

                    System.out.println("\n--- Información de Ventas ---");
                    double gananciasTotales = 0;
                    for (Moto venta : ventas) {
                        double ganancia = venta.calcularGanancia();
                        gananciasTotales += ganancia;
                        System.out.println("Modelo: " + venta.modelo + ", Placa: " + venta.placa);
                        System.out.println("Dueño anterior: " + venta.nombreDueño + ", Nuevo dueño: " + venta.nuevoDueño);
                        System.out.println("Precio de compra: $" + venta.precioCompra + ", Precio de venta: $" + venta.precioVenta + ", Ganancia: $" + ganancia);
                        System.out.println("Fecha de salida: " + venta.fechaSalida);
                        System.out.println();
                    }
                    System.out.println("Ganancias totales: $" + gananciasTotales);
                    break;

                case 4:
                    if (motos.isEmpty()) {
                        System.out.println("No hay motos disponibles");
                        break;
                    }

                    System.out.println("\n--- Información de Motos Disponibles ---");
                    for (Moto moto : motos) {
                        System.out.println("Modelo: " + moto.modelo + ", Placa: " + moto.placa);
                        System.out.println("Marca: " + moto.marca + ", Dueño: " + moto.nombreDueño);
                        System.out.println("Precio de compra: $" + moto.precioCompra + ", Fecha de ingreso: " + moto.fechaIngreso);
                        System.out.println("Teléfono: " + moto.telefono);
                        System.out.println("Vendida: " + (moto.vendida ? "Sí" : "No"));
                        System.out.println();
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente");
            }
        } while (opcion != 5);

        scanner.close();
    }
}



abstract class Vehiculo {
    String placa;
    String marca;
    String modelo;
    double precioCompra;
    String fechaIngreso;

    public Vehiculo(String placa, String marca, String modelo, double precioCompra, String fechaIngreso) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.precioCompra = precioCompra;
        this.fechaIngreso = fechaIngreso;
    }
}

interface OperacionesVenta {
    void vender(String nuevoDueño, String nuevoTelefono, String fechaSalida, double precioVenta);
    double calcularGanancia();
}

class Moto extends Vehiculo implements OperacionesVenta {
    String nombreDueño;
    String telefono;
    boolean vendida;
    String nuevoDueño;
    String nuevoTelefono;
    String fechaSalida;
    double precioVenta;

    public Moto(String placa, String marca, String modelo, String nombreDueño, double precioCompra, String fechaIngreso, String telefono) {
        super(placa, marca, modelo, precioCompra, fechaIngreso);
        this.nombreDueño = nombreDueño;
        this.telefono = telefono;
        this.vendida = false;
    }

    @Override
    public void vender(String nuevoDueño, String nuevoTelefono, String fechaSalida, double precioVenta) {
        this.nuevoDueño = nuevoDueño;
        this.nuevoTelefono = nuevoTelefono;
        this.fechaSalida = fechaSalida;
        this.precioVenta = precioVenta;
        this.vendida = true;
    }

    @Override
    public double calcularGanancia() {
        return precioVenta - precioCompra;
    }
}


