package jm.task.core.jdbc.dao;
import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;



import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    // Создание таблицы
    public void createUsersTable() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getSession();
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age TINYINT)";
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.executeUpdate(); // Выполняем создание таблицы
            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Закрываем сессию
            }
        }
    }

    // Удаление таблицы
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getSession();
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users"; // Удаляем таблицу
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.executeUpdate(); // Выполняем удаление таблицы
            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Закрываем сессию
            }
        }
    }

    // Сохранение пользователя
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getSession();
            transaction = session.beginTransaction();
            String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    // Удаление пользователя по ID
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getSession();
            transaction = session.beginTransaction();
            String sql = "DELETE FROM users WHERE id = :id"; // Удаляем пользователя по ID
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.setParameter("id", id);
            query.executeUpdate(); // Выполняем удаление
            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Закрываем сессию
            }
        }
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        Session session = null;
        List<User> users = null;

        try {
            session = Util.getSession();
            String sql = "SELECT * FROM users"; // Получаем всех пользователей
            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            users = query.getResultList(); // Получаем список пользователей
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Закрываем сессию
            }
        }

        return users; // Возвращаем список пользователей
    }

    // Очистка таблицы от всех пользователей
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getSession();
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE users"; // Очищаем таблицу
            NativeQuery<?> query = session.createNativeQuery(sql);
            query.executeUpdate(); // Выполняем очистку таблицы
            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Закрываем сессию
            }
        }
    }
}
