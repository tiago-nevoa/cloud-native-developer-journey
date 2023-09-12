/**
 * (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * No part of this publication may be reproduced or transmitted in any form or for any purpose
 * without the express permission of SAP SE. The information contained herein may be changed
 * without prior notice.
 */

package com.sap.cc.movies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MovieTest {

	private static final String MOVIE_LOTR = "The Lord of the Rings: Return of the King";
	private static final String DIR_JACKSON = "Peter Jackson";

	@Test
	void constructorSetsFields() {

		final Movie movie = new Movie(MOVIE_LOTR, DIR_JACKSON);
		assertThat(movie.getTitle()).isEqualTo(MOVIE_LOTR);
		assertThat(movie.getDirector()).isEqualTo(DIR_JACKSON);
	}

	@Test
	void setterSetValues() {

		final Movie movie = new Movie(null, null);
		movie.setId(Long.valueOf(1));
		movie.setDirector(DIR_JACKSON);
		movie.setTitle(MOVIE_LOTR);

		assertThat(movie.getId()).isEqualTo(Long.valueOf(1));
		assertThat(movie.getTitle()).isEqualTo(MOVIE_LOTR);
		assertThat(movie.getDirector()).isEqualTo(DIR_JACKSON);
	}

	@Test
	void equalsReturnsTrueForIdenticalMovies() {

		final Movie movie1 = new Movie(MOVIE_LOTR, DIR_JACKSON);
		final Movie movie2 = new Movie(MOVIE_LOTR, DIR_JACKSON);

		assertThat(movie1.equals(movie2)).isTrue();
		assertThat(movie2.equals(movie1)).isTrue();
	}

	@Test
	void hashCodeReturnsIdenticalValueForIdenticalMovies() {

		final Movie movie1 = new Movie(MOVIE_LOTR, DIR_JACKSON);
		final Movie movie2 = new Movie(MOVIE_LOTR, DIR_JACKSON);

		assertThat(movie1.hashCode()).isEqualTo(movie2.hashCode());
	}
}
