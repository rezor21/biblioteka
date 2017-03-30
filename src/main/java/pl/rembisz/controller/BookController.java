package pl.rembisz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.rembisz.entity.Book;
import pl.rembisz.exceptions.book.BookIllegalStateException;
import pl.rembisz.exceptions.book.BookNotFoundException;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;


import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("/book")
@Api(
        name = "Home Library API",
        description = "Provides a list of methods that manage a home library",
        stage = ApiStage.RC)
public class BookController {

    @Autowired
    private pl.rembisz.repository.BookRepository bookRepository;

    @GetMapping
    @ApiMethod(description = "Get all the entries of the books from the database")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    @ApiMethod(description = "Get an entry about the book from the database")
    public Book getOneBook(@ApiPathParam(name = "id") @PathVariable Long id) {
        if (bookRepository.exists(id)) return bookRepository.findOne(id);
        throw new BookNotFoundException();
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    @ApiMethod(description = "Creating an entry about the book and save it to the database")
    public Book addBook(@RequestBody @Valid Book book) {
        return bookRepository.save(book);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiMethod(description = "Update the entry about the book with the provided ID from the database")
    public void updateBook(@ApiPathParam(name = "id") @PathVariable Long id, @RequestBody @Valid Book book) {
        if (id != book.getId()) {
            throw new BookIllegalStateException();
        }
        if(!bookRepository.exists(id)){
            throw new BookNotFoundException();
        }
        bookRepository.save(book);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiMethod(description = "Remove the entry about the book with the provided ID from the database", responsestatuscode="200")
    public void removeBook(@ApiPathParam(name = "id") @PathVariable Long id) {
        if(!bookRepository.exists(id)) throw new BookNotFoundException();
        bookRepository.delete(id);
    }
    /*@PostConstruct                    //używałem do testowania aplikacji
    public String injectData(){
        bookRepository.save(new Book ("Clean Code",730,"Robert C. Martin"));
        bookRepository.save(new Book ("Wiedźmin",1000,"Sapkowski"));
        bookRepository.save(new Book ("Hobbit",342,"Tolkien"));
        bookRepository.save(new Book ("Władcy pierścieni",534,"Tolkien"));
        return "Done";
    }*/

}