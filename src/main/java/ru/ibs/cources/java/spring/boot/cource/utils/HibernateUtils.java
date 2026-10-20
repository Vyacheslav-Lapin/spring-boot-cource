package ru.ibs.cources.java.spring.boot.cource.utils;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;

@UtilityClass
public class HibernateUtils {

  @SuppressWarnings("unchecked")
  public <T> Class<T> persistentClass(T object) {
    return (Class<T>) (object instanceof HibernateProxy proxy ?
                           proxy.getHibernateLazyInitializer().getPersistentClass()
                                                                  : object.getClass());
  }
}
