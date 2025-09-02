package br.com.jcv.treinadorpro.infrastructure.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class CustomIntegerArrayType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class returnedClass() {
        return Integer[].class;
    }

    @Override
    public boolean equals(Object x, Object y) {
        return Objects.deepEquals(x, y);
    }

    @Override
    public int hashCode(Object x) {
        return Arrays.hashCode((Integer[]) x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,
                              SharedSessionContractImplementor session, Object owner) throws SQLException {
        Array array = rs.getArray(names[0]);
        if (array == null) {
            return null;
        }
        return (Integer[]) array.getArray();
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.ARRAY);
        } else {
            Connection connection = st.getConnection();
            Integer[] casted = (Integer[]) value;
            Array array = connection.createArrayOf("integer", casted);
            st.setArray(index, array);
        }
    }

    @Override
    public Object deepCopy(Object value) {
        return value == null ? null : ((Integer[]) value).clone();
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) {
        return deepCopy(original);
    }
}
