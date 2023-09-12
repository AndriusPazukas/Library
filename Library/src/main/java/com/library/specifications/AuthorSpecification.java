package com.library.specifications;

import com.library.entity.Author;
import com.library.metamodel.Author_;
import org.springframework.data.jpa.domain.Specification;


public final class AuthorSpecification {


    public static Specification<Author> getAuthorByNatAndSurname(String nationality, String surname){
        return(root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get(Author_.NATIONALTY),nationality),
                    criteriaBuilder.equal(root.get(Author_.SURNAME), surname)
            );
        };
    }
}
