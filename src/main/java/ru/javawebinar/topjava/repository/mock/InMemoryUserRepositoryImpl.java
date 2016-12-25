package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    /*{
        save(new User(1,"vlad","q@g","12345",Role.ROLE_USER));
    }*/

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if(user.isNew())
            user.setId(counter.incrementAndGet());
        repository.put(user.getId(),user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        User user = repository.get(id);
        return user!=null?user:null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return repository.values().stream()
                .sorted((u1,u2)->u1.getName().compareTo(u2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst()
                .orElse(null);
    }
}
