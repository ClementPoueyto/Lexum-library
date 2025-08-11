package com.lexum.library.repository;

import com.lexum.library.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositorySearchImpl implements BookRepositorySearch {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Book> search(String query, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        // Build predicates once
        List<Predicate> predicates = buildPredicates(cb, root, query);

        // Sorting
        List<Order> orders = new ArrayList<>();
        Sort sort = pageable.getSort().isSorted() ? pageable.getSort() : Sort.by("id");
        for (Sort.Order sortOrder : sort) {
            Path<Object> path = root.get(sortOrder.getProperty());
            orders.add(sortOrder.isAscending() ? cb.asc(path) : cb.desc(path));
        }
        cq.orderBy(orders);

        // Where clause with predicates if present
        if (predicates.isEmpty()) {
            cq.select(root).distinct(true);
        } else {
            cq.select(root).where(cb.or(predicates.toArray(new Predicate[0]))).distinct(true);
        }

        List<Book> resultList = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = count(query);

        return new PageImpl<>(resultList, pageable, total);
    }

    private long count(String query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Book> root = countQuery.from(Book.class);

        List<Predicate> predicates = buildPredicates(cb, root, query);

        // Count distinct on book id to avoid duplicates due to joins
        countQuery.select(cb.countDistinct(root.get("id")));

        if (!predicates.isEmpty()) {
            countQuery.where(cb.or(predicates.toArray(new Predicate[0])));
        }

        return em.createQuery(countQuery).getSingleResult();
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Book> root, String query) {
        List<Predicate> predicates = new ArrayList<>();

        if (query != null && !query.isBlank()) {
            // Join authors only once
            Join<Book, String> authorsJoin = root.join("authors", JoinType.LEFT);

            Predicate titlePredicate = cb.like(cb.lower(root.get("title")), "%" + query.toLowerCase() + "%");
            Predicate authorPredicate = cb.like(cb.lower(authorsJoin), "%" + query.toLowerCase() + "%");

            predicates.add(cb.or(titlePredicate, authorPredicate));
        }

        return predicates;
    }
}
