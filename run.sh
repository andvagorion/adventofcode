src_file=$1
full_path=$(realpath $0)
dir_path=$(dirname $full_path)
cd ${dir_path}/src/main/java
javac -cp . net/stefangaertner/aoc19/${src_file}.java
java -cp ../resources:. net.stefangaertner.aoc19.${src_file}
