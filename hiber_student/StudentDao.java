package ru.alexander_kramarenko.hiber_student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class StudentDao {
    private SessionFactory sessionFactory;

    public StudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Student findById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Student product = session.get(Student.class, id);
            session.getTransaction().commit();
            return product;
        }
    }

    public List<Student> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Student> products = session.createQuery("from Student").getResultList();
            session.getTransaction().commit();
            return products;
        }
    }

    public void delete(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete from Student p where p.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void delete(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }
    }

    public Student saveOrUpdate(Student student) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(student);
            session.getTransaction().commit();
            return student;
        }
    }


    public void add(String name, int mark) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Student student = new Student(name, mark);
            session.save(student);
            session.getTransaction().commit();
        }
    }
}
