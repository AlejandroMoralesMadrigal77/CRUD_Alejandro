package repository.impl;

import model.Course;
import repository.CourseRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCourseRepository implements CourseRepository {
    public FileCourseRepository() {
        File archivo = new File("cursos.txt");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }
        }
    }

    // ---------- Métodos auxiliares ----------

    // Lee el archivo y devuelve la lista de cursos
    private List<Course> leerCursos() {
        List<Course> cursos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("cursos.txt"))) {
            String linea = br.readLine();
            while (linea != null) {
                if (!linea.isBlank()) {
                    String[] partes = linea.split(";");
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    int horas = Integer.parseInt(partes[2]);
                    String modalidad = partes[3];
                    cursos.add(new Course(id, nombre, horas, modalidad));
                }
                linea = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return cursos;
    }

    // Escribe toda la lista en el archivo (reemplaza lo que había)
    private void guardarCursos(List<Course> cursos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("cursos.txt"))) {
            for (Course c : cursos) {
                pw.println(c.getId() + ";"  + ";" + c.getHours() + ";" + c.getModality());
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    // ---------- Métodos del repositorio ----------

    @Override
    public Course create(Course c) {
        List<Course> cursos = leerCursos();

        // El nuevo id es el mayor que haya + 1
        int nuevoId = 1;
        for (Course otro : cursos) {
            if (otro.getId() >= nuevoId) {
                nuevoId = otro.getId() + 1;
            }
        }

        c.setId(nuevoId);
        cursos.add(c);
        guardarCursos(cursos);
        return c;
    }

    @Override
    public Course findById(int id) {
        List<Course> cursos = leerCursos();
        for (Course c : cursos) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        return leerCursos();
    }

    @Override
    public boolean update(Course c) {
        List<Course> cursos = leerCursos();
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == c.getId()) {
                cursos.set(i, c);
                guardarCursos(cursos);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        List<Course> cursos = leerCursos();
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == id) {
                cursos.remove(i);
                guardarCursos(cursos);
                return true;
            }
        }
        return false;
    }

    @Override
    public int count() {
        return leerCursos().size();
    }
}