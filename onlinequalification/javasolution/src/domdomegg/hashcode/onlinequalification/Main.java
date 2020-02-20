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

        List<Book> books = Arrays.stream(in.readLine().split(" "))
                .map(Integer::parseInt)
                .map(Book::new)
                .collect(Collectors.toList());
        assert books.size() == B;

        List<Library> libraries = parseLibraries(in, books);
        assert books.size() == L;

        solve(D, books, libraries);
    }

    private static void solve(int days, List<Book> books, List<Library> libraries) {

    }

    private static List<Library> parseLibraries(BufferedReader in, List<Book> allBooks) throws IOException {
        List<Library> libraries = new ArrayList<>();
        String line;
        while ((line = in.readLine()) != null) {
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
        int score;

        public Book(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "score=" + score +
                    '}';
        }
    }
}
