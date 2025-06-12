package br.com.jcv.treinadorpro.infrastructure.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.jdbc.PgArray;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringArrayListUserType implements UserType {

	// Define os tipos SQL que este UserType irá mapear.
	// Usamos Types.ARRAY para indicar que é um tipo array do JDBC.
	@Override
	public int[] sqlTypes() {
		return new int[]{Types.ARRAY};
	}

	// Retorna a classe Java que este UserType irá mapear.
	@Override
	public Class<?> returnedClass() {
		return List.class;
	}

	// Compara dois objetos para verificar se são iguais.
	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equals(x, y);
	}

	// Calcula o código hash de um objeto.
	@Override
	public int hashCode(Object x) throws HibernateException {
		return Objects.hashCode(x);
	}

	// Recupera o valor do ResultSet e o converte para o tipo Java.
	// Var1: ResultSet
	// Var2: Nomes das colunas
	// Var3: SharedSessionContractImplementor (sessão Hibernate)
	// Var4: O objeto proprietário (não usado nesta implementação simples)
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		if (rs.wasNull()) {
			return null;
		}
		// Pega o array do ResultSet
		Array sqlArray = rs.getArray(names[0]);
		if (sqlArray == null) {
			return null;
		}

		// Converte o array SQL em um array de String Java
		String[] javaArray = (String[]) sqlArray.getArray();
		return Arrays.asList(javaArray);
	}

	// Define o valor no PreparedStatement a partir do tipo Java.
	// Var1: PreparedStatement
	// Var2: Objeto a ser setado
	// Var3: Posição do parâmetro
	// Var4: SharedSessionContractImplementor (sessão Hibernate)
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.ARRAY);
		} else {
			// Converte a List<String> para um array de String
			List<String> list = (List<String>) value;
			String[] array = list.toArray(new String[0]);

			// Cria um array SQL usando a conexão atual
			Connection connection = st.getConnection();
			Array sqlArray = connection.createArrayOf("text", array); // "text" é o tipo SQL do PostgreSQL

			st.setArray(index, sqlArray);
		}
	}

	// Cria uma cópia profunda do objeto.
	// Para tipos imutáveis ou coleções, uma nova instância é geralmente retornada.
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		// Como String é imutável e List é mutável, fazemos uma nova ArrayList
		return new ArrayList<>((List<String>) value);
	}

	// Indica se o tipo é mutável.
	// List é mutável, então retorna true.
	@Override
	public boolean isMutable() {
		return true;
	}

	// Converte o objeto para uma representação serializável.
	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	// Reconstrói o objeto a partir de sua representação serializável.
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	// Retorna uma substituição para o objeto durante o merge.
	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}