package domdomegg.hashcode.onlinequalification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;

        line = in.readLine();
        int B = Integer.parseInt(line.split(" ")[0]);
        int L = Integer.parseInt(line.split(" ")[1]);
        int D = Integer.parseInt(line.split(" ")[2]);

        List<Book> books = new ArrayList<>();
        String[] bookScores = in.readLine().split(" ");
        for (int i = 0; i < bookScores.length; i++) {
            books.add(new Book(i, Integer.parseInt(bookScores[i])));
        }
        assert books.size() == B;

        List<Library> libraries = parseLibraries(in, L, books);
        assert books.size() == L;

        solve(D, books, libraries);
    }

    private static int getBestLibraryIndex(int days, List<Library> libraries, Set<Book> scannedBooks) {
        long maxVal = 0;
        int maxIndex = 0;
        for (int i = 0; i < libraries.size(); i++) {
            Library library = libraries.get(i);
            long val = 0;
            if (days >= library.signupProcessLength) {
                long booksWeCanSend = Math.min((long) (days) * library.shippingSpeed, library.books.size());

                if (booksWeCanSend > maxVal) {
                    val = library.books.stream()
                            .filter(book -> !scannedBooks.contains(book))
                            .sorted(Comparator.comparing(Book::getScore).reversed())
                            .limit(booksWeCanSend).count();
                }
            }

            if (maxVal < val) {
                maxIndex = i;
                maxVal = val;
            }
        }

        return maxIndex;
    }

//    private static long getScoreAdded(int days, Library library, Set<Book> scannedBooks) {
//        if (days < library.signupProcessLength) {
//            return 0;
//        }
//
//        long booksWeCanSend = Math.min((long) (days) * library.shippingSpeed, library.books.size());
//        return library.books.stream()
//                .filter(book -> !scannedBooks.contains(book))
//                .sorted(Comparator.comparing(Book::getScore).reversed())
//                .limit(booksWeCanSend).count();
//    }


    private static void solve(int days, List<Book> books, List<Library> libraries) {
        List<Library> librariesToPrint = new ArrayList<>();

        Set<Book> scannedBooks = new HashSet<>();

        while (!libraries.isEmpty()) {
            int libraryIndex = getBestLibraryIndex(days, libraries, scannedBooks);
            Library library = libraries.get(libraryIndex);
            libraries.remove(library);
            if (days > library.signupProcessLength) {
                days -= library.signupProcessLength;
                long booksWeCanSend = Math.min((long) (days) * library.shippingSpeed, library.books.size());
                List<Book> booksBeingScannedAtThisLibrary = library.books.stream()
                        .filter(book -> !scannedBooks.contains(book))
                        .sorted(Comparator.comparing(Book::getScore).reversed())
                        .limit(booksWeCanSend)
                        .collect(Collectors.toList());
                if (booksBeingScannedAtThisLibrary.size() == 0) {
                    days += library.signupProcessLength;
                    continue;
                }
                scannedBooks.addAll(booksBeingScannedAtThisLibrary);
                library.output = library.id + " " + booksBeingScannedAtThisLibrary.size() + "\n" + booksBeingScannedAtThisLibrary.stream().map(book -> "" + book.id).collect(Collectors.joining(" "));
                librariesToPrint.add(library);
            }
        }

        librariesToPrint.sort(Comparator.comparing(Library::getId));
        
        System.out.println(librariesToPrint.size());
        for (Library library : librariesToPrint) System.out.println(library.output);
    }

    private static List<Library> parseLibraries(BufferedReader in, int numberOfLibraries, List<Book> allBooks) throws IOException {
        List<Library> libraries = new ArrayList<>();
        String line;
        for (int i = 0; i < numberOfLibraries; i++) {
            line = in.readLine();
            int signupProcessLength = Integer.parseInt(line.split(" ")[1]);
            int shippingSpeed = Integer.parseInt(line.split(" ")[2]);
            line = in.readLine();
            List<Book> books = Arrays.stream(line.split(" ")).map(Integer::parseInt).map(allBooks::get).collect(Collectors.toList());
            libraries.add(new Library(i, signupProcessLength, shippingSpeed, books));
        }

        return libraries;
    }

    public static Library signupTimeRank(List<Library> libraries) {
        Library library = libraries.get(0);
        Library bestLibrary = libraries.get(0);
        int bestSignupTime = library.signupProcessLength;

        for (int i = 1; i < libraries.size(); i++) {
            library = libraries.get(i);
            int signupTime = library.signupProcessLength;

            if (signupTime < bestSignupTime) {
                bestLibrary = libraries.get(i);
                bestSignupTime = signupTime;
            }
        }

       return bestLibrary;
    }

    public static Library shippingSpeedRank(List<Library> libraries) {
        Library library = libraries.get(0);
        Library bestLibrary = libraries.get(0);
        int bestShippingSpeed = library.shippingSpeed;

        for (int i = 1; i<libraries.size(); i++) {
            library = libraries.get(i);
            int shippingSpeed = library.shippingSpeed;

            if (shippingSpeed > bestShippingSpeed) {
                bestLibrary = libraries.get(i);
                bestShippingSpeed = shippingSpeed;
            }
        }

       return bestLibrary;
    }

    private static class Library {
        int id;
        int signupProcessLength;
        int shippingSpeed;
        List<Book> books;
        String output;

        public Library(int id, int signupProcessLength, int shippingSpeed, List<Book> books) {
            this.id = id;
            this.signupProcessLength = signupProcessLength;
            this.shippingSpeed = shippingSpeed;
            this.books = books;
        }

        public int getId() {
            return id;
        }

        public int getShippingSpeed() {
            return shippingSpeed;
        }

        public int getSignupProcessLength() {
            return signupProcessLength;
        }

        @Override
        public String toString() {
            return "Library{" +
                    "id=" + id +
                    ", signupProcessLength=" + signupProcessLength +
                    ", shippingSpeed=" + shippingSpeed +
                    ", books=" + books +
                    '}';
        }

        public int getSumOfBookScores() {
            int libraryScore = 0;
            for (int i = 0; i < this.books.size(); i++) {
                libraryScore += this.books.get(i).score;
            }
            return libraryScore;
        }
    }

    private static class Book {
        int id;
        int score;

        public Book(int id, int score) {
            this.id = id;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", score=" + score +
                    '}';
        }

        public int getId() {
            return id;
        }

        public int getScore() {
            return score;
        }
    }
}
