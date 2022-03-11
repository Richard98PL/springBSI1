package com.bp.springmvc.dao;
import com.bp.springmvc.controllers.RandomString;
import com.bp.springmvc.beans.User;
import com.bp.springmvc.beans.WalletEntry;
import com.bp.springmvc.controllers.SecuritySuite;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class WalletEntryDao {
    JdbcTemplate template;
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    public List<WalletEntry> getAllWalletEntries(User loggedUser){
        List<WalletEntry> walletEntries =  template.query("select id,web_address,description from `bsi`.`password` WHERE id_user = " + loggedUser.getId() + "",
                new RowMapper<WalletEntry>(){
                    @Override
                    public WalletEntry mapRow(ResultSet rs, int row)
                    throws SQLException{
                        WalletEntry walletEntry = new WalletEntry();
                        walletEntry.setId(rs.getInt(1));
                        walletEntry.setWeb_address(rs.getString(2));
                        walletEntry.setDescription(rs.getString(3));
                        return walletEntry;
                    }
                });
        return walletEntries;
    }
    
    SecuritySuite securitySuite = new SecuritySuite();
    public int insertWalletEntry(WalletEntry walletEntry, User loggedUser){
        String userPasswordHash = loggedUser.getPasswordHash();
        String walletPassword = walletEntry.getPassword();
        Key key;
        try {
            key = securitySuite.generateKey(userPasswordHash);
            String encryptedPassword = securitySuite.encrypt(walletPassword, key);
            
            String sql = "insert into `bsi`.`password` (password_hash,id_user,web_address,description,login) " +
            "values('" +
                encryptedPassword + "'," + 
                loggedUser.getId() + ",'" +
                walletEntry.getWeb_address() + "','" +
                walletEntry.getDescription() + "','" +
                walletEntry.getLogin() + "')";
            return template.update(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 

    public List<WalletEntry> getWalletEntryCredentials(final User loggedUser, int walletEntryId){
        List<WalletEntry> walletEntries =  template.query("select id,web_address,description,login, password_hash from `bsi`.`password` WHERE id = " + walletEntryId + " and id_user = " + loggedUser.getId(),
                new RowMapper<WalletEntry>(){
                    @Override
                    public WalletEntry mapRow(ResultSet rs, int row)
                    throws SQLException{
                        WalletEntry walletEntry = new WalletEntry();
                        walletEntry.setId(rs.getInt(1));
                        walletEntry.setWeb_address(rs.getString(2));
                        walletEntry.setDescription(rs.getString(3));
                        walletEntry.setLogin(rs.getString(4));
                        try {
                            Key key = securitySuite.generateKey(loggedUser.getPasswordHash());
                            String walletPassword = rs.getString(5);
                            String decryptedPassword = securitySuite.decrypt(walletPassword, key);
                            walletEntry.setPassword(decryptedPassword);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        
                        return walletEntry;
                    }
                });
        return walletEntries;
    }
}
