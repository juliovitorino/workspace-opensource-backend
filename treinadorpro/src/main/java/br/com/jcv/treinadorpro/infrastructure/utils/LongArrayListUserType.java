package br.com.jcv.treinadorpro.infrastructure.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LongArrayListUserType implements UserType {

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.ARRAY};
	}

	@Override
	public Class returnedClass() {
		return List.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equals(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
		Array sqlArray = rs.getArray(names[0]);
		if (sqlArray == null) {
			return null;
		}
		Long[] javaArray = (Long[]) sqlArray.getArray();
		return Arrays.asList(javaArray);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
		if (value == null) {
			st.setNull(index, Types.ARRAY);
		} else {
			List<Long> listValue = (List<Long>) value;
			Long[] javaArray = listValue.toArray(new Long[0]);
			Array sqlArray = st.getConnection().createArrayOf("bigint", javaArray);
			st.setArray(index, sqlArray);
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		List<Long> originalList = (List<Long>) value;
		return originalList.stream().collect(Collectors.toList());
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return deepCopy(original);
	}
}