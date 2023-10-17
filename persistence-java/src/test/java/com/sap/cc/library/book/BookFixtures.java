package com.sap.cc.library.book;

public class BookFixtures {
    public static Book cleanCode() {
        return new Book("Clean Code", new Author("Robert C. Martin"));
    }

    public static Book refactoring() {
        return new Book("Refactoring", new Author("Martin Fowler"));
    }

    public static Book domainDrivenDesign() {
        return new Book("Domain-Driven-Design", new Author("Eric Evans"));
    }

    public static Book designPatterns() {
        return new Book("Design Patterns", new Author("Gang of Four"));
    }

    public static Book modernOperatingSystems() {
        return new Book("Modern Operating Systems", new Author("Andrew S. Tanenbaum"));
    }
}
