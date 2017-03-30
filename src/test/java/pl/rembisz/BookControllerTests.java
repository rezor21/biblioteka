package pl.rembisz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.rembisz.controller.BookController;
import pl.rembisz.repository.BookRepository;
import pl.rembisz.entity.Book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.rembisz.TestJsontoString.asJsonString;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTests {
    @Autowired
    private MockMvc mvc;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .build();
    }


    @Test
    public void testGetAllBooks() throws Exception {
        when(bookRepository.findAll()).thenReturn(loadBook());
        this.mvc.perform(get("/book"))
                .andExpect(
                status().isOk()).
                andExpect(content().
                        contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository);
    }



    @Test
    public void testGetBookExpectNotFound() throws Exception {
        when(bookRepository.exists(1L)).thenReturn(false);
        mvc.perform(get("/book/{id}", 1L)).
                andExpect(status().isNotFound());
        verify(bookRepository, times(1)).exists(1L);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void testGetOneBook() throws Exception {
        when(bookRepository.findOne(1L)).thenReturn(loadBook().get(0));
        when(bookRepository.exists(1L)).thenReturn(true);
        mvc.perform(get("/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.authorName", is("qwe")));
        verify(bookRepository, times(1)).findOne(1L);
        verify(bookRepository, times(1)).exists(1L);
        verifyNoMoreInteractions(bookRepository);
    }


    @Test
    public void testAddOneBookExpectIsCreated() throws Exception {
        Book book = loadBook().get(0);
        when(bookRepository.save(book)).thenReturn(book);
        mvc.perform(post("/book/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(book))).andExpect(status().isCreated());
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void testUpdateOneBook() throws Exception {
        when(bookRepository.save(loadBook().get(0))).thenReturn(loadBook().get(0));
        when(bookRepository.exists(loadBook().get(0).getId())).thenReturn(true);
        mvc.perform(
                put("/book/{id}", loadBook().get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loadBook().get(0))))
                .andExpect(status().isOk());
        verify(bookRepository, times(1)).save(loadBook().get(0));
        verify(bookRepository, times(1)).exists(loadBook().get(0).getId());
        verifyNoMoreInteractions(bookRepository);

    }

    @Test
    public void testUpdateOneBookExpectNotFound() throws Exception {
        when(bookRepository.exists(loadBook().get(0).getId())).thenReturn(false);
        mvc.perform(put("/book/{id}", loadBook().get(0).getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(loadBook().get(0)))).andExpect(status().isNotFound());
        verify(bookRepository, times(1)).exists(loadBook().get(0).getId());
        verifyNoMoreInteractions(bookRepository);
    }


    private ArrayList<Book> loadBook() {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        Book book = new Book();
        book.setTitle("abc");
        book.setId(1L);
        book.setPages(265);
        book.setAuthorName("qwe");


        bookArrayList.add(book);
        book = new Book();
        book.setTitle("cde");
        book.setId(1L);
        book.setPages(265);
        book.setAuthorName("rty");
        bookArrayList.add(book);
        return bookArrayList;

    }
}
