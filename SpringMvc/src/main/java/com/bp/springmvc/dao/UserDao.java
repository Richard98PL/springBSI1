package com.bp.springmvc.dao;
import com.bp.springmvc.controllers.RandomString;
import com.bp.springmvc.beans.User;
import com.bp.springmvc.controllers.SecuritySuite;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {
    JdbcTemplate template;
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    SecuritySuite securitySuite = new SecuritySuite();
    public int update(User u){
        RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        String salt = gen.nextString();
        
        String passwordAndHashAndPepper = u.getPassword() + salt + SecuritySuite.PEPPER;
        
        String hashOutOfIt = this.securitySuite.calculateSHA512(passwordAndHashAndPepper);
        String hmac = this.securitySuite.calculateHMAC(u.getPassword(), SecuritySuite.PEPPER);
        String passwordToDatabase = u.getIsPasswordKeptAsHash() ? hashOutOfIt : hmac;
        
        String sql = "update `bsi`.`user` SET salt = '" + salt +
                "', password_hash = '" + passwordToDatabase +
                "', isPasswordKeptAsHash = "  +
                u.getIsPasswordKeptAsHash() +
                " WHERE login = '" + u.getLogin() + "'";
        return template.update(sql);
    }
    
    public int save(User p) {
        RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        String salt = gen.nextString();
        String passwordAndHashAndPepper = p.getPassword() + salt + SecuritySuite.PEPPER;
       
        String hashOutOfIt = this.securitySuite.calculateSHA512(passwordAndHashAndPepper);
        String hmac = this.securitySuite.calculateHMAC(p.getPassword(), SecuritySuite.PEPPER);
        String passwordToDatabase = p.getIsPasswordKeptAsHash() ? hashOutOfIt : hmac;
        
        String sql = "insert into `bsi`.`user` (login,password_hash,salt,isPasswordKeptAsHash) " +
            "values('" +
                p.getLogin() + "','" + 
                passwordToDatabase + "','" +
                salt + "'," +
                p.getIsPasswordKeptAsHash() + ")";
        return template.update(sql);
    }
    
    public String getUserPasswordHash(User u){
        List<User> users =  template.query("select password_hash from `bsi`.`user` WHERE login = '" + u.getLogin() + "'",
                new RowMapper<User>(){
                    @Override
                    public User mapRow(ResultSet rs, int row)
                    throws SQLException{
                        User u = new User();
                        u.setPassword(rs.getString(1));
                        return u;
                    }
                });
        return users.size() == 1 ? users.get(0).getPassword() : null;
    }
    
    public String getUserSalt(User u){
        List<User> users =  template.query("select salt from `bsi`.`user` WHERE login = '" + u.getLogin() + "'",
                new RowMapper<User>(){
                    @Override
                    public User mapRow(ResultSet rs, int row)
                    throws SQLException{
                        User u = new User();
                        u.setSalt(rs.getString(1));
                        return u;
                    }
                });
        return users.size() == 1 ? users.get(0).getSalt() : null;
    }
    
    public Integer getUserId(User u){
        List<User> users =  template.query("select id from `bsi`.`user` WHERE login = '" + u.getLogin() + "'",
                new RowMapper<User>(){
                    @Override
                    public User mapRow(ResultSet rs, int row)
                    throws SQLException{
                        User u = new User();
                        u.setId(rs.getInt(1));
                        return u;
                    }
                });
        return users.size() == 1 ? users.get(0).getId() : null;
    }
    
    public Boolean isUserPasswordKeptAsHash(User u){
        List<User> users =  template.query("select isPasswordKeptAsHash from `bsi`.`user` WHERE login = '" + u.getLogin() + "'",
                new RowMapper<User>(){
                    @Override
                    public User mapRow(ResultSet rs, int row)
                    throws SQLException{
                        User u = new User();
                        u.setIsPasswordKeptAsHash(rs.getBoolean(1));
                        return u;
                    }
                });
        return users.size() == 1 ? users.get(0).getIsPasswordKeptAsHash() : null;
    }
    
    public LoginAttemptWrapper loginAttempt(User u){
        LoginAttemptWrapper wrapper = new LoginAttemptWrapper();
        Boolean isPasswordKeptAsHash = isUserPasswordKeptAsHash(u);
        if(isPasswordKeptAsHash == null){
            return wrapper;
        }
        String password = u.getPassword();
        if(password == null){
            return wrapper;
        }
        String salt = getUserSalt(u);
        String passwordAndHashAndPepper = password + salt + SecuritySuite.PEPPER;
        String hashOutOfIt = this.securitySuite.calculateSHA512(passwordAndHashAndPepper);
        
        String hmac = this.securitySuite.calculateHMAC(password, SecuritySuite.PEPPER);
        String userActualPasswordHash = getUserPasswordHash(u);
        String passwordToDatabase = isPasswordKeptAsHash ? hashOutOfIt : hmac;
        if(Objects.equals(passwordToDatabase, userActualPasswordHash)){
            wrapper.attempt = true;
            wrapper.u.setLogin(u.getLogin());
            wrapper.u.setPasswordHash(userActualPasswordHash);
            wrapper.u.setIsPasswordKeptAsHash(isPasswordKeptAsHash);
            wrapper.u.setSalt(salt);
            wrapper.u.setId(getUserId(u));
            return wrapper;
        }else{
            return wrapper;
        }
    }
    
    public class LoginAttemptWrapper{
        public User u = new User();
        public Boolean attempt = false;
    }
}