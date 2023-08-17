/**
 * (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * No part of this publication may be reproduced or transmitted in any form or for any purpose
 * without the express permission of SAP SE. The information contained herein may be changed
 * without prior notice.
 */

package com.sap.cc.movies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryMovieStorageTest {

	private InMemoryMovieStorage movieStorage;

	private Movie theGodfather = new Movie("The Godfather", "Francis Ford Coppola");
	private Movie theThing = new Movie("The Thing", "John Carpenter");

	@BeforeEach
	void setUp() {
		movieStorage = new InMemoryMovieStorage();
		theGodfather = new Movie("The Godfather", "Francis Ford Coppola");
		theThing = new Movie("The Thing", "John Carpenter");
	}

	@Test
	void initalStorageShouldBeEmpty() {
		assertThat(movieStorage.getAll()).isEmpty();
	}

	@Test
	void saveAddsAMovieToTheStorage() {

		movieStorage.save(theGodfather);

		assertThat(movieStorage.getAll()).hasSize(1);
		assertThat(movieStorage.getAll().get(0)).isEqualTo(theGodfather);
	}

	@Test
	void saveAddsAnIdToTheMovie() {

		movieStorage.save(theGodfather);
		assertThat(movieStorage.getAll().get(0).getId()).isNotNull();
	}

	@Test
	void saveMovieWithUknownIdSetsANewIdForTheMovie() {

		theGodfather.setId(Long.valueOf(10));
		movieStorage.save(theGodfather);

		assertThat(movieStorage.getAll().get(0).getId()).isNotEqualTo(10);
	}

	@Test
	void saveKnownMovieUpdatesStorage() {

		movieStorage.save(theGodfather);

		theGodfather.setDirector("Someone Else");
		movieStorage.save(theGodfather);

		assertThat(movieStorage.getAll().get(0).getDirector()).isEqualTo("Someone Else");
	}

	@Test
	void getReturnsTheCorrectMovie() {

		movieStorage.save(theGodfather);

		assertThat(movieStorage.get(theGodfather.getId())).isNotEmpty();
		assertThat(movieStorage.get(theGodfather.getId())).contains(theGodfather);
	}

	@Test
	void getReturnsEmptyResultForNonExistingMovie() {

		assertThat(movieStorage.get(Long.valueOf(1))).isEmpty();
	}

	@Test
	void deleteRemovesMovieFromStorage() {

		movieStorage.save(theGodfather);
		movieStorage.delete(theGodfather.getId());

		assertThat(movieStorage.getAll()).isEmpty();
		assertThat(movieStorage.get(theGodfather.getId())).isEmpty();
	}

	@Test
	void deleteAllRemovesAllMovieFromStorage() {

		movieStorage.save(theGodfather);
		movieStorage.save(theThing);
		movieStorage.deleteAll();

		assertThat(movieStorage.getAll()).isEmpty();
	}
}
