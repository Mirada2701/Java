package org.example;

import org.example.entities.Genre;
import org.example.util.HibernateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    //Email tocar20181@andinews.com
    //Password = Qwerty1-!
    //postgresql://neondb_owner:npg_1gEcsJI7SfLD@ep-damp-dust-a5pp65ci-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require

    public static void main(String[] args) {
        var session = HibernateUtil.getSession();
        try {
            session.beginTransaction();

            var genre = new Genre();
            genre.setName("Стрілялки");
            //session.save(genre);
            session.persist(genre);

            session.getTransaction().commit();
        } catch (Exception ex ){
            System.out.println("Щось пішло не так! " + ex.getMessage());
        }
    }
}