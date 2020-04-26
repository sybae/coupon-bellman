package com.example.coupon.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Random;

public class CodeGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        Connection connection = session.connection();
        String newCode;
        Boolean isUnique = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs;
            do {
                newCode = generateCode();
                rs = statement.executeQuery(String.format("select count(code) as count from coupon where code = '%s'", newCode));
                if (rs.next()) {
                    isUnique = rs.getInt("count") == 0 ? true : false;
                    if (isUnique) {
                        return newCode;
                    }
                }
            } while (isUnique);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateCode() {
        //TODO 성능 확인해서 구간별 난수로 변경할 수 있음
        Random rand = new Random(System.currentTimeMillis());
        return Optional.of(String.valueOf(Math.abs(rand.nextLong())).substring(0, 11))
                .map(s -> s.substring(0,4).concat("-")
                        .concat(s.substring(4,8)).concat("-")
                        .concat(s.substring(8,11))).get();
    }
}
