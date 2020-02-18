package dto;

import entities.Joke;
import java.util.ArrayList;
import java.util.List;

public class JokeDTO implements Comparable<JokeDTO> {

    private Long id;
    private String value;
    private String reference;
    private String type;

    public JokeDTO(Long id, String value, String reference, String type) {
        this.id = id;
        this.value = value;
        this.reference = reference;
        this.type = type;
    }

    public JokeDTO(Joke joke) {
        this.id = joke.getId();
        this.value = joke.getValue();
        this.reference = joke.getReference();
        this.type = joke.getType();
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getReference() {
        return reference;
    }

    public String getType() {
        return type;
    }

    public static List<JokeDTO> convertList(List<Joke> list) {
        List<JokeDTO> result = new ArrayList<JokeDTO>();

        for (Joke joke : list) {
            result.add(new JokeDTO(joke));
        }

        return result;
    }

    @Override
    public int compareTo(JokeDTO o) {
        if (this.id > o.id) {
            return 1;
        } else if (this.id < o.id) {
            return -1;
        } else {
            return 0;
        }
    }

}
