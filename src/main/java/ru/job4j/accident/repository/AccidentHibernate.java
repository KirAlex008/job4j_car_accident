package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import java.util.List;

@Repository
public class AccidentHibernate implements Store {

    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }


    @Override
    public Accident save(Accident accident) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(accident);
        session.getTransaction().commit();
        session.close();
        return accident;
    }

    @Override
    public List<Accident> getAll() {
        try (Session session = this.sf.openSession()) {
            return session.createQuery("select distinct a from Accident a " + "join fetch a.rules", Accident.class).list();
        }
    }

    @Override
    public void update(Accident accident) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(accident);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public Accident findAccidentById(Integer id) {
        try (Session session = this.sf.openSession()) {
            return session
                    .createQuery("select distinct a from Accident a "
                            + "join fetch a.rules "
                            + "join fetch a.type "
                            + "where a.id = :id", Accident.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    @Override
    public void delete(Accident accident) {

    }

    @Override
    public AccidentType findTypeById(Integer id) {
        try (Session session = sf.openSession()) {
            return session.get(AccidentType.class, id);
        }
    }

    @Override
    public Rule findRuleById(String idStr) {
        int id = Integer.parseInt(idStr);
        try (Session session = sf.openSession()) {
            return session.get(Rule.class, id);
        }
    }

    @Override
    public List<Rule> getAllRule() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule r", Rule.class)
                    .list();
        }
    }

    @Override
    public List<AccidentType> getAllAccidentType() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType t", AccidentType.class)
                    .list();
        }
    }
}
