package com.vladproduction.ch31_ConcurrentHashMap;

import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class LetterCounter {

    private final int subtaskCount;

    public LetterCounter(int subtaskCount) {
        this.subtaskCount = subtaskCount;
    }

    public Map<Character, Integer> count(String input) {
        Map<Character, Integer> accumulator = createAccumulator();
        Stream<Subtask> subtasks = createSubtasks(accumulator, input);
        execute(subtasks);
        return accumulator;
    }

    protected abstract Map<Character, Integer> createAccumulator();

    protected abstract void execute(Stream<Subtask> subtasks);

    private Stream<Subtask> createSubtasks(Map<Character, Integer> accumulator, String input) {
        int subTaskCharCount = findSubTaskCharCount(input);
        return IntStream.range(0, subtaskCount).mapToObj(i -> createSubtask(accumulator, input, subTaskCharCount, i));
    }


    private int findSubTaskCharCount(String input) {
        return (int)Math.ceil((double)input.length() / subtaskCount);
    }

    private static Subtask createSubtask(Map<Character, Integer> accumulator,
                                         String input,
                                         int charCount,
                                         int index) {
        int start = index * charCount;
        int end = Math.min((index + 1) * charCount, input.length());
        return new Subtask(accumulator, input, start, end);
    }


    protected static class Subtask{
        private final Map<Character, Integer> accumulator;
        private final String input;
        private final int start;
        private final int end;

        public Subtask(Map<Character, Integer> accumulator, String input, int start, int end) {
            this.accumulator = accumulator;
            this.input = input;
            this.start = start;
            this.end = end;
        }

        public void execute(){
            IntStream.range(start, end)
                    .map(input::codePointAt)
                    .filter(Character::isLetter)
                    .map(Character::toLowerCase)
                    .forEach(this::accumulate);

        }

        //thread safe example:
        private void accumulate(int codePoint){
            accumulator.merge((char) codePoint, 1, Integer::sum);
        }

        //this method is not thread-safe
        /*private void accumulate(int codePoint){
            final Character character = (char) codePoint;
            final Integer frequency = accumulator.get(character);
            if(frequency != null){
                accumulator.put(character, frequency + 1);
            }else {
                accumulator.put(character, 1);
            }
        }*/

    }


}
