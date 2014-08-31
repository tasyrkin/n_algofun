package test.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps vertexes represented as characters to integers and vice versa.
 */
public class VertexMapper {
    private int nextAvailableId = 0;
    Map<Character, Integer> characterIdIndex = new HashMap<>();
    Map<Integer, Character> idCharacterIndex = new HashMap<>();

    public int mapCharacterToId(final char character) {
        Integer id = characterIdIndex.get(character);
        if (id == null) {
            id = nextAvailableId++;
        }

        characterIdIndex.put(character, id);
        idCharacterIndex.put(id, character);
        return id;
    }

    public int id(final char character) {
        Integer id = characterIdIndex.get(character);
        if (id == null) {
            throw new IllegalArgumentException(String.format("No id found for [%s]", character));
        }

        return id;
    }

    public char character(final int id) {
        Character ch = idCharacterIndex.get(id);
        if (ch == null) {
            throw new IllegalArgumentException(String.format("No character found for [%s]", id));
        }

        return ch;
    }

    public int numberOfVertexes() {
        return nextAvailableId;
    }
}
