package pl.rembisz.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.StringJoiner;
import java.util.UUID;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name = "book")
@ApiObject
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @ApiObjectField(description = "The book's ID")
    private Long id;

    @Column(name = "title", nullable = false)
    @Size(min=2, max=30)
    @ApiObjectField(description = "The book's title")
    private String title;

    @Column(name = "pages", nullable = false)
    @Min(0)
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @ApiObjectField(description = "The book's pages number")
    private int pages;

    @Column(name = "authorName", nullable = false)
    @Size(min=2, max=30)
    @ApiObjectField(description = "The book's author")
    private String authorName;


    public Book() {
    }

    public Book(String title, int pages, String authorName) {
        this.title = title;
        this.pages = pages;
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages (int pages) {
        this.pages = pages;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return id.equals(book.id);
    }
}


