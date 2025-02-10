package org.example;

import org.example.entities.Game;
import org.example.entities.Genre;
import org.example.entities.Order;
import org.example.entities.User;
import org.example.util.HibernateUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    //Email tocar20181@andinews.com
    //Password = Qwerty1-!
    //postgresql://neondb_owner:npg_1gEcsJI7SfLD@ep-damp-dust-a5pp65ci-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require

    public static void main(String[] args) {
        var session = HibernateUtil.getSession();
/*
        try {
            session.beginTransaction();

            var order = new Order();
            order.setTotalPrice(new BigDecimal("120"));
            User u = new User();
            u.setId(1);
            order.setUser(u);
            //session.save(genre);
            session.persist(order);

            session.getTransaction().commit();
        } catch (Exception ex ){
            System.out.println("Щось пішло не так! " + ex.getMessage());
        }

*/
    }
}