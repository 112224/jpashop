package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book extends Item {
    private String author;
    private String isbn;


    //== 생성메서드==
    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        Book book = new Book();

        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);

        return book;
    }
}
