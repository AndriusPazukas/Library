package com.library.metamodel;


import com.library.entity.Author;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Author.class)
public abstract class  Author_ {
    public static volatile SingularAttribute<Author, String> name;
    public static volatile SingularAttribute<Author, String> surname;
    public static volatile SingularAttribute<Author, String> dateOfBirth;
    public static volatile SingularAttribute<Author, String> nationality;
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String DATEOFBIRTH = "dateofbirth";
    public static final String NATIONALTY = "nationality";

}
