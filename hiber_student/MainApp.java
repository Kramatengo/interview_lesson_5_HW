package ru.alexander_kramarenko.hiber_student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MainApp {

    private static SessionFactory factory;

    public static void init() {
        PrepareDataApp.forcePrepareData();
        factory = new Configuration()
                .configure("hibernate_map.cfg.xml")
                .buildSessionFactory();
    }

    public static void main(String[] args) {

        init();

        Session session = null;
        try {

            StudentDao studentDao = new StudentDao(factory);

            // Работа метода add()
            for (int i = 0; i < 900; i++) {
                studentDao.add("Jimmy" + i, i);
            }

            session = factory.getCurrentSession();

            // Работа минуя StudentDAO - начало
            session.beginTransaction();

            // Работа в ResultList() и хождение по таблице
            List<Student> students = session.createQuery("from Student").getResultList();
            System.out.println("Session students = Id : " + students.get(0).getId() + " Name : " + students.get(0).getName() + " Mark : " + students.get(0).getMark() + "\n");
            System.out.println("Session students = Id : " + students.get(49).getId() + " Name : " + students.get(49).getName() + " Mark : " + students.get(49).getMark() + "\n");
            System.out.println("Session students = Id : " + students.get(99).getId() + " Name : " + students.get(99).getName() + " Mark : " + students.get(99).getMark() + "\n");


            Student students1 = session.createQuery("select s from Student s where s.id = 3", Student.class).getSingleResult();
            System.out.println("Session students1 = Id : " + students1.getId() + " Name : " + students1.getName() + " Mark " + students1.getMark() + "\n");

            Student student = session.find(Student.class, 20L);
            System.out.println("Student student = Id : " + student.getId() + " Name : " + student.getName() + " Mark " + student.getMark());

            session.getTransaction().commit();
            // Работа минуя StudentDAO - конец

            // Поиск по Id
            Student studentViaDao = studentDao.findById(827L);
            System.out.println("studentViaDao findById= Id : " + studentViaDao.getId() + " Name : " + studentViaDao.getName() + " Mark : " + studentViaDao.getMark());

            // Изменение существующей записи
            studentViaDao.setName(studentViaDao.getName() + "_AddedName");
            studentDao.saveOrUpdate(studentViaDao);
            System.out.println("studentViaDao SaveOrUpdate = Id : " + studentViaDao.getId() + " Name : " + studentViaDao.getName() + " Mark : " + studentViaDao.getMark());

            // Добавляем новую запись
            studentDao.add("David", 10);

            // Выводим список всех студентов через StudentDAO
            List<Student> studentList = studentDao.findAll();
            for (Student l : studentList) {
                System.out.println(l);
            }

            // Удаляем запись по Id
            studentDao.delete(3L);

            // Удаляем запись по объекту (Id =20)
            studentDao.delete(student);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }
}
