package com.library.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAuthorById() throws Exception {
    }

    @Test
    void postAuthor() {
    }

    @Test
    void deleteAuthor() {
    }

    @Test
    void getAllAuthors() {
    }

    @Test
    void editAuthor() {
    }

    @Test
    void getAuthorByNationality1() {
    }

    @Test
    void getAuthorsByNationalityAndSurname() {
    }
}