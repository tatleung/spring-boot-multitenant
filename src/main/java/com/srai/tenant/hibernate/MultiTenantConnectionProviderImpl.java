package com.srai.tenant.hibernate;

import com.srai.tenant.TenantContext;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

  private static final long serialVersionUID = 6246085840652870138L;

  @Autowired
  private DataSource dataSource;

  @Override
  public Connection getAnyConnection() throws SQLException {
    return dataSource.getConnection();
  }

  @Override
  public void releaseAnyConnection(Connection connection) throws SQLException {
    connection.close();
  }

  @Override
  public Connection getConnection(String tenantIdentifier) throws SQLException {
    System.out.println("Number of connections before getConnection(): " + ((org.apache.tomcat.jdbc.pool.DataSource)dataSource).getActive());
    final Connection connection = getAnyConnection();
    System.out.println("Number of connections after getConnection(): " + ((org.apache.tomcat.jdbc.pool.DataSource)dataSource).getActive());

    try {
      connection.createStatement().execute( "USE " + tenantIdentifier );
    }
    catch ( SQLException e ) {
      throw new HibernateException(
          "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]",
          e
          );
    }
    return connection;
  }

  @Override
  public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
    try {
      connection.createStatement().execute( "USE " + TenantContext.DEFAULT_TENANT );
    }
    catch ( SQLException e ) {
      throw new HibernateException(
          "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]",
          e
          );
    }
    System.out.println("Number of connections before close(): " + ((org.apache.tomcat.jdbc.pool.DataSource)dataSource).getActive());
    connection.close();
    System.out.println("Number of connections after close(): " + ((org.apache.tomcat.jdbc.pool.DataSource)dataSource).getActive());

  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean isUnwrappableAs(Class unwrapType) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> unwrapType) {
    return null;
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return true;
  }

}