package org.example.util;

import org.example.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    //Повертає фабрику для підключення БД
    private static SessionFactory sessionFactory;

    //Це метод який визивається автоматом, без потреби створення класу
    static {
        try {
            var config = new Configuration()
                    .configure(); //Шукаємо стандартну конфігурацію, тобто файл hibernate.cfg.xml
            config.addAnnotatedClass(Genre.class);
            config.addAnnotatedClass(Game.class);
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Order.class);
            config.addAnnotatedClass(Payment.class);

            config.addAnnotatedClass(Order_Item.class);




            sessionFactory = config.buildSessionFactory();
            System.out.println("------Ми підключилися до БД------");
        } catch (Exception ex) {
            System.out.println("Помилка при підключенні до БД!" + ex.getMessage());
        }
    }

    public static  SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public  static Session getSession(){
        return sessionFactory.openSession();
    }
    public static void shutdown(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }
}
