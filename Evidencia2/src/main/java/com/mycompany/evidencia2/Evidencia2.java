/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.evidencia2;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Evidencia2 {
    private static ArrayList<Doctor> doctores = new ArrayList<>();
    private static ArrayList<Paciente> pacientes = new ArrayList<>();
    private static ArrayList<Cita> citas = new ArrayList<>();
    

    static class Doctor{
        private int id;
        private String name;
        private String especialidad;

        public Doctor(int id, String name, String especialidad){
            this.id = id;
            this.name = name;
            this.especialidad = especialidad;
        }
    }

    static class Paciente{
        private int id;
        private String name;

        public Paciente(int id, String name){
            this.id = id;
            this.name = name;
        }
    }

    static class Cita{
        private int id;
        private Date fechaHora;
        private String motivo;
        private Doctor doctor;
        private Paciente paciente;

        public Cita(int id, Date fechaHora, String motivo, Doctor doctor, Paciente paciente){
            this.id = id;
            this.fechaHora = fechaHora;
            this.motivo = motivo;
            this.doctor = doctor;
            this.paciente = paciente;
        }
    }

    static class ControlAcceso {
        public String user = "user";
        public String pass = "pass";

        public boolean verificarCredenciales(String usuario, String contraseña) {
            return usuario.equals(user) && contraseña.equals(pass);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ControlAcceso controlAcceso = new ControlAcceso();
        System.out.println("Ingrese su nombre de usuario:");
        String usuario = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contraseña = scanner.nextLine();

        if (!controlAcceso.verificarCredenciales(usuario, contraseña)) {
            System.out.println("Credenciales incorrectas. Saliendo del programa.");
            return;
        }

        System.out.println("¡Bienvenido!");

        int opcion;
        do {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar Doctor");
            System.out.println("2. Agregar Paciente");
            System.out.println("3. Hacer cita");
            System.out.println("4. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    agregarDoctor(scanner);
                    break;
                case 2:
                    agregarPaciente(scanner);
                    break;
                case 3:
                    hacerCita(scanner, doctores, pacientes, citas);
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        } while (opcion != 4);

        scanner.close();
    }
    
     private static void agregarDoctor(Scanner scanner) {
        System.out.println("Ingrese el ID del doctor:");
        int idDoctor = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el nombre del doctor:");
        String nombreDoctor = scanner.nextLine();
        System.out.println("Ingrese la especialidad del doctor:");
        String especialidadDoctor = scanner.nextLine();
        Doctor doctor = new Doctor(idDoctor, nombreDoctor, especialidadDoctor);
        doctores.add(doctor);
        System.out.println("Doctor agregado exitosamente.");
    }

    private static void agregarPaciente(Scanner scanner) {
        System.out.println("Ingrese el ID del paciente:");
        int idPaciente = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el nombre del paciente:");
        String nombrePaciente = scanner.nextLine();
        Paciente paciente = new Paciente(idPaciente, nombrePaciente);
        pacientes.add(paciente);
        System.out.println("Paciente agregado exitosamente.");
    }
    
    private static void hacerCita(Scanner scanner, ArrayList<Doctor> doctores, ArrayList<Paciente> pacientes, ArrayList<Cita> citas) {
        
        System.out.println("Ingrese el ID de la cita:");
        int idCita = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese la fecha y hora de la cita (formato dd/MM/yyyy HH:mm):");
        String fechaHoraStr = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaHora = null;
        try {
            fechaHora = dateFormat.parse(fechaHoraStr);
        } catch (Exception e) {
            System.out.println("Error al parsear la fecha y hora.");
            return;
        }

        System.out.println("Ingrese el motivo de la cita:");
        String motivo = scanner.nextLine();

        System.out.println("Seleccione un doctor:");
        for (int i = 0; i < doctores.size(); i++) {
            System.out.println((i + 1) + ". " + doctores.get(i).name);
        }
        int indexDoctor = scanner.nextInt();
        Doctor doctor = doctores.get(indexDoctor - 1);

        System.out.println("Seleccione un paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).name);
        }
        int indexPaciente = scanner.nextInt();
        Paciente paciente = pacientes.get(indexPaciente - 1);

        Cita cita = new Cita(idCita, fechaHora, motivo, doctor, paciente);
        citas.add(cita);
        guardarObjeto(citas, "citas.txt");
        System.out.println("Cita creada exitosamente.");
    }
    
    private static void guardarObjeto(ArrayList<Cita> citas, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (Cita cita : citas) {
                writer.write(cita.id + "," + cita.fechaHora + "," + cita.motivo + "," + cita.doctor.name + "," + cita.paciente.name);
                writer.newLine();
            }
            writer.close();
            System.out.println("Citas guardadas exitosamente en el archivo " + filename);
        } catch (IOException e) {
            System.out.println("Error al guardar las citas en el archivo " + filename + ": " + e.getMessage());
        }
    }
    
}