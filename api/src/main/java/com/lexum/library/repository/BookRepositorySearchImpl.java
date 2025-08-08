package com.lexum.library.repository;

import com.lexum.library.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositorySearchImpl implements BookRepositorySearch {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Book> search(String title, String author, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        List<Predicate> predicates = buildPredicates(cb, root, title, author);
        cq.select(root).where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true);

        List<Book> resultList = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = count(title, author);
        return new PageImpl<>(resultList, pageable, total);
    }

    private long count(String title, String author) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Book> root = countQuery.from(Book.class);

        List<Predicate> predicates = buildPredicates(cb, root, title, author);
        countQuery.select(cb.count(root)).where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(countQuery).getSingleResult();
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Book> root, String title, String author) {
        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (author != null && !author.isBlank()) {
            root.join("authors", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(root.join("authors", JoinType.LEFT)), "%" + author.toLowerCase() + "%"));
        }

        return predicates;
    }
}
