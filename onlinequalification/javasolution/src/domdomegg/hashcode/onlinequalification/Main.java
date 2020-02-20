package domdomegg.hashcode.onlinequalification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static void scoreLibraries(Library library) {
        int libraryScore = 0;
        for (int i = 0; i < library.books.size(); i++) {
          libraryScore += library.books.get(i).score;
        }

    }


    private static void orderLibraries(List<Library> libraries) {

        List<Library> sortedLibraries = new ArrayList<>();
        int[] libraryScores = new int[]
        for (int i = 0; i < libraries.size(); i++) {
          libraryScore = scoreLibraries(libraries.get(i));
          int count = 0;
          if(!sortedLibraries.isEmpty()) {
            while(libraryScores.length() > count && libraryScores[count] > libraryScore) {
              count++;
            }
            libraryScores[count] = libraryScore;
            sortedLibraries.add(count, libraries.get(i));
          }

        }


    }


    private static void solve(int days, List<Book> books, List<Library> libraries) {

        Library library = libraries.get(0);
        days -= library.signupProcessLength;
        int booksWeCanSend = Math.min(days * library.shippingSpeed, library.books.size());

        System.out.println("1");
        System.out.println("0 " + booksWeCanSend);
        System.out.println(library.books.stream().limit(booksWeCanSend).map(book -> "" + book.id).collect(Collectors.joining(" ")));

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
            libraries.add(new Library(signupProcessLength, shippingSpeed, books));
        }

        return libraries;
    }

    private static class Library {
        int signupProcessLength;
        int shippingSpeed;
        List<Book> books;

        public Library(int signupProcessLength, int shippingSpeed, List<Book> books) {
            this.signupProcessLength = signupProcessLength;
            this.shippingSpeed = shippingSpeed;
            this.books = books;
        }

        @Override
        public String toString() {
            return "Library{" +
                    "signupProcessLength=" + signupProcessLength +
                    ", shippingSpeed=" + shippingSpeed +
                    ", books=" + books +
                    '}';
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
    }
}
