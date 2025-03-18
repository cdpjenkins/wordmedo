fn main() {
    println!("Hello, world!");

    let file_contents = std::fs::read_to_string("/usr/share/dict/words").unwrap();

    let lines = file_contents.lines();


    let possible_words: Vec<&str>  = lines.filter(|l| { l.len() == 5}).collect();

    for word in &possible_words {
        println!("{word}");
    }

    println!("There are {} possible words", possible_words.len());

}
