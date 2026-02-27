package com.alicedeveloppementweb.tp_automatisation_tests.service;

import com.alicedeveloppementweb.tp_automatisation_tests.model.Book;
import com.alicedeveloppementweb.tp_automatisation_tests.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @Test
    void should_create_book() {
        // ARRANGE
        Book book = new Book(null, "1984", "Orwell", 1948);

        // ACT
        when(repository.save(book)).thenReturn(
                new Book(1L, "1984", "Orwell", 1948)
        );

        Book saved = service.create(book);

        // ASSERT
        assertNotNull(saved.getId());
        assertEquals("1984", saved.getTitre());
        verify(repository).save(book);
    }

    @Test
    void should_get_all_books() {
        // ARRANGE
        List<Book> books = List.of(
                new Book(1L, "1984", "Orwell", 1948),
                new Book(2L, "Dune", "Herbert", 1965)
        );
        when(repository.findAll()).thenReturn(books);

        // ACT
        List<Book> result = service.getAll();

        // ASSERT
        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void should_get_book_by_id() {
        // ARRANGE
        Book book = new Book(1L, "1984", "Orwell", 1948);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        // ACT
        Book result = service.getById(1L);

        // ASSERT
        assertNotNull(result);
        assertEquals("1984", result.getTitre());
        verify(repository).findById(1L);
    }

    @Test
    void should_update_book() {
        // ARRANGE
        Book book = new Book(1L, "1984", "Orwell", 1948);
        when(repository.save(book)).thenReturn(book);

        // ACT
        Book result = service.update(1L, book);

        // ASSERT
        assertEquals(1L, result.getId());
        verify(repository).save(book);
    }

    @Test
    void should_delete_book() {
        // ACT
        service.delete(1L);

        // ASSERT
        verify(repository).deleteById(1L);
    }
}
