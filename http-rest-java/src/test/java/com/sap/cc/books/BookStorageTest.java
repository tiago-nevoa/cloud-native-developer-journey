package com.sap.cc.books;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookStorageTest {

	private BookStorage bookStorage = new InMemoryBookStorage();

	private final Book SONG_OF_ICE_AND_FIRE = createBookWithAuthor("George R.R. Martin");
	private final Book HITCHHIKERS_GUIDE_TO_THE_GALAXY = createBookWithAuthor("Douglas Adams");

	@BeforeEach
	public void beforeEach() {
		bookStorage.deleteAll();
	}

	@Test
	public void getNonExistingBook_bookNotPresent() {
		Optional<Book> returnedBook = bookStorage.get(1L);
		assertThat(returnedBook.isPresent()).isEqualTo(false);
	}

	@Test
	public void saveOneBook_returnsTheBook() {
		Book returnedBook = bookStorage.save(SONG_OF_ICE_AND_FIRE);

		assertThat(returnedBook.getAuthor()).isEqualTo(SONG_OF_ICE_AND_FIRE.getAuthor());
		assertThat(returnedBook.getId()).isEqualTo(1L);
	}

	@Test
	public void saveTwoBooks_returnsTheSecondBook() {
		bookStorage.save(SONG_OF_ICE_AND_FIRE);

		Book returnedBook = bookStorage.save(HITCHHIKERS_GUIDE_TO_THE_GALAXY);

		assertThat(returnedBook.getAuthor()).isEqualTo(HITCHHIKERS_GUIDE_TO_THE_GALAXY.getAuthor());
		assertThat(returnedBook.getId()).isEqualTo(2L);
	}

	@Test
	public void saveOneBookTryToForceId_ignoreTheForcedId() {
		Book book = SONG_OF_ICE_AND_FIRE;
		book.setId(10L);
		Book returnedBook = bookStorage.save(book);

		assertThat(returnedBook.getId()).isEqualTo(1L);
	}

	@Test
	public void oneBook_getById_returnsTheBook() {
		bookStorage.save(SONG_OF_ICE_AND_FIRE);

		Optional<Book> returnedBook = bookStorage.get(1L);

		assertThat(returnedBook.isPresent()).isEqualTo(true);
		assertThat(returnedBook.get().getId()).isEqualTo(1L);
		assertThat(returnedBook.get().getAuthor()).isEqualTo(SONG_OF_ICE_AND_FIRE.getAuthor());
	}

	@Test
	public void oneBook_updateTitle_returnsUpdatedBook() {
		Book returnedBook = bookStorage.save(SONG_OF_ICE_AND_FIRE);
		assertThat(returnedBook.getId()).isEqualTo(1L);

		final String newAuthor = "Bruce Schneier";
		returnedBook.setAuthor(newAuthor);

		returnedBook = bookStorage.save(returnedBook);

		assertThat(returnedBook.getAuthor()).isEqualTo(newAuthor);
		assertThat(returnedBook.getId()).isEqualTo(1L);
	}

	@Test
	public void noBooks_getAll_returnsEmptyList() {
		List<Book> returnedBooks = bookStorage.getAll();
		assertThat(returnedBooks.size()).isEqualTo(0);
	}

	@Test
	public void oneBook_getAll_returnsTheBook() {
		bookStorage.save(SONG_OF_ICE_AND_FIRE);

		List<Book> returnedBooks = bookStorage.getAll();
		assertThat(returnedBooks.size()).isEqualTo(1);
		assertThat(returnedBooks.iterator().next().getAuthor()).isEqualTo(SONG_OF_ICE_AND_FIRE.getAuthor());
		assertThat(returnedBooks.iterator().next().getId()).isEqualTo(1L);
	}

	@Test
	public void twoBooks_getAll_returnsTwoBooks() {
		bookStorage.save(SONG_OF_ICE_AND_FIRE);
		bookStorage.save(HITCHHIKERS_GUIDE_TO_THE_GALAXY);

		List<Book> returnedBooks = bookStorage.getAll();
		assertThat(returnedBooks.size()).isEqualTo(2);
	}

	@Test
	public void twoBooks_deleteFirst_returnsTheSecond() {
		bookStorage.save(SONG_OF_ICE_AND_FIRE);
		bookStorage.save(HITCHHIKERS_GUIDE_TO_THE_GALAXY);
		bookStorage.delete(1L);

		List<Book> returnedBooks = bookStorage.getAll();
		assertThat(returnedBooks.size()).isEqualTo(1);
		assertThat(returnedBooks.iterator().next().getAuthor()).isEqualTo(HITCHHIKERS_GUIDE_TO_THE_GALAXY.getAuthor());
		assertThat(returnedBooks.iterator().next().getId()).isEqualTo(2L);
	}

	@Test
	public void twoBooks_deleteAll_returnsEmptyList() {
		bookStorage.save(SONG_OF_ICE_AND_FIRE);
		bookStorage.save(HITCHHIKERS_GUIDE_TO_THE_GALAXY);
		bookStorage.deleteAll();

		List<Book> returnedBooks = bookStorage.getAll();
		assertThat(returnedBooks.size()).isEqualTo(0);
	}

	private Book createBookWithAuthor(String title) {
		Book book = new Book();
		book.setAuthor(title);
		return book;
	}

}
