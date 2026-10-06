package repository.impl;

import model.Course;
import repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class SimulatedMySQLCourseRepository implements CourseRepository {


    private List<Course> cursos = new ArrayList<>();

    // Para dar un id distinto a cada curso
    private int siguienteId = 1;

    @Override
    public Course create(Course c) {
        c.setId(siguienteId);
        siguienteId++;
        cursos.add(c);          // añadir a la lista
        return c;
    }

    @Override
    public Course findById(int id) {
        for (Course c : cursos) {
            if (c.getId() == id) {
                return c;       // lo hemos encontrado
            }
        }
        return null;            // no existe
    }

    @Override
    public List<Course> findAll() {
        return cursos;          // devolver toda la lista
    }

    @Override
    public boolean update(Course c) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == c.getId()) {
                cursos.set(i, c);   // reemplazar el curso viejo por el nuevo
                return true;
            }
        }
        return false;           // no existe
    }

    @Override
    public boolean deleteById(int id) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == id) {
                cursos.remove(i);   // borrar de la lista
                return true;
            }
        }
        return false;           // no existe
    }

    @Override
    public int count() {
        return cursos.size();   // cuántos cursos hay
    }
}
