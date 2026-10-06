package repository.impl;

import model.Course;
import repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class SimulatedFileRepository implements CourseRepository {
    private List<Course> cursos = new ArrayList<>();

    private boolean archivoCreado = false;

    @Override
    public Course create(Course c) {
        // Si el archivo no existe todavía, lo "creamos"
        if (!archivoCreado) {
            archivoCreado = true;
            System.out.println("[Archivo] Creando archivo cursos.txt...");
        }

        // El nuevo id es el mayor que haya + 1
        int nuevoId = 1;
        for (Course otro : cursos) {
            if (otro.getId() >= nuevoId) {
                nuevoId = otro.getId() + 1;
            }
        }

        c.setId(nuevoId);
        cursos.add(c);
        System.out.println("[Archivo] Línea añadida a cursos.txt: "
                + c.getId() + ";" + ";" + c.getHours() + ";" + c.getModality());
        return c;
    }

    @Override
    public Course findById(int id) {
        System.out.println("[Archivo] Leyendo cursos.txt para buscar id " + id);
        for (Course c : cursos) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        System.out.println("[Archivo] Leyendo todas las líneas de cursos.txt");
        return cursos;
    }

    @Override
    public boolean update(Course c) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == c.getId()) {
                cursos.set(i, c);
                System.out.println("[Archivo] Línea del id " + c.getId() + " modificada en cursos.txt");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == id) {
                cursos.remove(i);
                System.out.println("[Archivo] Línea del id " + id + " eliminada de cursos.txt");
                return true;
            }
        }
        return false;
    }

    @Override
    public int count() {
        System.out.println("[Archivo] Contando líneas de cursos.txt");
        return cursos.size();
    }
}
