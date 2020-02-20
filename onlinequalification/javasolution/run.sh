javac -d . src/domdomegg/hashcode/onlinequalification/Main.java
rm ../output/*
java domdomegg/hashcode/onlinequalification/Main < ../a_example.txt > ../output/a_output.txt
java domdomegg/hashcode/onlinequalification/Main < ../b_read_on.txt > ../output/b_output.txt
java domdomegg/hashcode/onlinequalification/Main < ../c_incunabula.txt > ../output/c_output.txt
java domdomegg/hashcode/onlinequalification/Main < ../d_tough_choices.txt > ../output/d_output.txt
java domdomegg/hashcode/onlinequalification/Main < ../e_so_many_books.txt > ../output/e_output.txt
java domdomegg/hashcode/onlinequalification/Main < ../f_libraries_of_the_world.txt > ../output/f_output.txt
cp src/domdomegg/hashcode/onlinequalification/Main.java ../output