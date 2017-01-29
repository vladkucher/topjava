package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    //private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final RowMapper<User> ROW_MAPPER =(rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRegistered(rs.getDate("registered"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setCaloriesPerDay(rs.getInt("calories_per_day"));
        Collection<Role> c = Arrays.asList(Role.valueOf(rs.getString("role")));
        user.setRoles(c);
        return user;
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;



    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            deleteRoles(user);
            insertRoles(user);
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(getListWithRoles(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE email=?", ROW_MAPPER, email);
        return DataAccessUtils.singleResult(getListWithRoles(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email", ROW_MAPPER);
        return getListWithRoles(users);
    }

    private List<User> getListWithRoles(List<User> users){
        Map<Integer,Collection<Role>> map =  new ConcurrentHashMap<>();
        for(User user: users){
            if(map.containsKey(user.getId())){
                Collection<Role> roles = map.get(user.getId());
                roles.addAll(user.getRoles());
                map.put(user.getId(),roles);
            }else{
                map.put(user.getId(),user.getRoles());
            }
        }
        List<User> userList = new ArrayList<>();
        for (User user: users){
            if(map.containsKey(user.getId())){
                user.setRoles(map.get(user.getId()));
                userList.add(user);
                map.remove(user.getId());
            }
        }
        return userList;
    }

    private void insertRoles(User user){
        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();
        jdbcTemplate.batchUpdate("INSERT INTO user_roles VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1,user.getId());
                ps.setString(2,iterator.next().name());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    private void deleteRoles(User user){
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
    }
}
