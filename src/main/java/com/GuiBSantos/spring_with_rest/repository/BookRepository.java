package com.GuiBSantos.spring_with_rest.repository;

import com.GuiBSantos.spring_with_rest.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}